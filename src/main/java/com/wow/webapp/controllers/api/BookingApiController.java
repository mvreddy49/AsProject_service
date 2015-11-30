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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wow.webapp.dao.BookingDAO;
import com.wow.webapp.domain.model.BookingModel;
import com.wow.webapp.entitymodel.Booking;
import com.wow.webapp.entitymodel.Clinic;
import com.wow.webapp.entitymodel.Doctor;
import com.wow.webapp.entitymodel.Slots;
import com.wow.webapp.entitymodel.User;

@RestController
@RequestMapping(value = "/api/boolings/")
public class BookingApiController {

	private static final Logger logger = LoggerFactory.getLogger(BookingApiController.class);

	@Autowired
	private BookingDAO bookingDao;

	@RequestMapping(value = "/slotBooking")
	public BookingModel slotBooking(@RequestParam("slot_id") String slot_id,
			@RequestParam("slot_time") String slot_time,HttpServletRequest request) {

		BookingModel bookingModel = new BookingModel();

		logger.info("enter into slotBooking slot_id::" + slot_id+" slot_time is::"+slot_time);
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
					String userId="1";
					if(userId ==null)
					{
						String name=request.getParameter("name");
						String mobile=request.getParameter("mobile");
						if(name!=null && mobile!=null)
						{
							
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
					System.out.println("new booking saved is success");
					bookingModel.setBooking_status("OK");
					bookingModel.setBooking_message("slot booked successfully");
					bookingModel.setBooking_desc("slots availbale");
				} else {
					
					logger.info("booking already there");
					bookingModel.setBooking_status("ok");
					bookingModel.setBooking_message("already slot booked");
					bookingModel.setBooking_desc("already available");
				}
			} else
				logger.info("slot is not availble");

		} catch (Exception e) {
			e.printStackTrace();
			bookingModel.setBooking_status("ok");
			bookingModel.setBooking_message("error occurs");
			bookingModel.setBooking_desc("error");
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
	public Date convertStringToDate(String time) 
	{
		try{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date date = sdf.parse(time);
		return date;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
