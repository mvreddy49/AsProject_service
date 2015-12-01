package com.wow.webapp.controllers.api;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wow.webapp.dao.BookingDAO;
import com.wow.webapp.dao.UserDAO;
import com.wow.webapp.domain.model.BookingModel;
import com.wow.webapp.entitymodel.Booking;
import com.wow.webapp.entitymodel.Clinic;
import com.wow.webapp.entitymodel.Doctor;
import com.wow.webapp.entitymodel.Slots;
import com.wow.webapp.entitymodel.User;

@RestController
@RequestMapping(value = "/api/bookings/")
public class BookingApiController {

	private static final Logger logger = LoggerFactory.getLogger(BookingApiController.class);

	@Autowired
	private BookingDAO bookingDao;
	@Autowired
	private UserDAO userDao;

	@RequestMapping(value = "/myBookings")
	public BookingModel myBookings() {
		BookingModel bookingModel = null;
		try {
			String userId = "3";
			//List<Booking> bookings = bookingDao.findBookingsOnUserId(Integer.parseInt(userId));
			User user=userDao.findByid(Integer.parseInt(userId));
			logger.info("bookings are:::"+user.toString());
			if (user != null) {
				bookingModel = new BookingModel(200, "records", "records", user.getBookings());
			} else {
				bookingModel = new BookingModel(400, "no records", "no records", null);
			}
		} catch (Exception e) {
			logger.info("some thing error comes");
			e.printStackTrace();
			bookingModel = new BookingModel(500, "internal error occurs", "error");

		}
		return bookingModel;
	}

	@RequestMapping(value = "/slotBooking")
	public BookingModel slotBooking(@RequestParam("slot_id") String slot_id,
			@RequestParam("slot_time") String slot_time, HttpServletRequest request) {

		BookingModel bookingModel = null;

		logger.info("enter into slotBooking slot_id::" + slot_id + " slot_time is::" + slot_time);
		try {
			Slots slot = bookingDao.findSlot(Integer.parseInt(slot_id));
			if (slot != null) {
				Clinic clinic = new Clinic(slot.getClinic().getId());
				Doctor doctor = new Doctor(slot.getDoctor().getId());

				boolean bookingStatus = false;
				boolean slotTimimgsStatus = false;

				if (checkSlotTimings(slot, slot_time)) {
					slotTimimgsStatus = true;
					List<Booking> bookings = bookingDao.findBookings(clinic, doctor);
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

					logger.info("booking started");
					String userId = null;
					if (userId == null) {
						String name = request.getParameter("name");
						String mobile = request.getParameter("mobile");
						if (name != null && mobile != null) {
							User user = userDao.findByUserMobile(mobile);
							if (user != null) {
								userId = String.valueOf(user.getId());
							} else {
								User u = new User();
								u.setEnabled(true);
								u.setMobile(mobile);
								u.setUsername(mobile);
								BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
								u.setPassword(passwordEncoder.encode(mobile));
								userDao.save(u);

								userId = String.valueOf(u.getId());
								logger.info("user saved sucess::userId::" + userId);
							}

						} else {
							logger.info("invalid parameters");
							bookingModel = new BookingModel(400, "Invalid user parameters",
									"we expected some user parameters");
							return bookingModel;
						}

					}

					User user = new User(Integer.parseInt(userId));
					Booking booking = new Booking();
					booking.setClinic(clinic);
					booking.setDoctor(doctor);
					booking.setUser(user);
					Date slotTime = convertStringToDate(slot_time);
					booking.setBooking_time(slotTime);
					bookingDao.save(booking);
					logger.info("new booking saved");
					bookingModel = new BookingModel(200, "slot booked successfully", "slot is allocated to user");

				} else {

					logger.info("booking already there");
					bookingModel = new BookingModel(202, "Request slot is not available",
							"already slot booked by another user");

				}
			} else {
				logger.info("slot is not availble");
				bookingModel = new BookingModel(400, "Request slot is not availble in slot table",
						"slot table is not availeble that record");
			}

		} catch (Exception e) {
			e.printStackTrace();
			bookingModel = new BookingModel(500, "internal exception occurs", "error");

		}
		return bookingModel;

	}

	private boolean checkSlotTimings(Slots slot, String slot_time) {
		// TODO Auto-generated method stub
		try {
			Date slotTime = convertStringToDate(slot_time);
			System.out.println("slotTime:::" + slotTime);
			Date startTime = slot.getStartTime();
			Date endTime = slot.getEndTime();
			int comp = startTime.compareTo(slotTime);
			int comp1 = endTime.compareTo(slotTime);
			System.out.println("comp:::" + comp + "  comp1:::" + comp1);
			if ((comp == -1 || comp == 0) && (comp1 == 0 || comp1 == 1)) {
				System.out.println("validate");
				logger.info("slot checking is true");
				return true;
			} else {
				logger.info("slot checking is false");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("date parsing exception occurs");
		}

		return false;
	}

	public boolean checkBookingTimings(String slot_time, List<Booking> bookings) {
		try {

			Date slotTime = convertStringToDate(slot_time);
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
			e.printStackTrace();
		}
		return false;
	}

	public Date convertStringToDate(String time) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			Date date = sdf.parse(time);
			return date;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
