package org.nanocan.savanah.plates

import org.nanocan.file.ResultFile

class Readout {

    static belongsTo = [plate: Plate]

    String typeOfReadout
    int wavelength
    String assayType
    static hasMany = [wells: WellReadout]
    ResultFile resultFile
    ResultFile resultImage
    ResultFile protocol
    ResultFileConfig lastConfig

    static constraints = {
        lastConfig nullable: true
        resultImage nullable: true
        protocol nullable: true
        typeOfReadout inList: ['Fluorescence']
        assayType inList: ['Cell Titer Blue']
    }
}
