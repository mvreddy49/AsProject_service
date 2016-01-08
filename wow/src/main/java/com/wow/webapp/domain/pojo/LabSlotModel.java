package com.wow.webapp.domain.pojo;

import java.util.List;

public class LabSlotModel {
	
	private List<String> slots;
	
	private List<String> bookedSlots;

	public List<String> getSlots() {
		return slots;
	}

	public List<String> getBookedSlots() {
		return bookedSlots;
	}

	public void setSlots(List<String> slots) {
		this.slots = slots;
	}

	public void setBookedSlots(List<String> bookedSlots) {
		this.bookedSlots = bookedSlots;
	}
	
	
}
