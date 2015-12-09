package com.wow.webapp.domain.model;

import java.util.ArrayList;
import java.util.List;

public class SlotsModel {
	private Integer id;
	private String startTime;
	private String endTime;
	private List<String> slots=new ArrayList<String>();
	private List<String> bookedSlots=new ArrayList<String>();
	public List<String> getSlots() {
		return slots;
	}
	public void setSlots(List<String> slots) {
		this.slots = slots;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public List<String> getBookedSlots() {
		return bookedSlots;
	}
	public void setBookedSlots(List<String> bookedSlots) {
		this.bookedSlots = bookedSlots;
	}
}
