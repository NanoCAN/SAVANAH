package org.nanocan.io

import groovy.sql.Sql

class ReadoutImportService {

    def nextStep
    def fileImportService
    /**
     * Main method
     */
    def processResultFile(def objectInstance, String sheetContent, def columnMap, int skipLines, String progressId)
    {
        Scanner scanner = new Scanner(sheetContent)

        //skip lines including header this time
        for(int i = 0; i < skipLines; i++)
        {
            if(scanner.hasNextLine()) scanner.nextLine()
        }

        def wellReadouts = []

        while(scanner.hasNextLine())
        {
            def currentLine = scanner.nextLine()

            currentLine = currentLine.split(',')

            try
            {
                def newWellReadout = [:]

                newWellReadout.col = Integer.valueOf(currentLine[columnMap.col])
                newWellReadout.row = Integer.valueOf(currentLine[columnMap.row])

                newWellReadout.measuredValue = Double.valueOf(currentLine[columnMap.measuredValue])

                //compute well position if necessary
                if(newWellReadout.col == null && newWellReadout.row == null && currentLine[columnMap.wellPosition])
                {
                    def wellPosition = currentLine[columnMap.wellPosition]
                    newWellReadout.row = Character.getNumericValue(wellPosition.toString().charAt(0))-9
                    newWellReadout.col = Integer.valueOf(wellPosition.toString().substring(1))
                }

                wellReadouts << newWellReadout

            }catch(ArrayIndexOutOfBoundsException e)
            {
                log.info "could not parse line, assuming the end is reached."
            }
        }

        //clean up
        scanner.close()

        nextStep = fileImportService.initializeProgressBar(wellReadouts, progressId)

        objectInstance.addAllToWells(wellReadouts).save(flush:true)

        return objectInstance
    }

    def createMatchingMap(def resultFileCfg, List<String> header) {
        def matchingMap = [:]

        if (resultFileCfg) {

            for (String colName : header) {
                def trimmedColName = colName

                //remote leading and tailing quote
                if (colName.startsWith("\"") && colName.endsWith("\""))
                    trimmedColName = colName.substring(1, colName.length() - 1);


                switch (trimmedColName) {
                    case resultFileCfg.blockCol:
                        matchingMap.put(colName, "row")
                        break
                    case resultFileCfg.rowCol:
                        matchingMap.put(colName, "col")
                        break
                    case resultFileCfg.columnCol:
                        matchingMap.put(colName, "wellPosition")
                        break
                    case resultFileCfg.fgCol:
                        matchingMap.put(colName, "measuredValue")
                        break
                }
            }
        }
        matchingMap
    }
}
