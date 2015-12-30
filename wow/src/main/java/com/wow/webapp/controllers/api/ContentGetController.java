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
			@RequestParam(required=false) String location,@RequestParam("type") String type){
		ApiReturnModel returnValue = null;
		try {
			location = "Hyderabad";
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
	public ApiReturnModel slots(@RequestParam("clinicId") String clinicId,@RequestParam("doctorId") String doctorId,@RequestParam("date") String date)
	{
		Utils utils=new Utils();
		ApiReturnModel returnValue = null;
		try
		{
			Doctor doctor=new Doctor();
			Clinic clinic=new Clinic(Integer.parseInt(clinicId));
			Slot slot=contentDao.findSlotsByClinicAndDoctor(doctor,clinic);
			if(slot!=null)
			{
				Date slotDate=utils.convertStringToDateOnly(date);
				List<String> bookedSlots=new ArrayList<String>();
				List<Booking> bookings=bookingDao.findBookings(clinic, doctor, utils.convertStringToDateOnly(slotDate));
				if(bookings!=null & bookings.size()>0)
				{
					for(Booking b:bookings)
						bookedSlots.add(utils.convertDateToUTCFormat(b.getBooking_time()));
					
				}
				/* slots info */
				SlotsModel slotModel=new SlotsModel();
				slotModel.setId(slot.getId());
				//slotModel.setStartTime(utils.convertDateToUTCFormat(slot.getStartTime()));
				//slotModel.setEndTime(utils.convertDateToUTCFormat(slot.getEndTime()));
				//slotModel.setSlots(utils.getRangeTimes(utils.addDateAndTime(slotDate,slot.getStartTime()),utils.addDateAndTime(slotDate, slot.getEndTime())));
				slotModel.setBookedSlots(bookedSlots);
				
				returnValue=new ApiReturnSlotModel(Responses.SUCCESS_CODE, Responses.SUCCESS_STATUS, Responses.SUCCESS_MSG, slotModel);
				
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.debug(e.toString());
			returnValue = new ApiReturnModel(Responses.FAILURE_CODE,Responses.ERROR_MSG, e.getMessage());
		}
		return returnValue;
	}
}
