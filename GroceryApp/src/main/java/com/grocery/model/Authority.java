package com.grocery.model;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class Authority implements GrantedAuthority {

	String authority;

	@Override
	public String getAuthority() {
		return this.authority;
	}

}
