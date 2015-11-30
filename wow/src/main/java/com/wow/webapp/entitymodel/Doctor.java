package com.wow.webapp.entitymodel;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;


@Entity
@Table(name="Doctor")
public class Doctor {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="name",length=100, nullable=false)
	private String name;
	
	@Column(name="speciality",length=100, nullable=false)
	private String speciality;
	
	@Column(name="mobile",length=20, unique=true, nullable=false)
	private String mobile;

	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_on", updatable = true, insertable = false,columnDefinition="TIMESTAMP default CURRENT_TIMESTAMP")
	private Date modified_on;
	 
	
	public Doctor() {
		super();
	}

	public Doctor(Integer id) {
		super();
		this.id = id;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "inserted_on", updatable = false, insertable = true,columnDefinition="TIMESTAMP default CURRENT_TIMESTAMP") 
	private Date inserted_on;
	
	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	
}
