package com.banking.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.banking.project.dto.Response;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Response> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(new Response(HttpStatus.NOT_FOUND.value(), null, ex.getMessage()));
    }

	@ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<Response> handleAccountNotFoundException(AccountNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Response(HttpStatus.NOT_FOUND.value(), null, ex.getMessage()));
    }
	
	@ExceptionHandler(SenderWithoutEnoughBalanceException.class)
    public ResponseEntity<Response> senderWithoutEnoughBalanceException(SenderWithoutEnoughBalanceException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Response(HttpStatus.BAD_REQUEST.value(), null, ex.getMessage()));
    }
	
	@ExceptionHandler(SenderReceiverNotFoundException.class)
	public ResponseEntity<Response> sendeReceiverNotFoundException(SenderReceiverNotFoundException ex){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Response(HttpStatus.BAD_REQUEST.value(), null, ex.getMessage()));
	}
	
	@ExceptionHandler(TransactionUnsuccessfulException.class)
	public ResponseEntity<Response> transactionUnsuccessfulException(TransactionUnsuccessfulException ex){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Response(HttpStatus.BAD_REQUEST.value(), null, ex.getMessage()));
	}
	
	@ExceptionHandler(AccountAlreadyLinkedException.class)
	public ResponseEntity<Response> AccountAlreadyLinkedException(AccountAlreadyLinkedException ex){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Response(HttpStatus.BAD_REQUEST.value(), null, ex.getMessage()));
	}
	
	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<Response> UserAlreadyExistsException(UserAlreadyExistsException ex){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Response(HttpStatus.BAD_REQUEST.value(), null, ex.getMessage()));
	}
	
	@ExceptionHandler(AccountNumberAlreadyTaken.class)
	public ResponseEntity<Response> AccountNumberAlreadyTaken(AccountNumberAlreadyTaken ex){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Response(HttpStatus.BAD_REQUEST.value(), null, ex.getMessage()));
	}
}


