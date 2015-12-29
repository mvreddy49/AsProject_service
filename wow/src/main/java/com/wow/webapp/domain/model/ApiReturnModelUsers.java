package com.wow.webapp.domain.model;

import java.util.ArrayList;
import java.util.List;



public class ApiReturnModelUsers extends ApiReturnModel{
	public ApiReturnModelUsers() {
		super();
	}
	
	public ApiReturnModelUsers(Integer code,String status, String message, List<String> errors)
	{
		super(code,status,message,errors);
	}
	
	public ApiReturnModelUsers(Integer code,String status, String message)
	{
		super(code,status,message);
	}
	
	
	private List<ResponseUserModel> users = new ArrayList<ResponseUserModel>(0);

	public List<ResponseUserModel> getUsers() {
		return users;
	}
	
	public void setUsers(List<ResponseUserModel> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "ApiReturnModelUsers [users=" + users + ", getUsers()="
				+ getUsers() + ", getStatus()=" + getStatus()
				+ ", getMessage()=" + getMessage() + ", getErrors()="
				+ getErrors() + ", getCode()=" + getCode() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
}
