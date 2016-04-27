package org.nanocan.savanah.library

import org.nanocan.plates.Plate

class DilutedLibraryPlate {

    String barcode
    DilutedLibrary dilutedLibrary
    LibraryPlate libraryPlate
    Plate assayPlate
    String type

    static searchable = {
        root false
    }

    static belongsTo = [DilutedLibrary, LibraryPlate]

    static constraints = {
        type inList: ["Master", "Mother", "Daughter"]
        assayPlate nullable: true
    }

    String toString(){
        barcode
    }

}
