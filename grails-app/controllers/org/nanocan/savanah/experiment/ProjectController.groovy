package org.nanocan.savanah.experiment

import grails.plugins.springsecurity.Secured

@Secured(['ROLE_USER'])
class ProjectController {

    def springSecurityService
    def scaffold = true

    def save() {
        params.createdBy = springSecurityService.currentUser
        params.lastUpdatedBy = springSecurityService.currentUser

        def projectInstance = new Project(params)

        if (!projectInstance.save(flush: true)) {
            render(view: "create", model: [projectInstance: projectInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'project.label', default: 'Project'), projectInstance.id])
        redirect(action: "show", id: projectInstance.id)
    }


    def update() {
        def projectInstance = Project.get(params.id)
        if (!projectInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (projectInstance.version > version) {
                projectInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'project.label', default: 'Project')] as Object[],
                        "Another user has updated this Project while you were editing")
                render(view: "edit", model: [projectInstance: projectInstance])
                return
            }
        }
        params.lastUpdatedBy = springSecurityService.currentUser

        projectInstance.properties = params

        if (!projectInstance.save(flush: true)) {
            render(view: "edit", model: [projectInstance: projectInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'project.label', default: 'Project'), projectInstance.id])
        redirect(action: "show", id: projectInstance.id)
    }
}
