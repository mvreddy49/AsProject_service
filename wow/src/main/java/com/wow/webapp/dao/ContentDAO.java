package com.wow.webapp.dao;

import java.util.List;

import com.wow.webapp.domain.model.ClinicModel;
import com.wow.webapp.entitymodel.Clinic;
import com.wow.webapp.entitymodel.Doctor;
import com.wow.webapp.entitymodel.User;

public interface ContentDAO {
	public void save(Doctor d);
	public void save(Clinic c);
	public Doctor findDoctorByMobile(String mobile) throws Exception;
	public List<ClinicModel> getClinics(String speciality, String location);
}
