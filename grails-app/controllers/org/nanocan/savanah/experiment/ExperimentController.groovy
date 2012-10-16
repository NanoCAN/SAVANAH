package org.nanocan.savanah.experiment

import grails.plugins.springsecurity.Secured

/**
 * Created by IntelliJ IDEA.
 * User: mlist
 * Date: 15-10-12
 * Time: 11:59
 */

@Secured(['ROLE_USER'])
class ExperimentController {

    def scaffold = true
    def springSecurityService

    def save() {
        params.createdBy = springSecurityService.currentUser
        params.lastUpdatedBy = springSecurityService.currentUser

        def experimentInstance = new Experiment(params)

        if (!experimentInstance.save(flush: true)) {
            render(view: "create", model: [experimentInstance: experimentInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'experiment.label', default: 'Experiment'), experimentInstance.id])
        redirect(action: "show", id: experimentInstance.id)
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
        params.lastUpdatedBy = springSecurityService.currentUser

        experimentInstance.properties = params

        if (!experimentInstance.save(flush: true)) {
            render(view: "edit", model: [experimentInstance: experimentInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'experiment.label', default: 'Experiment'), experimentInstance.id])
        redirect(action: "show", id: experimentInstance.id)
    }
}
