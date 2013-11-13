package org.nanocan.io

import org.nanocan.layout.Identifier
import org.nanocan.layout.Sample
import org.nanocan.savanah.library.Entry
import org.nanocan.savanah.library.Library
import org.nanocan.savanah.library.LibraryPlate

import java.awt.Color

import static java.awt.Color.*

class LibraryUploadService {

    def uploadLibraryFile(def currentUser, String textFile, String libraryType, String plateFormat, String sampleType ) {
        String text = textFile
        // Initialize new library
        Library lib = new Library()

        lib.name = String.format("Library created at %s", (new Date()).getDateTimeString())
        lib.type = libraryType
        lib.plates = new HashSet<LibraryPlate>()

        // Index (in file) of db fields
        int plateIndex = -1;
        int wellPositionIndex = -1;
        int productIndex = -1;
        int probeIdIndex = -1;
        int mirBaseIdIndex = -1;
        int mirBassAccIndex = -1;
        int commentIndex = -1;


        Boolean first = true
        text.eachLine { line ->
            def splitLine = line.split("\t")
            if(first){
                // Column headers, assign indexes
                int indx = 0;
                splitLine.each {columnHeader ->

                    // Remove dots, only text
                    String cH = columnHeader.replace(".","").toLowerCase().trim()
                    if(cH.equalsIgnoreCase("plate")){
                        plateIndex = indx
                    }else if(cH.equalsIgnoreCase("wellposition")){
                        wellPositionIndex = indx
                    }else if(cH.equalsIgnoreCase("product")){
                        productIndex = indx
                    }else if(cH.equalsIgnoreCase("probeid")){
                        probeIdIndex = indx
                    }else if(cH.equalsIgnoreCase("mirbaseidmirplusid")){
                        mirBaseIdIndex = indx
                    }else if(cH.equalsIgnoreCase("miRBaseaccession")){
                        mirBassAccIndex = indx
                    }else if(cH.equalsIgnoreCase("CommentinmiRBase")){
                        commentIndex = indx
                    }

                    indx = indx + 1
                }

                first = false
            }else{
                def indexList = [plateIndex, wellPositionIndex, productIndex, probeIdIndex, mirBaseIdIndex, mirBassAccIndex, commentIndex]
                // Ensure all indexes has been found, return error if not
                if(indexList.any {indx -> indx == -1 }){
                    //flash.message = 'A field is missing.'
                    return
                }

                // Ensure that plate index is a number
                if(!splitLine[plateIndex].isInteger()){
                    //flash.message = 'PlateIndex ' + splitLine[plateIndex] + ' is not valid. [' + plateIndex+ "]"
                    return
                }

                Entry entry = new Entry()

                // Check that it actually is a new plateIndex, and not one from a former plate
                int plateNr = splitLine[plateIndex].toInteger()
                def plates = lib.getPlates()
                LibraryPlate libPlate

                if(!plates.any(){ p -> p.plateIndex == plateNr})
                {
                    libPlate = new LibraryPlate()
                    libPlate.plateIndex = plateNr
                    libPlate.format = plateFormat
                    libPlate.save(failOnError: true)
                    lib.plates.add(libPlate)
                }else{
                    libPlate = plates.find(){ p -> p.plateIndex == plateNr}
                }


                def mirBaseIds = splitLine[mirBaseIdIndex].split("/")
                Sample sample = null
                boolean multipleIds = splitLine[mirBaseIdIndex].contains("/")
                String sampleName
                if(!multipleIds){
                    sampleName = splitLine[mirBaseIdIndex]
                }else{
                    sampleName = String.format("X-%s" , mirBaseIds[0].replace("hsa-mir-", ""))
                }
                if(!sampleName.equalsIgnoreCase("NA")){

                    sample = Sample.findByName(sampleName)
                    if(sample == null){
                        sample = new Sample()
                        sample.type = sampleType
                        sample.target = ""
                        sample.color = ColorService.randomColor()
                        sample.name = sampleName
                        sample.identifiers = new HashSet<Identifier>()
                        sample.save(failOnError: true)
                    }

                    def mirBaseAccs = splitLine[mirBassAccIndex].split("/")

                    // Ensure they have the same size
                    if(mirBaseIds.size() != mirBaseAccs.size()){
                        //flash.message = 'mirBaseIds (' + splitLine[mirBaseIdIndex] + ') and mirBaseAccs (' + splitLine[mirBassAccIndex] + ') are unequal, one is missing information.'
                        return
                    }

                    for(int i = 0; i < mirBaseIds.size(); i++){

                        //TODO: Check if identifier exists in sample, otherwise add it
                        def identifiers = sample.getIdentifiers()

                        if(!identifiers.any() { ident -> ident.name == mirBaseIds[i]}){
                            Identifier identifier = new Identifier()
                            identifier.name = mirBaseIds[i].toString()
                            identifier.type = "miRBase"
                            identifier.sample = sample

                            // Only assign
                            if(!mirBaseAccs[i].toLowerCase().contains("not in mirbase")){
                                identifier.accessionNumber = mirBaseAccs[i]
                            }else{
                                identifier.accessionNumber = ""
                            }

                            identifier.save(failOnError: true)
                            // only add if it doesn't exist
                            sample.identifiers.add(identifier)
                            sample.save(failOnError: true)
                        }
                    }
                }

                entry.wellPosition = splitLine[wellPositionIndex].trim()
                entry.productNumber = splitLine[productIndex].trim()
                entry.probeId = splitLine[probeIdIndex].trim()
                entry.comment = splitLine[commentIndex].trim()
                entry.sample = sample
                entry.controlWell = true
                entry.save(failOnError: true)
                libPlate.addToEntries(entry)
                libPlate.save(failOnError: true)
            }
        }

        println("Done with library creation.")
        lib.dateCreated = new Date()
        lib.lastUpdated = new Date()

        lib.createdBy = currentUser
        lib.lastUpdatedBy = currentUser
        lib.save(failOnError: true)
    }


}
