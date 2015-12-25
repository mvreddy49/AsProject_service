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
import com.wow.webapp.dao.UserDAO;
import com.wow.webapp.domain.model.ApiBookingReturnModel;
import com.wow.webapp.domain.model.ApiReturnModel;
import com.wow.webapp.domain.model.BookingModel;
import com.wow.webapp.domain.model.CreateBookingModel;
import com.wow.webapp.entitymodel.Authority;
import com.wow.webapp.entitymodel.Booking;
import com.wow.webapp.entitymodel.Clinic;
import com.wow.webapp.entitymodel.Doctor;
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
				if(role!=null && role.contains(Constants.ROLE_CLINIC))
				{	
					User user=userDao.findByUserName(userName);
					//if clinic wants to date by bookings
					String requestedDate=request.getParameter("date");
					returnModel=getBookingsOnclinic(user.getClinic(),requestedDate);
				}else
					returnModel = getBookingsOnUser(userName);
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
		Utils utils=new Utils();
		ApiReturnModel returnModel = null;
		List<BookingModel> bookings=null;
		
		if(date!=null){
			Date bookingByDate=utils.convertStringToDateOnly(date);
			bookings = bookingDao.findBookingsOnClinic(clinic,utils.convertStringToDateOnly(bookingByDate));
		}else
			bookings = bookingDao.findBookingsOnClinic(clinic);
		// User user=userDao.findByid(Integer.parseInt(userId));
		returnModel=commonReturnBookingModel(bookings);
		return returnModel;
	}

	private ApiReturnModel getBookingsOnUser(String userName) {
		ApiReturnModel returnModel = null;
		List<BookingModel> bookings = bookingDao.findBookingsOnUser(userName);
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
			retunModel=new ApiBookingReturnModel(
					Responses.CUSTOM_CODE, Responses.SUCCESS_STATUS,
					Responses.ERROR_MSG, errors);
			return retunModel;
		}
		try {
			Slot slot = bookingDao.findSlot(Integer.parseInt(createBookingModel.getSlotId()));

			if (slot != null) {
				Clinic clinic = new Clinic(slot.getClinic().getId());
				Doctor doctor = new Doctor(slot.getDoctor().getId());

				boolean bookingStatus = false;
				boolean slotTimimgsStatus = false;
				String slot_time=createBookingModel.getSlotTime();
				if (checkSlotTimings(slot, slot_time)) {
					slotTimimgsStatus = true;
					List<Booking> bookings = bookingDao.findBookings(clinic,
							doctor,utils.convertStringToDateOnly(utils.convertStringToDateOnly(slot_time)));
					if (bookings != null && bookings.size() > 0) {
						//if type is a labs , don't check to  checkBookingTimings
						if(slot.getClinic().getType().equalsIgnoreCase(Constants.CLINIC_TYPE))
						{	
							//size limit of the users booking of given time
							if(bookings.size() >= Constants.LABS_BOOKING_LIMIt)
								bookingStatus = false;
							else 
								bookingStatus = true;
						
						}else
							bookingStatus=checkBookingTimings(slot_time, bookings);
							
					} else
						bookingStatus = true;

				} else
					slotTimimgsStatus = false;

				if (slotTimimgsStatus && bookingStatus) {

					
					String userName = null;
					ud=Utils.getUserSession();
					if (ud == null) {
						String name = createBookingModel.getName();
						String mobile =createBookingModel.getMobile();
						if (name != null && mobile != null) {
							User user = userDao.findByUserName(mobile);
							if (user != null) {
								logger.info("user is already availble in user table as a anonymous user");
								userName = user.getUsername();
							} else { 
								User u = new User();
								u.setEnabled(true);
								u.setUsername(mobile);
								u.setPassword(utils.getEncryptedPassword(mobile));
								Set<Authority> authorities = new HashSet<Authority>();
								authorities.add(new Authority(u, "ROLE_USER"));
								u.setUserRole(authorities);
								userDao.save(u);
								userName = u.getUsername();
								logger.info("user saved sucess::userName::"
										+ userName);
							}

						} else {
							logger.info("invalid user parameters");
							errors.add(Responses.INVALID_USER_PARAM);
							retunModel = new ApiBookingReturnModel(
									Responses.INVALID_PARAM_CODE,
									Responses.SUCCESS_STATUS,
									Responses.INVALID_PARAMS_MSG, errors);
							return retunModel;
						}

					}
					else
						userName = ud.getUsername();
					logger.info("userName:::::" + userName);
					User user = new User(userName);
					Booking booking = new Booking();
					booking.setClinic(clinic);
					booking.setDoctor(doctor);
					booking.setUser(user);
					Date slotTime = utils.convertStringToDate(slot_time);
					booking.setBooking_time(slotTime);
					booking.setUpdated_on(slotTime);

					bookingDao.save(booking);
					logger.info("new booking saved");
					String msg = "Requested slot is booked success::: slot time is ::"
							+ slot_time;
					ApiBookingReturnModel  api= new ApiBookingReturnModel(
							Responses.SUCCESS_CODE, Responses.SUCCESS_STATUS,
							msg);
					
					List<BookingModel> bookingModel=bookingDao.findBookingsOnId(booking.getId());
					api.setBookings(bookingModel);
					
					retunModel=api;

				} else {

					logger.info("booking already there");
					errors.add("Requested slot is booked by another user");
					retunModel = new ApiBookingReturnModel(
							Responses.CUSTOM_CODE, Responses.SUCCESS_STATUS,
							Responses.ERROR_MSG, errors);

				}
			} else {
				logger.info("slot is not availble");
				errors.add("Requested slot is not availble in slot table");
				retunModel = new ApiBookingReturnModel(Responses.CUSTOM_CODE,
						Responses.SUCCESS_STATUS, Responses.ERROR_MSG, errors);
			}

		} catch (Exception e) {
			logger.info("exception occurs:::" + e.toString());
			e.printStackTrace();
			retunModel = new ApiBookingReturnModel(Responses.FAILURE_CODE,
					Responses.SUCCESS_STATUS, e.getMessage());

		}
		return retunModel;

	}

	private boolean checkSlotTimings(Slot slot, String slot_time) {
		// TODO Auto-generated method stub
		try {
			Utils utils=new Utils();
			
			Date slotTime=utils.getTimeFromDate(utils.convertStringToDate(slot_time));
			Date startTime =utils.getTimeFromDate(slot.getStartTime());
			Date endTime=utils.getTimeFromDate(slot.getEndTime());
			System.out.println("times are ::slotTime::"+slotTime.toString()+" ::::startTime::"+startTime+" ::: endtime::"+endTime);
			
			int comp = startTime.compareTo(slotTime);
			int comp1 = endTime.compareTo(slotTime);
			if ((comp == -1 || comp == 0) && (comp1 == 0 || comp1 == 1)) {
				logger.info("slot timings checking is true");
				
				List<String> timeRanges=utils.getRangeTimes(startTime,endTime);
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
	
	

	
}
