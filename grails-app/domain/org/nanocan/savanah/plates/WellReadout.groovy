package org.nanocan.savanah.plates

class WellReadout {

    static belongsTo = [readout: Readout]
    double measuredValue

    int col
    int row

    static constraints = {
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
