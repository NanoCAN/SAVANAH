package org.nanocan.savanah.library

import grails.plugins.springsecurity.Secured

@Secured(['ROLE_USER'])
class LibraryPlateController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def scaffold = LibraryPlate

    def index() { }
}
