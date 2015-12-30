package com.wow.webapp.domain.model;

import java.util.List;

public class ApiReturnSlotModel extends ApiReturnModel{

	private List<SlotsModel> slotModel;

	private List<SlotsModel> bookedSlots;
	
	public ApiReturnSlotModel(){
		super();
	}
	
	public ApiReturnSlotModel(Integer code,String success,String message,List<SlotsModel> slotModel,List<SlotsModel> bookedSlots) {
		super(code,success,message);
		this.slotModel = slotModel;
		this.bookedSlots = bookedSlots;
	}
	
	public List<SlotsModel> getSlotModel() {
		return slotModel;
	}

	public void setSlotModel(List<SlotsModel> slotModel) {
		this.slotModel = slotModel;
	}

	public List<SlotsModel> getBookedSlots() {
		return bookedSlots;
	}

	public void setBookedSlots(List<SlotsModel> bookedSlots) {
		this.bookedSlots = bookedSlots;
	}
	

		
	
}
