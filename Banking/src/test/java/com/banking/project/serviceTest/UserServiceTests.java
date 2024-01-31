package com.banking.project.serviceTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.banking.project.dto.Response;
import com.banking.project.model.User;
import com.banking.project.repository.UserRepository;
import com.banking.project.service.UserServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;


    @Test
    public void testCreateUser() {
        // Arrange
        User mockUser = new User();
        mockUser.setUserId("123");
        mockUser.setFirstName("Ram");
        mockUser.setLastName("Nishanth");
        mockUser.setAddress("123 Main St");
        mockUser.setUserStatus("ACTIVE");
        mockUser.setAccountsLinked(new ArrayList<>());
        mockUser.setCreatedAt(LocalDateTime.now());
        mockUser.setUpdatedAt(LocalDateTime.now());

        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        // Act
        ResponseEntity<Response> result = userService.createUser(mockUser);

        // Assert
        Response response = result.getBody();
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertNotNull(response.getBody());
        System.out.println(response);
        assertTrue(response.getBody() instanceof User);

        User createdUser = (User) response.getBody();
        assertEquals("123", createdUser.getUserId());
        assertEquals("Ram", createdUser.getFirstName());
        assertEquals("Nishanth", createdUser.getLastName());
        assertEquals("123 Main St", createdUser.getAddress());
        assertEquals("ACTIVE", createdUser.getUserStatus());
        assertNotNull(createdUser.getCreatedAt());
        assertNotNull(createdUser.getUpdatedAt());
    }
    
    @Test
    public void testGetAllUsers() {
        // Arrange
        User user1 = new User("1", "Ram", "Nishanth", "Address1", "ACTIVE", new ArrayList<>(), LocalDateTime.now(), LocalDateTime.now());
        User user2 = new User("2", "Nishanth", "Solanki", "Address2", "ACTIVE", new ArrayList<>(), LocalDateTime.now(), LocalDateTime.now());
        List<User> mockUsers = Arrays.asList(user1, user2);

        when(userRepository.findAll()).thenReturn(mockUsers);

        // Act
        ResponseEntity<Response> result = userService.getAllUsers();

        // Assert
        Response response = result.getBody();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof List);

        List<?> userList = (List<?>) response.getBody();
        assertEquals(2, userList.size()); // Adjust based on the actual number of users in the list
        assertTrue(userList.get(0) instanceof User);
        assertTrue(userList.get(1) instanceof User);
        // Additional assertions for user details, if needed
    }
    
    @Test
    public void testGetUserById() {
    	//Arrange 
    	String userId="123";
    	User mockUser = new User(userId , "Ram", "Nishanth" , "Address", "ACTIVE", new ArrayList<>(),LocalDateTime.now(), LocalDateTime.now());
    	
    	when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
    	
    	//Act
    	ResponseEntity<Response> result=userService.getUserById(userId);
    	
    	//Assert
        Response response = result.getBody();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof User);

        User foundUser = (User) response.getBody();
        assertEquals(userId, foundUser.getUserId());
    }
    
    @Test
    public void testUpdateUserUserExists() {
        // Arrange
        String userId = "1";
        User existingUser = new User(userId, "Ram", "Nishanth", "Address", "ACTIVE", new ArrayList<>(), LocalDateTime.now(), LocalDateTime.now());
        User updatedUser = new User(userId, "UpdatedFirstName", "UpdatedLastName", "UpdatedAddress", "INACTIVE", new ArrayList<>(), LocalDateTime.now(), LocalDateTime.now());

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        // Act
        ResponseEntity<Response> result = userService.updateUser(userId, updatedUser);

        // Assert

        Response response = result.getBody();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof User);

        User updatedSavedUser = (User) response.getBody();
        assertEquals(userId, updatedSavedUser.getUserId());
        assertEquals("UpdatedFirstName", updatedSavedUser.getFirstName());
        assertEquals("UpdatedLastName", updatedSavedUser.getLastName());
        assertEquals("UpdatedAddress", updatedSavedUser.getAddress());
        assertEquals("INACTIVE", updatedSavedUser.getUserStatus());
        assertNotNull(updatedSavedUser.getUpdatedAt());
        
    }
    
    @Test
    public void testDeleteUserUserExists() {
        // Arrange
        String userId = "1";
        User existingUser = new User(userId, "Ram", "Nishanth", "Address", "ACTIVE", new ArrayList<>(), LocalDateTime.now(), LocalDateTime.now());

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        // Act
        ResponseEntity<Response> result = userService.deleteUser(userId);

        // Assert

        Response response = result.getBody();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(null, response.getBody()); // Adjust based on the actual response body
        assertEquals("User deleted successfully", response.getMessage());
    }
    
    @Test
    public void testGetUserAccountWithLinkedAccounts() {
        // Arrange
        String userId = "1";
        User userWithAccounts = new User(userId, "John", "Doe", "Address", "ACTIVE",
                Arrays.asList("account1", "account2"), LocalDateTime.now(), LocalDateTime.now());

        when(userRepository.findById(userId)).thenReturn(Optional.of(userWithAccounts));

        // Act
        ResponseEntity<Response> result = userService.getUserAccount(userId);

        //Assert
        Response response = result.getBody();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof List);

        List<?> userAccounts = (List<?>) response.getBody();
        assertEquals(2, userAccounts.size()); // Adjust based on the actual number of linked accounts
        assertTrue(userAccounts.get(0) instanceof String);
        assertTrue(userAccounts.get(1) instanceof String);
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
