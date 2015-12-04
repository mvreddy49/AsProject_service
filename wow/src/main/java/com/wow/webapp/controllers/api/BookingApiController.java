package com.wow.webapp.controllers.api;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.engine.query.spi.ReturnMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wow.webapp.dao.BookingDAO;
import com.wow.webapp.dao.UserDAO;
import com.wow.webapp.domain.model.ApiReturnModel;
import com.wow.webapp.domain.model.BookingModel;
import com.wow.webapp.domain.model.ApiBookingReturnModel;
import com.wow.webapp.entitymodel.Booking;
import com.wow.webapp.entitymodel.Clinic;
import com.wow.webapp.entitymodel.Doctor;
import com.wow.webapp.entitymodel.Slot;
import com.wow.webapp.entitymodel.User;
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
				User user=userDao.findByUserName(userName);
				if(user!=null && user.getClinic()!=null)
					returnModel=getBookingsOnclinic(user.getClinic());
				else
					returnModel = getBookingsOnUser(userName);
			}

		} catch (Exception e) {
			logger.info("exception occurs :::" + e.toString());
			returnModel = new ApiBookingReturnModel(Responses.FAILURE_CODE,
					Responses.SUCCESS_STATUS, e.getMessage());

		}
		return returnModel;
	}

	private ApiReturnModel getBookingsOnclinic(Clinic clinic) {
		// TODO Auto-generated method stub
		
		ApiReturnModel returnModel = null;
		List<BookingModel> bookings = bookingDao.findBookingsOnClinic(clinic);
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

			User user = userDao.findByUserMobile(mobile);
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
	public ApiReturnModel slotBooking(@RequestParam("slotId") String slot_id,
			@RequestParam("slotTime") String slot_time,
			HttpServletRequest request) {

		ApiReturnModel retunModel = null;
		Utils utils=new Utils();
		UserDetails ud=null;
		List<String> errors = new ArrayList<String>();
		logger.info("enter into slotBooking, params are::::slot_id::" + slot_id
				+ " slot_time is::" + slot_time);
		try {
			Slot slot = bookingDao.findSlot(Integer.parseInt(slot_id));

			if (slot != null) {
				Clinic clinic = new Clinic(slot.getClinic().getId());
				Doctor doctor = new Doctor(slot.getDoctor().getId());

				boolean bookingStatus = false;
				boolean slotTimimgsStatus = false;

				if (checkSlotTimings(slot, slot_time)) {
					slotTimimgsStatus = true;
					List<Booking> bookings = bookingDao.findBookings(clinic,
							doctor);
					if (bookings != null && bookings.size() > 0) {
						if (checkBookingTimings(slot_time, bookings))
							bookingStatus = true;
						else
							bookingStatus = false;
					} else
						bookingStatus = true;

				} else
					slotTimimgsStatus = false;

				if (slotTimimgsStatus && bookingStatus) {

					
					String userName = null;
					ud=Utils.getUserSession();
					if (ud == null) {
						String name = request.getParameter("name");
						String mobile = request.getParameter("mobile");
						if (name != null && mobile != null) {
							User user = userDao.findByUserMobile(mobile);
							if (user != null) {
								logger.info("user is already availble in user table as a anonymous user");
								userName = user.getUsername();
							} else {
								User u = new User();
								u.setEnabled(true);
								u.setMobile(mobile);
								u.setUsername(mobile);
								u.setPassword(utils.getEncryptedPassword(mobile));
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
					retunModel = new ApiBookingReturnModel(
							Responses.SUCCESS_CODE, Responses.SUCCESS_STATUS,
							msg);

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
			/*if (slotTime.after(endTime) && slotTime.before(startTime)) {
				logger.info("slot checking is true");
				return true;
		    }
			else
			{
				logger.info("slot checking is false");
				return false;
			}
			Date startTime = slot.getStartTime();
			Date endTime = slot.getEndTime();
			*/
			int comp = startTime.compareTo(slotTime);
			int comp1 = endTime.compareTo(slotTime);
			if ((comp == -1 || comp == 0) && (comp1 == 0 || comp1 == 1)) {
				logger.info("slot checking is true");
				return true;
			} else {
				logger.info("slot checking is false");
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
