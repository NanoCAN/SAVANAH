package org.nanocan.io

import org.apache.commons.io.IOUtils

import java.util.zip.ZipFile

class UnzipService {

    def HashMap<String, File> Unpack(InputStream iStream) {


        def zipFile = new ZipFile(getFileFromStream(iStream, ".zip"))

        def unpackedList = new HashMap<String, File>()

        // Do stuff for each entry
        zipFile.entries().each {
            innerFile ->

                // Only files
                if(innerFile.isDirectory()){
                    return
                }

                if(!innerFile.name.contains(".")){
                    return
                }

                String suffixWithDot = innerFile.name.substring(innerFile.name.lastIndexOf("."))

                unpackedList.put(
                        innerFile.name,
                        getFileFromStream(
                                zipFile.getInputStream(innerFile),
                                suffixWithDot
                        )
                )

        }

        return unpackedList
    }

    def getFileFromStream(InputStream inputStream, String suffixWithDot){

        // Creating a temp file
        File tempFile = File.createTempFile("newTempFile", suffixWithDot)

        // Deletes the file once we're done using it
        tempFile.deleteOnExit()

        // Copy the zip-file-stream -> temp file -> ZipFile
        FileOutputStream out = new FileOutputStream(tempFile)
        IOUtils.copy(inputStream, out);

        return tempFile
    }
}