package org.nanocan.io

import org.nanocan.layout.PlateLayout
import org.nanocan.layout.Sample
import org.nanocan.layout.WellLayout
import org.nanocan.plates.Plate
import org.nanocan.project.Experiment
import org.nanocan.savanah.library.DilutedLibraryPlate
import org.nanocan.savanah.library.Library

class LibraryToExperimentService {

    static transactional = true

    def createExperiment(Experiment experiment,
                         Library library,
                         def selectedDilutedLibraries,
                         def plateType,
                         def controls,
                         def cellLine,
                         def inducer,
                         def numberOfCellsSeeded,
                         def treatment,
                         def currentUser
                         ){

        /* Save experiment */
        experiment.createdBy = currentUser
        experiment.lastUpdatedBy = currentUser
        if(experiment.hasErrors()) throw new Exception(experiment.errors.toString())

        experiment.save()

        /* Create one new plate layout for each library and one plate for
         each of the diluted library plates (daughter = assay plates) */
        library.plates.sort{it.plateIndex}.each{ libraryPlate ->
            def plateLayoutTitle = experiment.title + " Plate " + libraryPlate.plateIndex

            //in case plate layout title exists keep adding (new)
            while(PlateLayout.findByName(plateLayoutTitle)){
                plateLayoutTitle += "(new)"
            }
            def newPlateLayout = new PlateLayout(name: plateLayoutTitle, format: libraryPlate.format)
            newPlateLayout.createdBy = currentUser
            newPlateLayout.lastUpdatedBy = currentUser
            newPlateLayout.save()
            if(newPlateLayout.hasErrors()) throw new Exception(newPlateLayout.errors.toString())
            experiment.addToPlateLayouts(newPlateLayout).save()

            /* next we add a replicate plate for each daughter plate */
            selectedDilutedLibraries.eachWithIndex{ dilutedLibrary, replicate ->

                def dilutedLibraryPlate = DilutedLibraryPlate.findByDilutedLibraryAndLibraryPlate(dilutedLibrary, libraryPlate)
                if(Plate.findByBarcode(dilutedLibraryPlate.barcode)){
                    throw new Exception("An assay plate with barcode ${dilutedLibraryPlate.barcode} already exists. " +
                            "Delete assay plates with barcodes used in this experiment before you try again or choose" +
                            "different barcodes for the diluted library.")
                }
                def newPlate = new Plate()
                newPlate.plateType = plateType
                newPlate.controlPlate = false
                newPlate.format = newPlateLayout.format
                newPlate.name = plateLayoutTitle + " Replicate " + (replicate + 1)
                newPlate.barcode = dilutedLibraryPlate.barcode
                newPlate.replicate = replicate + 1
                newPlate.experiment = experiment
                newPlateLayout.addToPlates(newPlate)

                dilutedLibraryPlate.assayPlate = newPlate
                if(dilutedLibraryPlate.hasErrors()) throw new Exception(dilutedLibraryPlate.errors.toString())

                dilutedLibraryPlate.save()
            }

            /* add wells based on library entities to plate layout
                Take control values from settings. */
            libraryPlate.entries.each{ entry ->
                Sample sample
                if(entry.controlWell == true)
                {
                    def ctrl = controls.find{ it.row == entry.row && it.col == entry.col}
                    if(ctrl) sample = Sample.get(ctrl.sampleId)
                }
                else sample = entry.sample
                def newWell = new WellLayout(
                          col: entry.col,
                          row: entry.row,
                          numberOfCellsSeeded: numberOfCellsSeeded,
                          cellLine: cellLine,
                          inducer: inducer,
                          treatment: treatment,
                          sample: sample,

                )
                newPlateLayout.addToWells(newWell)
            }
            if(newPlateLayout.hasErrors()) throw new Exception(newPlateLayout.errors.toString())
            newPlateLayout.save()
        }

        /* mark diluted library sets as used */
        selectedDilutedLibraries.each { dilutedLibrary ->
            dilutedLibrary.usedAsAssayPlate = true
            dilutedLibrary.save()
        }
        return(experiment)
    }
}
