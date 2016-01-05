package com.wow.webapp.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.wow.webapp.controllers.api.LabController;
import com.wow.webapp.dao.LabDAO;
import com.wow.webapp.entitymodel.LabType;

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
			labDao.save(lab);
		}
		catch(Exception ex){
			logger.error("Exception raised " + ex.toString());
			errors.add(ex.toString());
		}
		return errors;
	}
}
