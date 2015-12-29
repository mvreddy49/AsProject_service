package com.wow.webapp.domain.model;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class CreateSlotModel {

	@NotEmpty(message="doctorId Can not be empty")
	private String doctorId;
	
	@NotEmpty(message="startTime Can not be empty")
	private String startTime;
	
	@NotEmpty(message="endTime Can not be empty")
	private String endTime;

	public String getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	
	
	
	
}
