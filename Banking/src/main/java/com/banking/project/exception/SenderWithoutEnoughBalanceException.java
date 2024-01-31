package com.banking.project.exception;


public class SenderWithoutEnoughBalanceException extends RuntimeException {

    public SenderWithoutEnoughBalanceException(String message) {
        super(message);
    }
}
