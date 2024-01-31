package com.banking.project.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.banking.project.model.Transaction;

public interface TransactionRepository extends MongoRepository<Transaction, String> {

}
