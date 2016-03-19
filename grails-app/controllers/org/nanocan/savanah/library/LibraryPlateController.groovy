package org.nanocan.savanah.library

import grails.converters.JSON
import grails.plugins.springsecurity.Secured

@Secured(['ROLE_USER'])
class LibraryPlateController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def scaffold = LibraryPlate

    def platesAsJSON(){
        def library = Library.get(params.id as long)
        def libraryPlates = library.plates.sort{it.plateIndex}

        def platesAsJSON = libraryPlates.collect{
            [
                    "text"  : "${library.name} plate ${it.plateIndex}",
                    "id"    : "pl${it.id}",
                    "type" : "libPlate",
                    "children": true
            ]
        }

        render platesAsJSON as JSON
    }

}
