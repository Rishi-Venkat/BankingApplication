package com.banking.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.banking.project.dto.AddMoneyDTO;
import com.banking.project.dto.Response;
import com.banking.project.model.Transaction;
import com.banking.project.service.TransactionService;

@RestController
public class TransactionController {
	
	@Autowired
	TransactionService transactionService;
	
	@PostMapping("/transaction/send")
	public ResponseEntity<Response> sendMoney(@RequestBody Transaction transaction){
		return transactionService.sendMoney(transaction);
	}
	
	@PostMapping("/transaction/add")
	public ResponseEntity<Response> addMoney(@RequestBody AddMoneyDTO addMoneyDTO){
		return transactionService.addMoney(addMoneyDTO);
	}
}
