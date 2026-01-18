package com.LibraryManagement2.ProductionReadyLib.Exceptions;

public class BookNotAvailableException extends RuntimeException{
    public BookNotAvailableException(String message){
        super(message);
    }
}
