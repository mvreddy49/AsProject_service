package com.wow.webapp.entitymodel;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
@Table(name="booking")
public class Booking {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id",nullable = false)
	private Integer id;
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "clinic_id", nullable = false)
	private Clinic clinic;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "doctor_id", nullable = false)
	private Doctor doctor;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "username", nullable = false)
	private User user;
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	
	//@Temporal(TemporalType.DATE)
	private Date booking_time;
	
	


	public Clinic getClinic() {
		return clinic;
	}


	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}


	public Doctor getDoctor() {
		return doctor;
	}


	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Date getBooking_time() {
		return booking_time;
	}


	public void setBooking_time(Date booking_time) {
		this.booking_time = booking_time;
	}


	public Date getInserted_on() {
		return inserted_on;
	}


	public void setInserted_on(Date inserted_on) {
		this.inserted_on = inserted_on;
	}


	@Temporal(TemporalType.TIMESTAMP)
	//@Column(name = "inserted_on", length = 19,columnDefinition="TIMESTAMP default CURRENT_TIMESTAMP")
	@Column(name = "inserted_on", updatable = false,insertable = false)
	private Date inserted_on;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_on", length = 19,columnDefinition="TIMESTAMP default CURRENT_TIMESTAMP")
	private Date updated_on;
	
	public Date getUpdated_on() {
		return updated_on;
	}


	public void setUpdated_on(Date updated_on) {
		this.updated_on = updated_on;
	}


	public Booking(){
		
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}
	
	
	
}
