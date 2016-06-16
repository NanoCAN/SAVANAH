package org.nanocan.savanah.library

import grails.plugins.springsecurity.Secured
import org.nanocan.errors.LibraryUploadException
import org.springframework.web.multipart.MultipartHttpServletRequest

@Secured(['ROLE_ADMIN'])
class LibraryFileUploadController {
    def springSecurityService
    def libraryUploadService

    def index(){
        [libraryInstance: new Library()]
    }

    def create() {
        // Initialize new library
        Library lib = new Library()

        lib.name = params.name
        lib.type = params.type
        lib.vendor = params.vendor
        lib.catalogNr = params.catalogNr
        lib.sampleType = params.sampleType
        lib.accessionType = params.accessionType
        lib.plateFormat = params.plateFormat
        lib.plates = new HashSet<LibraryPlate>()
        lib.createdBy = springSecurityService.currentUser
        lib.lastUpdatedBy = springSecurityService.currentUser

        // Responses for UI
        flash.error = ''
        flash.okay = ''

        // Have to convert request into file request
        MultipartHttpServletRequest r = (MultipartHttpServletRequest) request

        // Grab file and check it exists
        def dataFile = r.getFile("dataFile")
        if (dataFile.empty) {
            flash.error = 'A file has to be chosen.'
            render(view: 'index', model: [libraryInstance: lib])
            return
        }

        String libraryName = params.get('name') as String
        if (libraryName == null || libraryName.isEmpty()) {
            flash.error = 'Title cannot be empty.'
            render(view: 'index', model: [libraryInstance: lib])
            return
        }

        if(Library.findByName(libraryName)){
            flash.error = 'Title already in use. Choose another one.'
            render(view: 'index', model: [libraryInstance: lib])
            return
        }

        if(!lib.validate()){
            flash.error = "Malformed library file entry with missing values in at least one row."
            render(view: "index", model: [libraryInstance: lib])
            return
        }

        // Read file into string
        InputStream stream = dataFile.getInputStream()
        String text = stream.getText()

        // Save info for later processing
        oneTimeData(libraryName){
            libraryInstance = lib
            libraryContent = text
        }
        [libraryInstance: lib]
    }

    def upload(){
        def libraryOneTimeData = getOneTimeData(params.name)
        def libraryInstance = libraryOneTimeData.libraryInstance
        String text = libraryOneTimeData.libraryContent

        // Responses for UI
        flash.error = ''
        flash.okay = ''

        try{
            libraryInstance = libraryUploadService.uploadLibraryFile(libraryInstance, springSecurityService.currentUser, text)
        }catch(LibraryUploadException e){
            flash.error = e.message.toString()
            render(view: "index", model: [libraryInstance: libraryInstance, invalidLibPlate: e.invalidLibPlate])
            return
        }catch(ArrayIndexOutOfBoundsException aiobe){
            flash.error = "Malformed library file entry with missing values in at least one row."
            render(view: "index", model: [libraryInstance: libraryInstance])
            return
        }

        if (libraryInstance.hasErrors()) {
            render(view: "index", model: [libraryInstance: libraryInstance])
            return
        }

        //flash.message = 'Library was saved successfully.'
        render "Library ${libraryInstance.name} created successfully"

        //redirect(controller: "library", action: "browser" )
    }
}
