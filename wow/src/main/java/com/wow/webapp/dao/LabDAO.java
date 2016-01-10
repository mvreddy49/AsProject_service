package com.wow.webapp.dao;


import java.util.Date;
import java.util.List;

import com.wow.webapp.entitymodel.LabBooking;
import com.wow.webapp.entitymodel.LabSlots;
import com.wow.webapp.entitymodel.LabSubType;
import com.wow.webapp.entitymodel.LabType;
import com.wow.webapp.entitymodel.User;

public interface LabDAO {

	public void save(LabType labType);
	public void save(LabSubType labSubType);
	public void save(LabSlots labSlots);
	public void save(LabBooking labBooking);
	
	public LabType findLabType(Integer id);
	
	public List<LabType> getLabType();
	public List<LabSubType> getLabSubType(LabType labType);
	public List<LabSubType> getLabSubType();
	public LabSubType gerLabsubType(Integer id);
	
	public List<String> findSlotsByStartAndEndTimes(Date startTime, Date endTime, LabSubType labsubType);
	
	public LabSlots findSlot(Integer id);
	public List<LabBooking> findBookingsOnslot(LabSlots slot , String receive_mode);
	public List<LabBooking> findBookingsOnslot(LabSlots slot);
	public List<LabSlots> findLabSlotsOnDate(LabSubType labSubType, String date);
	
	public List<LabBooking> findLabBookings(String date);
	public List<LabBooking> findLabBookings();
	public List<LabBooking> findLabBookingsByUser(User user);
}
