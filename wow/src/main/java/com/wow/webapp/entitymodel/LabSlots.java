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

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
@Table(name="lab_slots")
public class LabSlots {
	public LabSlots(){
		super();
	}
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@DateTimeFormat(iso=ISO.DATE_TIME)
	@Column(name="start_time")
	public Date startTime;
	
	@DateTimeFormat(iso=ISO.DATE_TIME)
	@Column(name="end_time")
	public Date endDime;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lab_subtype_id", nullable = true)
	private LabSubType subType;
	
	
	@Temporal(TemporalType.TIMESTAMP)
 	@Column(name = "inserted_on")
	private Date inserted_on;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_on")
	private Date modified_on;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndDime() {
		return endDime;
	}

	public void setEndDime(Date endDime) {
		this.endDime = endDime;
	}

	public LabSubType getSubType() {
		return subType;
	}

	public void setSubType(LabSubType subType) {
		this.subType = subType;
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

	
}
