package com.wow.webapp.dao;

import java.util.Date;
import java.util.List;

import com.wow.webapp.domain.model.BookingModel;
import com.wow.webapp.domain.model.ClinicModel;
import com.wow.webapp.domain.model.DoctorModel;
import com.wow.webapp.entitymodel.Clinic;
import com.wow.webapp.entitymodel.Doctor;
import com.wow.webapp.entitymodel.Slot;
import com.wow.webapp.entitymodel.User;

public interface ContentDAO {
	
	public void save(Doctor d);
	public void save(Clinic c);
	public void save(Slot s);
	
	public Doctor findDoctorByMobile(String mobile) throws Exception;
	public List<ClinicModel> getClinics(String speciality, String location);
	public List<DoctorModel> getDoctorsInfo(String speciality, String location,String type);
	
	public Doctor getDoctorById(Integer id);
	
	public Doctor getDoctorByUser(User user);
	
	public List<Slot> findSlotsByClinicAndDoctor(Doctor d,Clinic c,String date);
	
	public List<String> findSlotsByStartAndEndTimes(Date startTime,Date endTime,Doctor d);
	

	public List<BookingModel> findBookingsOnClinic(Clinic clinic);
	public List<BookingModel> findBookingsOnClinic(Clinic clinic,String date);
	public List<BookingModel> findBookingsOnUser(String userName);
	public List<BookingModel> findBookingsOnDoctor(Doctor doctor);
	
	public List<DoctorModel> getDoctors();
	public List<DoctorModel> getDoctors(String speciality,String location);

}
