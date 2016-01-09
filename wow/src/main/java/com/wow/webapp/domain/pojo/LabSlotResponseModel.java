package com.wow.webapp.domain.pojo;

import java.util.List;

public class LabSlotResponseModel {
	
	public List<LabSlotModel> getAvailableSlots() {
		return availableSlots;
	}

	public void setAvailableSlots(List<LabSlotModel> availableSlots) {
		this.availableSlots = availableSlots;
	}

	private List<LabSlotModel> availableSlots;
	
}
