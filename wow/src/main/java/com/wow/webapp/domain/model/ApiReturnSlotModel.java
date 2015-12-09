package com.wow.webapp.domain.model;

public class ApiReturnSlotModel extends ApiReturnModel{

	private SlotsModel slotModel;

	public SlotsModel getSlotModel() {
		return slotModel;
	}

	public void setSlotModel(SlotsModel slotModel) {
		this.slotModel = slotModel;
	}

	public ApiReturnSlotModel() {
		super();
	}

	public ApiReturnSlotModel(Integer code,String status,String message, SlotsModel slotModel) {
		super(code,status,message);
		this.slotModel = slotModel;
	}
	
	
	
}
