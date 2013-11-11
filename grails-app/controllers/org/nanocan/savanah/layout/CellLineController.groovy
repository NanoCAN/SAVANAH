package org.nanocan.savanah.layout

import grails.plugins.springsecurity.Secured
import org.nanocan.layout.CellLine

@Secured(['ROLE_USER'])
class CellLineController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def scaffold = CellLine

    def index() {}
}
