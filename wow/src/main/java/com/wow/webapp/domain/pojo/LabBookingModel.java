package com.wow.webapp.domain.pojo;

import com.wow.webapp.domain.model.UserModel;

public class LabBookingModel {
	
	private int id;
	
	private String testName;
	
	private String slotTime;
	
	private String receiveMode;
	
	private UserModel user;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getSlotTime() {
		return slotTime;
	}

	public void setSlotTime(String slotTime) {
		this.slotTime = slotTime;
	}

	public String getReceiveMode() {
		return receiveMode;
	}

	public void setReceiveMode(String receiveMode) {
		this.receiveMode = receiveMode;
	}

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	

}
