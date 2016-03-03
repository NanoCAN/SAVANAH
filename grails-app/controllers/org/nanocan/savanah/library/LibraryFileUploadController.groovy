package org.nanocan.savanah.library

import grails.plugins.springsecurity.Secured
import org.springframework.web.multipart.MultipartHttpServletRequest

@Secured(['ROLE_ADMIN'])
class LibraryFileUploadController {
    def springSecurityService
    def libraryUploadService

    def index(){
        [libraryInstance: new Library()]
    }

    def upload()
    {
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
        if(libraryName == null || libraryName.isEmpty()){
            flash.error = 'Title cannot be empty.'
            render(view: 'index', model: [libraryInstance: lib])
            return
        }

        // Read file into string
        InputStream stream = dataFile.getInputStream()
        String text = stream.getText()

        def libraryInstance = libraryUploadService.uploadLibraryFile(lib, springSecurityService.currentUser, text)

        if (!libraryInstance.save(flush: true)) {
            render(view: "index", model: [libraryInstance: libraryInstance])
            return
        }

        flash.message = 'Library was saved successfully.'

        redirect(controller: "library", action: "browser" )
    }
}
