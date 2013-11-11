package org.nanocan.savanah.layout

import grails.plugins.springsecurity.Secured
import org.nanocan.layout.Sample

@Secured(['ROLE_USER'])
class SampleController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def scaffold = Sample

    def index() {}
}
