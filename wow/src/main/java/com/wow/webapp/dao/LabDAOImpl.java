package com.wow.webapp.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wow.webapp.entitymodel.LabBooking;
import com.wow.webapp.entitymodel.LabSlots;
import com.wow.webapp.entitymodel.LabSubType;
import com.wow.webapp.entitymodel.LabType;
import com.wow.webapp.entitymodel.User;
import com.wow.webapp.util.Utils;

public class LabDAOImpl implements LabDAO{

	private SessionFactory sessionFactory;
	private static final Logger logger = LoggerFactory.getLogger(LabDAOImpl.class);

	public LabDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}
	
	@Transactional
	public void save(LabType labType) {
		logger.info("In DAO IMPL");
		Session session = this.getSession();
		session.saveOrUpdate(labType);

	}

	@Transactional
	public List<LabType> getLabType() {
		logger.info("In getLab types");
		Session session = this.getSession();
		List<LabType> labType=session.createQuery("from LabType where enabled=:enabled").setParameter("enabled", true).list();
		return labType;
	}

	@Transactional
	public void save(LabSubType labSubType) {
		logger.info("In save sub types");
		Session session = this.getSession();
		session.saveOrUpdate(labSubType);
		
	}

	@Transactional
	public LabType findLabType(Integer id) {
		
		logger.info("In find lab types");
		Session session = this.getSession();
		List<LabType> labType=session.createQuery("from LabType where id=:id").setParameter("id", id).list();
		if(labType.size() > 0 && labType != null) return labType.get(0);
		else return null;
	}

	
	@Transactional
	public List<LabSubType> getLabSubType(LabType labType) {
		
		logger.info("In find lab types");
		Session session = this.getSession();
		List<LabSubType> labSubType=session.createQuery("from LabSubType where labType=:labType and enabled=:enabled").setParameter("labType", labType).setParameter("enabled", true).list();
		return labSubType;
	}
	
	@Transactional
	public List<LabSubType> getLabSubType() {
		
		logger.info("In find lab types");
		Session session = this.getSession();
		List<LabSubType> labSubType=session.createQuery("from LabSubType where  enabled=:enabled").setParameter("enabled", true).list();
		return labSubType;
	}

	@Transactional
	public LabSubType gerLabsubType(Integer id) {
		
		logger.info("In find get lab types");
		Session session = this.getSession();
		List<LabSubType> labSubType=session.createQuery("from LabSubType where id=:id").setParameter("id", id).list();
		if(labSubType != null && labSubType.size() > 0) return labSubType.get(0);
		else return null;
		
	}

	@Transactional
	public List<String> findSlotsByStartAndEndTimes(Date startTime,
			Date endTime,LabSubType labSubType) {
		List<String> list=null;
		Utils utils=new Utils();
		try
		{
			Session session=this.getSession();
			list=new ArrayList<String>();
			
			List<LabSlots> slots=session.createQuery("from LabSlots WHERE subType=:subType and time BETWEEN :startDate AND :endDate").setParameter("subType", labSubType).setParameter("startDate", startTime).setParameter("endDate", endTime).list();
			if(slots!=null && slots.size()>0)
			{
				for(LabSlots slot:slots)
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
	public void save(LabSlots labSlots) {
		
		logger.info("in save labslots ");
		Session session=this.getSession();
		session.saveOrUpdate(labSlots);
		
	}

	@Transactional
	public LabSlots findSlot(Integer id) {

		logger.info("in lab slots");
		Session session=this.getSession();
		
		List<LabSlots> slots=session.createQuery("from LabSlots where id=:id").setParameter("id", id).list();
		if(slots!=null && !slots.isEmpty()) return slots.get(0);
		else return null;
		
	}

	@Transactional
	public List<LabBooking> findBookingsOnslot(LabSlots slot , String receive_mode) {
		
		logger.info("in find slots on type");
		Session session = this.getSession();
		List<LabBooking> bookings = session.createQuery("from LabBooking where labSlot=:labslot and receive_mode=:mode").setParameter("labslot", slot).setParameter("mode", receive_mode).list();
		return bookings;
	}

	@Transactional
	public void save(LabBooking labBooking) {
		
		logger.info("in find slots on type");
		Session session = this.getSession();
		session.saveOrUpdate(labBooking);
		
	}

	@Transactional
	public List<LabSlots> findLabSlotsOnDate(LabSubType labSubType, String date) {
		logger.info("in find lab slots on date");
		Session session = this.getSession();
		String slotDate="s.time like '%"+date+"%'";
		List<LabSlots> slots = session.createQuery("from LabSlots s where s.enabled=true and  s.subType=:subType and "+slotDate)
				.setParameter("subType", labSubType).list();
		return slots;
	}
	@Transactional
	public List<LabBooking> findBookingsOnslot(LabSlots slot) {
		logger.info("in find slots on type");
		Session session = this.getSession();
		List<LabBooking> bookings = session.createQuery("from LabBooking where labSlot=:labslot").setParameter("labslot", slot).list();
		return bookings;	}
	
	@Transactional
	public List<LabBooking> findLabBookings(String date) {
		Session session = this.getSession();
		String slotDate="l.inserted_on like '%"+date+"%'";
		List<LabBooking> bookings = session.createQuery("from LabBooking l where "+slotDate).list();
		return bookings;
	}
	
	@Transactional
	public List<LabBooking> findLabBookings() {
		Session session = this.getSession();
		List<LabBooking> bookings = session.createQuery("from LabBooking").list();
		return bookings;
	}

	@Transactional
	public List<LabBooking> findLabBookingsByUser(User user) {
		Session session = this.getSession();
		List<LabBooking> bookings = session.createQuery("from LabBooking where user=:user").setParameter("user", user).list();
		return bookings;
	}
}
