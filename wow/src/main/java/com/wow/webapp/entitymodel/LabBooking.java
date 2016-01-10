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
	
	public LabBooking(){
		super();
	}
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "username", nullable = false)
	private User user;
	
	@ManyToOne(fetch = FetchType.EAGER)
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

	@Column(name="receive_mode")
	private String receive_mode;

	@Column(name = "address")
	private String address;
	
	
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

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

	public String getReceive_mode() {
		return receive_mode;
	}

	public void setReceive_mode(String receive_mode) {
		this.receive_mode = receive_mode;
	}

	@Override
	public String toString() {
		return "LabBooking [id=" + id + ", user=" + user + ", labSlot=" + labSlot + ", status=" + status + ", source="
				+ source + ", inserted_on=" + inserted_on + ", modified_on=" + modified_on + ", receive_mode="
				+ receive_mode + ", address=" + address + "]";
	}

		
}
