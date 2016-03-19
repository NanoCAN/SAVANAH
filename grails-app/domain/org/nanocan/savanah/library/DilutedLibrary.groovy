package org.nanocan.savanah.library

class DilutedLibrary implements Serializable{

    Library library
    DilutedLibrary dilutedLibrary
    String type
    boolean usedAsAssayPlate
    int index
    String barcodePattern

    static searchable = true

    static constraints = {
        type inList: ["Master", "Mother", "Daughter"]
        dilutedLibrary nullable: true
        barcodePattern nullable: true
    }

    static mapping = {
        index column: "dilutionLibraryIndex"
    }

    static belongsTo = [Library] // orphaned DilutedLibraries are deleted in DilutedLibraryService
    static hasMany = [dilutedPlates : DilutedLibraryPlate]

    String toString(){
        def result = dilutedLibrary?dilutedLibrary.toString().concat(" - "):""
        return( result + type + " " + index)
    }
}


