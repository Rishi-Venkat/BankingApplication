package com.banking.project.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.banking.project.dto.Response;
import com.banking.project.exception.UserAlreadyExistsException;
import com.banking.project.exception.UserNotFoundException;
import com.banking.project.model.User;
import com.banking.project.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	public ResponseEntity<Response> createUser(User user) {

		Optional<User> optionalUser = userRepository.findByFirstNameAndLastName(user.getFirstName(),
				user.getLastName());
		if (optionalUser.isPresent()) {
			// User already exists, handle accordingly (e.g., throw an exception or return
			// an error response)
			throw new UserAlreadyExistsException("User with firstName: " + user.getFirstName() + " and lastName: "
					+ user.getLastName() + " already exists");
		} else {
			user.setCreatedAt(LocalDateTime.now());
			user.setUpdatedAt(LocalDateTime.now());
			User savedUser = userRepository.save(user);

			// Create a Response instance
			Response response = new Response(HttpStatus.CREATED.value(), savedUser, "User created successfully");
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		}
	}

	public ResponseEntity<Response> getAllUsers() {
		List<User> allUsers = userRepository.findAll();
		return ResponseEntity.ok(new Response(HttpStatus.OK.value(), allUsers, "All Users found"));
		// return userRepository.findAll();
	}

	public ResponseEntity<Response> getUserById(String userId) {
		Optional<User> optionalUser = userRepository.findById(userId);

		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			return ResponseEntity.ok(new Response(HttpStatus.OK.value(), user, "User found"));
		} else {
			throw new UserNotFoundException(userId);
		}
	}

	public ResponseEntity<Response> updateUser(String userId, User updatedUser) {
		Optional<User> optionalUser = userRepository.findById(userId);

		if (optionalUser.isPresent()) {
			User existingUser = optionalUser.get();
			existingUser.setFirstName(updatedUser.getFirstName());
			existingUser.setLastName(updatedUser.getLastName());
			existingUser.setAddress(updatedUser.getAddress());
			existingUser.setUserStatus(updatedUser.getUserStatus());
			existingUser.setUpdatedAt(LocalDateTime.now());

			User savedUser = userRepository.save(existingUser);

			return ResponseEntity.ok(new Response(HttpStatus.OK.value(), savedUser, "User updated successfully"));
		} else {
			throw new UserNotFoundException(userId);
		}
	}

	public ResponseEntity<Response> deleteUser(String userId) {
		Optional<User> optionalUser = userRepository.findById(userId);
		if (optionalUser.isPresent()) {
			userRepository.deleteById(userId);

			return ResponseEntity.ok(new Response(HttpStatus.OK.value(), null, "User deleted successfully"));

		} else {
			throw new UserNotFoundException(userId);
		}
	}

	@Override
	public ResponseEntity<Response> getUserAccount(String userId) {
		// TODO Auto-generated method stub
		List<String> userAccounts = new ArrayList<>();
		Optional<User> optionalUser = userRepository.findById(userId);
		if (optionalUser.isPresent()) {
			User u = optionalUser.get();
			if (u.getAccountsLinked().isEmpty()) {
				return null;
			} else {
				for (String accId : u.getAccountsLinked()) {
					userAccounts.add(accId);
				}
				return ResponseEntity
						.ok(new Response(HttpStatus.OK.value(), userAccounts, "User's Accounts Found successfully"));
			}
		}
		return ResponseEntity.ok(new Response(HttpStatus.NOT_FOUND.value(), "NULL", "User's Account Not Found"));
	}

}
