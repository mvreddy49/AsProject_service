package com.wow.webapp.domain.model;

import java.util.ArrayList;
import java.util.List;

public class ApiReturnModel {
	
	private String status;
	private String message;
	private List<String> errors = new ArrayList<String>(0);
	
	
	public ApiReturnModel() {
		this("OK", "", new ArrayList<String>(0));
	}

	public ApiReturnModel(String status) {
		this(status, "", new ArrayList<String>(0));
	}

	public ApiReturnModel(String status, String message) {
		this(status, message, new ArrayList<String>(0));
	}
	
	public ApiReturnModel(String status, String message, List<String> errors) {
		this.status = status;
		this.message = message;
		this.errors = errors;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<String> getErrors() {
		return errors;
	}
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	
	
}
