package com.wow.webapp.entitymodel;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="clinic_addresses")
public class ClinicAddress {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Override
	public String toString() {
		StringBuilder sb  = new StringBuilder();
		append(sb,this.getLine1());
		append(sb,this.getLine2());
		append(sb,this.getCity());
		append(sb,this.getState());
		append(sb,this.getCountry());
		append(sb,this.getZip());
		return sb.toString();
	}

	private void append(StringBuilder sb, String str){
		if(str != null && !str.isEmpty()){
			sb.append(str + ",");
		}
		
	}
	
	private String line1;
	private String line2;
	private String country;
	private String state;
	private String city;
	private String zip;
	private String landmark;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "clinic_id", nullable = false)
	private Clinic clinic;

	public Clinic getClinic() {
		return clinic;
	}
	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLine1() {
		return line1;
	}
	public void setLine1(String line1) {
		this.line1 = line1;
	}
	public String getLine2() {
		return line2;
	}
	public void setLine2(String line2) {
		this.line2 = line2;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getLandmark() {
		return landmark;
	}
	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	
	
}
