package org.nanocan.savanah.attributes

import grails.plugins.springsecurity.Secured

@Secured(['ROLE_USER'])
class NumberOfCellsSeededController {

    def scaffold = true
}
