package com.banking.project.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.banking.project.model.User;


public interface UserRepository extends MongoRepository<User, String> {

	Optional<User> findByFirstNameAndLastName(String firstName, String lastName);
}
