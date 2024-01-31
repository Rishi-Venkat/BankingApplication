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

import com.banking.project.dto.Response;
import com.banking.project.model.User;
import com.banking.project.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/create")
	public ResponseEntity<Response> createUser(@RequestBody User user) {
		return userService.createUser(user);
	}

	@GetMapping("/all")
	public ResponseEntity<Response> getAllUsers() {
		return userService.getAllUsers();
		
	}

	@GetMapping("/getby/{userId}")
	public ResponseEntity<Response> getUserById(@PathVariable String userId) {
		return userService.getUserById(userId);
	}

	@PutMapping("/update/{userId}")
	public ResponseEntity<Response> updateUser(@PathVariable String userId, @RequestBody User updatedUser) {
		return userService.updateUser(userId, updatedUser);
	}

	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<Response> deleteUser(@PathVariable String userId) {
		return userService.deleteUser(userId);
	}
	
	@GetMapping("/accounts/{userId}")
	public ResponseEntity<Response> getUserAccounts(@PathVariable String userId){
		return userService.getUserAccount(userId);
	}

}
