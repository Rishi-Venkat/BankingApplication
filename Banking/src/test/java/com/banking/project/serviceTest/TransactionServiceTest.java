package com.banking.project.serviceTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.banking.project.dto.AddMoneyDTO;
import com.banking.project.dto.Response;
import com.banking.project.model.Transaction;
import com.banking.project.repository.TransactionRepository;
import com.banking.project.service.CustomService;
import com.banking.project.service.TransactionServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {

    @Mock
    private CustomService customService;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Before
    public void setUp() {
        // Any setup you might need before tests
    }
    
    @Ignore
    @Test
    public void testSendMoney_SuccessfulTransaction() {
        // Arrange
        Transaction transaction = new Transaction();
        transaction.setSenderAccountId("senderAccountId");
        transaction.setReceiverAccountId("receiverAccountId");
        transaction.setTransactionAmmount(100.0);

        // Mocking CustomService methods
        when(customService.checkSenderReceiver("senderAccountId", "receiverAccountId")).thenReturn(true);
        when(customService.checkBalance("senderAccountId", 100.0)).thenReturn(true);
        when(customService.balanceInAccount("receiverAccountId")).thenReturn(500.0);
        doNothing().when(customService).updateBalance("senderAccountId", 400.0);
        doNothing().when(customService).updateBalance("receiverAccountId", 600.0);

        // Mocking TransactionRepository
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        // Act
        ResponseEntity<Response> result = transactionService.sendMoney(transaction);

        // Assert
        assertEquals(HttpStatus.OK.value(), result.getStatusCode().value());
        assertEquals("Transaction successful", result.getBody().getMessage());
        assertNotNull(result.getBody().getBody());
        
    }
    
    @Test
    public void testAddMoney_SuccessfulTransaction() {
        // Arrange
        AddMoneyDTO addMoneyDTO = new AddMoneyDTO("accountId", 100.0);
        Transaction expectedTransaction = new Transaction(UUID.randomUUID().toString(), addMoneyDTO.getAccountId(), "NULL", addMoneyDTO.getAmmount(),
                "Added Money", LocalDateTime.now());

        when(customService.checkAccount(addMoneyDTO.getAccountId())).thenReturn(true);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(expectedTransaction);

        // Act
        ResponseEntity<Response> result = transactionService.addMoney(addMoneyDTO);

        // Assert
        assertEquals(HttpStatus.OK.value(), result.getStatusCode().value());
        assertNotNull(result.getBody());
        assertEquals("Transaction successful", result.getBody().getMessage());

    }


}