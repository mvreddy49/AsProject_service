package com.wow.webapp.controllers.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wow.webapp.dao.BookingDAO;
import com.wow.webapp.dao.ContentDAO;
import com.wow.webapp.dao.UserDAO;
import com.wow.webapp.domain.model.ApiBookingReturnModel;
import com.wow.webapp.domain.model.ApiReturnModel;
import com.wow.webapp.domain.model.BookingModel;
import com.wow.webapp.domain.model.CreateBookingModel;
import com.wow.webapp.entitymodel.Authority;
import com.wow.webapp.entitymodel.Booking;
import com.wow.webapp.entitymodel.Clinic;
import com.wow.webapp.entitymodel.Doctor;
import com.wow.webapp.entitymodel.Profile;
import com.wow.webapp.entitymodel.Slot;
import com.wow.webapp.entitymodel.User;
import com.wow.webapp.util.Constants;
import com.wow.webapp.util.Responses;
import com.wow.webapp.util.Utils;

@RestController
@RequestMapping(value = "/api/bookings/")
public class BookingApiController {

	private static final Logger logger = LoggerFactory
			.getLogger(BookingApiController.class);

	@Autowired
	private BookingDAO bookingDao;
	@Autowired
	private UserDAO userDao;
	@Autowired
	private ContentDAO contentDao;

	@RequestMapping(value = "/myBookings")
	public ApiReturnModel myBookings(HttpServletRequest request) {
		ApiReturnModel returnModel = null;
		List<String> errors = new ArrayList<String>();
		UserDetails ud=null;
		try {
			ud=Utils.getUserSession();
			if (ud == null) {
				returnModel = new ApiBookingReturnModel(
						Responses.USER_NOTLOGGEDIN, Responses.SUCCESS_STATUS,
						"user not logged in,please login");
			} else {
				logger.debug("Authorities : "+ ud.getAuthorities());
				String userName = ud.getUsername();
				String role=null;
				for(GrantedAuthority auth :ud.getAuthorities())
					role=auth.getAuthority();
				if(role!=null && role.contains(Constants.ROLE_RECP))
				{	
					logger.debug("In role recp"+userName);
					User user=userDao.findByUserName(userName);
					//if clinic wants to date by bookings
					String requestedDate=request.getParameter("date");
					logger.debug("Before calling getCookings on clinic");
					returnModel=getBookingsOnclinic(user.getClinic(),requestedDate);
				}
				else{
					returnModel = getBookingsOnUser(userName);
				}
			}

		} catch (Exception e) {
			logger.info("exception occurs :::" + e.toString());
			returnModel = new ApiBookingReturnModel(Responses.FAILURE_CODE,
					Responses.SUCCESS_STATUS, e.getMessage());

		}
		return returnModel;
	}

	private ApiReturnModel getBookingsOnclinic(Clinic clinic,String date) {
		// TODO Auto-generated method stub
		logger.debug("In get bookings on clinic");
		Utils utils=new Utils();
		ApiReturnModel returnModel = null;
		List<BookingModel> bookings=null;
		
		if(date!=null){
			Date bookingByDate=utils.convertStringToDateOnly(date);
			bookings = contentDao.findBookingsOnClinic(clinic,utils.convertStringToDateOnly(bookingByDate));
		}else
			bookings = contentDao.findBookingsOnClinic(clinic);
		// User user=userDao.findByid(Integer.parseInt(userId));
		returnModel=commonReturnBookingModel(bookings);
		return returnModel;
	}
	

	private ApiReturnModel getBookingsOnUser(String userName) {
		ApiReturnModel returnModel = null;
		List<BookingModel> bookings = contentDao.findBookingsOnUser(userName);
		// User user=userDao.findByid(Integer.parseInt(userId));
		returnModel=commonReturnBookingModel(bookings);
		return returnModel;
	}

	private ApiReturnModel commonReturnBookingModel(List<BookingModel> bookings)
	{
		ApiReturnModel returnModel = null;
		ApiBookingReturnModel bookingReturnModel = new ApiBookingReturnModel(
				Responses.SUCCESS_CODE, Responses.SUCCESS_STATUS,
				Responses.SUCCESS_MSG);
		bookingReturnModel.setBookings(bookings);
		returnModel = bookingReturnModel;
		return returnModel;
	}
	
	@RequestMapping(value = "/anonymousBookings")
	public ApiReturnModel anonymousBookings(
			@RequestParam("mobile") String mobile) {
		ApiReturnModel returnModel = null;
		List<String> errors = new ArrayList<String>();
		try {

			User user = userDao.findByUserName(mobile);
			if (user != null)
				returnModel = getBookingsOnUser(user.getUsername());
			else
				returnModel = new ApiBookingReturnModel(Responses.CUSTOM_CODE,
						Responses.SUCCESS_STATUS,
						"Mobile number is not registred");

		} catch (Exception e) {
			logger.info("exception occurs :::" + e.toString());
			returnModel = new ApiBookingReturnModel(Responses.FAILURE_CODE,
					Responses.SUCCESS_STATUS, e.getMessage());

		}
		return returnModel;
	}

	private void addUser(CreateBookingModel createBookingModel)
	{
		try
		{
		User user=new User();
		user.setUsername(createBookingModel.getMobile());
		user.setPassword(new Utils().getEncryptedPassword(createBookingModel.getMobile()));
		user.setEnabled(true);
		Set<Authority> authorities = new HashSet<Authority>();
		authorities.add(new Authority(user, Constants.ROLE_USER));
		user.setUserRole(authorities);
		
		Profile userProfile =  new Profile(user, createBookingModel.getName());
		
		user.setUserProfile(userProfile);
		
		userDao.save(user);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	@RequestMapping(value = "/slotBooking")
	public ApiReturnModel slotBooking(@Valid CreateBookingModel createBookingModel,BindingResult bindingResult) {

		ApiReturnModel retunModel = null;
		Utils utils=new Utils();
		UserDetails ud=null;
		List<String> errors = new ArrayList<String>();
		
		if(bindingResult.hasFieldErrors()){
			for(FieldError e : bindingResult.getFieldErrors()){
				logger.debug("Field Name : " + e.getField() + ", Error : " + e.getDefaultMessage() );
				errors.add(e.getDefaultMessage());
			}
			retunModel=new ApiReturnModel(Responses.FAILURE_CODE, Responses.FAILURE_STATUS,
					Responses.ERROR_MSG, errors);
			
		}
		else
		{
			try
			{
				Slot slot = bookingDao.findSlot(Integer.parseInt(createBookingModel.getSlotId()));
				if (slot != null) {
					
					ud=Utils.getUserSession();
					String role=null;
					String userName=null;
					
					if (ud == null) {
						if(createBookingModel.getMobile() == null){
							retunModel=new ApiReturnModel(Responses.FAILURE_CODE, Responses.FAILURE_STATUS,
									"Please enter mobile number", errors);
							return retunModel;
						}
						User user=userDao.findByUserName(createBookingModel.getMobile());
						if(user == null){
							addUser(createBookingModel);
							userName = createBookingModel.getMobile();
						}
						else
							userName=user.getUsername();
						slot.setSource("web");
					}
					else
					{
						for(GrantedAuthority auth :ud.getAuthorities())
							role=auth.getAuthority();
						if(role!=null && role.contains(Constants.ROLE_RECP))
						{
							addUser(createBookingModel);
							userName = createBookingModel.getMobile();
							slot.setSource("recp");
						}else
						{
							userName=ud.getUsername();
							slot.setSource("web");
						}
							
					}
					User user=new User(userName);
					slot.setUser(user);
					slot.setStatus(true);
					
					contentDao.save(slot);
					
					retunModel=new ApiReturnModel(Responses.SUCCESS_CODE, Responses.SUCCESS_STATUS,
							"slot ceated success", errors);
					
				}
				else
					retunModel=new ApiReturnModel(Responses.FAILURE_CODE, Responses.FAILURE_STATUS,
							"slot is not availble", errors);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				logger.info("exception occurs while slotBooking");
				retunModel=new ApiReturnModel(Responses.FAILURE_CODE, Responses.FAILURE_STATUS,
						"slot is not availble", errors);
			}
		}
		return retunModel;

	}

	/*private boolean checkSlotTimings(Slot slot, String slot_time) {
		// TODO Auto-generated method stub
		try {
			Utils utils=new Utils();
			
			Date slotTime=utils.getTimeFromDate(utils.convertStringToDate(slot_time));
			Date startTime =utils.getTimeFromDate(new Date());
			Date endTime=utils.getTimeFromDate(new Date());
			System.out.println("times are ::slotTime::"+slotTime.toString()+" ::::startTime::"+startTime+" ::: endtime::"+endTime);
			
			int comp = startTime.compareTo(slotTime);
			int comp1 = endTime.compareTo(slotTime);
			if ((comp == -1 || comp == 0) && (comp1 == 0 || comp1 == 1)) {
				logger.info("slot timings checking is true");
				
				List<String> timeRanges=utils.getRangeTimes(startTime,endTime,30);
				logger.info("time ranges are:::"+timeRanges.toString());
				if(timeRanges.contains(utils.convertDateToUTCFormat(slotTime)))
				{	
					logger.info("slot time is  validating in time period");
					return true;
				
				}else{
					logger.info("slot time is  not validating in time period");
					return false;
				}
			} else {
				logger.info("slot timings checking is false");
				return false;
			}
		} catch (Exception e) {
			logger.info("exception ocuurs:::" + e.toString());
		}

		return false;
	}

	public boolean checkBookingTimings(String slot_time, List<Booking> bookings) {
		try {

			Date slotTime = new Utils().convertStringToDate(slot_time);
			List<Date> booking_slottimings = new ArrayList<Date>();
			for (Booking b : bookings) {
				booking_slottimings.add(b.getBooking_time());
			}
			if (booking_slottimings.contains(slotTime)) {
				logger.info("request slot time is already booked");
				return false;
			} else
				return true;
		} catch (Exception e) {
			logger.info("exception ocuurs:::" + e.toString());
		}
		return false;
	}
	*/
	

	
}
