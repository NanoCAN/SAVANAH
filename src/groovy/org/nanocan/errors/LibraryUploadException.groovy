package org.nanocan.errors

class LibraryUploadException extends Exception{

    def invalidLibPlate

    public LibraryUploadException(String message){
        super(message)
    }

    public LibraryUploadException(String message, def invalidObject){
        super(message)
        this.invalidLibPlate = invalidObject
    }
}
