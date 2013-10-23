package org.nanocan.savanah.library

import org.nanocan.layout.Identifier
import org.nanocan.layout.Sample

class Entry implements Serializable {

    static belongsTo = LibraryPlate
    String wellPosition //e.g. A01, H12
    int col
    int row
    String productNumber
    String probeId

    Sample sample
    String comment
    boolean controlWell

    static mapping = {
    }

    static constraints = {

    }

    String toString(){
        sample?sample.toString():("empty well")
    }
}

