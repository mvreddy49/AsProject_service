package com.wow.webapp.domain.model;

import java.util.List;

public class ApiReturnSlotModel extends ApiReturnModel{

	private List<SlotsModel> slotModel;

	public ApiReturnSlotModel(Integer code,String success,String message,List<SlotsModel> slotModel) {
		super(code,success,message);
		this.slotModel = slotModel;
	}

	public List<SlotsModel> getSlotModel() {
		return slotModel;
	}

	public void setSlotModel(List<SlotsModel> slotModel) {
		this.slotModel = slotModel;
	}
	

		
	
}
