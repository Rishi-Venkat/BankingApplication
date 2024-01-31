package com.banking.project.controllerTest;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.banking.project.controller.TransactionController;
import com.banking.project.dto.AddMoneyDTO;
import com.banking.project.dto.Response;
import com.banking.project.model.Transaction;
import com.banking.project.service.TransactionServiceImpl;

//public class TransactionControllerTests {
//
//}

@RunWith(MockitoJUnitRunner.class)
public class TransactionControllerTests {

	@InjectMocks
	private TransactionController transactionController;

	@Mock
	private TransactionServiceImpl transactionService;

	@Test
	public void testAddMoney() {
		AddMoneyDTO addMoneyDTO = new AddMoneyDTO();
		addMoneyDTO.setAccountId("123");
		addMoneyDTO.setAmmount(500.0);

		when(transactionService.addMoney(addMoneyDTO)).thenReturn(
				new ResponseEntity<>(new Response(201, null, "Money added successfully"), HttpStatus.CREATED));

		ResponseEntity<Response> responseEntity = transactionController.addMoney(addMoneyDTO);

		// Assert
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

		Response responseBody = responseEntity.getBody();
		assertNotNull(responseBody);
		assertEquals(201, responseBody.getStatus());
		assertEquals("Money added successfully", responseBody.getMessage());
		assertNull(responseBody.getBody());
	}

	@Test
	public void testSendMoney() {

		Transaction transaction = new Transaction();
		transaction.setTansactionId("123");
		transaction.setSenderAccountId("456");
		transaction.setReceiverAccountId("789");
		transaction.setTransactionAmmount(100.0);

		when(transactionService.sendMoney(transaction))
				.thenReturn(new ResponseEntity<>(new Response(200, null, "Money sent successfully"), HttpStatus.OK));

		ResponseEntity<Response> responseEntity = transactionController.sendMoney(transaction);

		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		Response responseBody = responseEntity.getBody();
		assertNotNull(responseBody);
		assertEquals(200, responseBody.getStatus());
		assertEquals("Money sent successfully", responseBody.getMessage());
		assertNull(responseBody.getBody());
	}

}