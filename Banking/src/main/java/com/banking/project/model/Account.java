package com.banking.project.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection= "Account")
public class Account {
	
	@Id
	private String accountId;
	private String linkedUserId;
	private String accountNumber;
	private double accountBalance;
	private String accountStatus;
	private LocalDateTime accountCreatedAt;
	private LocalDateTime accountUpdatedAt;
}
