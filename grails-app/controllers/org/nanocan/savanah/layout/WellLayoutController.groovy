package org.nanocan.savanah.layout

import grails.plugins.springsecurity.Secured
import org.nanocan.layout.WellLayout

@Secured(['ROLE_USER'])
class WellLayoutController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def scaffold = WellLayout

    def index() {}
}
