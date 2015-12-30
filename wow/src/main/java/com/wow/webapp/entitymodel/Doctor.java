package com.wow.webapp.entitymodel;



import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;


@Entity
@Table(name="doctor")
public class Doctor {

	public Doctor(Integer id) {
		super();
		this.id = id;
	}


	public Doctor() {
		super();
	}


	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	
	@Column(name="speciality",length=100, nullable=false)
	private String speciality;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userName")
	private User user;
	
	@Column(name="enabled")
	public boolean enabled;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_on")
	private Date modified_on;
	 
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "inserted_on")
	private Date inserted_on;
	
	@Column(name = "duration")
	private Integer duation;
		
	@OneToMany(mappedBy = "doctor" , fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	private Set<Slot> slots = new HashSet<Slot>(0);


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getSpeciality() {
		return speciality;
	}


	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public boolean isEnabled() {
		return enabled;
	}


	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
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


	public Set<Slot> getSlots() {
		return slots;
	}


	public void setSlots(Set<Slot> slots) {
		this.slots = slots;
	}


	public Integer getDuation() {
		return duation;
	}


	public void setDuation(Integer duation) {
		this.duation = duation;
	}
	
	
	
}
