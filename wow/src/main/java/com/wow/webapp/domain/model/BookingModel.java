package com.wow.webapp.domain.model;

import java.util.Date;

public class BookingModel {
	
	private String slotTime;
	private String userName;
	private ClinicModel clinic;
	private DoctorModel doctor;
	public String getSlotTime() {
		return slotTime;
	}
	public void setSlotTime(String slotTime) {
		this.slotTime = slotTime;
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	
	
	

}
