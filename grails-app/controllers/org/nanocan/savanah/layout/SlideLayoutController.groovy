package org.nanocan.savanah.layout

import grails.plugins.springsecurity.Secured
import org.nanocan.layout.SlideLayout

@Secured(['ROLE_USER'])
class SlideLayoutController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def scaffold = SlideLayout

    def index() {}
}
