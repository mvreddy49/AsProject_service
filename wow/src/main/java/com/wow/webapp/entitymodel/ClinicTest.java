package com.wow.webapp.entitymodel;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="clinic_tests")
public class ClinicTest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "clinic_id" , nullable = false)
	private Clinic clinic;

	private String test_name;
	private String test_description;
	private double current_price;
	private double actual_price;
	private boolean home_pickup;
	private boolean online_reports;
	private String speciality_name;
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Test:" + this.getTest_name() + "\n");
		sb.append("Description:" + this.getTest_description() + "\n");
		sb.append("Actual Price:" + this.getActual_price() + "\n");
		sb.append("Current Price:" + this.getCurrent_price() + "\n");
		sb.append("Home Pickup:" + this.isHome_pickup() + "\n");
		sb.append("Online Reports:" + this.isOnline_reports() + "\n");
		sb.append("speciality Name:" + this.getSpeciality_name() + "\n");
		
		return sb.toString();
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Clinic getClinic() {
		return clinic;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
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
	
	public String getSpeciality_name() {
		return speciality_name;
	}

	public void setSpeciality_name(String speciality_name) {
		this.speciality_name = speciality_name;
	}
	
}
