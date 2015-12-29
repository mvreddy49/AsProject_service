package com.wow.webapp.domain.model;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class CreateDoctorModel {

	@NotEmpty(message="name Can not be empty")
	@Size(max=60)
	private String name;
	
	@NotEmpty(message="speciality Can not be empty")
	@Size(max=60)
	private String speciality;
	
	@NotEmpty(message="mobie Can not be empty")
	@Size(max=12)
	private String mobile;
	
	@NotEmpty(message="duration Can not be empty")
	@Size(max=2)
	private String duration;
	
	@Size(max=20)
	private String startTime;
	
	@Size(max=20)
	private String endTime;
	
		
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}
}
