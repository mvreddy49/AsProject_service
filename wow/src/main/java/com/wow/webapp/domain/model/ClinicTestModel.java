package com.wow.webapp.domain.model;

public class ClinicTestModel {
	private String test_name;
	private String test_description;
	private double current_price;
	private double actual_price;
	private boolean home_pickup;
	private boolean online_reports;
	private String clinic_name;
	private String speciality_name;
	
	public String getSpeciality_name() {
		return speciality_name;
	}
	public void setSpeciality_name(String speciality_name) {
		this.speciality_name = speciality_name;
	}
	public String getTest_name() {
		return test_name;
	}
	public void setTest_name(String test_name) {
		this.test_name = test_name;
	}
	public String getTest_description() {
		return test_description;
	}
	public void setTest_description(String test_description) {
		this.test_description = test_description;
	}
	public double getCurrent_price() {
		return current_price;
	}
	public void setCurrent_price(double current_price) {
		this.current_price = current_price;
	}
	public double getActual_price() {
		return actual_price;
	}
	public void setActual_price(double actual_price) {
		this.actual_price = actual_price;
	}
	public boolean isHome_pickup() {
		return home_pickup;
	}
	public void setHome_pickup(boolean home_pickup) {
		this.home_pickup = home_pickup;
	}
	public boolean isOnline_reports() {
		return online_reports;
	}
	public void setOnline_reports(boolean online_reports) {
		this.online_reports = online_reports;
	}
	public String getClinic_name() {
		return clinic_name;
	}
	public void setClinic_name(String clinic_name) {
		this.clinic_name = clinic_name;
	}
}
