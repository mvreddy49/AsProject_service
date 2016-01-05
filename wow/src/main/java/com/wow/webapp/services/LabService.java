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
import com.wow.webapp.entitymodel.LabSubType;
import com.wow.webapp.entitymodel.LabType;

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
			List<LabSubType> labSubType=labDao.getLabSubType(id);
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
