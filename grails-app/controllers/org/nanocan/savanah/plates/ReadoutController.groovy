package org.nanocan.savanah.plates

import org.springframework.dao.DataIntegrityViolationException
import org.apache.commons.io.FilenameUtils
import org.springframework.security.access.annotation.Secured
import org.nanocan.file.ResultFileConfig
import org.springframework.web.multipart.MultipartHttpServletRequest

@Secured(['ROLE_USER'])
class ReadoutController {

    def fileUploadService
    def fileImportService
    def readoutImportService
    def progressService
    def unzipService
    def xlsxToReadoutService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [readoutInstanceList: Readout.list(params), readoutInstanceTotal: Readout.count()]
    }

    def create() {
        [readoutInstance: new Readout(params)]
    }

    def save() {
        def readoutInstance = new Readout(params)

        //deal with file uploads
        readoutInstance = fileUploadService.dealWithFileUploads(request, readoutInstance)

        if (!readoutInstance.save(flush: true)) {
            render(view: "create", model: [readoutInstance: readoutInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'readout.label', default: 'Readout'), readoutInstance.id])
        redirect(action: "addReadoutData", id: readoutInstance.id)
    }

    def show() {
        def readoutInstance = Readout.get(params.id)
        if (!readoutInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'readout.label', default: 'Readout'), params.id])
            redirect(action: "list")
            return
        }

        [readoutInstance: readoutInstance]
    }

    def edit() {
        def readoutInstance = Readout.get(params.id)
        if (!readoutInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'readout.label', default: 'Readout'), params.id])
            redirect(action: "list")
            return
        }

        [readoutInstance: readoutInstance]
    }

    def update() {
        def readoutInstance = Readout.get(params.id)
        if (!readoutInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'readout.label', default: 'Readout'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (readoutInstance.version > version) {
                readoutInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'readout.label', default: 'Readout')] as Object[],
                        "Another user has updated this Readout while you were editing")
                render(view: "edit", model: [readoutInstance: readoutInstance])
                return
            }
        }

        readoutInstance.properties = params
        //deal with file uploads
        readoutInstance = fileUploadService.dealWithFileUploads(request, readoutInstance)

        if (!readoutInstance.save(flush: true)) {
            render(view: "edit", model: [readoutInstance: readoutInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'readout.label', default: 'Readout'), readoutInstance.id])
        redirect(action: "show", id: readoutInstance.id)
    }

    def delete() {
        def readoutInstance = Readout.get(params.id)
        if (!readoutInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'readout.label', default: 'Readout'), params.id])
            redirect(action: "list")
            return
        }

        try {
            readoutInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'readout.label', default: 'Readout'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'readout.label', default: 'Readout'), params.id])
            redirect(action: "show", id: params.id)
        }
    }

    /* Add and delete spots, import from result file */
    def addReadoutData() {
        def readoutInstance = Readout.get(params.id)

        def index = 1

        def fileEnding = FilenameUtils.getExtension(readoutInstance.resultFile.filePath)

        def sheets = fileImportService.getSheets(readoutInstance).collect{
            [index: index++, name: it]
        }

        [readoutInstance: readoutInstance, configs: ResultFileConfig.list(), fileEnding: fileEnding, sheets: sheets]
    }

    final ArrayList<String> readoutProperties = ["wellPosition", "row", "column", "measuredValue"]


    def readInputFile(){
        def readoutInstance = Readout.get(params.id)

        //read sheet / file
        def sheetContent
        try{
            sheetContent = fileImportService.importFromFile(readoutInstance.resultFile.filePath, params.int("sheet")?:0, params.int("minColRead")?:1)
        }catch(IOException e)
        {
            render "<div class='message'>Could not read file</div>"
            return
        }

        //convert CSV2 to CSV:
        if (params.csvType == "CSV2") sheetContent = fileImportService.convertCSV2(sheetContent)

        //convert custom CSV format to standard CSV:
        else if (params.csvType == "custom") sheetContent = fileImportService.convertCustomCSV(sheetContent, params.columnSeparator, params.decimalSeparator, params.thousandSeparator)

        //read config
        def resultFileCfg

        if(!params.config.equals("")){
            resultFileCfg = ResultFileConfig.get(params.config)
            readoutInstance.lastConfig = resultFileCfg
            readoutInstance.save()
        }
        def skipLines = resultFileCfg?.skipLines?:0
        if (params.skipLines == "on") skipLines = params.int("howManyLines")

        def header

        try{
            header = fileImportService.extractHeader(sheetContent, skipLines)
        }catch(NoSuchElementException e) {
            render "<div class='message'>Could not read header!</div>"
            return
        }

        //keeping content for later
        flash.totalSkipLines = ++skipLines
        flash.sheetContent = sheetContent

        //matching properties

        def matchingMap = readoutImportService.createMatchingMap(resultFileCfg, header)

        render view: "assignFields", model: [progressId: "pId${params.id}", header: header, readoutProperties: readoutProperties, matchingMap: matchingMap]
    }

    def processResultFile() {

        def columnMap = [:]

        params.keySet().each{
            if(it.toString().startsWith("column") && it.toString() != "columnSeparator") columnMap.put(params.get(it), Integer.parseInt(it.toString().split('_')[1]))
        }

        println columnMap

        def readoutInstance = Readout.get(params.id)

        def progressId = "pId" + params.id

        progressService.setProgressBarValue(progressId, 0)

        if (readoutInstance.wells.size() > 0) {
            render "this readout instance already contains readout data. please delete them first!"
            progressService.setProgressBarValue(progressId, 100)
            return
        }

        def result = readoutImportService.processResultFile(readoutInstance, flash.sheetContent, columnMap, flash.totalSkipLines, progressId)

        progressService.setProgressBarValue(progressId, 100)

        if(!(result instanceof Readout)) render result

        else render "${readoutInstance.wells.size()} readout values have been added to the database and linked to this readout."
    }


    def createFromZippedFile(){
        if(request.getMethod() == 'POST'){
            // Have to convert request into file request
            MultipartHttpServletRequest r = (MultipartHttpServletRequest) request

            // Grab file and check it exists
            def dataFile = r.getFile("zippedFile")
            if (dataFile.empty) {
                flash.error = 'A file has to be chosen.'
                render(view: 'createFromZippedFile')
                return
            }


             // Add transactional property
             Readout.withTransaction {status  ->
                try{
                    def unpacked = unzipService.Unpack(dataFile.getInputStream())
                    unpacked.keySet().each {key ->
                        println("-\nParsing " + key)
                        xlsxToReadoutService.parseToReadout(unpacked.get(key), key)
                    }
                }catch(Exception e){
                    //If an error occurs, rollback all changes
                    status.setRollbackOnly()
                    flash.error = 'An error occured:<br/>'+e.message
                }

             }

            if(flash.error == ''){
                flash.okay = 'The readout was parsed successfully.'
            }
        }


    }
}
