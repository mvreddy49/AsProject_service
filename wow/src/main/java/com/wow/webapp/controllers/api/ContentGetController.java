package com.wow.webapp.controllers.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wow.webapp.dao.BookingDAO;
import com.wow.webapp.dao.ContentDAO;
import com.wow.webapp.dao.UserDAO;
import com.wow.webapp.domain.model.ApiReturnModel;
import com.wow.webapp.domain.model.ApiReturnModelClinics;
import com.wow.webapp.domain.model.ApiReturnModelDoctor;
import com.wow.webapp.domain.model.ApiReturnSlotModel;
import com.wow.webapp.domain.model.ClinicModel;
import com.wow.webapp.domain.model.DoctorModel;
import com.wow.webapp.domain.model.SlotsModel;
import com.wow.webapp.entitymodel.Booking;
import com.wow.webapp.entitymodel.Clinic;
import com.wow.webapp.entitymodel.Doctor;
import com.wow.webapp.entitymodel.Slot;
import com.wow.webapp.util.Responses;
import com.wow.webapp.util.Utils;



@RestController
@RequestMapping("/api")
public class ContentGetController {

	private static final Logger logger = LoggerFactory.getLogger(ContentGetController.class);
	
	@Autowired
	private ContentDAO contentDao;
	
	@Autowired
	private BookingDAO bookingDao;
	
	@Autowired
	private UserDAO userDao;
	
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
	
	@RequestMapping(value = "/getDoctors", method = RequestMethod.GET)
	public ApiReturnModel doctors(
			@RequestParam(value = "speciality", required = false) String speciality,
			@RequestParam(value = "location", required = false, defaultValue = "hyderabad") String location,
			@RequestParam(value = "type", required = false, defaultValue = "clinic") String type
			){
		ApiReturnModel returnValue = null;
		try {
			List<DoctorModel> doctors = contentDao.getDoctorsInfo(speciality, location,type);
			returnValue = new ApiReturnModelDoctor(Responses.SUCCESS_CODE,Responses.SUCCESS_STATUS,Responses.SUCCESS_MSG,doctors);
			returnValue.setMessage("Location : " + location + "," + "Speciality : " + speciality);
			
			} catch (Exception e) {
			logger.debug(e.toString());
			returnValue = new ApiReturnModel(Responses.FAILURE_CODE,Responses.ERROR_MSG, e.getMessage());
		}
		
		return returnValue;
	}
	
	@RequestMapping(value="/slotsByDate", method=RequestMethod.GET)
	public ApiReturnModel slots(@RequestParam("doctorId") String doctorId,@RequestParam("date") String date)
	{
		logger.info("enter into slotsBydate");
		Utils utils=new Utils();
		ApiReturnModel returnValue = null;
		try
		{
			if(!doctorId.isEmpty() && !date.isEmpty())
			{
			Clinic clinic = userDao.getClinicByUserName("9999999999");
			Doctor doctor=new Doctor(Integer.parseInt(doctorId));
			//Date time=utils.convertStringToDateOnly(date);
			List<Slot> slots=contentDao.findSlotsByClinicAndDoctor(doctor,clinic, date); 
			if(slots!=null && slots.size()>0)
			{
				logger.info("slots availble for request date"+date);
				List<SlotsModel> list=new ArrayList<SlotsModel>();
				List<SlotsModel> bookedSlots = new ArrayList<SlotsModel>();
				for(Slot slot:slots)
				{
					SlotsModel slotModel=new SlotsModel();
					slotModel.setId(slot.getId());
					slotModel.setTime(utils.convertDateToUTCFormat(slot.getTime()));
					if(slot.getUser() == null)	list.add(slotModel);
					else bookedSlots.add(slotModel);
					
				}
				returnValue=new ApiReturnSlotModel(Responses.SUCCESS_CODE, Responses.SUCCESS_STATUS, Responses.SUCCESS_MSG,list,bookedSlots);
				
			}
			else
			{
				returnValue=new ApiReturnModel(Responses.FAILURE_CODE, Responses.FAILURE_STATUS, "slots not available");
				logger.info("slots are not availble for requestdate::"+date);
			}
			}
			else
				returnValue = new ApiReturnModel(Responses.FAILURE_CODE,Responses.ERROR_MSG, "doctor Id and date  cant be empty");
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.debug(e.toString());
			returnValue = new ApiReturnModel(Responses.FAILURE_CODE,Responses.ERROR_MSG, e.getMessage());
		}
		return returnValue;
	}
	
	@RequestMapping(value="/doctor",method=RequestMethod.GET)
	public ApiReturnModel getDoctors(
			@RequestParam(value = "speciality", required = false) String speciality,
			@RequestParam(value = "location", required = false, defaultValue = "hyderabad") String location,
			@RequestParam(value = "type", required = false, defaultValue = "clinic") String type
			)
	{
		ApiReturnModel returnValue=null;
		logger.info("enter into getDoctors:::");
		try
		{
			List<DoctorModel> doctors=contentDao.getDoctors(speciality,location);
			returnValue=new ApiReturnModelDoctor(Responses.SUCCESS_CODE,Responses.SUCCESS_STATUS,Responses.SUCCESS_MSG,doctors);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("while exception occurs getdoctors info:::"+e.toString());
			returnValue = new ApiReturnModel(Responses.FAILURE_CODE,Responses.ERROR_MSG, e.getMessage());
		}
		return returnValue;
		
	}
	
	
}
