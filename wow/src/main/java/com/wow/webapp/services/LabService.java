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
import com.wow.webapp.domain.pojo.LabTypeModel;
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
	

	public List<LabTypeModel> getLabTypes(){
		
		List<LabTypeModel> list = new ArrayList<LabTypeModel>();
		try{
			
			logger.info("in getLab types");
			List<LabType> labType=labDao.getLabType();
			if(labType.size() > 0 && labType != null)
			{
				for(LabType type:labType)
				{
					LabTypeModel labTypeModel=new LabTypeModel();
					labTypeModel.setId(type.getId());
					labTypeModel.setTypeName(type.getName());
					
					list.add(labTypeModel);
				}
			}
		}
		catch(Exception ex){
			logger.error("Exception raised " + ex.toString());
			
		}
		return list;
	}
	
	
	
}
