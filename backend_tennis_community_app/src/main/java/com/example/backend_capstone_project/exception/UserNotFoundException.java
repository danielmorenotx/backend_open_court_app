package com.example.backend_capstone_project.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message); // Call the constructor of RuntimeException with the error message
    }
}
