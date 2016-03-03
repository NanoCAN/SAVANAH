package org.nanocan.savanah.library

import org.nanocan.layout.Identifier
import org.nanocan.layout.Sample

class Entry implements Serializable, Comparable {

    static belongsTo = LibraryPlate
    String wellPosition //e.g. A01, H12
    int col
    int row
    String productNumber
    String probeId

    static searchable = true

    Sample sample
    String comment
    boolean controlWell
    boolean deprecated

    static mapping = {
    }

    static constraints = {

    }

    String toString(){
        sample?sample.toString():("empty well")
    }

    @Override
    int compareTo(Object o) {

        String letterA = this?.wellPosition?.substring(0, 1)
        String letterB = o?.wellPosition?.substring(0, 1)

        if(letterA == letterB)
        {
            int numA = this?.wellPosition?.substring(1) as Integer
            int numB = o?.wellPosition?.substring(1) as Integer
            return(numA.compareTo(numB))
        }
        else{
            return letterA.compareTo(letterB)
        }
    }
}

