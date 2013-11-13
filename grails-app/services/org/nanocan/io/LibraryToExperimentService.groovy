package org.nanocan.io

import org.nanocan.errors.LibraryToExperimentException
import org.nanocan.layout.CellLine
import org.nanocan.layout.PlateLayout
import org.nanocan.layout.WellLayout
import org.nanocan.project.Experiment
import org.nanocan.project.Project
import org.nanocan.savanah.library.Library
import org.nanocan.security.Person

class LibraryToExperimentService {

    def plateLayoutService
    static transactional = true

    def public create(
            String title,
            String projectTitle,
            String libraryName,
            int nrReplicates,
            int lowReplicateNr,
            String defaultCellLine,
            String barcodePattern,
            Person createdBy
    )  throws LibraryToExperimentException
    {
        println("Starting creation.")

        // ---- START OF VALIDATION
        if(title.isEmpty()){
            throw new LibraryToExperimentException("The title cannot be empty.")
        }

        if(projectTitle.isEmpty()){
            throw new LibraryToExperimentException("A project must be selected.")
        }

        if(libraryName.isEmpty()){
            throw new LibraryToExperimentException("A library must be selected.")
        }

        if(nrReplicates == null || nrReplicates <= 0){
            throw new LibraryToExperimentException("A library must be selected.")
        }

        if(lowReplicateNr == null || lowReplicateNr <= 0){
            throw new LibraryToExperimentException("A library must be selected.")
        }

        if(defaultCellLine.isEmpty()){
            throw new LibraryToExperimentException("A default cell-line must be selected.")
        }

        if(barcodePattern.isEmpty()){
            throw new LibraryToExperimentException("A barcode pattern must be present.")
        }

        if(!barcodePattern.contains("\\P")){
            throw new LibraryToExperimentException("The barcode pattern must contain the sequence \"\\\\P\".")
        }

        if(!barcodePattern.contains("\\\\R")){
            throw new LibraryToExperimentException("The barcode pattern must contain the sequence \"\\\\R\".")
        }

        def project = Project.findByProjectTitle(projectTitle)
        if(project == null)
        {
            throw new LibraryToExperimentException(String.format("Project \"%s\" was not found", projectTitle))
        }

        def library = Library.findByName(libraryName)
        if(library == null)
        {
            throw new LibraryToExperimentException(String.format("Library \"%s\" was not found", libraryName))
        }

        def cellLine = CellLine.findByName(defaultCellLine)
        if(cellLine == null)
        {
            throw new LibraryToExperimentException(String.format("Cell-line \"%s\" was not found", defaultCellLine))
        }
        // ---- END OF VALIDATION

        // Use transaction for ensuring only correct experiments are saved
        Experiment.withNewTransaction {

            Experiment newExperiment = new Experiment()
            newExperiment.dateCreated = new Date()
            newExperiment.lastUpdated = new Date()
            newExperiment.createdBy = createdBy
            newExperiment.lastUpdatedBy = createdBy
            newExperiment.title = title
            newExperiment.project = project
            newExperiment.description = "Test experiment."

            // TODO: You need to get this from the user via the gsp (via the params object in the controller).
            newExperiment.firstDayOfTheExperiment = new Date()

            library.plates.each {plate ->

                for(int replicateNr = lowReplicateNr; replicateNr < lowReplicateNr+nrReplicates; replicateNr++){

                    PlateLayout plateLayout = new PlateLayout()
                    plateLayout.dateCreated = new Date()
                    plateLayout.lastUpdated = new Date()
                    plateLayout.createdBy = createdBy
                    plateLayout.lastUpdatedBy = createdBy


                    plateLayout.format = plate.format
                    plateLayout.name = barcodePattern
                            .replace("\\\\P", String.format("%02d", plate.plateIndex))
                            .replace("\\\\R", String.format("%02d", replicateNr))
                    plateLayout.save(failOnError: true)

                    newExperiment.addToPlateLayouts(plateLayout)
                    newExperiment.save(failOnError: true)

                    // TODO: I think we have a misunderstanding here. You need to be aware of the fact that you need 96
                    // or even 384 or even 1586 for each plateLayout. Luckily there is already a service class doing this.
                    // Have a look at PlateLayoutService in hstbackend. Modify the service to provide two methods
                    // One where you specify the cell-line and one where this is null.
                    // The code for adding the wells looks overly complicated. This is because there are two strategies
                    // either normal grails / hibernate by creating WellLayout with plateLayout set.
                    // or direct groovySQL which is lightning fast since there is no hibernate overhead
                    // hibernate can be really slow when you want to batch create stuff, look at
                    // http://naleid.com/blog/2009/10/01/batch-import-performance-with-grails-and-mysql/
                    // if you want to know more

                    plateLayoutService.createWellLayouts(plateLayout) //plateLayoutService throws a nullpointer exception here. I didn't  have enough time to figure out why, sorry!

                    plateLayout.save(failOnError: true)
                }
            }

            newExperiment.save(failOnError: true)

            project.addToExperiments(newExperiment)
            project.save(failOnError: true)
        }
        println("Ended creation.")
    }
}
