package com.wow.webapp.dao;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wow.webapp.domain.model.BookingModel;
import com.wow.webapp.domain.model.ClinicModel;
import com.wow.webapp.domain.model.DoctorModel;
import com.wow.webapp.domain.model.UserModel;
import com.wow.webapp.entitymodel.Booking;
import com.wow.webapp.entitymodel.Clinic;
import com.wow.webapp.entitymodel.Doctor;
import com.wow.webapp.entitymodel.Slot;
import com.wow.webapp.entitymodel.User;
import com.wow.webapp.util.Utils;

public class BookingDAOImpl implements BookingDAO {

	private SessionFactory sessionFactory;
	private static final Logger logger = LoggerFactory.getLogger(BookingDAOImpl.class);

	public BookingDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	@Transactional
	public Slot findSlot(Integer slot_id) {
		// TODO Auto-generated method stub
		System.out.println("enter into getSlots");
		Session session = this.getSession();
		
		System.out.println("before executing:::");
		List<Slot> slots = session.createQuery("from Slot s where s.id=?").setParameter(0, slot_id).list();
		return slots.get(0);
		
	}

	@Transactional
	public List<Booking> findBookings(Clinic clinic,Doctor doctor) {
		// TODO Auto-generated method stub
		System.out.println("enter into findBookings");
		Session session = this.getSession();
		
		List<Booking> bookings=session.createQuery("from Booking b where b.clinic=? and b.doctor=?").setParameter(0, clinic)
				.setParameter(1, doctor).list();
		
		return bookings;
	}

	@Transactional
	public void save(Booking b) {
		// TODO Auto-generated method stub
		System.out.println("enter into save bookings");
		Session session = this.getSession();
		session.persist(b);
		
		
	}
	@Transactional
	public List<BookingModel> findBookingsOnUser(String userName) {
		// TODO Auto-generated method stub
		Session session = this.getSession();
		User user=new User(userName);
		List<BookingModel> bookingModel=null;
		List<Booking> bookings=session.createQuery("from Booking b where b.user=?").setParameter(0,user).list();
		bookingModel=getBookings(bookings);
		return bookingModel;
	}

	private List<BookingModel> getBookings(List<Booking> bookings)
	{
		List<BookingModel> bookingModel=new ArrayList<BookingModel>();
		if(bookings!=null&&bookings.size()>0)
		{
			for(Booking b:bookings)
			{
				BookingModel bm=new BookingModel();
				String time=new Utils().convertDateToUTCFormat(b.getBooking_time());
				logger.info("booking time in utc format:::"+time);
				bm.setSlotTime(time);
				
				/* userinfo */
				UserModel userModel=new UserModel();
				User u=b.getUser();
				userModel.setUsername(u.getUsername());
				userModel.setMobile(u.getMobile());
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
				doctorModel.setName(b.getDoctor().getName());
				doctorModel.setMobile(b.getDoctor().getMobile());
				doctorModel.setSpeciality(b.getDoctor().getSpeciality());
				bm.setDoctor(doctorModel);
				
				bookingModel.add(bm);
			}
		}
		return bookingModel;
	}
	
	@Transactional
	public List<BookingModel> findBookingsOnClinic(Clinic clinic) {
		// TODO Auto-generated method stub
		
		Session session = this.getSession();
		List<BookingModel> bookingModel=null;
		List<Booking> bookings=session.createQuery("from Booking b where b.clinic=?").setParameter(0,clinic).list();
		bookingModel=getBookings(bookings);
		return bookingModel;
	}

}
