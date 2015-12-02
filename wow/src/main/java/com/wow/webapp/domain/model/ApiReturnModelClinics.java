package com.wow.webapp.domain.model;

import java.util.ArrayList;
import java.util.List;

public class ApiReturnModelClinics extends ApiReturnModel{
	private List<ClinicModel> clinics = new ArrayList<ClinicModel>(0);

	public List<ClinicModel> getClinics() {
		return clinics;
	}

	public void setClinics(List<ClinicModel> clinics) {
		this.clinics = clinics;
	}


	public ApiReturnModelClinics() {
	}

	public ApiReturnModelClinics(Integer code,String status,String message,List<ClinicModel> clinics) {
		super(code,status,message);
		this.clinics = clinics;
	}
}
