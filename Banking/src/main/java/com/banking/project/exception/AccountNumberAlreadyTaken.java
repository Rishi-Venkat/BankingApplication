package com.banking.project.exception;

public class AccountNumberAlreadyTaken extends RuntimeException {
	public AccountNumberAlreadyTaken(String msg) {
		super(msg);
	}
}
