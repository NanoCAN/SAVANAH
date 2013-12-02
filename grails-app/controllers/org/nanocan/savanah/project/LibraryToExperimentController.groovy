package org.nanocan.savanah.project

import grails.plugins.springsecurity.Secured
import org.nanocan.errors.LibraryToExperimentException
import org.nanocan.project.Experiment
import org.nanocan.project.Project
import org.nanocan.savanah.plates.PlateType
import org.nanocan.security.Person

@Secured(['ROLE_USER'])
class LibraryToExperimentController {
    def springSecurityService
    def libraryToExperimentService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {

        if(PlateType.findAll().size() == 0){
            def newPlateType = new PlateType()
            newPlateType.name = "testplatetype1"
            newPlateType.ultraLowAdhesion = true
            newPlateType.vendor = "Martin shop"
            newPlateType.wellShape = "round-bottom"
            newPlateType.save(failOnError: true)
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

    def createExperiment(){

        def okayString = "<div class=\"message\" role=\"status\">%STR%</div>"
        def errorString = "<div class=\"errors\" role=\"error\">%STR%</div>"

            // Preparing input for experiment creation service
            def title = params.get('title') as String
            def projectTitle = params.get('selectedProject')  as String
            def libraryName = params.get('selectedLibrary')  as String
            def nrReplicates = params.int('nrReplicates')
            def lowReplicateNr = params.int('lowReplicateNr')
            def defaultCellLine = params.get('defaultCellLine') as String
            def barcodePattern = params.get('barcodePattern') as String
            def startDateText = params.get('startDate') as String
            def plateTypeName = params.get('plateType') as String
            def plateLayoutName = params.get('plateLayoutName') as String
            def throwErrorIfLayoutExists = (params.get('plateLayoutName') as String) != ''

            def plateType = PlateType.findByName(plateTypeName)
            if(plateTypeName.isEmpty() || plateType == null){
                render errorString.replace("%STR%", "A valid plate type must be chosen.")
                return
            }

            if(startDateText == null || startDateText.isEmpty()){
                render errorString.replace("%STR%", "Experiment start date must be chosen.")
                return
            }

            def startDate = new Date(startDateText)
            // Try to create the experiment, if input is wrong, LibraryToExperimentException will be cast.

            Experiment.withTransaction {   status  ->
                try{
                    libraryToExperimentService.create(
                            title,
                            projectTitle,
                            libraryName,
                            nrReplicates,
                            lowReplicateNr,
                            defaultCellLine,
                            barcodePattern,
                            (Person) springSecurityService.getCurrentUser(),
                            startDate,
                            plateType,
                            plateLayoutName,
                            throwErrorIfLayoutExists
                    )
                    render okayString.replace("%STR%", String.format("The experiment \"%s\" was created successfully.", title))
                    return
                }catch(Exception e){
                    //If an error occurs, rollback all changes
                    status.setRollbackOnly()
                    render errorString.replace("%STR%", e.message)
                    return
                }
            }

    }

}
