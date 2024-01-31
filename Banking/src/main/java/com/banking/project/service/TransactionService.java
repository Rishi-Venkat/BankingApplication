package com.banking.project.service;

import org.springframework.http.ResponseEntity;

import com.banking.project.dto.AddMoneyDTO;
import com.banking.project.dto.Response;
import com.banking.project.model.Transaction;

public interface TransactionService {

	ResponseEntity<Response> sendMoney(Transaction transaction);

	ResponseEntity<Response> addMoney(AddMoneyDTO addMoneyDTO);

}
