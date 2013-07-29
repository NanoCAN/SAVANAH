package org.nanocan.savanah.plates

class ResultFileConfig {

    String name

    String measuredValueCol
    String wellPositionCol
    String rowCol
    String columnCol

    int skipLines

    static constraints = {
        unique: "name"
    }

    String toString()
    {
        name
    }
}
