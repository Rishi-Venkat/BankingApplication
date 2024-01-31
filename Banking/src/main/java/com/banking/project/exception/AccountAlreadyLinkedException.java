package com.banking.project.exception;

public class AccountAlreadyLinkedException extends RuntimeException {
	public AccountAlreadyLinkedException(String message) {
        super(message);
    }
}
