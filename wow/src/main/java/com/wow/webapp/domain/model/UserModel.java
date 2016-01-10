package com.wow.webapp.domain.model;

public class UserModel {
	
	
	
	private String username;
	private String mobile;
	private String address = "Default Address"; 
	
	public UserModel(){
		
	}
	
	public UserModel(String username,String mobile){
		this.mobile = mobile;
		this.username = username;
	}
	
	public UserModel(String username,String mobile,String address){
		this.address = address;
		this.mobile = mobile;
		this.username = username;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

}
