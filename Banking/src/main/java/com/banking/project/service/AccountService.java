package com.banking.project.service;

import org.springframework.http.ResponseEntity;

import com.banking.project.dto.LinkUnlinkDTO;
import com.banking.project.dto.Response;
import com.banking.project.model.Account;

public interface AccountService {

	ResponseEntity<Response> createAccount(Account account);

	ResponseEntity<Response> updateAccount(String accountId, Account account);

	ResponseEntity<Response> deleteAccount(String accountId);

	ResponseEntity<Response> getAccountById(String accountId);

	ResponseEntity<Response> linkAccountWithUser(LinkUnlinkDTO linkDTO);

	ResponseEntity<Response> unLinkAccountWithUser(LinkUnlinkDTO unLinkDTO);

}
