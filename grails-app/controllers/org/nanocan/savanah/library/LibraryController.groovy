package org.nanocan.savanah.library

import org.springframework.dao.DataIntegrityViolationException

import grails.converters.JSON
import grails.plugins.springsecurity.Secured

@Secured(['ROLE_USER'])
class LibraryController {

    def springSecurityService
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def browser(){
        [libraryInstanceList: Library.list()]
    }

    def showInBrowser(){
        def libraryInstance = Library.get(params.id)
        if (!libraryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'library.label', default: 'Library'), params.id])
            redirect(action: "index")
            return
        }

        [libraryInstance: libraryInstance]
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
            redirect(controller: "libraryPlate", action: "platesAsJSON", id: params.id)
        }

        else if (params.type == "libPlate")
        {
            redirect(controller: "entry", action: "entriesAsJSON", id:  params.id)
        }
    }

    def index() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [libraryInstanceList: Library.list(params), libraryInstanceTotal: Library.count()]
    }

    @Secured(['ROLE_ADMIN'])
    def create() {
        redirect(controller: "libraryFileUpload", action: "index")
    }

    def show() {
        def libraryInstance = Library.get(params.id)
        if (!libraryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'library.label', default: 'Library'), params.id])
            redirect(action: "index")
            return
        }

        [libraryInstance: libraryInstance]
    }

    @Secured(['ROLE_ADMIN'])
    def edit() {
        def libraryInstance = Library.get(params.id)
        if (!libraryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'library.label', default: 'Library'), params.id])
            redirect(action: "index")
            return
        }

        [libraryInstance: libraryInstance]
    }

    @Secured(['ROLE_ADMIN'])
    def update() {
        def libraryInstance = Library.get(params.id)
        if (!libraryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'library.label', default: 'Library'), params.id])
            redirect(action: "index")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (libraryInstance.version > version) {
                libraryInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'library.label', default: 'Library')] as Object[],
                        "Another user has updated this Library while you were editing")
                render(view: "edit", model: [libraryInstance: libraryInstance])
                return
            }
        }

        params.lastUpdatedBy = springSecurityService.currentUser
        libraryInstance.properties = params

        if (!libraryInstance.save(flush: true)) {
            render(view: "edit", model: [libraryInstance: libraryInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'library.label', default: 'Library'), libraryInstance.id])
        redirect(action: "show", id: libraryInstance.id)
    }

    @Secured(['ROLE_ADMIN'])
    def delete() {
        def libraryInstance = Library.get(params.id)
        if (!libraryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'library.label', default: 'Library'), params.id])
            redirect(action: "index")
            return
        }

        try {
            libraryInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'library.label', default: 'Library'), params.id])
            redirect(action: "index")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'library.label', default: 'Library'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
