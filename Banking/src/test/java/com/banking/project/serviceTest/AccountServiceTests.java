package com.banking.project.serviceTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.banking.project.dto.Response;
import com.banking.project.model.Account;
import com.banking.project.model.User;
import com.banking.project.repository.AccountRepository;
import com.banking.project.repository.UserRepository;
import com.banking.project.service.AccountServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTests {

    @Mock
    private AccountRepository accountRepository;
    
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Before
    public void setUp() {
        // Any setup you might need before tests
    }

    @Test
    public void testCreateAccount() {
        // Arrange
        Account accountToCreate = new Account("accountId","linkedUserId", "accountNumber", 1000.0, "ACTIVE",
                LocalDateTime.now(), LocalDateTime.now());

        when(accountRepository.save(any(Account.class))).thenReturn(accountToCreate);

        // Act
        ResponseEntity<Response> result = accountService.createAccount(accountToCreate);

        // Assert
        Response response = result.getBody();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof Account);

        Account createdAccount = (Account) response.getBody();
        assertEquals("accountId", createdAccount.getAccountId());
        assertEquals("linkedUserId", createdAccount.getLinkedUserId());
        assertEquals("accountNumber", createdAccount.getAccountNumber());
        assertEquals(1000.0, createdAccount.getAccountBalance(), 0.001);
        assertEquals("ACTIVE", createdAccount.getAccountStatus());
        assertNotNull(createdAccount.getAccountCreatedAt());
        assertNotNull(createdAccount.getAccountUpdatedAt());
        assertEquals("Account Created", response.getMessage());
    }
    
    @Test 
    public void testUpdateAccount() {
        // Arrange
        String accountId = "accountId";
        Account existingAccount = new Account(accountId,"linkedUserId","accountNumber", 1000.0, "ACTIVE",
                LocalDateTime.now(), LocalDateTime.now());
        Account updatedAccount = new Account(accountId,"linkedUserId" ,"updatedAccountNumber", 1500.0, "INACTIVE",
                LocalDateTime.now(), LocalDateTime.now());

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(existingAccount));
       // when(accountRepository.save(any(Account.class))).thenReturn(updatedAccount);

        // Act
        ResponseEntity<Response> result = accountService.updateAccount(accountId, updatedAccount);

        // Assert
        Response response = result.getBody();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof Account);

        Account updatedSavedAccount = (Account) response.getBody();
        assertEquals(accountId, updatedSavedAccount.getAccountId());
        assertEquals("linkedUserId", updatedSavedAccount.getLinkedUserId());
        assertEquals("updatedAccountNumber", updatedSavedAccount.getAccountNumber());
        assertEquals(1500.0, updatedSavedAccount.getAccountBalance(), 0.001);
        assertEquals("INACTIVE", updatedSavedAccount.getAccountStatus());
        assertNotNull(updatedSavedAccount.getAccountUpdatedAt());
        assertEquals("Account Updated", response.getMessage());
    }
    
    @Test
    public void givenExistingAccount_whenDeleteAccount_thenAccountAndLinkedUserUpdated() {
        // Arrange
        String accountId = "accountId";
        String linkedUserId = "linkedUserId";

        Account existingAccount = new Account(accountId, linkedUserId, "accountNumber", 1000.0, "ACTIVE",
                LocalDateTime.now(), LocalDateTime.now());

        User linkedUser = new User(linkedUserId, "firstName", "lastName", "address", "ACTIVE",
                new ArrayList<>(Arrays.asList(accountId)), LocalDateTime.now(), LocalDateTime.now());

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(existingAccount));
        when(userRepository.findById(linkedUserId)).thenReturn(Optional.of(linkedUser));

        // Act
        ResponseEntity<Response> result = accountService.deleteAccount(accountId);

        // Assert
        Response response = result.getBody();
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("accountIdis deleted",response.getBody()); 
        assertEquals("Account is deleted", response.getMessage());
    }
    
    @Test
    
    public void testGetAccountById() {
        // Arrange
        String accountId = "linkedUserId";
        Account existingAccount = new Account(accountId, "linkedUserId" ,"accountNumber", 1000.0, "ACTIVE",
                LocalDateTime.now(), LocalDateTime.now());

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(existingAccount));

        // Act
        ResponseEntity<Response> result = accountService.getAccountById(accountId);

        // Assert
        Response response = result.getBody();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof Account);

        Account retrievedAccount = (Account) response.getBody();
        assertEquals(accountId, retrievedAccount.getAccountId());
        assertEquals("linkedUserId", retrievedAccount.getAccountId());
        assertEquals("accountNumber", retrievedAccount.getAccountNumber());
        assertEquals(1000.0, retrievedAccount.getAccountBalance(), 0.001);
        assertEquals("ACTIVE", retrievedAccount.getAccountStatus());
        assertNotNull(retrievedAccount.getAccountCreatedAt());
        assertNotNull(retrievedAccount.getAccountUpdatedAt());
        assertEquals(" Account found", response.getMessage());
    }
    
}

