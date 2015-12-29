package com.wow.webapp.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wow.webapp.domain.model.ClinicModel;
import com.wow.webapp.domain.model.DoctorModel;
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
		session.save(s);
		
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
			List<Slot> slots = session.createQuery("from Slot where clinic=:paramType")
					.setParameter("paramType", c)
					.list();
			if(slots.size() == 0) continue;
			for(Slot s : slots){
				if(!s.getDoctor().getSpeciality().equalsIgnoreCase(speciality)) continue;
				
				DoctorModel doctorModel = new DoctorModel();
				Doctor d = s.getDoctor();
				doctorModel.setId(d.getId());
				//doctorModel.setName(d.getName());
				//doctorModel.setMobile(d.getMobile());
				doctorModel.setSpeciality(s.getDoctor().getSpeciality());
				
				/*Clinic Info */ 
				
				ClinicModel clinicModel = new ClinicModel();
				clinicModel.setId(c.getId());
				clinicModel.setClinicName(c.getName());
				clinicModel.setClinicDesc(c.getDescription());
				clinicModel.setClinicAddress(c.getAddresses().toString());
				clinicModel.setClinicPhones(c.getPhoneNos().toString());
				doctorModel.setClinic(clinicModel);
				
				/* Slots info 
				
				SlotsModel slotModel = new SlotsModel();
				slotModel.setId(s.getId());
				logger.debug("startTime:::"+utils.convertDateToUTCFormat(s.getStartTime()));
				slotModel.setStartTime(utils.convertDateToUTCFormat(s.getStartTime()));
				slotModel.setEndTime(utils.convertDateToUTCFormat(s.getEndTime()));
				slotModel.setSlots(utils.getRangeTimes(s.getStartTime(),s.getEndTime()));
				
				doctorModel.setSlot(slotModel);
				
				
				
				 Booked slots info
				List<BookingModel> bookingsList=new ArrayList<BookingModel>();
				
				List<Booking> bookings=session.createQuery("from Booking b where b.clinic=? and b.doctor=?").setParameter(0, c)
						.setParameter(1, d).list();
				for(Booking b:bookings)
				{
					BookingModel bookingModel=new BookingModel();
					bookingModel.setSlotTime(utils.convertDateToUTCFormat(b.getBooking_time()));
					bookingsList.add(bookingModel);
				}
				doctorModel.setBooking(bookingsList);*/
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
	public Slot findSlotsByClinicAndDoctor(Doctor d,Clinic c){
		Session session = this.getSession();
		logger.debug("Before");
		List<Slot> slots = session.createQuery("from Slot s where s.clinic=:clinic and s.doctor=:doctor")
				.setParameter("clinic", c).setParameter("doctor", d).list();
		logger.debug("after");
		if(slots == null || slots.size() <= 0) return null;
		return slots.get(0);
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

}
