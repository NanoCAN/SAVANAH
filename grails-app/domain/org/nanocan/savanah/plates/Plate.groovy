package org.nanocan.savanah.plates

import org.nanocan.savanah.library.Library
import org.nanocan.layout.PlateLayout
import org.nanocan.savanah.library.LibraryPlate
import org.nanocan.project.Experiment

class Plate {

    PlateType plateType
    static embedded = ['plateType']
    String format
    String barcode
    String name
    PlateLayout layout
    LibraryPlate libraryPlate

    static belongsTo = [experiment: Experiment]

    int replicate

    int cols
    int rows

    static mapping = {

    }

    static hasMany = [readouts: Readout]

    static constraints = {
        //barcodes need to be unique across the database
        barcode unique:  true
        name nullable: true, blank: false, unique: true
        plateType nullable: true
        libraryPlate nullable: true
        format inList: ["96-well", "384-well"], blank: false, editable: false
        //we can only have one replicate x for each library plate y in experiment z
        replicate(unique: ['experiment', 'libraryPlate'])
    }

    String toString(){
      ("Plate" + barcode?:"no barcode" + name?:"")
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
