package org.nanocan.io

import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.nanocan.errors.XlsxToReadoutException
import org.nanocan.file.ResultFile
import org.nanocan.savanah.plates.Plate
import org.nanocan.savanah.plates.Readout
import org.nanocan.savanah.plates.WellReadout
import org.springframework.web.multipart.MultipartFile

class XlsxToReadoutService {
    def unzipService

    def parseToReadout(MultipartFile file, String fileName){

        String suffixWithDot = file.originalFilename.substring(file.originalFilename.lastIndexOf("."))
        if(suffixWithDot != ".xls" && suffixWithDot != ".xlsx"){
            return(false)
        }

        File excelFile = unzipService.getFileFromStream(file.getInputStream(), suffixWithDot)

        parseToReadout(excelFile, fileName)
    }

    def parseToReadout(File excelFile, String fileName) {

        String suffixWithDot = excelFile.name.substring(excelFile.name.lastIndexOf("."))
        if(suffixWithDot != ".xls" && suffixWithDot != ".xlsx"){
            throw new XlsxToReadoutException(String.format("\"%s\" is not an Excelfile.",excelFile.name))
        }

        //The plate barcode is the filename without suffix
        String plateBarcode = fileName.replace(suffixWithDot, "")
          println(plateBarcode)
        Workbook workbook

        try{
            workbook = WorkbookFactory.create(excelFile)

        }catch(org.apache.poi.hssf.OldExcelFormatException oefe){
            //if we have to deal with very old excel files, try to convert it using ssconvert (needs to be installed of course)
            /*Process p = Runtime.getRuntime().exec("ssconvert ${filePath} ${filePath}x")
            p.waitFor()
            resultFile.filePath = "${filePath}x"
            resultFile.save(flush:true)
            new File(filePath).delete()   */
        }

        if(workbook.getNumberOfSheets() == 0){
            throw new XlsxToReadoutException(String.format("No sheets were found in file \"%s\".",excelFile.name))
        }

        def sheet = null
        for(def i = 0; i < workbook.getNumberOfSheets(); i++){
            // SheetName = List; Plates 1 - 1
            if(workbook.getSheetName(i).toLowerCase().contains("list")){
                sheet = workbook.getSheetAt(i)
                break
            }
        }

        if(sheet == null){
            throw new XlsxToReadoutException(String.format("No sheet \"List; Plates 1 - 1\" was found in file \"%s\".",excelFile.name))
        }

        // Save Excel file as a ResultFile
        ResultFile newResultFile = new ResultFile(fileName: excelFile.name, filePath: excelFile.path, dateUploaded: new Date(), fileType: "Result")
        newResultFile.save(failOnError: true)

        // Create the Readout
        Readout readout = new Readout()

        def ownerplate = Plate.findByBarcode(plateBarcode)
        if(!ownerplate || ownerplate == null){
            throw new XlsxToReadoutException(String.format("No plate with barcode \"%s\" was found (from file \"%s\").",plateBarcode, excelFile.name))
        }

        readout.plate = ownerplate
        readout.assayType = "Cell Titer Blue"
        readout.typeOfReadout = "Fluorescence"
        readout.resultFile = newResultFile
        readout.save(failOnError: true)

        def rows = sheet.rowIterator()

        boolean first = true
        rows.each { row ->

            //first row is header, ignore it
            if(row.rowNum == 0){
                first = false
                return
            }

            def plateNr = row.getCell(0).getNumericCellValue()
            def repeat = row.getCell(1).getNumericCellValue()
            def type = row.getCell(3).getStringCellValue()
            def time = row.getCell(4).getDateCellValue()

            def wellPosition = row.getCell(2).getStringCellValue()
            def cellTiter = row.getCell(5).getNumericCellValue()

            def newWellReadout = new WellReadout()
            newWellReadout.measuredValue = cellTiter

            //Convert fx "A02" to row and column numbers
            newWellReadout.row = LetterToRow(wellPosition.charAt(0))
            newWellReadout.col = Integer.valueOf(wellPosition.substring(1, wellPosition.length()))
            newWellReadout.readout = readout
            newWellReadout.save(failOnError: true)
            readout.addToWells(newWellReadout)
        }
        readout.save(failOnError: true)
    }

    def private LetterToRow(char letter){
        letter = letter.toUpperCase()
        return(Character.getNumericValue(letter)-9)
    }
}
