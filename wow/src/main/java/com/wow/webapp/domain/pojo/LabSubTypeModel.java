package com.wow.webapp.domain.pojo;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class LabSubTypeModel {
	
	private Integer id;
	
	private String name; 
	
	private String description;
	
	private Integer duration;
	
	private boolean home_pickup;
	
	private Integer max_homepickup_bookings;
	
	private Integer max_online_bookings;
	
	private boolean online_booking;

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Integer getDuration() {
		return duration;
	}

	public boolean isHome_pickup() {
		return home_pickup;
	}

	public Integer getMax_homepickup_bookings() {
		return max_homepickup_bookings;
	}

	public Integer getMax_online_bookings() {
		return max_online_bookings;
	}

	public boolean isOnline_booking() {
		return online_booking;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public void setHome_pickup(boolean home_pickup) {
		this.home_pickup = home_pickup;
	}

	public void setMax_homepickup_bookings(Integer max_homepickup_bookings) {
		this.max_homepickup_bookings = max_homepickup_bookings;
	}

	public void setMax_online_bookings(Integer max_online_bookings) {
		this.max_online_bookings = max_online_bookings;
	}

	public void setOnline_booking(boolean online_booking) {
		this.online_booking = online_booking;
	}
	
	
	
	

}
