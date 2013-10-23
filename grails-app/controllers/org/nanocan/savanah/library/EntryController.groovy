package org.nanocan.savanah.library

import org.springframework.dao.DataIntegrityViolationException
import grails.plugins.springsecurity.Secured

@Secured(['ROLE_ADMIN'])
class EntryController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def scaffold = true
}
