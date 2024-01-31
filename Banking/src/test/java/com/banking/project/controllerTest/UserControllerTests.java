package com.banking.project.controllerTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.banking.project.controller.UserController;
import com.banking.project.dto.Response;
import com.banking.project.model.User;
import com.banking.project.repository.UserRepository;
import com.banking.project.service.UserServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTests {

	@Mock
	private UserServiceImpl userService;

	@InjectMocks
	private UserController userController;

	@Mock
	private UserRepository userRepository;

	@Test
	public void testCreateUser() {

		User sampleUser = new User();
		sampleUser.setUserId("123");
		sampleUser.setFirstName("John");
		sampleUser.setLastName("Doe");
		sampleUser.setAddress("123 Main St");
		sampleUser.setUserStatus("ACTIVE");
		sampleUser.setAccountsLinked(null);
		sampleUser.setCreatedAt(LocalDateTime.now());
		sampleUser.setUpdatedAt(LocalDateTime.now());

		when(userService.createUser(any(User.class))).thenReturn(
				new ResponseEntity<>(new Response(201, sampleUser, "User created successfully"), HttpStatus.CREATED));

		ResponseEntity<Response> responseEntity = userController.createUser(sampleUser);

		assertNotNull(responseEntity);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

		Response responseBody = responseEntity.getBody();
		assertNotNull(responseBody);
		assertEquals(201, responseBody.getStatus());
		assertEquals("User created successfully", responseBody.getMessage());
		assertEquals(sampleUser, responseBody.getBody()); // Assuming body is not set in this case
	}

	@Test
	public void testGetUserById() {
		String sampleUserId = "123";

		User sampleUser = new User();
		sampleUser.setUserId(sampleUserId);
		sampleUser.setFirstName("Rishi");
		sampleUser.setLastName("Venkat");
		
		when(userService.getUserById(sampleUserId)).thenReturn(
				new ResponseEntity<>(new Response(200, sampleUser, "User retrieved successfully"), HttpStatus.OK));

		ResponseEntity<Response> responseEntity = userController.getUserById(sampleUserId);

		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		Response responseBody = responseEntity.getBody();
		assertNotNull(responseBody);
		assertEquals(200, responseBody.getStatus());
		assertEquals("User retrieved successfully", responseBody.getMessage());
		assertNotNull(responseBody.getBody()); // Assuming body is set in this case
	}



	@Test
	public void testUpdateUser() {
		String sampleUserId = "123";

		User updatedUser = new User();
		updatedUser.setUserId(sampleUserId);
		updatedUser.setFirstName("Rishi");
		updatedUser.setLastName("Venkat");
		updatedUser.setAddress("123 Address");
		updatedUser.setUserStatus("UPDATED");
		updatedUser.setAccountsLinked(null);
		updatedUser.setCreatedAt(LocalDateTime.now());
		updatedUser.setUpdatedAt(LocalDateTime.now());
		
		when(userService.updateUser(sampleUserId, updatedUser)).thenReturn(
				new ResponseEntity<>(new Response(200, updatedUser, "User updated successfully"), HttpStatus.OK));

		ResponseEntity<Response> responseEntity = userController.updateUser(sampleUserId, updatedUser);

		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		Response responseBody = responseEntity.getBody();
		assertNotNull(responseBody);
		assertEquals(200, responseBody.getStatus());
		assertEquals("User updated successfully", responseBody.getMessage());
		assertNotNull(responseBody.getBody());
	}


	@Test
	public void testDeleteUser() {
		String sampleUserId = "123";
		
		when(userService.deleteUser(eq(sampleUserId))).thenReturn(
				new ResponseEntity<>(new Response(204, null, "User deleted successfully"), HttpStatus.NO_CONTENT));

		ResponseEntity<Response> responseEntity = userController.deleteUser(sampleUserId);

		assertNotNull(responseEntity);
		assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());

		Response responseBody = responseEntity.getBody();
		assertNotNull(responseBody);
		assertEquals(204, responseBody.getStatus());
		assertEquals("User deleted successfully", responseBody.getMessage());
		assertNull(responseBody.getBody()); 
	}

}
