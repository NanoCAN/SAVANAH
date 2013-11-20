package org.nanocan.io

import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.springframework.web.multipart.MultipartFile

class XlsxToReadoutService {
    def unzipService

    def parseToReadout(MultipartFile file){

        String suffixWithDot = file.originalFilename.substring(file.originalFilename.lastIndexOf("."))
        if(suffixWithDot != ".xls" && suffixWithDot != ".xlsx"){
            return(false)
        }

        File excelFile = unzipService.getFileFromStream(file.getInputStream(), suffixWithDot)

        parseToReadout(excelFile)
    }


    def parseToReadout(File excelFile) {

        String suffixWithDot = excelFile.name.substring(excelFile.name.lastIndexOf("."))
        if(suffixWithDot != ".xls" && suffixWithDot != ".xlsx"){
            return(false)
        }

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
            return(false)
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
            return(false)
        }

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
            def well = row.getCell(2).getStringCellValue()
            def type = row.getCell(3).getStringCellValue()
            def time = row.getCell(4).getDateCellValue()
            def cellTiter = row.getCell(5).getNumericCellValue()


            //println(String.format("[%f, %f, %s, %s, %s, %f]",plateNr,repeat,well,type,time.toString(), cellTiter))
        }
    }
}
