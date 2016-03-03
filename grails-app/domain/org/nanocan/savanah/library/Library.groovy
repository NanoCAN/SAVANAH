package org.nanocan.savanah.library

import org.nanocan.security.Person

class Library implements Serializable{

    String name
    String type
    String vendor
    String catalogNr
    String sampleType
    String accessionType
    String plateFormat

    Date dateCreated
    Date lastUpdated

    Person createdBy
    Person lastUpdatedBy

    static hasMany = [plates : LibraryPlate, dilutedLibraries: DilutedLibrary]

    static constraints = {
        name unique: true
        sampleType nullable: true
        accessionType nullable: true
        plateFormat nullable: true
        type inList: ["microRNA inhibitor", "microRNA mimics", "siRNA", "small compounds"]
    }

    String toString(){
        name
    }
}
