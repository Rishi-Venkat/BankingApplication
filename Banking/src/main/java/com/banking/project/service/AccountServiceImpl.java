package com.banking.project.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.banking.project.dto.LinkUnlinkDTO;
import com.banking.project.dto.Response;
import com.banking.project.exception.AccountAlreadyLinkedException;
import com.banking.project.exception.AccountNotFoundException;
import com.banking.project.exception.AccountNumberAlreadyTaken;
import com.banking.project.model.Account;
import com.banking.project.model.User;
import com.banking.project.repository.AccountRepository;
import com.banking.project.repository.UserRepository;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	UserRepository userRepository;

	@Override
	public ResponseEntity<Response> createAccount(Account account) {
		// TODO Auto-generated method stub
		Optional<Account> optionalAccount = accountRepository.findByAccountNumber(account.getAccountNumber());
		if (!optionalAccount.isPresent()) {
			account.setAccountCreatedAt(LocalDateTime.now());
			account.setAccountUpdatedAt(LocalDateTime.now());
			Account createdAccount = accountRepository.save(account);

			return ResponseEntity.ok(new Response(HttpStatus.OK.value(), createdAccount, "Account Created"));
		}
		throw new AccountNumberAlreadyTaken("Account number " + account.getAccountNumber()+" already taken!! Use Another Account Number");
	}

	@Override
	public ResponseEntity<Response> updateAccount(String accountId, Account account) {
		// TODO Auto-generated method stub
		Optional<Account> optionalAccount = accountRepository.findById(accountId);
		if (optionalAccount.isPresent()) {
			Account existingAccount = optionalAccount.get();
			existingAccount.setAccountBalance(account.getAccountBalance());
			existingAccount.setAccountNumber(account.getAccountNumber());
			existingAccount.setAccountStatus(account.getAccountStatus());
			existingAccount.setAccountUpdatedAt(LocalDateTime.now());

			accountRepository.save(existingAccount);
			return ResponseEntity.ok(new Response(HttpStatus.OK.value(), existingAccount, "Account Updated"));
		}
//		 return ResponseEntity.ok(new Response(HttpStatus.NOT_FOUND.value(), null, "No Account found"));
		throw new AccountNotFoundException("Account with ID " + accountId + " not found");
	}

	@Override
	public ResponseEntity<Response> deleteAccount(String accountId) {
		// TODO Auto-generated method stub
		Optional<Account> optionalAccount = accountRepository.findById(accountId);
		if (optionalAccount.isPresent()) {
			accountRepository.deleteById(accountId);
			// return ResponseEntity.noContent().build();
			String userId = optionalAccount.get().getLinkedUserId();
			Optional<User> user = userRepository.findById(userId);
			if (user.get().getAccountsLinked().contains(accountId)) {
				user.get().getAccountsLinked().remove(accountId);
				userRepository.save(user.get());
			}
			return ResponseEntity
					.ok(new Response(HttpStatus.OK.value(), accountId + "is deleted", "Account is deleted"));
		}
//		return ResponseEntity.ok(new Response(HttpStatus.NOT_FOUND.value(), "NULL" , "Account not found"));
		throw new AccountNotFoundException("Account with ID " + accountId + " not found");
	}

	@Override
	public ResponseEntity<Response> getAccountById(String accountId) {
		// TODO Auto-generated method stub
		Optional<Account> optioanAccount = accountRepository.findById(accountId);
		if (optioanAccount.isPresent())
			return ResponseEntity.ok(new Response(HttpStatus.OK.value(), optioanAccount.get(), " Account found"));

		throw new AccountNotFoundException("Account with ID " + accountId + " not found");
//	ResponseEntity.ok(new Response(HttpStatus.NOT_FOUND.value(), "NULL", "No Account found"));
	}

	@Override
	public ResponseEntity<Response> linkAccountWithUser(LinkUnlinkDTO linkDTO) {
		String linkingUserId = linkDTO.getUserId();
		String linkingAccountId = linkDTO.getAccountId();

		Optional<User> foundUser = userRepository.findById(linkingUserId);
		Optional<Account> foundAccount = accountRepository.findById(linkingAccountId);

		if (foundUser.isPresent() && foundAccount.isPresent()) {
			User u = foundUser.get();
			List<String> allAccountLinkedWithUser = u.getAccountsLinked();

			// If the list is null, initialize it. As I got Null Pointer Exception
			if (allAccountLinkedWithUser == null) {
				allAccountLinkedWithUser = new ArrayList<>();
			}
			if (!(allAccountLinkedWithUser.contains(linkingAccountId))) {
				allAccountLinkedWithUser.add(linkingAccountId);
				u.setAccountsLinked(allAccountLinkedWithUser);
				userRepository.save(u);

				Account a = foundAccount.get();
				a.setLinkedUserId(linkingUserId);
				accountRepository.save(a);
				return ResponseEntity.ok(new Response(HttpStatus.OK.value(), a, "Account Linked"));
			}
			throw new AccountAlreadyLinkedException("User And Account Already Linked");
		}

		throw new AccountNotFoundException("No Account or User Found");
	}

	@Override
	public ResponseEntity<Response> unLinkAccountWithUser(LinkUnlinkDTO unLinkDTO) {
		String unLinkingUserId = unLinkDTO.getUserId();
		String unLinkingAccountId = unLinkDTO.getAccountId();

		Optional<User> foundUser = userRepository.findById(unLinkingUserId);
		Optional<Account> foundAccount = accountRepository.findById(unLinkingAccountId);
		if (foundUser.isPresent() && foundAccount.isPresent()) {
			User u = foundUser.get();
			Account a = foundAccount.get();
			List<String> allAccountLinkedWithUser = u.getAccountsLinked();
			if (allAccountLinkedWithUser.contains(unLinkingAccountId)) {
				// lambda expression to remove the specified account
				allAccountLinkedWithUser.removeIf(accountId -> accountId.equals(unLinkingAccountId));
				u.setAccountsLinked(allAccountLinkedWithUser);
				userRepository.save(u);
				a.setLinkedUserId(null);
				accountRepository.save(a);
				return ResponseEntity.ok(new Response(HttpStatus.OK.value(), a, "Account Unlinked"));
			}
			throw new AccountNotFoundException("No Account or User Found");
		}
		throw new AccountNotFoundException("No Account or User Found");
	}

}
