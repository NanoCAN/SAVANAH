package org.nanocan.savanah.plates

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON
import org.nanocan.dart.RawData
import org.nanocan.savanah.library.Library
import grails.plugins.springsecurity.Secured
import org.nanocan.dart.Labware

@Secured(['ROLE_USER'])
class PlateController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def importReadoutData(){

    }

    def platesAsJSON() {

        def parentPlates

        def prefixLength = params.id.size()

        if (params.int("id") == 0)
        {
            parentPlates = Plate.findAllWhere(parentPlate: null, library: params.library?Library.get(params.int("library")):null)
            prefixLength = 0
        }

        else {
            parentPlates = Plate.findAllByParentPlate(Plate.get(params.id))
        }

        def jsonPlates = parentPlates.collect{
            [
                    "data" : it.id.substring(prefixLength),
                    "attr" : [ "id" : it.id , "rel":"libPlates", "nodeType" : "plate"],
                    "state" : "closed"
            ]
        }

        render jsonPlates as JSON
    }

    def getDataOrigins(){

        def labwares = Labware.findAllByBarcode(params.id)
        def plateInstance = Plate.findById(params.id)

        render (template: "data_origins", model:[plateInstance: plateInstance, labwares: labwares, barcode: params.id])
    }

    def getRawValues(){
        println params

        def rawData = RawData.findAllByBarcodeAndRun("E1S1M1D1", 522).sort{ a, b -> a.wellNum <=> b.wellNum}

        render(template: "heatmap", model: [rawData: rawData, plateInstance: Plate.get(params.id)])
    }

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [plateInstanceList: Plate.list(params), plateInstanceTotal: Plate.count()]
    }

    def create() {
        [plateInstance: new Plate(params)]
    }

    def save() {
        def plateInstance = new Plate(params)
        if (!plateInstance.save(flush: true)) {
            render(view: "create", model: [plateInstance: plateInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'plate.label', default: 'Plate'), plateInstance.id])
        redirect(action: "show", id: plateInstance.id)
    }

    def show() {
        def plateInstance = Plate.get(params.id)
        if (!plateInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'plate.label', default: 'Plate'), params.id])
            redirect(action: "list")
            return
        }

        [plateInstance: plateInstance]
    }

    def edit() {
        def plateInstance = Plate.get(params.id)
        if (!plateInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'plate.label', default: 'Plate'), params.id])
            redirect(action: "list")
            return
        }

        [plateInstance: plateInstance]
    }

    def update() {
        def plateInstance = Plate.get(params.id)
        if (!plateInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'plate.label', default: 'Plate'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (plateInstance.version > version) {
                plateInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'plate.label', default: 'Plate')] as Object[],
                          "Another user has updated this Plate while you were editing")
                render(view: "edit", model: [plateInstance: plateInstance])
                return
            }
        }

        plateInstance.properties = params

        if (!plateInstance.save(flush: true)) {
            render(view: "edit", model: [plateInstance: plateInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'plate.label', default: 'Plate'), plateInstance.id])
        redirect(action: "show", id: plateInstance.id)
    }

    def delete() {
        def plateInstance = Plate.get(params.id)
        if (!plateInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'plate.label', default: 'Plate'), params.id])
            redirect(action: "list")
            return
        }

        try {
            plateInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'plate.label', default: 'Plate'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'plate.label', default: 'Plate'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
