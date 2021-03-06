package org.nanocan.savanah.library

import grails.converters.JSON
import grails.plugins.springsecurity.Secured

@Secured(['ROLE_ADMIN'])
class EntryController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    static layout = "body"

    def scaffold = true

    def showInBrowser(){
        def entryInstance = Entry.get(params.id)

        render view: "show", model: [entryInstance: entryInstance]
    }

    def entriesAsJSON(){

        def libraryPlate = LibraryPlate.get(params["id"].toString().substring(2) as long).entries.sort{it}

        def jsonLibraries = libraryPlate.collect{
            [
                    "text"  : it.wellPosition,
                    "id"    : "wl${it.id}",
                    "type" : "libWell",
            ]
        }

        render jsonLibraries as JSON
    }
}
