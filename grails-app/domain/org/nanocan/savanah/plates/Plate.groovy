package org.nanocan.savanah.plates

import org.nanocan.savanah.library.Library

class Plate {

    String type
    Plate parentPlate
    Library library
    Plate libraryPlate
    String id

    static mapping = {
        id column: 'barcode', generator: 'assigned'
    }

    static constraints = {
        parentPlate nullable: true
        libraryPlate nullable: true
        type inList: ["library", "supermaster", "master", "mother", "daughter"]
    }

    String toString(){
        (id + "(" + type + ")")
    }
}
