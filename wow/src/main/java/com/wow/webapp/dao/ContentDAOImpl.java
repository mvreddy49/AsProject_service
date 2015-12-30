package com.wow.webapp.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wow.webapp.domain.account.UserModel;
import com.wow.webapp.domain.model.BookingModel;
import com.wow.webapp.domain.model.ClinicModel;
import com.wow.webapp.domain.model.DoctorModel;
import com.wow.webapp.entitymodel.Booking;
import com.wow.webapp.entitymodel.Clinic;
import com.wow.webapp.entitymodel.ClinicAddress;
import com.wow.webapp.entitymodel.Doctor;
import com.wow.webapp.entitymodel.Slot;
import com.wow.webapp.entitymodel.User;
import com.wow.webapp.util.Utils;

public class ContentDAOImpl implements ContentDAO{

	private SessionFactory sessionFactory;
	private static final Logger logger = LoggerFactory.getLogger(BookingDAOImpl.class);

	public ContentDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}
	
	@Transactional
	public void save(Doctor d) {
		// TODO Auto-generated method stub
		System.out.println("enter into save bookings");
		Session session = this.getSession();
		session.saveOrUpdate(d.getUser());
		session.saveOrUpdate(d);
		
		
	}
	
	@Transactional
	public void save(Clinic c) {
		logger.debug("save clinic start" );
		Session session = this.getSession();
        //session.persist(c);
		session.saveOrUpdate(c);
		logger.debug("save clinic end" );
	}
	
	@Transactional
	public void save(Slot s){
		Session session = this.getSession();
		session.saveOrUpdate(s);
		
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
	public List<DoctorModel> getDoctorsInfo(String speciality, String location,String type){
		logger.debug("getDoctorsInfo start");
		Utils utils=new Utils();
		List<DoctorModel> list = new ArrayList<DoctorModel>();
		Session session = this.getSession();
		List<Clinic> clinics = session.createQuery("from Clinic where type=:paramType")
				.setParameter("paramType", type)
				.list();
		for(  Clinic c : clinics){
			boolean addressMatch = false;
			for( ClinicAddress addr : c.getAddresses()){
				if(addr.getCity().toLowerCase().equals(location.toLowerCase())){
					addressMatch = true; break;
				}
			}
			logger.debug("addressMatch Match : " + addressMatch);
			if(!addressMatch) continue;
			List<Slot> slots = session.createQuery("from Slot where clinic=:paramType group by doctor,clinic")
					.setParameter("paramType", c)
					.list();
			if(slots.size() == 0) continue;
			for(Slot s : slots){
				if(!s.getDoctor().getSpeciality().equalsIgnoreCase(speciality)) continue;
				
				DoctorModel doctorModel = new DoctorModel();
				Doctor d = s.getDoctor();
				doctorModel.setId(d.getId());
				doctorModel.setName(d.getUser().getUserProfile().getName());
				doctorModel.setMobile(d.getUser().getUsername());
				doctorModel.setSpeciality(s.getDoctor().getSpeciality());
				
				/*Clinic Info */ 
				
				ClinicModel clinicModel = new ClinicModel();
				clinicModel.setId(c.getId());
				clinicModel.setClinicName(c.getName());
				clinicModel.setClinicDesc(c.getDescription());
				clinicModel.setClinicAddress(c.getAddresses().toString());
				clinicModel.setClinicPhones(c.getPhoneNos().toString());
				doctorModel.setClinic(clinicModel);
				
				list.add(doctorModel);
				
			}
			
		}
		logger.debug("getClinics end");
		return list;
	}
	
	@Transactional
	public Doctor findDoctorByMobile(String mobile) throws Exception {
		logger.debug("findDoctorByMobile start " + mobile);
		Session session=this.getSession();
		User user=new User(mobile);
		List<Doctor> doctorList = session.createQuery("from Doctor where user=:user")
				.setParameter("user", user)
				.list();
		if(doctorList == null || doctorList.size() <= 0){
		logger.info("username is not availble in doctor table");
			return null;
		}
		return doctorList.get(0);
	}
	
	/*private Doctor findDoctorByMobile(Session session ,String mobile) throws Exception{
		@SuppressWarnings("unchecked")
		
		List<Doctor> doctorList = session.createQuery("from Doctor where User=:user")
				.setParameter("user", user)
				.list();
		if(doctorList == null || doctorList.size() <= 0){
//			logger.debug("Not Found : " + username);
//			throw new Exception("Not Found : " + username);
			return null;
		}
		return doctorList.get(0);
	}*/
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Slot> findSlotsByClinicAndDoctor(Doctor d,Clinic c,String date){
		Session session = this.getSession();
		logger.debug("Before");
		String slotDate="s.time like '%"+date+"%'";
		List<Slot> slots = session.createQuery("from Slot s where s.clinic=:clinic and s.doctor=:doctor and "+slotDate)
				.setParameter("clinic", c).setParameter("doctor", d).list();
		logger.debug("after");
		return slots;
	}

	@Transactional
	public Doctor getDoctorById(Integer id) {
		try
		{
			Session session = this.getSession();
			logger.debug("Before");
			List<Doctor> doctor = session.createQuery("from Doctor where id=:id").setParameter("id", id).list();
			logger.debug("after");
			if(doctor == null || doctor.size() <= 0) return null;
			return doctor.get(0);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.info("exception occurs while getting doctor by id:::"+e.toString());
		}
		return null;
	}

	@Transactional
	public List<String> findSlotsByStartAndEndTimes(Date startTime,
			Date endTime) {
		List<String> list=null;
		Utils utils=new Utils();
		try
		{
			Session session=this.getSession();
			list=new ArrayList<String>();
			
			List<Slot> slots=session.createQuery("from Slot WHERE time BETWEEN :startDate AND :endDate").setParameter("startDate", startTime).setParameter("endDate", endTime).list();
			if(slots!=null && slots.size()>0)
			{
				for(Slot slot:slots)
				{
					String time=utils.convertDateToUTCFormat(slot.getTime());
					list.add(time);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.info("exception while getting slots information using start and endtimes:::"+e.toString());
		}
		return list;
	}
	
	@Transactional
	public List<BookingModel> findBookingsOnClinic(Clinic clinic) {
		// TODO Auto-generated method stub
		logger.debug("In get bookings on clinic");
		Session session = this.getSession();
		List<BookingModel> bookingModel=null;
		List<Slot> bookings=session.createQuery("from Slot b where b.clinic=? and b.user is not null order by booked_time desc").setParameter(0,clinic).list();
		bookingModel=getBookings(bookings);
		return bookingModel;
	}
	
	@Transactional
	public List<BookingModel> findBookingsOnClinic(Clinic clinic, String date) {
		Session session = this.getSession();
		logger.info("enter into findbookings with date");
		List<BookingModel> bookingModel=null;
		String dateAdded="b.booked_time like '%"+date+"%'";
		List<Slot> bookings=session.createQuery("from Slot b where b.clinic=:clinic  and user is not null and "+dateAdded+"").setParameter("clinic",clinic).list();
		bookingModel=getBookings(bookings);
		return bookingModel;
	}
	
	@Transactional
	public List<BookingModel> findBookingsOnUser(String userName) {
		// TODO Auto-generated method stub
		Session session = this.getSession();
		User user=new User(userName);
		List<BookingModel> bookingModel=null;
		List<Slot> bookings=session.createQuery("from Slot b where b.user=? order by booked_time desc").setParameter(0,user).list();
		bookingModel=getBookings(bookings);
		return bookingModel;
	}
	
	private List<BookingModel> getBookings(List<Slot> bookings)
	{
		List<BookingModel> bookingModel=new ArrayList<BookingModel>();
		if(bookings!=null&&bookings.size()>0)
		{
			for(Slot b:bookings)
			{
				BookingModel bm=new BookingModel();
				String time=new Utils().convertDateToUTCFormat(b.getTime());
				logger.info("booking time in utc format:::"+time);
				bm.setSlotTime(time);
				
				/* userinfo */
				UserModel userModel=new UserModel();
				User u=b.getUser();
				userModel.setUsername(u.getUsername());
				userModel.setName(u.getUserProfile().getName());
				bm.setUser(userModel);
				
				/* clinic info */
				ClinicModel clinicModel=new ClinicModel();
				Clinic c=b.getClinic();
				clinicModel.setId(c.getId());
				clinicModel.setClinicName(c.getName());
				clinicModel.setClinicDesc(c.getDescription());
				clinicModel.setClinicAddress(c.getAddresses().toString());
				clinicModel.setClinicPhones(c.getPhoneNos().toString());
				bm.setClinic(clinicModel);
				
				/* doctor info */
				DoctorModel doctorModel=new DoctorModel();
				doctorModel.setId(b.getDoctor().getId());
				doctorModel.setName(b.getDoctor().getUser().getUserProfile().getName());
				doctorModel.setMobile(b.getDoctor().getUser().getUsername());
				doctorModel.setSpeciality(b.getDoctor().getSpeciality());
				bm.setDoctor(doctorModel);
				
				bookingModel.add(bm);
			}
		}
		return bookingModel;
	}

	
}
