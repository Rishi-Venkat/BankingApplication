package com.banking.project.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.banking.project.model.Account;

public interface AccountRepository extends MongoRepository<Account,String> {

	Optional<Account> findByAccountNumber(String accountNumber);
}
