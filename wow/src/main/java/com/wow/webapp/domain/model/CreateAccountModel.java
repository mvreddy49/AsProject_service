package com.wow.webapp.domain.model;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class CreateAccountModel {
	
	/*@NotEmpty(message="email Can not be empty")
	@Email
	private String email;*/
	
	@NotEmpty(message="passwd Can not be empty")
	@Size(min=10, max=50)
	private String passwd;
	
	@NotEmpty(message="clinicName Can not be empty")
	@Size(min=2, max=50)
	private String clinicName;
	
	@NotEmpty(message="clinicDesc Can not be empty")
	@Size(min=2, max=100)
	private String clinicDesc;
	
	@NotEmpty(message="clinicAddrLine1 Can not be empty")
	@Size(min=2, max=100)
	private String clinicAddrLine1;
	
	@NotEmpty(message="clinicAddrLine2 Can not be empty")
	@Size(min=2, max=100)
	private String clinicAddrLine2;
	
	@NotEmpty(message="clinicCity Can not be empty")
	@Size(min=2, max=100)
	private String clinicCity;
		
	@NotEmpty(message="clinicState Can not be empty")
	@Size(min=2, max=100)
	private String clinicState;

	@NotEmpty(message="clinicCountry Can not be empty")
	@Size(min=2, max=100)
	private String clinicCountry;
	
	@NotEmpty(message="clinicZipCode code Can not be empty")
	@Size(min=2, max=10)
	private String clinicZipCode;

	@Size(max=100)
	private String clinicLandmark;

	@NotEmpty(message="clinicPhone1 Number Can not be empty")
	@Size(min=10, max=10)
	private String clinicPhone1;
	
	@NotEmpty(message="type Can not be empty")
	@Size(max=20)
	private String type;
	

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
	public String getClinicAddrLine1() {
		return clinicAddrLine1;
	}
	public void setClinicAddrLine1(String clinicAddrLine1) {
		this.clinicAddrLine1 = clinicAddrLine1;
	}
	public String getClinicAddrLine2() {
		return clinicAddrLine2;
	}
	public void setClinicAddrLine2(String clinicAddrLine2) {
		this.clinicAddrLine2 = clinicAddrLine2;
	}
	public String getClinicCity() {
		return clinicCity;
	}
	public void setClinicCity(String clinicCity) {
		this.clinicCity = clinicCity;
	}
	public String getClinicState() {
		return clinicState;
	}
	public void setClinicState(String clinicState) {
		this.clinicState = clinicState;
	}
	public String getClinicCountry() {
		return clinicCountry;
	}
	public void setClinicCountry(String clinicCountry) {
		this.clinicCountry = clinicCountry;
	}
	public String getClinicZipCode() {
		return clinicZipCode;
	}
	public void setClinicZipCode(String clinicZipCode) {
		this.clinicZipCode = clinicZipCode;
	}
	public String getClinicLandmark() {
		return clinicLandmark;
	}
	public void setClinicLandmark(String clinicLandmark) {
		this.clinicLandmark = clinicLandmark;
	}
	public String getClinicPhone1() {
		return clinicPhone1;
	}
	public void setClinicPhone1(String clinicPhone1) {
		this.clinicPhone1 = clinicPhone1;
	}
	/*public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}*/
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}	
}
