package org.nanocan.savanah.project

import grails.plugins.springsecurity.Secured
import org.nanocan.project.Project

@Secured(['ROLE_USER'])
class ProjectController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def scaffold = Project

    def index() {}
}
