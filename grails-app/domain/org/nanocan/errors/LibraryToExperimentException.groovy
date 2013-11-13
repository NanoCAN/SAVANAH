package org.nanocan.errors


// Creating a custom exception for easier error handling
public class LibraryToExperimentException extends Exception{
    public LibraryToExperimentException(String message){
        super(message)
    }
}