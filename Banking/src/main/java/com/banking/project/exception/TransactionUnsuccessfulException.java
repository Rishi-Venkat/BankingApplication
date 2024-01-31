package com.banking.project.exception;

public class TransactionUnsuccessfulException extends RuntimeException{
	public TransactionUnsuccessfulException(String ex) {
		super(ex);
	}
}