package org.nanocan.savanah.plates

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON
import org.nanocan.dart.RawData
import org.nanocan.savanah.library.Library
import grails.plugins.springsecurity.Secured
import org.nanocan.dart.Labware

@Secured(['ROLE_USER'])
class PlateController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def importReadoutData(){

    }

    def getDataOrigins(){

        def labwares = Labware.findAllByBarcode(params.id)
        def plateInstance = Plate.findById(params.id)

        render (template: "data_origins", model:[plateInstance: plateInstance, labwares: labwares, barcode: params.id])
    }

    def getRawValues(){
        println params

        def rawData = RawData.findAllByBarcodeAndRun("E1S1M1D1", 522).sort{ a, b -> a.wellNum <=> b.wellNum}

        render(template: "heatmap", model: [rawData: rawData, plateInstance: Plate.get(params.id)])
    }

    def scaffold = true
}
