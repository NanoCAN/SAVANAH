package org.nanocan.savanah.library

import org.nanocan.savanah.security.Person

class Library {

    String name
    String type

    Date dateCreated
    Date lastUpdated

    Person createdBy
    Person lastUpdatedBy

    static constraints = {
        name [:]
        type inList: ["microRNA inhibitor", "microRNA mimics", "genome-wide", "druggable genome"]
    }

    String toString(){
        name unique: true
    }
}
