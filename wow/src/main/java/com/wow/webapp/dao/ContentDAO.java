package com.wow.webapp.dao;

import java.util.Date;
import java.util.List;

import com.wow.webapp.domain.model.ClinicModel;
import com.wow.webapp.domain.model.DoctorModel;
import com.wow.webapp.entitymodel.Clinic;
import com.wow.webapp.entitymodel.Doctor;
import com.wow.webapp.entitymodel.Slot;

public interface ContentDAO {
	
	public void save(Doctor d);
	public void save(Clinic c);
	public void save(Slot s);
	
	public Doctor findDoctorByMobile(String mobile) throws Exception;
	public List<ClinicModel> getClinics(String speciality, String location);
	public List<DoctorModel> getDoctorsInfo(String speciality, String location,String type);
	
	public Doctor getDoctorById(Integer id);
	
	public Slot findSlotsByClinicAndDoctor(Doctor d,Clinic c);
	
	public List<String> findSlotsByStartAndEndTimes(Date startTime,Date endTime);
}
