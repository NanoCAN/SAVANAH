package org.nanocan.io

import grails.transaction.Transactional
import org.nanocan.savanah.library.DilutedLibrary

@Transactional
class DilutedLibraryService {

    def deleteOrphanedDilutedLibraries(dilutedLibraryInstance) {

        DilutedLibrary.findAllByDilutedLibrary(dilutedLibraryInstance).each{
            deleteOrphanedDilutedLibraries(it)
        }

        dilutedLibraryInstance.delete(flush: true, failOnError: true)
    }
}
