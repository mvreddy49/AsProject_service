package com.wow.webapp.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.wow.webapp.domain.model.ClinicModel;
import com.wow.webapp.domain.model.ClinicTestModel;
import com.wow.webapp.entitymodel.Clinic;
import com.wow.webapp.entitymodel.ClinicAddress;
import com.wow.webapp.entitymodel.ClinicTest;
import com.wow.webapp.entitymodel.Doctor;
import com.wow.webapp.entitymodel.Profile;
import com.wow.webapp.entitymodel.Slot;
import com.wow.webapp.entitymodel.User;

public class UserDAOImpl implements UserDAO{

	private SessionFactory sessionFactory;
	private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);
	
	public UserDAOImpl(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}
	
	@Transactional
	public void save(Doctor d) {
		Session session = this.getSession();
        session.persist(d);
	}
	
	@Transactional
	public void save(Slot s) {
		Session session = this.getSession();
        session.persist(s);
	}
	
	@Transactional
	public void save(User u) {
		Session session = this.getSession();
        session.saveOrUpdate(u);
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<User> list() {
		Session session = this.getSession();
		
		List<User> list = session.createQuery("from User order by modified_on desc").list();
		for(User usr : list){
			logger.debug("User : " + usr.getUsername() + ":" + usr.getPassword() + ":" + usr.isEnabled());
		}
        return list;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public Clinic findByClinicName(String clinicname) {
		logger.debug("findByClinicName start " + clinicname);
		Clinic c = null;
		Session session = this.getSession();
		
		List<Clinic> list = session.createQuery("from Clinic where name=:clinicname")
		.setParameter("clinicname", clinicname)
		.list();

		if(list.size() > 0){
			logger.debug("Clinic List > 0 ");
			c = list.get(0);
			logger.debug("Clinic found : " + c.getName());
		}
		logger.debug("findByClinicName end ");
		return c;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public void AddTestToClinic(String clinicname, ClinicTest t) {
		logger.debug("AddTestToClinic start " + clinicname);
		Clinic c = null;
		Session session = this.getSession();
		
		List<Clinic> list = session.createQuery("from Clinic where name=:clinicname")
		.setParameter("clinicname", clinicname)
		.list();

		if(list.size() > 0){
			logger.debug("Clinic List > 0 ");
			
			c = list.get(0);
			logger.debug("Clinic found : " + c.getName());
			c.getTests().add(t);
			t.setClinic(c);
			session.saveOrUpdate(c);
		}
		logger.debug("AddTestToClinic end ");
		
	}
	
	
	@Transactional
	public User findByUserName(String username) throws Exception {
		logger.debug("findByUserName start " + username + ":" + username);
		User u = this.findUserByUsername(this.getSession(), username);
		//if(u== null) throw new Exception("Not Found : " + username);
		logger.debug("findByUserName end ");
		return u;
	}
	
	@Transactional
	public void save(Clinic c) {
		logger.debug("save clinic start" );
		Session session = this.getSession();
        //session.persist(c);
		session.saveOrUpdate(c);
		logger.debug("save clinic end" );
	}
	
		
	@SuppressWarnings("unchecked")
	@Transactional
	public List<ClinicModel> getLabs(String speciality, String location){
		logger.debug("getLabs start");
		List<ClinicModel> list = new ArrayList<ClinicModel>();
		Session session = this.getSession();
		List<Clinic> clinics = session.createQuery("from Clinic where type=:paramType")
				.setParameter("paramType", "lab")
				.list();
		for(  Clinic c : clinics){
			logger.debug(c.getName() + " " + c.getSpecialities() + " " + c.getAddresses() );
			boolean specialityMatch = false;
			if(c.getSpecialities() != null ){
				for(String spec : c.getSpecialities().split(",")){
					logger.debug(spec);
					if(spec.toLowerCase().equals(speciality.toLowerCase())) {
						specialityMatch = true; break;
					}
				}
			}
			logger.debug("Speciality Match : " + specialityMatch);
			if(!specialityMatch) continue;
			boolean addressMatch = false;
			for( ClinicAddress addr : c.getAddresses()){
				if(addr.getCity().toLowerCase().equals(location.toLowerCase())){
					addressMatch = true; break;
				}
			}
			logger.debug("addressMatch Match : " + addressMatch);
			if(!addressMatch) continue;
			ClinicModel model = new ClinicModel();
			model.setClinicName(c.getName());
			model.setClinicDesc(c.getDescription());
			
			model.setClinicAddress(c.getAddresses().toString());
			model.setClinicPhones(c.getPhoneNos().toString());
			list.add(model);
		}
		logger.debug("getLabs end");
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<ClinicModel> getClinics(String speciality, String location){
		logger.debug("getClinic start");
		List<ClinicModel> list = new ArrayList<ClinicModel>();
		Session session = this.getSession();
		List<Clinic> clinics = session.createQuery("from Clinic where type=:paramType")
				.setParameter("paramType", "clinic")
				.list();
		for(  Clinic c : clinics){
			logger.debug(c.getName() + " " + c.getSpecialities() + " " + c.getAddresses() );
			boolean specialityMatch = false;
			if(c.getSpecialities() != null ){
				for(String spec : c.getSpecialities().split(",")){
					logger.debug(spec);
					if(spec.toLowerCase().equals(speciality.toLowerCase())) {
						specialityMatch = true; break;
					}
				}
			}
			logger.debug("Speciality Match : " + specialityMatch);
			if(!specialityMatch) continue;
			boolean addressMatch = false;
			for( ClinicAddress addr : c.getAddresses()){
				if(addr.getCity().toLowerCase().equals(location.toLowerCase())){
					addressMatch = true; break;
				}
			}
			logger.debug("addressMatch Match : " + addressMatch);
			if(!addressMatch) continue;
			ClinicModel model = new ClinicModel();
			model.setClinicName(c.getName());
			model.setClinicDesc(c.getDescription());
			
			model.setClinicAddress(c.getAddresses().toString());
			model.setClinicPhones(c.getPhoneNos().toString());
			list.add(model);
		}
		logger.debug("getClinics end");
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<ClinicModel> getPharmacy(String speciality, String location){
		logger.debug("getPharmacy start");
		List<ClinicModel> list = new ArrayList<ClinicModel>();
		Session session = this.getSession();
		List<Clinic> clinics = session.createQuery("from Clinic where type=:paramType")
				.setParameter("paramType", "pharmacy")
				.list();
		for(  Clinic c : clinics){
			logger.debug(c.getName() + " " + c.getSpecialities() + " " + c.getAddresses() );
			boolean specialityMatch = false;
			if(c.getSpecialities() != null ){
				for(String spec : c.getSpecialities().split(",")){
					logger.debug(spec);
					if(spec.toLowerCase().equals(speciality.toLowerCase())) {
						specialityMatch = true; break;
					}
				}
			}
			logger.debug("Speciality Match : " + specialityMatch);
			if(!specialityMatch) continue;
			boolean addressMatch = false;
			for( ClinicAddress addr : c.getAddresses()){
				if(addr.getCity().toLowerCase().equals(location.toLowerCase())){
					addressMatch = true; break;
				}
			}
			logger.debug("addressMatch Match : " + addressMatch);
			if(!addressMatch) continue;
			ClinicModel model = new ClinicModel();
			model.setClinicName(c.getName());
			model.setClinicDesc(c.getDescription());
			
			model.setClinicAddress(c.getAddresses().toString());
			model.setClinicPhones(c.getPhoneNos().toString());
			list.add(model);
		}
		logger.debug("getPharmacy end");
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<ClinicTestModel> getTests(){
		logger.debug("getTests start");
		Session session = this.getSession();
		List<ClinicTest> tests = session.createQuery("from ClinicTest").list();
		List<ClinicTestModel> list = new ArrayList<ClinicTestModel>();
		if(tests.size() > 0)
		{
			for(ClinicTest t : tests){
				ClinicTestModel model = new ClinicTestModel();
				model.setTest_name(t.getTest_name());
				model.setTest_description(t.getTest_description());
				model.setOnline_reports(t.isOnline_reports());
				model.setHome_pickup(t.isHome_pickup());
				model.setActual_price(t.getActual_price());
				model.setCurrent_price(t.getCurrent_price());
				model.setClinic_name(t.getClinic().getName());
				list.add(model);
			}
		}
		logger.debug("getTests end");
		return list;
	}
	
	/*@Transactional
	public void save(BookingReturnModel b, String username) throws Exception {
		logger.debug("save(BookingModel) start");
		Session session = this.getSession();
		
		ClinicTest test = findClinicTestByNameForClinic(session, b.getClinic_name(), b.getTest_name());
		User u = findUserByUsername(session,username);

		Booking booking = new Booking(u, b.getDate(), test, b.isHomePickUp());
		session.saveOrUpdate(booking);

		logger.debug("save(BookingModel) end");
		
	}
	
	@Transactional
	public List<BookingReturnModel> getBookingsForUser(String username) throws Exception {
		Session session = this.getSession();
		List<BookingReturnModel> list = new ArrayList<BookingReturnModel>();
		User u = this.findUserByUsername(session, username);
		for(Booking b : u.getBookings()){
			BookingModel model = new BookingModel();
			model.setId(b.getBooking_id());
			model.setTest_name(b.getTest().getTest_name());
			model.setClinic_name(b.getTest().getClinic().getName());
			model.setHomePickUp(b.isHomePickUp());
			model.setDate(b.getDate());
			list.add(model);
		}
		return list;
	}*/
	
	private Session getSession(){
		return this.sessionFactory.getCurrentSession();
	} 
	
	private ClinicTest findClinicTestByNameForClinic(Session session, Clinic clinic, String testName) throws Exception{
		ClinicTest test = null;
		for(ClinicTest t : clinic.getTests()){
			if(t.getTest_name().equals(testName)){
				test = t;
				break;
			}
		}
		
		if(test == null ){
			logger.debug("Not Found : " + testName);
			throw new Exception("Not Found : " + testName);
		}
		return test;
	}
	
	private ClinicTest findClinicTestByNameForClinic(Session session, String clinicName, String testName) throws Exception{
		Clinic clinic = findClinicByName(session, clinicName);
		return findClinicTestByNameForClinic(session, clinic, testName);
		
	}
	
	private Clinic findClinicByName(Session session, String clinicName) throws Exception{
		@SuppressWarnings("unchecked")
		List<Clinic> clinicList = session.createQuery("from Clinic where name=:clinic_name")
				.setParameter("clinic_name", clinicName)
				.list();
		if(clinicList == null || clinicList.size() <= 0){
			String msg = "Not Found : " + clinicName;
			logger.debug(msg);
			throw new Exception(msg);
			
		}
		return clinicList.get(0);
	}
	
	private User findUserByUsername(Session session ,String username) throws Exception{
		@SuppressWarnings("unchecked")
		List<User> userList = session.createQuery("from User where username=:username")
				.setParameter("username", username)
				.list();
		if(userList == null || userList.size() <= 0){
			return null;
		}
		return userList.get(0);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public Clinic getClinicByUserName(String username){
		Session session = this.getSession();
		List<User> userList = session.createQuery("from User where username=:username")
				.setParameter("username", username)
				.list();
		if(userList == null || userList.size() <= 0){
			return null;
		}
		logger.debug("Before clinics :"+ userList.get(0));
		return userList.get(0).getClinic();
	}

	public void RandomTypeFill() throws Exception {
		
		
	}
	/*@Transactional
	public User findByUserMobile(String mobile) throws Exception {
		// TODO Auto-generated method stub
		Session session=this.getSession();
		List<User> user=session.createQuery("from User u where u.mobile=?").setParameter(0, mobile).list();
		if(user!=null && user.size() > 0)
			return user.get(0);
		return null;
	}*/

}
