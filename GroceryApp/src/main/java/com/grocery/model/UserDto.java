package com.grocery.model;

import com.grocery.entities.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	
	private Long id;

	private String username;

	private String password;

	private String email;
	
	private Boolean isUserEnabled;
	
	private Role role;
	
	private String roleType;

	
}
