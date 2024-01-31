package com.banking.project.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.banking.project.exception.AccountNotFoundException;
import com.banking.project.model.Account;
import com.banking.project.repository.AccountRepository;


@Service
public class CustomService {
	
	@Autowired
	 AccountRepository accountRepository;
	
	public boolean checkSenderReceiver(String sender, String receiver) {
		return accountRepository.findById(sender).isPresent() && accountRepository.findById(receiver).isPresent() ?true:false;
		}
	
	public boolean checkBalance(String sender, double transferAmmount) {
		Optional<Account> acc= accountRepository.findById(sender);
		Account foundSender= acc.get();
		return (foundSender.getAccountBalance() >= transferAmmount)  ? true : false;
		}
	
	public double balanceInAccount(String accountID) {
		Optional<Account> acc= accountRepository.findById(accountID);
		if(acc.isPresent()) {
			Account foundAccount= acc.get();
			return foundAccount.getAccountBalance();
		}
		return -1;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateBalance(String accountId, double balance) {
        Optional<Account> acc = accountRepository.findById(accountId);

        if (acc.isPresent()) {
            Account foundAccount = acc.get();
            foundAccount.setAccountBalance(balance);
            accountRepository.save(foundAccount);
            } else {
                throw new AccountNotFoundException("Account with ID " + accountId + " not found");
            }
       }

	public boolean checkAccount(String accountId) {
		// TODO Auto-generated method stub
		Optional<Account> acc= accountRepository.findById(accountId);
		return acc.isPresent()?true :false;
			
	}
	
	
	
	
}
