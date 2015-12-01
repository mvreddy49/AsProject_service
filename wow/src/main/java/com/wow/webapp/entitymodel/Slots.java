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

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;


@Entity
@Table(name = "Slots")
public class Slots {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "clinic_id", nullable = true)
	private Clinic clinic;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "doctor_id", nullable = false)
	private Doctor doctor;
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	public Date startTime;

	@DateTimeFormat(iso = ISO.DATE_TIME)
	public Date endTime;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_on", updatable = true, insertable = false,columnDefinition="TIMESTAMP default CURRENT_TIMESTAMP")
	private Date modified_on;
	 
	
	@Temporal(TemporalType.TIMESTAMP)
	//@Column(name = "inserted_on", updatable = false,insertable = false,columnDefinition="TIMESTAMP default CURRENT_TIMESTAMP") 
	@Column(name = "inserted_on", updatable = false,insertable = false)
	private Date inserted_on;

	public Integer getId() {
		return id;
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


	public void setId(int id) {
		this.id = id;
	}


	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getModified_on() {
		return modified_on;
	}

	public void setModified_on(Date modified_on) {
		this.modified_on = modified_on;
	}

	public Date getInserted_on() {
		return inserted_on;
	}

	public void setInserted_on(Date inserted_on) {
		this.inserted_on = inserted_on;
	}
}
