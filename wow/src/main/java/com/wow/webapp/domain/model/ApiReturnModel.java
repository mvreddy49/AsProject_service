package com.wow.webapp.domain.model;

import java.util.ArrayList;
import java.util.List;

import com.wow.webapp.util.Responses;

public class ApiReturnModel {
	
	private String status;
	private String message;
	private Integer code = Responses.SUCCESS_CODE;
	private List<String> errors = new ArrayList<String>(0);
	
	
	public ApiReturnModel() {
		this(Responses.SUCCESS_CODE,Responses.SUCCESS_STATUS, "SUCCESS", new ArrayList<String>(0));
	}

	public ApiReturnModel(Integer code,String status) {
		this(code,status, "", new ArrayList<String>(0));
	}

	public ApiReturnModel(Integer code,String status, String message) {
		this(code,status, message, new ArrayList<String>(0));
	}
	
	public ApiReturnModel(Integer code,String status, String message, List<String> errors) {
		this.code = code;
		this.status = status;
		this.message = message;
		this.errors = errors;
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

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
	
	
}
