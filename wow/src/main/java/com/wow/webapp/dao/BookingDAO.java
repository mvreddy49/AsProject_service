package com.wow.webapp.dao;

import java.util.List;

import com.wow.webapp.domain.model.BookingModel;
import com.wow.webapp.entitymodel.Booking;
import com.wow.webapp.entitymodel.Clinic;
import com.wow.webapp.entitymodel.Doctor;
import com.wow.webapp.entitymodel.Slot;
import com.wow.webapp.entitymodel.User;

public interface BookingDAO {

	public Slot findSlot(Integer slot_id); 
	public List<Booking> findBookings(Clinic clinic,Doctor doctor);
	public void save(Booking b);
	public List<BookingModel> findBookingsOnUser(String userName);
	
	
}
