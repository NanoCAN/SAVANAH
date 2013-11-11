package org.nanocan.savanah.library

class LibraryPlate {

    static belongsTo = Library
    static hasMany = [entries: Entry]
    int plateIndex
    String format


    static constraints = {
        format inList: ["96-well", "384-well"], blank: false, editable: false
    }

    String toString(){
        String.format("plateIndex: %d (%s)", plateIndex, format)
    }
}
