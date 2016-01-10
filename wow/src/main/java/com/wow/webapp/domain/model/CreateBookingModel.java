package com.wow.webapp.domain.model;

import org.hibernate.validator.constraints.NotEmpty;

public class CreateBookingModel {
	
	 @NotEmpty(message="slotId can not be empty")
	 private String slotId;
	 
	 
	 private String slotTime;
	 
	 private String name;
	 
	 private String mobile;
	 
	 private String age;
	 
	 private String source;

	public String getSlotId() {
		return slotId;
	}

	public CreateBookingModel() {
		super();
	}

	public void setSlotId(String slotId) {
		this.slotId = slotId;
	}

	public String getSlotTime() {
		return slotTime;
	}

	public void setSlotTime(String slotTime) {
		this.slotTime = slotTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	 
	

}
