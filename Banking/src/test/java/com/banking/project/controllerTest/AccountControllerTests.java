package com.banking.project.controllerTest;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.banking.project.controller.AccountController;
import com.banking.project.dto.LinkUnlinkDTO;
import com.banking.project.dto.Response;
import com.banking.project.model.Account;
import com.banking.project.service.AccountServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class AccountControllerTests {

	@Mock
	AccountServiceImpl accountService;

	@InjectMocks
	AccountController accountController;

	@Test
	public void testCreateAccount() {
		Account sampleAccount = new Account();
		sampleAccount.setAccountId("123");
		sampleAccount.setLinkedUserId("456");
		sampleAccount.setAccountNumber("789");
		sampleAccount.setAccountBalance(1000.0);
		sampleAccount.setAccountStatus("ACTIVE");
		sampleAccount.setAccountCreatedAt(LocalDateTime.now());
		sampleAccount.setAccountUpdatedAt(LocalDateTime.now());

		when(accountService.createAccount(any(Account.class))).thenReturn(new ResponseEntity<>(
				new Response(201, sampleAccount, "Account created successfully"), HttpStatus.CREATED));

		ResponseEntity<Response> responseEntity = accountController.createAccount(sampleAccount);

		// Assert
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

		Response responseBody = responseEntity.getBody();
		assertNotNull(responseBody);
		assertEquals(201, responseBody.getStatus());
		assertEquals("Account created successfully", responseBody.getMessage());
		assertNotNull(responseBody.getBody());
	}

	@Test
	public void testUpdateAccount() {
		String sampleAccountId = "123";

		// Create a sample updated Account object for testing
		Account updatedAccount = new Account();
		updatedAccount.setAccountId(sampleAccountId);
		updatedAccount.setLinkedUserId("456");
		updatedAccount.setAccountNumber("789");
		updatedAccount.setAccountBalance(2000.0);
		updatedAccount.setAccountStatus("UPDATED");
		updatedAccount.setAccountCreatedAt(LocalDateTime.now());
		updatedAccount.setAccountUpdatedAt(LocalDateTime.now());

		when(accountService.updateAccount(sampleAccountId, updatedAccount)).thenReturn(
				new ResponseEntity<>(new Response(200, updatedAccount, "Account updated successfully"), HttpStatus.OK));

		ResponseEntity<Response> responseEntity = accountController.updateAccount(sampleAccountId, updatedAccount);

		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		Response responseBody = responseEntity.getBody();
		assertNotNull(responseBody);
		assertEquals(200, responseBody.getStatus());
		assertEquals("Account updated successfully", responseBody.getMessage());
		assertNotNull(responseBody.getBody());
	}

	@Test
	public void testDeleteAccount() {
		String sampleAccountId = "123";

		when(accountService.deleteAccount(sampleAccountId)).thenReturn(
				new ResponseEntity<>(new Response(204, null, "Account deleted successfully"), HttpStatus.NO_CONTENT));

		ResponseEntity<Response> responseEntity = accountController.deleteAccount(sampleAccountId);

		assertNotNull(responseEntity);
		assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());

		Response responseBody = responseEntity.getBody();
		assertNotNull(responseBody);
		assertEquals(204, responseBody.getStatus());
		assertEquals("Account deleted successfully", responseBody.getMessage());
		assertNull(responseBody.getBody());
	}

	@Test
	public void testGetAccountById() {
		String sampleAccountId = "123";

		Account sampleAccount = new Account();
		sampleAccount.setAccountId(sampleAccountId);
		sampleAccount.setLinkedUserId("456");
		sampleAccount.setAccountNumber("789");
		sampleAccount.setAccountBalance(1500.0);
		sampleAccount.setAccountStatus("ACTIVE");
		sampleAccount.setAccountCreatedAt(LocalDateTime.now());
		sampleAccount.setAccountUpdatedAt(LocalDateTime.now());

		when(accountService.getAccountById(sampleAccountId)).thenReturn(new ResponseEntity<>(
				new Response(200, sampleAccount, "Account retrieved successfully"), HttpStatus.OK));

		ResponseEntity<Response> responseEntity = accountController.getAccountById(sampleAccountId);

		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		Response responseBody = responseEntity.getBody();
		assertNotNull(responseBody);
		assertEquals(200, responseBody.getStatus());
		assertEquals("Account retrieved successfully", responseBody.getMessage());
		assertNotNull(responseBody.getBody());
	}

	@Test
	public void testLinkAccountWithUser() {
		LinkUnlinkDTO linkDTO = new LinkUnlinkDTO();
		linkDTO.setUserId("123");
		linkDTO.setAccountId("456");

		when(accountService.linkAccountWithUser(linkDTO)).thenReturn(new ResponseEntity<>(
				new Response(201, null, "Account linked with user successfully"), HttpStatus.CREATED));

		ResponseEntity<Response> responseEntity = accountController.linkAccountWithUser(linkDTO);

		assertNotNull(responseEntity);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

		Response responseBody = responseEntity.getBody();
		assertNotNull(responseBody);
		assertEquals(201, responseBody.getStatus());
		assertEquals("Account linked with user successfully", responseBody.getMessage());
		assertNull(responseBody.getBody());
	}

	@Test
	public void testUnLinkAccountWithUser() {
		LinkUnlinkDTO unLinkDTO = new LinkUnlinkDTO();
		unLinkDTO.setUserId("123");
		unLinkDTO.setAccountId("456");

		when(accountService.unLinkAccountWithUser(unLinkDTO)).thenReturn(new ResponseEntity<>(
				new Response(204, null, "Account unlinked from user successfully"), HttpStatus.NO_CONTENT));

		ResponseEntity<Response> responseEntity = accountController.unLinkAccountWithUser(unLinkDTO);

		assertNotNull(responseEntity);
		assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());

		Response responseBody = responseEntity.getBody();
		assertNotNull(responseBody);
		assertEquals(204, responseBody.getStatus());
		assertEquals("Account unlinked from user successfully", responseBody.getMessage());
		assertNull(responseBody.getBody());
	}

}
