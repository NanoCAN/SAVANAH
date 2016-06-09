#dependencies
library(RJSONIO)
library(dplyr)
library(plyr)
library(Hmisc)
library(RCurl)
library(foreach)
#library(logging)

#import single readout
import.readout <- function(connection=NULL, securityToken=NA, readoutIndex=NA, baseUrl = "http://localhost:8080/SAVANAH/readoutExport/"){

    connection <- check.connection(connection, securityToken, baseUrl)
    if(!is.na(securityToken) && is.na(readoutIndex)) readoutIndex <- scan(text=getURL(paste(baseUrl, "getReadoutIdFromSecurityToken/", securityToken, sep = ""), curl=connection), what="integer")

    #read the data from database
    cat(paste("Fetching readout for id", readoutIndex, "\n"))
    readoutUrl <- paste(baseUrl, "exportAsJSON/", readoutIndex, sep = "")
    if(!is.na(securityToken)) readoutUrl <- paste(readoutUrl, "?securityToken=", securityToken, sep="")

    wells <- getURL(readoutUrl, curl=connection)
    wells <- ldply(RJSONIO::fromJSON(wells, simplify = T, nullValue = NA))
    cat(paste(dim(wells)[1], "well readouts downloaded. Formatting...\n"))

    metaUrl <- paste(baseUrl, "exportMetaDataAsJSON/", readoutIndex, sep = "")
    if(!is.na(securityToken)) metaUrl <- paste(metaUrl, "?securityToken=", securityToken, sep="")

    meta <- getURL(metaUrl, curl=connection)
    meta <- RJSONIO::fromJSON(meta, simplify=T)
    colnames(wells) <- meta
    wells <- reformatPlateColTypes(wells)

    return(wells)
}

import.layout <- function(connection=NULL, securityToken=NA, plateLayoutIndex=NA, baseUrl = "http://localhost:8080/SAVANAH/readoutExport/"){

    connection <- check.connection(connection, securityToken, baseUrl)
    if(!is.na(securityToken) && is.na(plateLayoutIndex)) plateLayoutIndex <- scan(text=getURL(paste(baseUrl, "getPlateLayoutIdFromSecurityToken/", securityToken, sep = ""), curl=connection), what="integer")

    #read the data from database
    cat(paste("Fetching plate layout for id", plateLayoutIndex, "\n"))
    plateLayoutUrl <- paste(baseUrl, "exportPlateLayoutAsJSON/", plateLayoutIndex, sep = "")
    if(!is.na(securityToken)) plateLayoutUrl <- paste(plateLayoutUrl, "?securityToken=", securityToken, sep="")

    wells <- getURL(plateLayoutUrl, curl=connection)
    wells <- ldply(RJSONIO::fromJSON(wells, simplify = T, nullValue = NA))
    cat(paste(dim(wells)[1], "well layouts downloaded. Formatting...\n"))

    metaUrl <- paste(baseUrl, "exportPlateLayoutMetaDataAsJSON/", plateLayoutIndex, sep = "")
    if(!is.na(securityToken)) metaUrl <- paste(metaUrl, "?securityToken=", securityToken, sep="")

    meta <- getURL(metaUrl, curl=connection)
    meta <- RJSONIO::fromJSON(meta, simplify=T)
    colnames(wells) <- meta
    wells <- reformatPlateColTypes(wells, layout=TRUE)

    return(wells)
}

reformatPlateColTypes <- function(plate, layout=FALSE)
{
    browser()

    plate$id <- as.integer(plate$id)
    plate$PlateRow <- as.integer(plate$PlateRow)
    plate$PlateCol <- as.integer(plate$PlateCol)
    plate$PlateLayout <- as.integer(plate$PlateLayout)

    if(!layout){
        plate$Plate <- as.integer(plate$Plate)
        plate$PlateReadout <- as.numeric(plate$PlateReadout)
        plate$Replicate <- as.integer(plate$Replicate)
        plate$DateOfReadout <- as.Date(as.POSIXct(as.numeric(substring(plate$DateOfReadout, 1, 10)), origin="1970-01-01"))
    }
    if(layout){
        plate %>% dplyr::group_by_(setdiff(colnames(plate), "Accession")) %>%
            dplyr::summarize(Accession = list(Accession))
    }

    return(plate)
}

batch.import.readouts <- function(connection=NULL, readoutSecurityTokens=NULL, plateSecurityTokens=NULL, readoutIndices=NULL, baseUrl = "http://localhost:8080/SAVANAH/readoutExport/"){
    if(is.null(readoutSecurityTokens) &&is.null(plateSecurityTokens) && is.null(readoutIndices)) stop("you have to provide securityTokens or indices together with a connection object.")

    if(is.null(readoutSecurityTokens) && !is.null(plateSecurityTokens))
    {
        connection <- check.connection(connection, plateSecurityTokens[1], baseUrl)

        readoutSecurityTokens <- foreach(token=plateSecurityTokens, .combine=cbind) %do% {
            readoutTokens <- getURL(paste(baseUrl, "getReadoutSecurityTokensFromPlateSecurityToken/", token, sep = ""), curl=connection)
            readoutTokens <- RJSONIO::fromJSON(readoutTokens, simplify = T, nullValue = NA)
            if(length(readoutTokens) == 0) warning(paste("No readout data was found for plate", token))
            else return(readoutTokens)
        }
        readoutSecurityTokens <- unique(as.character(readoutSecurityTokens))

        plateLayoutSecurityTokens <- foreach(token=plateSecurityTokens, .combine=cbind) %do% {
            plateTokens <- getURL(paste(baseUrl, "getPlateLayoutSecurityTokenFromPlateSecurityToken/", token, sep = ""), curl=connection)
            plateTokens <- RJSONIO::fromJSON(plateTokens, simplify = T, nullValue = NA)
            if(length(plateTokens) > 1) stop("An error occured. It should not be possible to have several plate layouts linked to a single plate.")
            else if(length(plateTokens) == 0) warning(paste("No plate layout data was found for plate", token))
            else return(plateTokens)
        }
        plateLayoutSecurityTokens <- unique(as.character(plateLayoutSecurityTokens))
    }

    if(!is.null(readoutSecurityTokens)){
        readout.data <- foreach(token=readoutSecurityTokens, .combine=rbind) %do%{
            readout <- import.readout(connection, token, baseUrl=baseUrl)
        }
    }

    if(!is.null(plateLayoutSecurityTokens)){
        layout.data <- foreach(token=plateLayoutSecurityTokens, .combine=rbind) %do%{
            layout <- import.layout(connection, token, baseUrl=baseUrl)
        }
    }

    else if(!is.null(connection) && !is.null(readoutIndices)){
        result <- foreach(index=readoutIndices, .combine=rbind) %do% import.readout(connection, readoutIndex=index, baseUrl=baseUrl)
    }

    result <- dplyr::left_join(layout.data, readout.data, by=c("PlateLayout", "PlateRow", "PlateCol"))
    return(result)
}

check.connection <- function(connection=NULL, securityToken=NA, baseUrl){
    #no means of authentication given
    if(is.null(connection) && is.na(securityToken)){
        cat("Cannot authenticate. Either login using authenticate or provide a security token\n")
    }

    #authentication via securityToken
    if(is.null(connection) && !is.na(securityToken)){
        cat("Using security token authentication\n")

        #create curl handle without authentication
        agent="Mozilla/5.0" #or whatever

        #Set RCurl pars
        connection = getCurlHandle()
        curlSetOpt(ssl.verifypeer=FALSE, timeout=60, cookiefile=tempfile(), cookiejar=tempfile(), useragent = agent, followlocation = TRUE, curl=connection, verbose=FALSE)

        #first check if security token is valid
        isTokenValid <- scan(text=capitalize(getURL(paste(baseUrl, "isSecurityTokenValid/", securityToken, sep = ""), curl=connection)), what=TRUE)
        if(!isTokenValid){
            stop("Security token is invalid!\n")
        }
    }
    else{
        message("Using user authentication\n")
    }
    return(connection)
}

authenticate <- function(baseUrl="http://localhost:8080/SAVANAH/", user, password, verbose=F){
    loginUrl = paste(baseUrl, "login/auth", sep="")
    authenticateUrl = paste(baseUrl, "j_spring_security_check", sep="")

    cat(paste("authenticating user", user))
    agent="Mozilla/5.0" #or whatever

    #Set RCurl pars
    curl = getCurlHandle()
    curlSetOpt(ssl.verifypeer=FALSE, timeout=60, cookiefile=tempfile(), cookiejar=tempfile(), useragent = agent, followlocation = TRUE, curl=curl, verbose=verbose)

    #open login page
    getURL(loginUrl, curl=curl)

    #Post login form
    postForm(authenticateUrl, .params= list(j_username=user, j_password=password), curl=curl, style="POST")

    return(curl)
}
