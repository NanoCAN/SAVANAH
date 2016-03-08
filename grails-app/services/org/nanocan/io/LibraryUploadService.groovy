package org.nanocan.io

import org.nanocan.layout.Identifier
import org.nanocan.layout.Sample
import org.nanocan.savanah.library.Entry
import org.nanocan.savanah.library.Library
import org.nanocan.savanah.library.LibraryPlate

class LibraryUploadService {

    def sessionFactory

    def cleanUpGorm() {
        def session = sessionFactory.currentSession
        session.flush()
        session.clear()
    }

    def uploadLibraryFile(Library lib, def currentUser, String textFile) {

        log.debug "BEGIN importing library with name ${lib.name}"
        String libraryName = lib.name
        String sampleType = lib.sampleType
        String accessionType = lib.accessionType
        String plateFormat = lib.plateFormat
        String text = textFile

        // Index (in file) of db fields
        int plateIndex = -1;
        int wellPositionIndex = -1;
        int productIndex = -1;
        int probeIdIndex = -1;
        int sampleNameIdIndex = -1;
        int accessionIndex = -1;
        int commentIndex = -1;


        Boolean first = true

        text.eachLine { line ->
            log.debug "Library import - Processing line: ${line}"
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
                    }else if(cH.equalsIgnoreCase("catalognr")){
                        productIndex = indx
                    }else if(cH.equalsIgnoreCase("probeid")){
                        probeIdIndex = indx
                    }else if(cH.equalsIgnoreCase("samplename")){
                        sampleNameIdIndex = indx
                    }else if(cH.equalsIgnoreCase("accession")){
                        accessionIndex = indx
                    }else if(cH.equalsIgnoreCase("comment")){
                        commentIndex = indx
                    }

                    indx = indx + 1
                }

                first = false
            }else{
                def indexList = [plateIndex, wellPositionIndex, productIndex, probeIdIndex, sampleNameIdIndex, accessionIndex, commentIndex]
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
                    cleanUpGorm()
                    libPlate = new LibraryPlate()
                    libPlate.plateIndex = plateNr
                    libPlate.format = plateFormat
                    libPlate.save(failOnError: true)
                    lib.plates.add(libPlate)
                }else{
                    libPlate = plates.find(){ p -> p.plateIndex == plateNr}
                }

                def sampleNameIds = splitLine[sampleNameIdIndex].split("/")
                Sample sample = null
                boolean multipleIds = splitLine[sampleNameIdIndex].contains("/")
                String sampleName = splitLine[sampleNameIdIndex]

                if(!sampleName.equalsIgnoreCase("NA")){

                    sample = Sample.findByName(sampleName)

                    if(sample == null){
                        sample = new Sample()
                        sample.type = sampleType
                        sample.target = ""
                        sample.color = ColorService.randomColor()
                        sample.name = sampleName
                        sample.identifiers = new HashSet<Identifier>()
                        log.debug "Library import - Saving sample ${sample.properties}"
                        sample.save(failOnError: true)

                        def accessions = splitLine[accessionIndex].split("/")

                        for(int i = 0; i < accessions.size(); i++){

                        Identifier identifier = new Identifier()
                        identifier.name = sampleNameIds[i].toString()
                        identifier.type = accessionType
                        identifier.sample = sample

                        // Only assign
                            if(!accessions[i].toLowerCase().contains("NA")){
                                identifier.accessionNumber = accessions[i]
                                }else{
                                    return
                                }
                                log.debug "Library import - Saving identifier ${identifier.properties}"
                                identifier.save(failOnError: true)
                                sample.identifiers.add(identifier)
                                // only add if it doesn't exist
                        }

                        sample.save(failOnError: true)
                    }
                }

                entry.wellPosition = splitLine[wellPositionIndex].trim()
                entry.row = Character.getNumericValue(entry.wellPosition.charAt(0))-9
                entry.col = entry.wellPosition.substring(1) as int
                entry.productNumber = splitLine[productIndex].trim()
                entry.probeId = splitLine[probeIdIndex].trim()
                entry.comment = splitLine[commentIndex].trim()
                entry.sample = sample
                entry.controlWell = (sampleName.equals("") || sampleName.equalsIgnoreCase("NA"))
                entry.save(failOnError: true)
                libPlate.addToEntries(entry)
                libPlate.save(failOnError: true)
            }
        }
        log.debug "END importing library with name ${libraryName}"
        lib.dateCreated = new Date()
        lib.lastUpdated = new Date()

        lib.createdBy = currentUser
        lib.lastUpdatedBy = currentUser
        lib.save(flush: true)
        log.info("Library ${libraryName} created successfully.")
        return(lib)
    }


}
