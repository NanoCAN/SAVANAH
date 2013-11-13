package org.nanocan.savanah.project

import grails.plugins.springsecurity.Secured
import org.nanocan.errors.LibraryToExperimentException
import org.nanocan.io.LibraryToExperimentService
import org.nanocan.project.Project
import org.nanocan.security.Person

@Secured(['ROLE_USER'])
class LibraryToExperimentController {
    def springSecurityService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {

        // Checking for POST
        if(request.getMethod() == 'POST'){

            // Preparing input for experiment creation service
            def title = params.get('title') as String
            def projectTitle = params.get('selectedProject')  as String
            def libraryName = params.get('selectedLibrary')  as String
            def nrReplicates = params.int('nrReplicates')
            def lowReplicateNr = params.int('lowReplicateNr')
            def defaultCellLine = params.get('defaultCellLine') as String
            def barcodePattern = params.get('barcodePattern') as String

            // Try to create the experiment, if input is wrong, LibraryToExperimentException will be cast.
            try{
                def ltes = new LibraryToExperimentService()
                ltes.create(title, projectTitle, libraryName, nrReplicates, lowReplicateNr, defaultCellLine, barcodePattern, (Person) springSecurityService.getCurrentUser())
                flash.message = String.format("The experiment \"%s\" was created successfully.", title)
            }catch(LibraryToExperimentException e){
                 flash.error = e.message
            }
            return
        }

        if(Project.all.size() == 0){
            def me = Person.findByUsername("mdissing")

            Project p = new Project(
                    projectTitle: "FirstTest",
                    projectDescription: "Bla blah blah",
                    createdBy: me,
                    lastUpdatedBy: me,
                    dateCreated: new Date(),
                    lastUpdated: new Date()
            )
            p.save(failOnError: true)

            Project p2 = new Project(
                    projectTitle: "SecondTest",
                    projectDescription: "Bla blah blah2",
                    createdBy: me,
                    lastUpdatedBy: me,
                    dateCreated: new Date(),
                    lastUpdated: new Date()
            )
            p2.save(failOnError: true)
        }
    }


}
