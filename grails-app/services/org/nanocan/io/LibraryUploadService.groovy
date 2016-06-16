package org.nanocan.io

import grails.transaction.Transactional
import org.nanocan.errors.LibraryUploadException
import org.nanocan.layout.Identifier
import org.nanocan.layout.Sample
import org.nanocan.savanah.library.Entry
import org.nanocan.savanah.library.Library
import org.nanocan.savanah.library.LibraryPlate

@Transactional(rollbackFor = Throwable)
class LibraryUploadService {

    def sessionFactory
    def propertyInstanceMap = org.codehaus.groovy.grails.plugins.DomainClassGrailsPlugin.PROPERTY_INSTANCE_MAP
    def progressService
    def elasticSearchAdminService

    def cleanUpGorm() {
        def session = sessionFactory.currentSession
        session.flush()
        session.clear()
        propertyInstanceMap.get().clear()
    }

    def uploadLibraryFile(Library lib, def currentUser, String textFile) throws LibraryUploadException,ArrayIndexOutOfBoundsException {
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


        String[] lines = text.split("\r\n|\r|\n");
        int numOfLines = lines.length;
        int numOfLinesOnePercent = Math.floor(numOfLines / 100)
        int percentCount = 0

        text.eachLine { line, count ->
            //log.debug "Library import - Processing line: ${line}"
            def splitLine = line.split("\t")
            if(count % numOfLinesOnePercent == 0)
                progressService.setProgressBarValue(lib.name, ++percentCount)

            if(count == 0){
                // Column headers, assign indexes
                int indx = 0;
                splitLine.each {columnHeader ->

                    // Remove dots, only text
                    String cH = columnHeader.replace(".","").toLowerCase().trim()
                    cH = cH.replace(" ","")
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

            }else{
                def indexList = [plateIndex, wellPositionIndex, productIndex, probeIdIndex, sampleNameIdIndex, accessionIndex, commentIndex]
                // Ensure all indexes has been found, return error if not
                if(indexList.any {indx -> indx == -1 }){
                    throw new LibraryUploadException('Field is missing in the library file. Make sure that the header of ' +
                            'the file contains the fields plate, well.position, catalog.nr, probe.id, sample.name, ' +
                            'accession, and comment. The order is arbitrary. Dots and whitespaces are optional. Smaller and lower case ' +
                            'are ignored. Also check that the columns in the file are tab separated.')
                }

                // Ensure that plate index is a number
                if(!splitLine[plateIndex].isInteger()){
                    throw new LibraryUploadException('Plate index ' + splitLine[plateIndex] + ' is not an integer. [' + plateIndex+ ']')
                    return
                }

                Entry entry = new Entry()

                // Check that it actually is a new plateIndex, and not one from a former plate
                // this way the order of the entries is arbitrary and it will still work.
                int plateNr = splitLine[plateIndex].toInteger()
                def plates = lib.getPlates()
                LibraryPlate libPlate

                if(!plates.any(){ p -> p.plateIndex == plateNr})
                {
                    log.debug "Library import - Cleaning up session"
                    cleanUpGorm()
                    log.debug "Library import - Processing plate: ${plateNr}"
                    libPlate = new LibraryPlate()
                    libPlate.plateIndex = plateNr
                    libPlate.format = plateFormat
                    libPlate.save(validate: false)
                    lib.plates.add(libPlate)
                }else{
                    libPlate = plates.find(){ p -> p.plateIndex == plateNr}
                }

                def sampleNameIds = splitLine[sampleNameIdIndex].split("/|\\s")
                Sample sample = null
                String sampleName = splitLine[sampleNameIdIndex]

                if(!sampleName.equalsIgnoreCase("NA") &&
                    !sampleName.empty){

                    sample = Sample.findByName(sampleName)

                    if(sample == null){
                        sample = new Sample()
                        sample.type = sampleType
                        sample.target = ""
                        sample.color = ColorService.randomColor()
                        sample.name = sampleName
                        sample.identifiers = new HashSet<Identifier>()
                        //log.debug "Library import - Saving sample ${sample.properties}"
                        sample.save(validate: false)

                        def accessions = splitLine[accessionIndex].split("/|\\s")

                        for(int i = 0; i < accessions.size(); i++){

                        def accessionName

                        if (sampleNameIds.size() == accessions.size()) {
                             accessionName = sampleNameIds[i].toString()
                        }
                        else{
                            accessionName = sampleName
                        }

                        Identifier identifier = new Identifier()
                        identifier.name = accessionName
                        identifier.type = accessionType
                        identifier.sample = sample

                        // Only assign
                            if(!accessions[i].toLowerCase().contains("NA")){
                                identifier.accessionNumber = accessions[i]
                                }else{
                                    return
                                }
                                //log.debug "Library import - Saving identifier ${identifier.properties}"
                                identifier.save(validate: false)
                                sample.identifiers.add(identifier)
                                // only add if it doesn't exist
                        }

                        sample.save(validate: false)
                    }
                }

                String wellPosition = splitLine[wellPositionIndex].trim()
                if(!(wellPosition ==~ /[A-Z][0-9]+/)){
                    throw new LibraryUploadException("${wellPosition} is not a well index of form A1, B2, etc.")
                }
                entry.wellPosition = wellPosition

                int currentRow
                int currentCol
                int maxRow
                int maxCol

                try {
                    int numOfWells = Integer.valueOf(libPlate.format.replace("-well", ""))

                    maxRow = Math.sqrt(numOfWells / 1.5)
                    maxCol = maxRow * 1.5

                    currentRow = Character.getNumericValue(entry.wellPosition.charAt(0)) - 9
                    currentCol = entry.wellPosition.substring(1) as int
                }catch(Exception e){
                    throw LibraryUploadException("Could not extract row and column from ${wellPosition}. Make sure to use well indices of format A1, B1, ...")
                }
                if(currentRow < 0 || currentRow > maxRow ) throw new LibraryUploadException("Invalid row index: ${wellPosition}")
                if(currentCol < 0 || currentCol > maxCol ) throw new LibraryUploadException("Invalid column index: ${wellPosition}")

                entry.row = currentRow
                entry.col = currentCol
                entry.productNumber = splitLine[productIndex].trim()
                entry.probeId = splitLine[probeIdIndex].trim()
                entry.comment = splitLine[commentIndex].trim()
                entry.sample = sample
                entry.controlWell = (sampleName.equals("") || sampleName.equalsIgnoreCase("NA"))
                entry.libraryPlate = libPlate
                libPlate.addToEntries(entry)

                if(!libPlate.validate()){
                    throw new LibraryUploadException("Validation of library plate ${libPlate.plateIndex} failed", libPlate.errors)
                }
                libPlate.save(validate: false)
            }
        }
        log.debug "END importing library with name ${libraryName}"

        log.debug "Checking if all plates are complete"
        for(LibraryPlate plate in lib.getPlates()){
            int expectedNumOfWells = Integer.valueOf(plate.format.replace("-well", ""))
            if(plate.entries.size() != expectedNumOfWells)
            {
                throw new LibraryUploadException("Plate ${plate.plateIndex} has only ${plate.entries.size()} wells. " +
                        "According to the format of the plate it should be ${expectedNumOfWells}.")
            }
        }

        lib.dateCreated = new Date()
        lib.lastUpdated = new Date()

        lib.createdBy = currentUser
        lib.lastUpdatedBy = currentUser
        lib.save(flush: true, validate: false)
        log.info("Library ${libraryName} created successfully.")
        return(lib)
    }


}
