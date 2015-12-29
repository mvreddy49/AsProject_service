package com.wow.webapp.domain.model;


public class ResponseUserModel {
	
	
	private String mobile;
	
	private String name;
	
	private String role;
	
	private String updatedOn;
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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
	
	public String getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}

	public ResponseUserModel(){
	}
	public ResponseUserModel(String mobile, String name, String role){
		this.mobile = mobile;
		this.name = name;
		this.role = role;
	}

	@Override
	public String toString() {
		return "ResponseUserModel [mobile=" + mobile + ", name=" + name
				+ ", role=" + role + ", getMobile()=" + getMobile()
				+ ", getName()=" + getName() + ", getRole()=" + getRole()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

	

}
