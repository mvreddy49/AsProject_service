package com.wow.webapp.dao;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wow.webapp.domain.model.ClinicModel;
import com.wow.webapp.entitymodel.Clinic;
import com.wow.webapp.entitymodel.ClinicAddress;
import com.wow.webapp.entitymodel.Doctor;
import com.wow.webapp.entitymodel.User;

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
		session.persist(d);
		
		
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
	
	
	@Transactional
	public Doctor findDoctorByMobile(String mobile) throws Exception {
		logger.debug("findDoctorByMobile start " + mobile);
		Doctor d = this.findDoctorByMobile(this.getSession(), mobile);
		logger.debug("findDoctorByMobile end ");
		return d;
	}
	
	private Doctor findDoctorByMobile(Session session ,String mobile) throws Exception{
		@SuppressWarnings("unchecked")
		List<Doctor> doctorList = session.createQuery("from Doctor where mobile=:mobile")
				.setParameter("mobile", mobile)
				.list();
		if(doctorList == null || doctorList.size() <= 0){
//			logger.debug("Not Found : " + username);
//			throw new Exception("Not Found : " + username);
			return null;
		}
		return doctorList.get(0);
	}

}
