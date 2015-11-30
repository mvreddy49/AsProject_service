package com.wow.webapp.domain.account;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class LoginModel {
	
	@NotEmpty(message="User Name Can not be empty")
	@Email
	private String username;
	
	@NotEmpty(message="Password Can not be empty")
	private String password;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
