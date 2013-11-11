package org.nanocan.savanah.layout

import grails.plugins.springsecurity.Secured
import org.nanocan.layout.PlateLayout

@Secured(['ROLE_USER'])
class PlateLayoutController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def scaffold = PlateLayout

    def index() {}
}
