package com.banking.project.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banking.project.dto.AddMoneyDTO;
import com.banking.project.dto.Response;
import com.banking.project.exception.AccountNotFoundException;
import com.banking.project.exception.SenderReceiverNotFoundException;
import com.banking.project.exception.SenderWithoutEnoughBalanceException;
import com.banking.project.exception.TransactionUnsuccessfulException;
import com.banking.project.model.Transaction;
import com.banking.project.repository.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	CustomService customService;

	@Autowired
	TransactionRepository transactionRepository;

	@Override
	@Transactional
	public ResponseEntity<Response> sendMoney(Transaction transaction) {
		final String tID = UUID.randomUUID().toString();
		transaction.setTansactionId(tID);

		String sender = transaction.getSenderAccountId();
		String receiver = transaction.getReceiverAccountId();
		double transferAmount = transaction.getTransactionAmmount();

		// Verify whether sender and receiver exist in the database
		boolean bothSenderReceiverExist = customService.checkSenderReceiver(sender, receiver);

		if (!bothSenderReceiverExist) {
			throw new SenderReceiverNotFoundException("Sender or receiver does not exist");
		}

		// Verify whether sender has enough amount in the account
		boolean senderWithEnoughBalance = customService.checkBalance(sender, transferAmount);

		if (!senderWithEnoughBalance) {
			throw new SenderWithoutEnoughBalanceException("Sender does not have enough balance");
		}

		double senderBalance = customService.balanceInAccount(sender);
		double receiverBalance = customService.balanceInAccount(receiver);

		// Perform credit and debit operations
		double debitBalance = senderBalance - transferAmount;
		double creditBalance = receiverBalance + transferAmount;

		// Update the balances in the database
		customService.updateBalance(sender, debitBalance);
		customService.updateBalance(receiver, creditBalance);

		transaction.setTransactionStatus("Transaction Completed");
		transaction.setTransactionAt(LocalDateTime.now());
		// You may want to return a success response
		transactionRepository.save(transaction);

//		if (customService.balanceInAccount(sender) == debitBalance
//				&& customService.balanceInAccount(receiver) == creditBalance) {
//			return ResponseEntity.status(HttpStatus.OK)
//					.body(new Response(HttpStatus.OK.value(), transaction, "Transaction successful"));
//		}else {
//		customService.updateBalance(sender, senderBalance);
//		throw new TransactionUnsuccessfulException("Something Went Wrong!! Transaction Unsuccessful");
//		}
		if (customService.balanceInAccount(sender) == debitBalance
		        && customService.balanceInAccount(receiver) == creditBalance) {
		    return ResponseEntity.status(HttpStatus.OK)
		            .body(new Response(HttpStatus.OK.value(), transaction, "Transaction successful"));
		} else {
		    customService.updateBalance(sender, senderBalance);
		    throw new TransactionUnsuccessfulException("Something Went Wrong!! Transaction Unsuccessful");
		}

	}

	@Override
	public ResponseEntity<Response> addMoney(AddMoneyDTO addMoneyDTO) {
		// TODO Auto-generated method stub
		Transaction transaction= new Transaction(UUID.randomUUID().toString(),addMoneyDTO.getAccountId(),"NULL", addMoneyDTO.getAmmount(),
				"Added Money",LocalDateTime.now());
		boolean accountExist = customService.checkAccount(addMoneyDTO.getAccountId());
		if (!accountExist) {
			transaction.setTransactionStatus("No Account found!! Transaction Failed");
			transactionRepository.save(transaction);
			throw new AccountNotFoundException("Account with ID " + addMoneyDTO.getAccountId() + " not found");
		}
		customService.updateBalance(addMoneyDTO.getAccountId(), addMoneyDTO.getAmmount());
		transactionRepository.save(transaction);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new Response(HttpStatus.OK.value(), transaction, "Transaction successful"));
	}

}
