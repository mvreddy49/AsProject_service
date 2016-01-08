package com.wow.webapp.domain.model;

import org.hibernate.validator.constraints.NotEmpty;

public class CreateLabBookingModel {
	
	@NotEmpty(message = "Slot Id cannt be empty")
	private String slotId;
	
	private String receive_mode;
	
	private String address;
	
	private String mobile;
	
	private String name;
	
	private String source;

	public String getSlotId() {
		return slotId;
	}

	public void setSlotId(String slotId) {
		this.slotId = slotId;
	}

	public String getReceive_mode() {
		return receive_mode;
	}

	public void setReceive_mode(String receive_mode) {
		this.receive_mode = receive_mode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	

}
