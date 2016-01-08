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

@Entity
@Table(name="lab_subtype")
public class LabSubType {

	public LabSubType(){
		super();
	}
	
	public LabSubType(Integer id) {
		super();
		this.id = id;
	}

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="name",length=100, nullable=false)
	private String name;
	
	@Column(name="description",length=256, nullable=false)
	private String description;
	
	@Column(name="online_booking")
	private boolean onlineBooking;
	
	@Column(name="home_pickup")
	private boolean homePickup;
		
	@Column(name="duration")
	private Integer duration;
	
	@Column(name="max_online_bookings")
	private Integer maxOnlineBookings;
	
	@Column(name="max_homepickup_bookings")
	private Integer maxHomePickupBookings;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "lab_type_id", nullable = false)
	private LabType labType;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_on")
	private Date modified_on;
	 
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "inserted_on")
	private Date inserted_on;
	
	@Column(name="enabled")
	public boolean enabled = true;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isOnlineBooking() {
		return onlineBooking;
	}

	public void setOnlineBooking(boolean onlineBooking) {
		this.onlineBooking = onlineBooking;
	}

	public boolean isHomePickup() {
		return homePickup;
	}

	public void setHomePickup(boolean homePickup) {
		this.homePickup = homePickup;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Integer getMaxOnlineBookings() {
		return maxOnlineBookings;
	}

	public void setMaxOnlineBookings(Integer maxOnlineBookings) {
		this.maxOnlineBookings = maxOnlineBookings;
	}

	public Integer getMaxHomePickupBookings() {
		return maxHomePickupBookings;
	}

	public void setMaxHomePickupBookings(Integer maxHomePickupBookings) {
		this.maxHomePickupBookings = maxHomePickupBookings;
	}

	public LabType getLabType() {
		return labType;
	}

	public void setLabType(LabType labType) {
		this.labType = labType;
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

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	

}
