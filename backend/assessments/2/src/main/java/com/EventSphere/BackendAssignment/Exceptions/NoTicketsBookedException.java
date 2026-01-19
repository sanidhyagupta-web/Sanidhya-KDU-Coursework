package com.EventSphere.BackendAssignment.Exceptions;

public class NoTicketsBookedException extends RuntimeException{
    public NoTicketsBookedException(String message){
        super(message);
    }
}
