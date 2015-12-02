package com.wow.webapp.domain.model;

public class ClinicModel {

	private Integer id;
	private String email;	
	private String clinicName;	
	private String clinicDesc;	
	private String clinicAddress;
	private String clinicPhones;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getClinicName() {
		return clinicName;
	}
	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}
	public String getClinicDesc() {
		return clinicDesc;
	}
	public void setClinicDesc(String clinicDesc) {
		this.clinicDesc = clinicDesc;
	}


	public String getClinicAddress() {
		return clinicAddress;
	}
	public void setClinicAddress(String clinicAddress) {
		this.clinicAddress = clinicAddress;
	}
	public String getClinicPhones() {
		return clinicPhones;
	}
	public void setClinicPhones(String clinicPhones) {
		this.clinicPhones = clinicPhones;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
}
