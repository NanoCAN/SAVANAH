package org.nanocan.savanah.plates

import org.nanocan.savanah.attributes.CellLine
import org.nanocan.savanah.attributes.Inducer
import org.nanocan.savanah.attributes.Treatment
import org.nanocan.savanah.attributes.Sample
import org.nanocan.savanah.attributes.NumberOfCellsSeeded

class WellLayout implements Comparable {

    int col
    int row

    NumberOfCellsSeeded numberOfCellsSeeded
    CellLine cellLine
    Inducer inducer
    Treatment treatment

    static belongsTo = [plateLayout: PlateLayout]

    Sample sample

    static constraints = {
        cellLine nullable: true
        inducer nullable:  true
        treatment nullable: true
        numberOfCellsSeeded nullable: true
        sample nullable: true
    }

    String toString(){
        plateLayout.toString() + col + row
    }

    //makes samples sortable in order block -> column -> row
    public int compareTo(def other) {

        if(row < other.row) return -1
        else if(row > other.row) return 1
        else{
            if(col < other.col) return -1
            else if(col > other.col) return 1
            else return 0
        }
    }
}
