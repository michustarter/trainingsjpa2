package com.capgemini.util;

public class EmployeeAlreadyExistsException extends RuntimeException{


    public EmployeeAlreadyExistsException(String message) {
    super(message);
    }
}