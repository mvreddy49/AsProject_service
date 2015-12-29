package com.wow.webapp.dao;

import java.util.List;


import com.wow.webapp.domain.model.ClinicModel;
import com.wow.webapp.domain.model.ClinicTestModel;
import com.wow.webapp.entitymodel.Clinic;
import com.wow.webapp.entitymodel.ClinicTest;
import com.wow.webapp.entitymodel.Doctor;
import com.wow.webapp.entitymodel.Slot;
import com.wow.webapp.entitymodel.User;

public interface UserDAO {
	public void save(User u);
	public void save(Clinic c);
	public void save(Doctor d);
	public void save(Slot s);
	
	public List<User> list();
	
	public List<ClinicModel> getLabs(String speciality, String location);
	public List<ClinicModel> getClinics(String speciality, String location);
	public List<ClinicModel> getPharmacy(String speciality, String location);
	
	
	public User findByUserName(String username) throws Exception;
	
	public Clinic findByClinicName(String clinicname);
	
	public void AddTestToClinic(String clinicname, ClinicTest t) ;
	public List<ClinicTestModel> getTests();
/*	public void save(BookingReturnModel b, String username) throws Exception ;
	public List<BookingReturnModel> getBookingsForUser(String username) throws Exception ;
*/	public void RandomTypeFill() throws Exception;

	public Clinic getClinicByUserName(String username);
}
