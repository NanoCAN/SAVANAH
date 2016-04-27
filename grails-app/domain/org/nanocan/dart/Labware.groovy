package org.nanocan.dart

class Labware {

    Integer id
    Integer run
    Integer numOfCols
    Integer numOfRows
    String barcode
    String labwareType

    static searchable = {
        root false
    }

    static mapping = {
        datasource 'DART'
        table 'Labware_Details'

        version false
        id column: 'labware_id', generator: 'assigned'
        run column: 'run_id'
        barcode column: 'labware_barcode'
        numOfRows column: 'labware_row_count'
        numOfCols column: 'labware_column_count'
        labwareType column: 'labware_type'
    }

    static constraints = {
        barcode nullable:true
        }

    String toString()
    {
        barcode?:id.toString()
    }
}
