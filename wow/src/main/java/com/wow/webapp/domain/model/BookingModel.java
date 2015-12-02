package com.wow.webapp.domain.model;

import java.util.Date;

public class BookingModel {
	
	private String slotTime;
	private Integer user_id;
	private ClinicModel clinic;
	private DoctorModel doctor;
	public String getSlotTime() {
		return slotTime;
	}
	public void setSlotTime(String slotTime) {
		this.slotTime = slotTime;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public ClinicModel getClinic() {
		return clinic;
	}
	public void setClinic(ClinicModel clinic) {
		this.clinic = clinic;
	}
	public DoctorModel getDoctor() {
		return doctor;
	}
	public void setDoctor(DoctorModel doctor) {
		this.doctor = doctor;
	}
	
	
	
	

}
