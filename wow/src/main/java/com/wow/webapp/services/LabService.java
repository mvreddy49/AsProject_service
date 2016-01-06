package com.wow.webapp.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.wow.webapp.controllers.api.LabController;
import com.wow.webapp.dao.LabDAO;
import com.wow.webapp.domain.model.CreateLabSubTypeModel;
import com.wow.webapp.domain.pojo.LabSubTypeModel;
import com.wow.webapp.domain.pojo.LabTypeModel;
import com.wow.webapp.entitymodel.Clinic;
import com.wow.webapp.entitymodel.LabSlots;
import com.wow.webapp.entitymodel.LabSubType;
import com.wow.webapp.entitymodel.LabType;
import com.wow.webapp.entitymodel.Slot;
import com.wow.webapp.util.Utils;

@Service
@Component
public class LabService {

	@Autowired
	private LabDAO labDao;
	
	private static final Logger logger = LoggerFactory.getLogger(LabController.class);
	
	public List<String> addLabType(String name){
		List<String> errors = new ArrayList<String>();
		LabType lab = new LabType();
		try{
			logger.info("Name id :"+name );
			lab.setName(name);
			lab.setInserted_on(new Date());
			labDao.save(lab);
		}
		catch(Exception ex){
			logger.error("Exception raised " + ex.toString());
			errors.add(ex.toString());
		}
		return errors;
	}
	
	public List<String> addLabSubType(CreateLabSubTypeModel model){
		List<String> errors = new ArrayList<String>();
		
		try{
			logger.info("in add sub types::");
			LabType labType=labDao.findLabType(Integer.parseInt(model.getTypeId()));
			if(labType != null)
			{
				LabSubType labSubType=new LabSubType();
				labSubType.setLabType(labType);
				labSubType.setName(model.getName());
				labSubType.setDescription(model.getDescription());
				labSubType.setHomePickup(Boolean.parseBoolean(model.getHome_pickup()));
				labSubType.setMaxHomePickupBookings(Integer.parseInt(model.getMax_online_bookings()));
				labSubType.setMaxOnlineBookings(Integer.parseInt(model.getMax_online_bookings()));
				labSubType.setDuration(Integer.parseInt(model.getDuration()));
				
				labDao.save(labSubType);
				
				logger.info("lab sub type insertion success");
				
				if(model.getStartTime()!=null && !model.getStartTime().isEmpty() && model.getEndTime()!=null && !model.getEndTime().isEmpty())
				{
					logger.info("enter into insert slots for lab");
					List<String> error=addLabSlots(String.valueOf(labSubType.getId()),model.getStartTime(),model.getEndTime());
					logger.info("lab sots insertion success:::"+error.toString());
					
				}
				
			}
			else
				errors.add("requested laType Id is not found");
			
			
		}
		catch(Exception ex){
			logger.error("Exception raised " + ex.toString());
			errors.add(ex.toString());
		}
		return errors;
	}

	public List<Object> getLabTypes(){
		
		List<Object> list = new ArrayList<Object>();
		try{
			
			logger.info("in getLab types");
			List<LabType> labType=labDao.getLabType();
			if(labType.size() > 0 && labType != null)
			{
				for(LabType type:labType)
				{
					LabTypeModel labTypeModel=new LabTypeModel();
					labTypeModel.setId(type.getId());
					labTypeModel.setName(type.getName());
					
					list.add(labTypeModel);
				}
			}
		}
		catch(Exception ex){
			logger.error("Exception raised " + ex.toString());
			
		}
		return list;
	}
	
	public List<Object> getLabSubTypes(Integer id){
		
		List<Object> list = new ArrayList<Object>();
		try{
			
			logger.info("in getLab types");
			LabType labType=new LabType(id);
			List<LabSubType> labSubType=labDao.getLabSubType(labType);
			if(labSubType.size() > 0 && labSubType != null)
			{
				for(LabSubType type:labSubType)
				{
					LabSubTypeModel model=new LabSubTypeModel();
					model.setId(type.getId());
					model.setName(type.getName());
					model.setDescription(type.getDescription());
					model.setDuration(type.getDuration());
					model.setHome_pickup(type.isHomePickup());
					model.setOnline_booking(type.isOnlineBooking());
					model.setMax_homepickup_bookings(type.getMaxHomePickupBookings());
					model.setMax_online_bookings(type.getMaxOnlineBookings());
					
					list.add(model);
				}
			}
		}
		catch(Exception ex){
			logger.error("Exception raised " + ex.toString());
			
		}
		return list;
	}
	
	public List<String> addLabSlots(String subTypeId,String starttime,String endtime)
	{
		List<String> errors = new ArrayList<String>();
		try
		{
			Utils utils=new Utils();
			Date startTime=utils.convertStringToDate(starttime);
			Date endTime=utils.convertStringToDate(endtime);
			int comp = startTime.compareTo(endTime);
			logger.info("dates comparsion checking is :::start::"+startTime+":::end:::"+endTime+"::results are:::"+comp);
			if(comp == 0 || comp == -1)
			{
				LabSubType labsubType=labDao.gerLabsubType(Integer.parseInt(subTypeId));
				if(labsubType != null)
				{
					int duration=labsubType.getDuration();
					List<String> existedList=labDao.findSlotsByStartAndEndTimes(startTime, endTime,labsubType);
					logger.info("existedList are ::: "+existedList.toString());
					List<String> list=utils.getRangeTimes(startTime,endTime,duration);
					List<String> insertedList=null;
					if(existedList!=null && existedList.size()>0)
					{
						insertedList = new ArrayList<String>(list);
						insertedList.removeAll(existedList);
					}
					else
						insertedList=list;
					
					logger.info("inserted list are:::"+insertedList.toString());
					
					if(insertedList!=null && insertedList.size()>0)
						for(String str:insertedList)
						{
							LabSlots s = new LabSlots();
							Date time=utils.convertStringToDate(str);
							s.setTime(time);
							s.setSubType(labsubType);
							s.setInserted_on(new Date());
							labDao.save(s);
							
						}
					
					
				}
				else
					errors.add("labsub type not found on based on id ::"+subTypeId);
			}
			else
				errors.add("end date must be greater then start date");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.info("exception occurs while adding lab slots:::"+e.toString());
			errors.add(e.getMessage());
		}
		return errors;
	}

	public List<Object> getSlots(String subTypeId) {
		List<Object> list = new ArrayList<Object>();
		try{
			
			logger.info("in get lab slots");
			LabType labType=new LabType();
			List<LabSubType> labSubType=labDao.getLabSubType(labType);
			if(labSubType.size() > 0 && labSubType != null)
			{
				for(LabSubType type:labSubType)
				{
					LabSubTypeModel model=new LabSubTypeModel();
					model.setId(type.getId());
					model.setName(type.getName());
					model.setDescription(type.getDescription());
					model.setDuration(type.getDuration());
					model.setHome_pickup(type.isHomePickup());
					model.setOnline_booking(type.isOnlineBooking());
					model.setMax_homepickup_bookings(type.getMaxHomePickupBookings());
					model.setMax_online_bookings(type.getMaxOnlineBookings());
					
					list.add(model);
				}
			}
		}
		catch(Exception ex){
			logger.error("Exception raised " + ex.toString());
			
		}
		return list;
	}
	
}
