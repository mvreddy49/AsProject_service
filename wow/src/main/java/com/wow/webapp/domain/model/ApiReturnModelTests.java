package com.wow.webapp.domain.model;

import java.util.ArrayList;
import java.util.List;

public class ApiReturnModelTests extends ApiReturnModel{
	private List<ClinicTestModel> tests = new ArrayList<ClinicTestModel>(0);

	public List<ClinicTestModel> getTests() {
		return tests;
	}

	public void setTests(List<ClinicTestModel> tests) {
		this.tests = tests;
	}


	public ApiReturnModelTests() {
	}

	public ApiReturnModelTests(List<ClinicTestModel> tests) {
		this.tests = tests;
	}
	
	
	
	

}
