package org.nanocan.savanah.library

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN'])
class DilutedLibraryController {

    def addMaster(){
        def libraryInstance = Library.get(params.id)
        render view: "addPlateSets", model: [dilutedLibraryInstance: libraryInstance,
                                             type: "Master",
                                             examplePattern: "LIB%L%-%MA%"]
    }

    def addMother(){
        def dilutedLibraryInstance = DilutedLibrary.get(params.id)
        render view: "addPlateSets", model: [dilutedLibraryInstance: dilutedLibraryInstance,
                                             type: "Mother",
                                             examplePattern: dilutedLibraryInstance.barcodePattern?dilutedLibraryInstance.barcodePattern+"-%MO%":"LIB%L%-%MA%-%MO%"]
    }

    def addDaughter(){
        def dilutedLibraryInstance = DilutedLibrary.get(params.id)
        render view: "addPlateSets", model: [dilutedLibraryInstance: dilutedLibraryInstance,
                                             type: "Daughter",
                                             examplePattern: dilutedLibraryInstance.barcodePattern?dilutedLibraryInstance.barcodePattern+"-%DA%":"LIB%L%-%MA%-%MO%-%DA%"]
    }

    def showPlate(){
        def plate = DilutedLibraryPlate.get(params.id)
        if(plate.assayPlate) {
            def experiment = plate.assayPlate.experiment
            render """
    This plate (${plate.barcode}) was used for an assay in ${experiment.title} conducted at ${experiment.firstDayOfTheExperiment} by ${experiment.createdBy} </br><br/>
    <a href="${g.createLink(controller: "experiment", action: "show", id: experiment.id)}">Link to Experiment</a> <br/><br/>
    <a href="${g.createLink(controller: "Plate", action: "show", id: plate.assayPlate.id)}">Link to Assay plate</a>
                """
        }
        else render "This plate (${plate.barcode}) has not been used in an experiment yet."
    }

    def browser(){
        [libraryInstanceList: Library.list()]
    }

    def showDaughter(){
        def dilutedLibraryInstance = DilutedLibrary.get(params.id)
        [dilutedLibraryInstance: dilutedLibraryInstance]
    }

    def deleteDilutedLibrary(){
        def dilutedLibraryInstance = DilutedLibrary.get(params.id as long)
        if (!dilutedLibraryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'dilutedLibrary.label', default: 'DilutedLibrary'), params.id])
            redirect(action: "browser")
            return
        }

        try {
            dilutedLibraryInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'dilutedLibrary.label', default: 'DilutedLibrary'), params.id])
            redirect(action: "browser")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'dilutedLibrary.label', default: 'DilutedLibrary'), params.id])
            redirect(action: "browser")
        }
    }

    def addDilutedLibraries(){

        def parentDilutedLibrary
        def library

        //check barcode pattern
        def patternToCheck

        if(params.type == "Master") patternToCheck = /^(?=.*%L%)(?=.*%MA%)/
        else if(params.type == "Mother") patternToCheck = /^(?=.*%L%)(?=.*%MA%)(?=.*%MO%)/
        else if(params.type == "Daughter") patternToCheck = /^(?=.*%L%)(?=.*%MA%)(?=.*%MO%)(?=.*%DA%)/

        if(!params.barcodePattern ==~ patternToCheck){
            flash.message = "Barcode pattern was invalid"
            render view: "browser", model: [libraryInstanceList: Library.list()]
        }

        if(params.type == "Master"){
            library = Library.get(params.long("id"))
        }
        else{
            parentDilutedLibrary = DilutedLibrary.get(params.id as long)
            library = parentDilutedLibrary.library
        }

        //number of existing plate sets of this type (siblings)
        int siblings = DilutedLibrary.findAllByLibraryAndDilutedLibrary(library, parentDilutedLibrary).size()

        for(int i = 1; i <= params.int("numberOfSets"); i++)
        {
            def newDilutedLibrary = new DilutedLibrary()
            newDilutedLibrary.barcodePattern = params.barcodePattern
            newDilutedLibrary.library = library
            newDilutedLibrary.dilutedLibrary = parentDilutedLibrary
            int thisIndex = siblings + i
            newDilutedLibrary.index = thisIndex
            def newPlates = library.plates.collect{
                def dilutedLibraryPlate = new DilutedLibraryPlate()

                def barcode = params.barcodePattern
                barcode = barcode.replaceAll("%L%", it.plateIndex.toString())
                if(params.type == "Master"){
                    barcode = barcode.replaceAll("%MA%", thisIndex.toString())
                }
                else if(params.type == "Mother"){
                    barcode = barcode.replaceAll("%MA%", parentDilutedLibrary.index.toString())
                    barcode = barcode.replaceAll("%MO%", thisIndex.toString())
                }
                else{
                    barcode = barcode.replaceAll("%MA%", parentDilutedLibrary.dilutedLibrary.index.toString())
                    barcode = barcode.replaceAll("%MO%", parentDilutedLibrary.index.toString())
                    barcode = barcode.replaceAll("%DA%", thisIndex.toString())
                }

                dilutedLibraryPlate.barcode = barcode
                dilutedLibraryPlate.libraryPlate = it
                dilutedLibraryPlate.dilutedLibrary = newDilutedLibrary
                dilutedLibraryPlate.type = params.type
                return(dilutedLibraryPlate)
            }
            newDilutedLibrary.type = params.type
            newDilutedLibrary.dilutedPlates = newPlates
            newDilutedLibrary.usedAsAssayPlate = false
            newDilutedLibrary.save(flush: true, failOnError: true)

            library.addToDilutedLibraries(newDilutedLibrary)
            library.save(flush: true, failOnError: true)
        }
        flash.message = "${params.numberOfSets} new ${params.type} plate sets added to ${parentDilutedLibrary?.type?:library.name}"
        redirect(action: "browser")
    }

    def librariesAsJSON(){
        def libraries

        if (params.type == "#")
        {
            libraries = Library.list()

            def jsonLibraries = libraries.collect{
                [
                        "text"  : it.name,
                        "id"    : "${it.id}",
                        "type"     : "library",
                        "children" : true
                ]
            }

            render jsonLibraries as JSON
        }

        else if (params.type == "library")
        {
            def dilutedLibraries = DilutedLibrary.findAllByLibraryAndType(Library.get(params.long("id")), "Master")
            def jsonLibraries = dilutedLibraries.collect{
                [
                        "text"  : "Master ${it.index}",
                        "id"    : "mstr${it.id}",
                        "type"     : "master",
                        "children" : true
                ]
            }
            render jsonLibraries as JSON
        }

        else if (params.type == "master")
        {
            def dilutedLibraries = DilutedLibrary.findAllByDilutedLibrary(DilutedLibrary.get(params["id"].substring(4) as long))
            def jsonLibraries = dilutedLibraries.collect{
                [
                        "text"  : "Mother ${it.index}",
                        "id"    : "mthr${it.id}",
                        "type"     : "mother",
                        "children" : true
                ]
            }
            render jsonLibraries as JSON
        }
        else if (params.type == "mother")
        {
            def dilutedLibraries = DilutedLibrary.findAllByDilutedLibrary(DilutedLibrary.get(params["id"].substring(4) as long))
            def jsonLibraries = dilutedLibraries.collect{
                [
                        "text"  : "Daughter ${it.index}",
                        "id"    : "dthr${it.id}",
                        "type"     : "daughter",
                        "children" : true
                ]
            }
            render jsonLibraries as JSON
        }
        else if (params.type == "daughter")
        {
            def dilutedLibraryPlates = DilutedLibraryPlate.findAllByDilutedLibrary(DilutedLibrary.get(params["id"].substring(4) as long))
            dilutedLibraryPlates = dilutedLibraryPlates.sort{it.libraryPlate.plateIndex}
            def jsonLibraries = dilutedLibraryPlates.collect{
                [
                        "text"  : "${it.libraryPlate.plateIndex}",
                        "id"    : "plte${it.id}",
                        "type"     : "dltdPlate",
                        "children" : false
                ]
            }
            render jsonLibraries as JSON
        }
    }

}

