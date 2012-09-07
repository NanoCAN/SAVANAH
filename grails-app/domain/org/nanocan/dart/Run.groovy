package org.nanocan.dart

class Run {

    Integer id
    Date startTime
    String techniques
    String preparations
    String instrumentName
    boolean completed
    String method
    String application
    String project

    static mapping = {
        datasource 'DART'
        table 'Run_Details'

        version false
        id column: 'Run', generator: "assigned"

        startTime column: 'run_start_time'
        techniques column: 'run_paradigm_techniques'
        completed column:  'run_completed_successfully'
        preparations column:  'run_paradigm_preparations'
        instrumentName column: 'run_paradigm_instrument_name'
        method column: 'run_method'
        application column:  'run_application'
        project column: 'run_project'
    }

    static constraints = {
        id()
        startTime()
        method()
        completed()
    }

    String toString()
    {
        "Run " + id.toString() + "( " + startTime.toString() + ") :" + method.toString()
    }
}
