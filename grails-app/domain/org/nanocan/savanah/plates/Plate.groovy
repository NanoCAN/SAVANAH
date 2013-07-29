package org.nanocan.savanah.plates

import org.nanocan.savanah.library.Library
import org.nanocan.layout.PlateLayout
import org.nanocan.savanah.library.LibraryPlate

class Plate {

    String plateType
    String format
    String family
    Plate parentPlate
    LibraryPlate libraryPlate
    String barcode
    String name
    PlateLayout layout

    int cols
    int rows

    static mapping = {

    }

    static hasMany = [readouts: Readout]

    static constraints = {
        barcode unique:  true
        name nullable: true, blank: false, unique: true
        plateType nullable: true
        parentPlate nullable: true
        libraryPlate nullable: true
        family inList: ["library", "supermaster", "master", "mother", "daughter"]
        format inList: ["96-well", "384-well"], blank: false, editable: false
    }

    String toString(){
        name?:(id + "(" + family + ")")
    }

    def beforeInsert = {
        if(format == "96-well")
        {
            cols = 12
            rows = 8
        }
        else if(format == "384-well")
        {
            cols = 24
            rows = 16
        }
    }

    def beforeUpdate = {
        beforeInsert()
    }
}
