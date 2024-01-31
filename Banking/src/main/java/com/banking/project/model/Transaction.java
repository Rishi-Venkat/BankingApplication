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
@Document(collection = "Transaction")
public class Transaction {
	@Id
	private String tansactionId; 
	private String senderAccountId;
	private String receiverAccountId;
	private double transactionAmmount;
	private String transactionStatus;
	private LocalDateTime  transactionAt; 
}
