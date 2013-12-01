package org.nanocan.io

import org.nanocan.errors.LibraryToExperimentException
import org.nanocan.layout.CellLine
import org.nanocan.layout.PlateLayout
import org.nanocan.project.Experiment
import org.nanocan.project.Project
import org.nanocan.savanah.library.Library
import org.nanocan.savanah.plates.Plate
import org.nanocan.savanah.plates.PlateType
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
            Person createdBy,
            Date startDate,
            PlateType plateType
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
        //Experiment.withNewTransaction {

            Experiment newExperiment = new Experiment()
            newExperiment.dateCreated = new Date()
            newExperiment.lastUpdated = new Date()
            newExperiment.createdBy = createdBy
            newExperiment.lastUpdatedBy = createdBy
            newExperiment.title = title
            newExperiment.project = project
            newExperiment.description = "Test experiment."
            newExperiment.firstDayOfTheExperiment = startDate

            library.plates.each { libraryPlate ->

                PlateLayout plateLayout = new PlateLayout()
                plateLayout.dateCreated = new Date()
                plateLayout.lastUpdated = new Date()
                plateLayout.createdBy = createdBy
                plateLayout.lastUpdatedBy = createdBy
                plateLayout.format = libraryPlate.format
                plateLayout.name = barcodePattern
                        .replace("\\\\P", String.format("%02d", libraryPlate.plateIndex))
                        .replace("\\\\R", "X")

                //TODO: Try to make it a nonblocking call - look at the progressbar example
                plateLayoutService.createWellLayouts(plateLayout)

                plateLayout.save(failOnError: true)
                newExperiment.addToPlateLayouts(plateLayout)
                newExperiment.save(failOnError: true)


                for(int replicateNr = lowReplicateNr; replicateNr < lowReplicateNr+nrReplicates; replicateNr++){


                    Plate plate = new Plate()
                    plate.plateLayout = plateLayout
                    plate.barcode = barcodePattern
                            .replace("\\\\P", String.format("%02d", libraryPlate.plateIndex))
                            .replace("\\\\R", String.format("%02d", replicateNr))
                    plate.replicate = replicateNr
                    plate.name = plate.barcode
                    plate.format = libraryPlate.format
                    plate.experiment = newExperiment
                    plate.plateType = plateType
                    plate.save(failOnError: true)
                }
            }

            newExperiment.save(failOnError: true)

            project.addToExperiments(newExperiment)
            project.save(failOnError: true)
        //}
        println("Ended creation.")
    }
}
