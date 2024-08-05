package com.example.backend_capstone_project.exception;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException(String message) {
        super(message); // Call the constructor of RuntimeException with the error message
    }
}
