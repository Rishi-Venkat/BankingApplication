package com.banking.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.project.dto.LinkUnlinkDTO;
import com.banking.project.dto.Response;
import com.banking.project.model.Account;
import com.banking.project.service.AccountService;

@RestController
@RequestMapping("/account")
public class AccountController {
	@Autowired
	AccountService accountService;

	@PostMapping("/create")
	public ResponseEntity<Response> createAccount(@RequestBody Account account) {
		return accountService.createAccount(account);
	}

	@PutMapping("/update/{accountId}")
	public ResponseEntity<Response> updateAccount(@PathVariable String accountId,@RequestBody Account account) {
		return accountService.updateAccount(accountId, account);
	}
	
	@DeleteMapping("/delete/{accountId}")
	public ResponseEntity<Response> deleteAccount(@PathVariable String accountId){
		return accountService.deleteAccount(accountId);
	}
	
	@GetMapping("/getby/{accountId}")
	public ResponseEntity<Response> getAccountById(@PathVariable String accountId) {
		return accountService.getAccountById(accountId);
	}
	
	@PostMapping("/link")
	public ResponseEntity<Response> linkAccountWithUser(@RequestBody LinkUnlinkDTO linkDTO){
		return accountService.linkAccountWithUser(linkDTO);
	}
	
	@PostMapping("/unlink")
	public ResponseEntity<Response> unLinkAccountWithUser(@RequestBody LinkUnlinkDTO unLinkDTO){
		return accountService.unLinkAccountWithUser(unLinkDTO);
	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
