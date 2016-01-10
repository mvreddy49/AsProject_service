package com.wow.webapp.domain.model;

import org.hibernate.validator.constraints.NotEmpty;

public class LoginModel {
	
	@NotEmpty(message="User Name Can not be empty")
	
	private String username;
	
	@NotEmpty(message="Password Can not be empty")
	private String password;
	
	private String name;
	
	public LoginModel() {
		
	}
	
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
