/*
 * Copyright (C) 2014
 * Center for Excellence in Nanomedicine (NanoCAN)
 * Molecular Oncology
 * University of Southern Denmark
 * ###############################################
 * Written by:	Markus List
 * Contact: 	mlist'at'health'.'sdu'.'dk
 * Web:			http://www.nanocan.org/miracle/
 * ###########################################################################
 *	
 *	This file is part of MIRACLE.
 *
 *  MIRACLE is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *   along with this program. It can be found at the root of the project page.
 *	If not, see <http://www.gnu.org/licenses/>.
 *
 * ############################################################################
 */
package org.nanocan.analysis

import org.nanocan.layout.PlateLayout
import org.nanocan.plates.Plate
import org.nanocan.plates.Readout
import org.nanocan.project.Experiment
import org.nanocan.project.Project
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_USER'])
class AnalysisController {

    def securityTokenService

    def start() {
        def projects = Project.list()

        def experiments = params.project?Experiment.findAllByProject(Project.get(params.project)):Experiment.list()

        def plateLayouts

        if(params.experiment) plateLayouts = Experiment.get(params.experiment).plateLayouts

        else if(params.project){
            plateLayouts = []
            Project.get(params.project).experiments.each{ plateLayouts.addAll(it.plateLayouts) }
        }

        else plateLayouts = PlateLayout.list()

        def plates = []
        plateLayouts.each{plates.addAll(Plate.findAllByPlateLayout(it))}
        plates.unique()

        [projects: projects,
                selectedProject: params.int("project"),
                experiments: experiments,
                selectedExperiment: params.int("experiment"),
                plateLayouts: plateLayouts,
                selectedPlateLayout: params.int("plateLayout"),
                plates: plates,
        ]
    }

    def exportToR(){
        def baseUrl = getBaseUrl()
        def plates = getPlates(params)
        def plateSecurityTokens = plates.collect{securityTokenService.getSecurityToken(it)}.join("\",\n\"")

        [baseUrl: baseUrl, plateSecurityTokens: "c(\"${plateSecurityTokens}\")"]
    }

    private String getBaseUrl(){
        def baseUrl = grailsApplication?.config?.baseUrl
        if(!baseUrl){
            baseUrl = g.createLink(controller: "readoutExport", absolute: true).toString()
            baseUrl = baseUrl.substring(0, baseUrl.size()-5)
        }
        return(baseUrl)
    }

    private def getPlates(params){
        def plates

        if(params.processAll)
        {
            plates = params.list("allPlates").collect{Plate.get(it)}
        }

        else{
            plates = params.list("selectedPlates").collect{Plate.get(it)}
        }

        return(plates)
    }

    def hitseekrAnalysis(){
        def hitseekrUrl = grailsApplication?.config?.hitseekr?.url?:"http://localhost:8787"

        if(!hitseekrUrl){
            render "HiTSeekR URL not defined"
            return
        }

        def baseUrl = getBaseUrl()
        def dataExportLink = java.net.URLEncoder.encode(baseUrl, "UTF-8")

        def plates = getPlates(params)

        def plateSecurityTokens = plates.collect{securityTokenService.getSecurityToken(it)}.join("|")
        if(plateSecurityTokens == ""){
            render "No plates have been selected."
            return
        }

        boolean readoutFound

        plates.each{
            Readout readout = Readout.findByPlate(it)
            if(readout) readoutFound = true
        }
        if(!readoutFound){
            render "The selected plates have no readouts associated with them."
            return
        }

        def analysisUrl = hitseekrUrl + "?baseUrl=" +
                dataExportLink +
                "&screenType=" + params.screenType.toString() +
                "&plateSecurityTokens=" + plateSecurityTokens
        redirect(url: analysisUrl)
    }
}
