package org.nanocan.savanah.library

import org.springframework.dao.DataIntegrityViolationException
import org.nanocan.savanah.plates.Plate
import grails.converters.JSON
import grails.plugins.springsecurity.Secured

@Secured(['ROLE_USER'])
class LibraryController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def browser(){
        [libraryInstanceList: Library.list()]
    }

    def librariesAsJSON(){
        def libraries

        def prefixLength = params.id.size()

        if (params.int("id") == 0)
        {
            libraries = Library.list()

            def jsonLibraries = libraries.collect{
                [
                        "data" : it.name,
                        "attr" : [ "id" : it.id , "nodeType" : "library"],
                        "state" : "closed"
                ]
            }

            render jsonLibraries as JSON
        }

        else if (params.nodeType == "library")
        {
            redirect(controller: "plate", action: "platesAsJSON", id: 0, params: [library: params.id])
        }

        else
        {
            redirect(controller: "plate", action: "platesAsJSON", id:  params.id)
        }
    }

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [libraryInstanceList: Library.list(params), libraryInstanceTotal: Library.count()]
    }

    @Secured(['ROLE_ADMIN'])
    def create() {
        [libraryInstance: new Library(params)]
    }

    @Secured(['ROLE_ADMIN'])
    def save() {
        def libraryInstance = new Library(params)
        if (!libraryInstance.save(flush: true)) {
            render(view: "create", model: [libraryInstance: libraryInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'library.label', default: 'Library'), libraryInstance.id])
        redirect(action: "show", id: libraryInstance.id)
    }

    def show() {
        def libraryInstance = Library.get(params.id)
        if (!libraryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'library.label', default: 'Library'), params.id])
            redirect(action: "list")
            return
        }

        [libraryInstance: libraryInstance]
    }

    @Secured(['ROLE_ADMIN'])
    def edit() {
        def libraryInstance = Library.get(params.id)
        if (!libraryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'library.label', default: 'Library'), params.id])
            redirect(action: "list")
            return
        }

        [libraryInstance: libraryInstance]
    }

    @Secured(['ROLE_ADMIN'])
    def update() {
        def libraryInstance = Library.get(params.id)
        if (!libraryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'library.label', default: 'Library'), params.id])
            redirect(action: "list")
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
            redirect(action: "list")
            return
        }

        try {
            libraryInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'library.label', default: 'Library'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'library.label', default: 'Library'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
