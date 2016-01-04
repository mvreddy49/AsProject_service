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

@Entity
@Table(name="lab_booking")
public class LabBooking {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "username", nullable = false)
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lab_slot_id", nullable = false)
	private LabSlots labSlot;
	
	@Column(name="status")
	public String status;
	
	@Column(name="source")
	public String source;
	
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LabSlots getLabSlot() {
		return labSlot;
	}

	public void setLabSlot(LabSlots labSlot) {
		this.labSlot = labSlot;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
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

	public Date getBooked_time() {
		return booked_time;
	}

	public void setBooked_time(Date booked_time) {
		this.booked_time = booked_time;
	}
	
}
