package org.nanocan.savanah.library

class LibraryPlate implements Serializable{

    static belongsTo = Library
    static hasMany = [entries: Entry]
    int plateIndex
    String format

    static searchable = {
        except = "entries"
    }

    static constraints = {
        format inList: ["96-well", "384-well", "1536-well"], blank: false, editable: false
    }

    String toString(){
        String.format("Plate %d (%s)", plateIndex, format)
    }
}
