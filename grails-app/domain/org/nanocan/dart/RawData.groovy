package org.nanocan.dart

class RawData {

    Integer run
    Date startTime
    String barcode
    Double measuredValue
    String sampleName
    Double volume
    Integer wellNum
    Integer labwareId

    static mapping = {
        datasource 'DART'
        table 'RawDataSet1'

        version false
        id column: 'Well', generator: "assigned"

        run column: 'Run'
        startTime column: 'run_start_time'
        barcode column: 'barcode'
        measuredValue column:  'RawDataSet1'
        sampleName column:  'sampleName'
        volume column: 'well_volume'
        wellNum column: 'well_index'
        labwareId column: 'labware_id'
    }

    static constraints = {
        measuredValue nullable: true
        sampleName nullable:  true
    }

    String toString()
    {
        "Run " + run.toString() + " | Well " + wellNum.toString() + ": " + measuredValue?:"NA"
    }
}
