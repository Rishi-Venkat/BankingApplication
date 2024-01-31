package com.banking.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkUnlinkDTO {
	
	private String userId;
	
	private String accountId;
}
