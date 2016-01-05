package com.wow.webapp.domain.model;

import java.util.ArrayList;
import java.util.List;

import com.wow.webapp.domain.pojo.LabTypeModel;

public class ApiReturnLabType extends ApiReturnModel{
	
	private List<LabTypeModel> labType = new ArrayList<LabTypeModel>();

	public List<LabTypeModel> getLabType() {
		return labType;
	}

	public void setLabType(List<LabTypeModel> labType) {
		this.labType = labType;
	}

	public ApiReturnLabType(Integer code,String success,String message,List<LabTypeModel> labType) {
		super(code,success,message);
		this.labType = labType;
	}
	
	

}
