package com.wow.webapp.domain.model;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class CreateLabSubTypeModel {
	
	
	@NotEmpty(message="typeId Can not be empty")
	private String typeId;
	
	@NotEmpty(message="name Can not be empty")
	@Size(max=60)
	private String name; 
	
	@NotEmpty(message="description Can not be empty")
	@Size(max=100)
	private String description;
	
	@NotEmpty(message="duration Can not be empty")
	private String duration;
	
	@NotEmpty(message="home_pickup Can not be empty")
	private String home_pickup;
	
	@NotEmpty(message="max_homepickup_bookings Can not be empty")
	private String max_homepickup_bookings;
	
	@NotEmpty(message="max_online_bookings Can not be empty")
	private String max_online_bookings;
	
	@NotEmpty(message="online_booking Can not be empty")
	private String online_booking;
	
	private String startTime;
	
	private String endTime;

	public String getTypeId() {
		return typeId;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getDuration() {
		return duration;
	}

	public String getHome_pickup() {
		return home_pickup;
	}

	public String getMax_homepickup_bookings() {
		return max_homepickup_bookings;
	}

	public String getMax_online_bookings() {
		return max_online_bookings;
	}

	public String getOnline_booking() {
		return online_booking;
	}

	public String getStartTime() {
		return startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public void setHome_pickup(String home_pickup) {
		this.home_pickup = home_pickup;
	}

	public void setMax_homepickup_bookings(String max_homepickup_bookings) {
		this.max_homepickup_bookings = max_homepickup_bookings;
	}

	public void setMax_online_bookings(String max_online_bookings) {
		this.max_online_bookings = max_online_bookings;
	}

	public void setOnline_booking(String online_booking) {
		this.online_booking = online_booking;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	
	
	

}
