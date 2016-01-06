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
@Table(name="lab_type")
public class LabType {

	public LabType(){
		super();
	}
	public LabType(Integer id){
		this.id=id;
	}
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="name",length=100, nullable=false,unique=true)
	private String name;
	
	
	@Column(name="enabled")
	public boolean enabled = true;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_on")
	private Date modified_on;
	 
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "inserted_on")
	private Date inserted_on;

	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
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


	
}
