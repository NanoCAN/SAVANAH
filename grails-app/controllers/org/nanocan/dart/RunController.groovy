package org.nanocan.dart

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON

class RunController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def browser() {
        [runInstanceList: Run.list()]
    }

    def runsAsJSON(){
        def runs

        def prefixLength = params.id.size()

        if (params.int("id") == 0)
        {
            runs = Run.list(sort: "id", order:"desc")

            def jsonRuns = runs.collect{
                [
                        "data" : it.id.toString() + "(" + it.startTime.toLocaleString() + ")",
                        "attr" : [ "id" : it.id , "nodeType" : "run"],
                        "state" : "closed"
                ]
            }

            render jsonRuns as JSON
        }

        else if (params.nodeType == "run")
        {
            def jsonPlates = Labware.findAllWhere(run: params.int("id")).collect{
                [
                        "data" : it.barcode?:it.id.toString(),
                        "attr" : ["id" : it.id, "run": params.id, "barcode" : it.barcode, "rel":"plate", "nodeType" : "plate"],
                ]
            }

            render jsonPlates as JSON
        }
    }

    def getRawValues(){
        def rawData = RawData.findAllByLabwareIdAndRun(params.id, params.run).sort{ a, b -> a.wellNum <=> b.wellNum}

        if(rawData.collect{it.measuredValue}.unique().size() > 1)
        {
            render(template: "/plate/heatmap", model: [rawData: rawData, plateInstance: Plate.get(params.id)])
        }
        else {
            render "No measurement data is associated with this plate in this run."
        }

    }

    def index() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        params.sort = params.sort?:"startTime"
        params.order = params.order?:"asc"
        [runInstanceList: Run.list(params), runInstanceTotal: Run.count()]
    }

    def create() {
        [runInstance: new Run(params)]
    }

    def save() {
        def runInstance = new Run(params)
        if (!runInstance.save(flush: true)) {
            render(view: "create", model: [runInstance: runInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'run.label', default: 'Run'), runInstance.id])
        redirect(action: "show", id: runInstance.id)
    }

    def show() {
        def runInstance = Run.get(params.id)
        if (!runInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'run.label', default: 'Run'), params.id])
            redirect(action: "index")
            return
        }

        [runInstance: runInstance]
    }

    def edit() {
        def runInstance = Run.get(params.id)
        if (!runInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'run.label', default: 'Run'), params.id])
            redirect(action: "index")
            return
        }

        [runInstance: runInstance]
    }

    def update() {
        def runInstance = Run.get(params.id)
        if (!runInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'run.label', default: 'Run'), params.id])
            redirect(action: "index")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (runInstance.version > version) {
                runInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'run.label', default: 'Run')] as Object[],
                          "Another user has updated this Run while you were editing")
                render(view: "edit", model: [runInstance: runInstance])
                return
            }
        }

        runInstance.properties = params

        if (!runInstance.save(flush: true)) {
            render(view: "edit", model: [runInstance: runInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'run.label', default: 'Run'), runInstance.id])
        redirect(action: "show", id: runInstance.id)
    }

    def delete() {
        def runInstance = Run.get(params.id)
        if (!runInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'run.label', default: 'Run'), params.id])
            redirect(action: "index")
            return
        }

        try {
            runInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'run.label', default: 'Run'), params.id])
            redirect(action: "index")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'run.label', default: 'Run'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
