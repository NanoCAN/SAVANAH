package org.nanocan.savanah.plates

import grails.plugins.springsecurity.Secured

@Secured(['ROLE_USER'])
class ResultFileConfigController {

    def scaffold = true
}
