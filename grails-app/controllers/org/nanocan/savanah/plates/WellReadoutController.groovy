package org.nanocan.savanah.plates

import org.springframework.dao.DataIntegrityViolationException

class WellReadoutController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [wellReadoutInstanceList: WellReadout.list(params), wellReadoutInstanceTotal: WellReadout.count()]
    }

    def create() {
        [wellReadoutInstance: new WellReadout(params)]
    }

    def save() {
        def wellReadoutInstance = new WellReadout(params)
        if (!wellReadoutInstance.save(flush: true)) {
            render(view: "create", model: [wellReadoutInstance: wellReadoutInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'wellReadout.label', default: 'WellReadout'), wellReadoutInstance.id])
        redirect(action: "show", id: wellReadoutInstance.id)
    }

    def show() {
        def wellReadoutInstance = WellReadout.get(params.id)
        if (!wellReadoutInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'wellReadout.label', default: 'WellReadout'), params.id])
            redirect(action: "list")
            return
        }

        [wellReadoutInstance: wellReadoutInstance]
    }

    def edit() {
        def wellReadoutInstance = WellReadout.get(params.id)
        if (!wellReadoutInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'wellReadout.label', default: 'WellReadout'), params.id])
            redirect(action: "list")
            return
        }

        [wellReadoutInstance: wellReadoutInstance]
    }

    def update() {
        def wellReadoutInstance = WellReadout.get(params.id)
        if (!wellReadoutInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'wellReadout.label', default: 'WellReadout'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (wellReadoutInstance.version > version) {
                wellReadoutInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'wellReadout.label', default: 'WellReadout')] as Object[],
                        "Another user has updated this WellReadout while you were editing")
                render(view: "edit", model: [wellReadoutInstance: wellReadoutInstance])
                return
            }
        }

        wellReadoutInstance.properties = params

        if (!wellReadoutInstance.save(flush: true)) {
            render(view: "edit", model: [wellReadoutInstance: wellReadoutInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'wellReadout.label', default: 'WellReadout'), wellReadoutInstance.id])
        redirect(action: "show", id: wellReadoutInstance.id)
    }

    def delete() {
        def wellReadoutInstance = WellReadout.get(params.id)
        if (!wellReadoutInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'wellReadout.label', default: 'WellReadout'), params.id])
            redirect(action: "list")
            return
        }

        try {
            wellReadoutInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'wellReadout.label', default: 'WellReadout'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'wellReadout.label', default: 'WellReadout'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
