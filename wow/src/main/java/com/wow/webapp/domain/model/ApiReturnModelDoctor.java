package com.wow.webapp.domain.model;

import java.util.ArrayList;
import java.util.List;

public class ApiReturnModelDoctor extends ApiReturnModel{
	private List<DoctorModel> doctors = new ArrayList<DoctorModel>(0);

	public List<DoctorModel> getDoctors() {
		return doctors;
	}

	public void getDoctors(List<DoctorModel> doctors) {
		this.doctors = doctors;
	}


	public ApiReturnModelDoctor() {
	}

	public ApiReturnModelDoctor(Integer code,String status,String message,List<DoctorModel> doctors) {
		super(code,status,message);
		this.doctors = doctors;
	}
}