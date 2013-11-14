package org.nanocan.savanah.library

import grails.plugins.springsecurity.Secured
import org.nanocan.io.LibraryUploadService
import org.nanocan.layout.Identifier
import org.nanocan.layout.Sample
import org.nanocan.security.Person
import org.springframework.web.multipart.MultipartHttpServletRequest


@Secured(['ROLE_USER'])
class LibraryFileUploadController {
    def springSecurityService

    def index() {}

    def upload()
    {

        // Responses for UI
        flash.error = ''
        flash.okay = ''

        // Have to convert request into file request
        MultipartHttpServletRequest r = (MultipartHttpServletRequest) request

        // Grab file and check it exists
        def dataFile = r.getFile("dataFile")
        if (dataFile.empty) {
            flash.error = 'A file has to be chosen.'
            render(view: 'index')
            return
        }

        String libraryName = params.get('libraryName') as String
        if(libraryName == null || libraryName.isEmpty()){
            flash.error = 'Title cannot be empty.'
            render(view: 'index')
            return
        }


        // Read file into string
        InputStream stream = dataFile.getInputStream()
        String text = stream.getText()


        def lus = new LibraryUploadService()
        lus.uploadLibraryFile(Person.findByUsername("mdissing"), libraryName, text, "microRNA inhibitor", "96-well", "miRNA inhibitor")


        if(flash.error == ''){
            flash.okay = 'Everything was saved to library.'
        }
        render(view: 'index')
        return

    }
}
