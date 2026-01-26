package com.BackendMiniProject.SmartHomeDeviceManagementSystem.Exceptions;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message){
        super(message);
    }
}
