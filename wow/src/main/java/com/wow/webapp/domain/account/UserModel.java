package com.wow.webapp.domain.account;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class UserModel {
	
	
	@NotEmpty(message="Mobile Can not be empty")
	@Size(max=12)
	private String username;
	
	@NotEmpty(message="Password Can not be empty")
	@Size(min=10,max=50)
	private String password;
	
	@NotEmpty(message="Name Can not be empty")
	@Size(max=64)
	private String name;
	
	@NotEmpty(message="Role Can not be empty")
	@Size(max=20)
	private String role;

	

	public String getUsername() {
		return username;
	}

	public void setUsername(String userName) {
		this.username = userName;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	
	

}
