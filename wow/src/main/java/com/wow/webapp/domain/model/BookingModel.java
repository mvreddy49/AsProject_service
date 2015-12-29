package com.wow.webapp.domain.model;

import com.wow.webapp.domain.account.UserModel;

public class BookingModel {
	
	private String slotTime;
	private UserModel user;
	
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
	
	
	public UserModel getUser() {
		return user;
	}
	public void setUser(UserModel user) {
		this.user = user;
	}
	
	
	

}
