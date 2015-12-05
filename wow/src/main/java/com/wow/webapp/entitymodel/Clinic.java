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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="clinic")
public class Clinic {	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Clinic() {
		super();
	}

	public Clinic(Integer id) {
		super();
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	
	
	@Column(name="name", length=100, nullable=false)
	private String name;
	
	@Column(name="description",length=255, nullable=false)
	private String description;

	// Possible values (lab, clinic) .Default clinic
	@Column(name="type", length=10, nullable=false)
	private String type = "clinic";
	
	@Column(name="enabled", nullable = false)
	private boolean enabled;
	
	private String specialities;
		
	public String getSpecialities() {
		return specialities;
	}

	public void setSpecialities(String specialities) {
		this.specialities = specialities;
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

	
	public Date getInserted_on() {
		return inserted_on;
	}

	public void setInserted_on(Date inserted_on) {
		this.inserted_on = inserted_on;
	}

	@OneToMany(mappedBy = "clinic" , fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	private Set<ClinicPhoneNo> phoneNos = new HashSet<ClinicPhoneNo>(0);

	@OneToMany(mappedBy = "clinic" , fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	private Set<ClinicAddress> addresses = new HashSet<ClinicAddress>(0);
	
	@OneToMany(mappedBy = "clinic" , fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	private Set<ClinicTest> tests = new HashSet<ClinicTest>(0);
	
	@OneToMany(mappedBy = "clinic" , fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	private Set<User> users = new HashSet<User>(0);

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<ClinicAddress> getAddresses() {
		return addresses;
	}

	public void setAddresses(Set<ClinicAddress> addresses) {
		this.addresses = addresses;
	}

	public Set<ClinicTest> getTests() {
		return tests;
	}

	public void setTests(Set<ClinicTest> tests) {
		this.tests = tests;
	}
	
	public Set<ClinicPhoneNo> getPhoneNos() {
		return phoneNos;
	}

	public void setPhoneNos(Set<ClinicPhoneNo> phoneNos) {
		this.phoneNos = phoneNos;
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Name : " + this.getName() + "\n");
		sb.append("Description:" + this.getDescription() +"\n");
		for( ClinicAddress addr : this.getAddresses()){
			sb.append(addr);
		}
		for( ClinicPhoneNo ph : this.getPhoneNos()){
			sb.append(ph);
		}
		for(ClinicTest t : this.getTests()){
			sb.append(t);
		}
		sb.append("\n");
		return sb.toString();
	}
}
