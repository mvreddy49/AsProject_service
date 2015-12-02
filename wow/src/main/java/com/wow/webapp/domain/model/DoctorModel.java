package com.wow.webapp.domain.model;

public class DoctorModel {

	private Integer id;
	private String name;
	private String mobile;
	private String speciality;
	private ClinicModel clinic;
	private SlotsModel slot;
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
	public String getSpeciality() {
		return speciality;
	}
	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public ClinicModel getClinic() {
		return clinic;
	}
	public void setClinic(ClinicModel clinic) {
		this.clinic = clinic;
	}
	public SlotsModel getSlot() {
		return slot;
	}
	public void setSlot(SlotsModel slot) {
		this.slot = slot;
	}
	
}
