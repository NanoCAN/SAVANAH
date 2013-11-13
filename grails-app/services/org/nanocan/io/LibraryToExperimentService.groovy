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

            // TODO: What should this value be?
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


                    WellLayout wellLayout = new WellLayout()
                    wellLayout.plateLayout = plateLayout
                    wellLayout.cellLine = cellLine
                    wellLayout.save(failOnError: true)

                    // TODO: I'm unable to see the wells on the Project, why?
                    plateLayout.addToWells(wellLayout)
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
