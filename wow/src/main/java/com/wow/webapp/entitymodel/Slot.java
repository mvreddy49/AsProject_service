package com.wow.webapp.entitymodel;



import java.util.Date;

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
import javax.persistence.UniqueConstraint;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;


@Entity
@Table(name = "slot")

public class Slot {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "clinic_id", nullable = false)
	private Clinic clinic;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "doctor_id", nullable = false)
	private Doctor doctor;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userName",nullable=true)
	private User user;

	@Column(name="source")
	public String source;

	
	@DateTimeFormat(iso=ISO.DATE_TIME)
	@Column(name="time")
	public Date time;

	@Column(name="enabled")
	public boolean enabled;
	
	@Column(name="status")
	public boolean status;
	
	@Temporal(TemporalType.TIMESTAMP)
 	@Column(name = "inserted_on")
	private Date inserted_on;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_on")
	private Date modified_on;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "booked_time")
	private Date booked_time;
	
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

	

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Date getInserted_on() {
		return inserted_on;
	}

	public void setInserted_on(Date inserted_on) {
		this.inserted_on = inserted_on;
	}

	public Date getModified_on() {
		return modified_on;
	}

	public void setModified_on(Date modified_on) {
		this.modified_on = modified_on;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Date getBooked_time() {
		return booked_time;
	}

	public void setBooked_time(Date booked_time) {
		this.booked_time = booked_time;
	}
	 
	
		
}
