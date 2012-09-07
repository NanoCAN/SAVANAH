package org.nanocan.savanah.experiment

import org.springframework.dao.DataIntegrityViolationException

class ExperimentController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [experimentInstanceList: Experiment.list(params), experimentInstanceTotal: Experiment.count()]
    }

    def create() {
        [experimentInstance: new Experiment(params)]
    }

    def save() {
        def experimentInstance = new Experiment(params)
        if (!experimentInstance.save(flush: true)) {
            render(view: "create", model: [experimentInstance: experimentInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'experiment.label', default: 'Experiment'), experimentInstance.id])
        redirect(action: "show", id: experimentInstance.id)
    }

    def show() {
        def experimentInstance = Experiment.get(params.id)
        if (!experimentInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'experiment.label', default: 'Experiment'), params.id])
            redirect(action: "list")
            return
        }

        [experimentInstance: experimentInstance]
    }

    def edit() {
        def experimentInstance = Experiment.get(params.id)
        if (!experimentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'experiment.label', default: 'Experiment'), params.id])
            redirect(action: "list")
            return
        }

        [experimentInstance: experimentInstance]
    }

    def update() {
        def experimentInstance = Experiment.get(params.id)
        if (!experimentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'experiment.label', default: 'Experiment'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (experimentInstance.version > version) {
                experimentInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'experiment.label', default: 'Experiment')] as Object[],
                          "Another user has updated this Experiment while you were editing")
                render(view: "edit", model: [experimentInstance: experimentInstance])
                return
            }
        }

        experimentInstance.properties = params

        if (!experimentInstance.save(flush: true)) {
            render(view: "edit", model: [experimentInstance: experimentInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'experiment.label', default: 'Experiment'), experimentInstance.id])
        redirect(action: "show", id: experimentInstance.id)
    }

    def delete() {
        def experimentInstance = Experiment.get(params.id)
        if (!experimentInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'experiment.label', default: 'Experiment'), params.id])
            redirect(action: "list")
            return
        }

        try {
            experimentInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'experiment.label', default: 'Experiment'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'experiment.label', default: 'Experiment'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
