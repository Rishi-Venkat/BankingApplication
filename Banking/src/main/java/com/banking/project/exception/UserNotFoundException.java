package com.banking.project.exception;

@SuppressWarnings("serial")
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String userId) {
        super("User not found with ID: " + userId);
    }
}

