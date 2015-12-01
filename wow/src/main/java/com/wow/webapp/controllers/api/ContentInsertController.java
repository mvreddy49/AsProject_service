package com.wow.webapp.controllers.api;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wow.webapp.dao.ContentDAO;
import com.wow.webapp.dao.UserDAO;
import com.wow.webapp.domain.model.ApiReturnModel;
import com.wow.webapp.domain.model.CreateAccountModel;
import com.wow.webapp.domain.model.CreateDoctorModel;
import com.wow.webapp.entitymodel.Authority;
import com.wow.webapp.entitymodel.Clinic;
import com.wow.webapp.entitymodel.ClinicAddress;
import com.wow.webapp.entitymodel.ClinicPhoneNo;
import com.wow.webapp.entitymodel.Doctor;
import com.wow.webapp.entitymodel.Slots;
import com.wow.webapp.entitymodel.User;
import com.wow.webapp.util.Utils;


@RestController
@RequestMapping("/content")
public class ContentInsertController {
	private static final Logger logger = LoggerFactory.getLogger(ContentGetController.class);
	
	@Autowired
	private ContentDAO contentDao;
	
	@Autowired
	private UserDAO userDao;
	
	@RequestMapping(value = "/register-clinic", method = RequestMethod.POST)
	public ApiReturnModel registerClinic(@Valid CreateAccountModel model, BindingResult bindingResult){
		logger.debug("register get start");
		List<String> errors = new ArrayList<String>();
		ApiReturnModel apiReturnModel= new ApiReturnModel();
		if(bindingResult.hasFieldErrors()){
			logger.debug("Invalid Data");
			for(FieldError e : bindingResult.getFieldErrors()){
				logger.debug("Field Name : " + e.getField() + ", Error : " + e.getDefaultMessage() );
				errors.add(e.getDefaultMessage());
			}
			
		}
		else{
			
			logger.debug("Persisting");
			try {
				User user = userDao.findByUserName(model.getEmail());
				logger.debug("After retriving user");
				if(user != null){
					throw new Exception("Not Found : " + model.getEmail());
				}
				errors = registerClinicImpl(model,errors);
			} catch (Exception e) {
				logger.debug("Exception is :" + e.toString());
				errors.add("Clinic Already registered");
			}
			logger.debug("Saved");
		}
		logger.debug(errors.toString());
		if(errors.size() != 0){
			apiReturnModel.setErrors(errors);
			apiReturnModel.setMessage("Clinic Registered unsuccessfullty");
		}
		else{
			apiReturnModel.setMessage("Clinic Registered successfullty");
		}
		return apiReturnModel;
	}
	
	
	@RequestMapping(value = "/register-doctor", method = RequestMethod.POST)
	public ApiReturnModel registerDoctor(@Valid CreateDoctorModel model, BindingResult bindingResult){
		logger.debug("register get start");
		List<String> errors = new ArrayList<String>();
		ApiReturnModel apiReturnModel= new ApiReturnModel();
		if(bindingResult.hasFieldErrors()){
			logger.debug("Invalid Data");
			for(FieldError e : bindingResult.getFieldErrors()){
				logger.debug("Field Name : " + e.getField() + ", Error : " + e.getDefaultMessage() );
				errors.add(e.getDefaultMessage());
			}
			
		}
		else{
			
			logger.debug("Persisting");
			try {
				Doctor doctor = contentDao.findDoctorByMobile(model.getMobile());
				logger.debug("After retriving user");
				if(doctor != null){
					throw new Exception("Not Found : " + model.getMobile());
				}
				errors = registerDoctorImpl(model,errors);
			} catch (Exception e) {
				logger.debug("Exception is :" + e.toString());
				errors.add("Doctor Already registered");
			}
			logger.debug("Saved");
		}
		logger.debug(errors.toString());
		if(errors.size() != 0){
			apiReturnModel.setErrors(errors);
			apiReturnModel.setMessage("Clinic Registered unsuccessfullty");
		}
		else{
			apiReturnModel.setMessage("Clinic Registered successfullty");
		}
		return apiReturnModel;
	}
	
	
	/*Register clinic Implementation */
	
	public List<String> registerClinicImpl(CreateAccountModel model,List<String> errors){
		
		try{
			User u = new User();
			u.setUsername(model.getEmail());
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			u.setPassword(passwordEncoder.encode(model.getPasswd()));
			u.setMobile(model.getClinicPhone1());
			u.setEnabled(true);
			
			Clinic c = new Clinic();
			c.setName(model.getClinicName());
			c.setDescription(model.getClinicDesc());
			c.setEnabled(true);
			
			Set<Authority> authorities = new HashSet<Authority>();
			//authorities.add(new Authority(u, "ROLE_USER"));
			authorities.add(new Authority(u, "ROLE_CLINIC"));
			u.setUserRole(authorities);
			u.setClinic(c);

			ClinicAddress addr = new ClinicAddress();
			addr.setLine1(model.getClinicAddrLine1());
			addr.setLine2(model.getClinicAddrLine2());
			addr.setCity(model.getClinicCity());
			addr.setState(model.getClinicState());
			addr.setCountry(model.getClinicCountry());
			addr.setZip(model.getClinicZipCode());
			addr.setClinic(c);
			
			ClinicPhoneNo ph = new ClinicPhoneNo();
			ph.setPhoneNo(model.getClinicPhone1());
			ph.setType("OFFICE");
			ph.setClinic(c);
			
			c.getAddresses().add(addr);
			c.getUsers().add(u);
			c.getPhoneNos().add(ph);

			contentDao.save(c);

		}
		catch(Exception ex){
			errors.add("Mobile number is already registered");
		}
		return errors;
	}
	
	
	/* Doctor Implementation */
	public List<String> registerDoctorImpl(CreateDoctorModel model,List<String> errors){
		String user_id = "8";
		String clinic_id = "8";
		Utils u = new Utils();
		Date startTime,endTime;
		try{
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			startTime= u.convertStringToDate(model.getStartTime());
			endTime = u.convertStringToDate(model.getEndTime());
			Doctor d = new Doctor();
			d.setMobile(model.getMobile());
			d.setName(model.getName());
			d.setSpeciality(model.getSpeciality());
			
			Slots s = new Slots();
			s.setDoctor(d);
			s.setStartTime(startTime);
			s.setEndTime(endTime);
			
			d.getSlots().add(s);
			contentDao.save(d);
			
			logger.debug("StaetTime :"+startTime);
		}
		catch(Exception ex){
			errors.add("Mobile number is already registered");
		}
		return errors;
	}
	
}
