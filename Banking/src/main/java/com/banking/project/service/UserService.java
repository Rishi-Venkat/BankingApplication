package com.banking.project.service;

import org.springframework.http.ResponseEntity;

import com.banking.project.dto.Response;
import com.banking.project.model.User;

public interface UserService {

	ResponseEntity<Response> createUser(User user);
	
	ResponseEntity<Response> getAllUsers();

	ResponseEntity<Response> getUserById(String userId);

	ResponseEntity<Response> updateUser(String userId, User updatedUser);

	ResponseEntity<Response> deleteUser(String userId);

	ResponseEntity<Response> getUserAccount(String userId);
	

}
