package com.banking.project.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "User")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
	
	@Id
    @NotNull
    private String userId;
    
    @NotNull
    private String firstName;
    @NotNull
    
    private String lastName;
    
    @NotNull
    private String address;
    
    @NotNull
    private String userStatus;
    
    private List<String> AccountsLinked;
 
    @NotNull
    private LocalDateTime createdAt;
    
    
    @NotNull
    private LocalDateTime updatedAt;

    // getters and setters
}
