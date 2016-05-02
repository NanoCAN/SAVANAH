package org.nanocan.savanah.project

import grails.plugins.springsecurity.Secured
import org.nanocan.layout.NumberOfCellsSeeded
import org.nanocan.plates.PlateType
import org.nanocan.project.Experiment
import org.nanocan.project.Project
import org.nanocan.savanah.library.DilutedLibrary
import org.nanocan.savanah.library.DilutedLibraryPlate
import org.nanocan.savanah.library.Entry
import org.nanocan.savanah.library.Library
import org.nanocan.security.Person
import org.nanocan.layout.*

@Secured(['ROLE_USER'])
class LibraryToExperimentController {
    def springSecurityService
    def libraryToExperimentService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index() {
        redirect(action: "libraryToExperiment")
    }

    def libraryToExperimentFlow = {

        selectLibrary {
            on("continue").to("buildExperiment")
        }

        buildExperiment{
            action{

                def experimentInstance = new Experiment(params)
                experimentInstance.createdBy = springSecurityService.currentUser
                experimentInstance.lastUpdatedBy = springSecurityService.currentUser

                flow.experimentInstance = experimentInstance

                if(!experimentInstance.validate()) return error()

                flow.library = Library.get(params["library.id"])

                if(flow.library.plateFormat == "96-well"){
                    flow.numOfCols = 12
                    flow.numOfRows = 8
                }
                else if(flow.library.plateFormat == "384-well"){
                    flow.numOfCols = 24
                    flow.numOfRows = 16
                }
                else if(flow.library.plateFormat == "1536-well"){
                    flow.numOfCols = 48
                    flow.numOfRows = 32
                }
                else{
                    throw new Exception("Plate format not supported")
                }

                def dilutedLibraries = DilutedLibrary.findAllByTypeAndLibrary("Daughter", flow.library)
                flow.dilutedLibraries = dilutedLibraries.findAll{!it.usedAsAssayPlate}
            }
            on("success").to "selectDaughterPlateSets"
            on("error").to "selectLibrary"
        }

        selectDaughterPlateSets {
            on("back").to("selectLibrary")
            on("continue").to("fetchDilutedLibraries")
        }

        fetchDilutedLibraries{
            action{
                def selectedDilutedLibraries = params.list("dilutedLibraries.id")?.collect{DilutedLibrary.get(it as Long)}

                if(selectedDilutedLibraries.any{ it.usedAsAssayPlate }){
                    def error = "Selected daughter plates were already used as assay plates."
                    flash.error = error
                    throw new Exception(error)
                }
                flow.selectedDilutedLibraries = selectedDilutedLibraries
            }
            on("success").to("selectDefaults")
            on(Exception).to("showError")
        }

        selectDefaults {
            on("back").to("selectDaughterPlateSets")
            on("continue"){
                flow.cellLine = CellLine.get(params.int("cellLine.id"))
                flow.inducer = Inducer.get(params.int("inducer.id"))
                flow.treatment = Treatment.get(params.int("treatment.id"))
                flow.numberOfCellsSeeded = NumberOfCellsSeeded.get(params.int("numberOfCellsSeeded.id"))
                flow.plateType = PlateType.get(params.int("plateType.id"))

            }.to("selectControls")
        }

        selectControls {
            on("back").to("selectDefaults")
            on("continue"){
                int wellId = 1
                def ctrlArray = []
                for(row in 1..flow.numOfRows){
                    for(col in 1..flow.numOfCols){
                        if(params[wellId.toString()]) {
                            ctrlArray << [row: row, col: col, sampleId: params[wellId.toString()]]
                        }
                        wellId++
                    }
                }
                flow.controls = ctrlArray
            }.to("createExperiment")
        }

        createExperiment{

            action {
                Library.withTransaction {status ->
                    try {
                        flow.experimentInstance = libraryToExperimentService.createExperiment(
                                flow.experimentInstance,
                                flow.library,
                                flow.selectedDilutedLibraries,
                                flow.plateType,
                                flow.controls,
                                flow.cellLine,
                                flow.inducer,
                                flow.numberOfCellsSeeded,
                                flow.treatment,
                                springSecurityService.currentUser
                        )
                    } catch (Exception e) {
                        flow.error = e.message
                    }
                }
                if (flow.error) {
                    throw new Exception(flow.error)
                }
            }
            on(Exception).to("showError")
            on("success").to("experimentCreated")
        }

        showError{

        }

        experimentCreated{

        }
    }
}
