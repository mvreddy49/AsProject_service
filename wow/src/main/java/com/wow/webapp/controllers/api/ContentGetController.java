package com.wow.webapp.controllers.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wow.webapp.dao.ContentDAO;
import com.wow.webapp.domain.model.ApiReturnModel;
import com.wow.webapp.domain.model.ApiReturnModelClinics;
import com.wow.webapp.domain.model.ApiReturnModelDoctor;
import com.wow.webapp.domain.model.ClinicModel;
import com.wow.webapp.domain.model.DoctorModel;
import com.wow.webapp.util.Responses;



@RestController
@RequestMapping("/api")
public class ContentGetController {

	private static final Logger logger = LoggerFactory.getLogger(ContentGetController.class);
	
	@Autowired
	private ContentDAO contentDao;
	
	@RequestMapping(value = "/clinics", method = RequestMethod.GET)
	public ApiReturnModel clinics(@RequestParam("speciality") String speciality,
			@RequestParam("location") String location){
		ApiReturnModel returnValue = null;
		
		try {
			List<ClinicModel> clinics = contentDao.getClinics(speciality, location);
			returnValue = new ApiReturnModelClinics(Responses.SUCCESS_CODE,Responses.SUCCESS_STATUS,Responses.SUCCESS_MSG,clinics);
			returnValue.setMessage("Location : " + location + "," + "Speciality : " + speciality);
			//returnValue = new ApiReturnModel("OK", "[\"c1\",\"c2\",\"c3\"]" );
		} catch (Exception e) {
			logger.debug(e.toString());
			returnValue = new ApiReturnModel(Responses.FAILURE_CODE,Responses.ERROR_MSG, e.getMessage());
		}
		
		return returnValue;
	}
	
	@RequestMapping(value = "/doctor", method = RequestMethod.GET)
	public ApiReturnModel doctors(@RequestParam("speciality") String speciality,
			@RequestParam("location") String location){
		ApiReturnModel returnValue = null;
		try {
			List<DoctorModel> doctors = contentDao.getDoctorsInfo(speciality, location);
			returnValue = new ApiReturnModelDoctor(Responses.SUCCESS_CODE,Responses.SUCCESS_STATUS,Responses.SUCCESS_MSG,doctors);
			returnValue.setMessage("Location : " + location + "," + "Speciality : " + speciality);
			
			} catch (Exception e) {
			logger.debug(e.toString());
			returnValue = new ApiReturnModel(Responses.FAILURE_CODE,Responses.ERROR_MSG, e.getMessage());
		}
		
		return returnValue;
	}
}
