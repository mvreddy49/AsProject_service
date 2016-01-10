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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wow.webapp.dao.ContentDAO;
import com.wow.webapp.dao.UserDAO;
import com.wow.webapp.domain.model.ApiReturnModel;
import com.wow.webapp.domain.model.ApiReturnModelDoctor;
import com.wow.webapp.domain.model.CreateAccountModel;
import com.wow.webapp.domain.model.CreateDoctorModel;
import com.wow.webapp.domain.model.CreateSlotModel;
import com.wow.webapp.domain.model.DoctorModel;
import com.wow.webapp.entitymodel.Authority;
import com.wow.webapp.entitymodel.Clinic;
import com.wow.webapp.entitymodel.ClinicAddress;
import com.wow.webapp.entitymodel.ClinicPhoneNo;
import com.wow.webapp.entitymodel.Doctor;
import com.wow.webapp.entitymodel.Profile;
import com.wow.webapp.entitymodel.Slot;
import com.wow.webapp.entitymodel.User;
import com.wow.webapp.util.Constants;
import com.wow.webapp.util.Responses;
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
				User user = userDao.findByUserName(model.getClinicPhone1());
				logger.debug("After retriving user");
				if(user != null){
					throw new Exception("Not Found : " + model.getClinicPhone1());
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
	
	
	@RequestMapping(value = "/addDoctor", method = RequestMethod.POST)
	public ApiReturnModel registerDoctor(@Valid CreateDoctorModel model, BindingResult bindingResult){
		logger.debug("add doctor start");
		ApiReturnModel apiReturnModel= null;
		List<String> errors = new ArrayList<String>();
		UserDetails ud = Utils.getUserSession();
		if(ud == null) return Responses.invaliedSession();
		else
		{
			String role=null;
			for(GrantedAuthority auth :ud.getAuthorities())
				role=auth.getAuthority();
			if(!(role!=null && role.contains(Constants.ROLE_RECP)))
			{
				return new ApiReturnModel(Responses.FAILURE_CODE,Responses.ERROR_STATUS,"only recp can insert doctor",errors);
			}
		}
		
		
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
				if(doctor != null){
					//errors = registerDoctorImpl(model,errors,ud,doctor);
					errors.add("Mobile number already registered");
					
				}
				else
					errors = registerDoctorImpl(model,errors,null);
			} catch (Exception e) {
				logger.debug("Exception is :" + e.toString());
				errors.add("Doctor Already registered");
			}
			logger.debug("Saved");
		}
		logger.debug(errors.toString());
		if(errors.size() != 0){
			apiReturnModel=new ApiReturnModel(Responses.FAILURE_CODE,Responses.ERROR_STATUS,"Doctor Registration unsuccessfullty",errors);
		}
		else{
			List<DoctorModel> doctors=contentDao.getDoctors();
			apiReturnModel=new ApiReturnModelDoctor(Responses.SUCCESS_CODE,Responses.SUCCESS_STATUS,"Doctor Registration successfullty",doctors);
			
		}
		return apiReturnModel;
	}
	
	
	@RequestMapping(value="/addSlots",method=RequestMethod.POST)
	public ApiReturnModel addSlots(@Valid CreateSlotModel createSlotModel,BindingResult bindingResult)
	{
		
		logger.info("enter into addslots");
		List<String> errors=new ArrayList<String>();
		ApiReturnModel apiReturnModel=null;
		UserDetails ud = Utils.getUserSession();
		if(ud == null) return Responses.invaliedSession();
		else
		{
			String role=null;
			for(GrantedAuthority auth :ud.getAuthorities())
				role=auth.getAuthority();
			if(!(role!=null && role.contains(Constants.ROLE_RECP)))
			{
				apiReturnModel=new ApiReturnModel(Responses.FAILURE_CODE,Responses.ERROR_STATUS,"only recp can insert slots",errors);
			}
		}
		if(bindingResult.hasFieldErrors()){
			logger.debug("Invalid Data");
			for(FieldError e : bindingResult.getFieldErrors()){
				logger.debug("Field Name : " + e.getField() + ", Error : " + e.getDefaultMessage() );
				errors.add(e.getDefaultMessage());
			}	
		}
		else{
			Integer id=Integer.parseInt(createSlotModel.getDoctorId());
			Doctor d=contentDao.getDoctorById(id);
			if(d!=null)
				errors=addSlots(createSlotModel.getStartTime(),createSlotModel.getEndTime(),d);
			else
				errors.add("doctor not found");
		}
		if(errors.size() != 0){
			apiReturnModel=new ApiReturnModel(Responses.FAILURE_CODE,Responses.ERROR_STATUS,"slots creation failed",errors);
		}
		else{
			apiReturnModel=new ApiReturnModel(Responses.SUCCESS_CODE,Responses.SUCCESS_STATUS,"slots creation success",errors);
			
		}
		return apiReturnModel;
	}
	
	/*Register clinic Implementation */
	
	public List<String> registerClinicImpl(CreateAccountModel model,List<String> errors){
		
		try{
			User u = new User();
			u.setUsername(model.getClinicPhone1());
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			u.setPassword(passwordEncoder.encode(model.getPasswd()));
			u.setEnabled(true);
			
			Clinic c = new Clinic();
			c.setName(model.getClinicName());
			c.setDescription(model.getClinicDesc());
			c.setEnabled(true);
			c.setType(model.getType());
			
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
	
	
	/* Doctor Implementation for new doctor to a clinic */
	public List<String> registerDoctorImpl(CreateDoctorModel model,List<String> errors,UserDetails ud){
		Utils u = new Utils();
		logger.info("enter into registerdoctorImpl");
		try{
			
			Doctor d = new Doctor();
			
			d.setSpeciality(model.getSpeciality());
			d.setEnabled(true);
			Integer duration=Integer.parseInt(model.getDuration());
			d.setDuation(duration);
			
			User user=new User();
			user.setUsername(model.getMobile());
			user.setPassword(u.getEncryptedPassword(model.getMobile()));
			user.setEnabled(true);
			Set<Authority> authorities = new HashSet<Authority>();
			authorities.add(new Authority(user, Constants.ROLE_DOCTOR));
			user.setUserRole(authorities);
			
			Profile userProfile =  new Profile(user, model.getName());
			
			user.setUserProfile(userProfile);
			
			
			d.setUser(user);
			
			contentDao.save(d);
			
			if(model.getStartTime()!=null && !model.getStartTime().isEmpty() && model.getEndTime()!=null && !model.getEndTime().isEmpty())
			{
				logger.info("start time and endtime is there to addd doctor");
				List<String> errors1=addSlots(model.getStartTime(),model.getEndTime(),d);
				logger.info("add slots status :::"+errors1.toString());
					
			}
			else
			{
				logger.info("no slot times ,only insert doctor only");
				
			}
				
			
			logger.info("add dctor done");
			
		}
		catch(Exception ex){
			ex.printStackTrace();
			errors.add("Mobile number is already registered");
		}
		return errors;
	}
	
	
	public List<String> addSlots(String starttime,String endtime,Doctor d)
	{
		List<String> errors=new ArrayList<String>();
		
		try{
			
		Utils utils=new Utils();
		Date startTime=utils.convertStringToDate(starttime);
		Date endTime=utils.convertStringToDate(endtime);
		int comp = startTime.compareTo(endTime);
		logger.info("dates comparsion checking is :::start::"+startTime+":::end:::"+endTime+"::results are:::"+comp);
		if(comp == 0 || comp == -1)
		{
			List<String> existedList=contentDao.findSlotsByStartAndEndTimes(startTime, endTime,d);
			logger.info("existedList are ::: "+existedList.toString());
			List<String> list=utils.getRangeTimes(startTime,endTime,d.getDuation());
			List<String> insertedList=null;
			if(existedList!=null && existedList.size()>0)
			{
				insertedList = new ArrayList<String>(list);
				insertedList.removeAll(existedList);
			}
			else
				insertedList=list;
			
			logger.info("inserted list are:::"+insertedList.toString());
			logger.info("doctor duration is :::"+d.getDuation());
			if(insertedList!=null && insertedList.size()>0)
				for(String str:insertedList)
				{
					Slot s = new Slot();
					Clinic c = userDao.getClinicByUserName("9999999999");
					s.setClinic(c);
					s.setDoctor(d);
					s.setEnabled(true);
					Date time=utils.convertStringToDate(str);
					s.setTime(time);
					
					contentDao.save(s);
				}

		}
		else
			errors.add("must be endtime greater then or equal to start time");
			
		}
		catch(Exception ex){
			logger.debug("Exception is : "+ ex);
			errors.add("exception occurs:::"+ex.toString());
		}
		return errors;
	}
	
	/* Doctor Implementation for new doctor to a clinic */
	public List<String> registerDoctorImpl(CreateDoctorModel model,List<String> errors,UserDetails ud, Doctor doctor){
		logger.debug("In register doctor");
		Utils u = new Utils();
		Date startTime,endTime;
		try{
			startTime= u.convertStringToDate(model.getStartTime());
			endTime = u.convertStringToDate(model.getEndTime());
			
			Slot s = null;
			try{
				Clinic c = userDao.getClinicByUserName(ud.getUsername());
				/*s = contentDao.findSlotsByClinicAndDoctor(doctor, c);
				logger.debug("After getting slot");
				if(s == null){
					s = new Slot();
					s.setClinic(c);
					s.setDoctor(doctor);
				}*/
			}
			catch(Exception ex){
				logger.debug("Exception is : "+ ex);
			}
			//s.setStartTime(startTime);
			//s.setEndTime(endTime);
			contentDao.save(s);
		}
		catch(Exception ex){
			errors.add("Mobile number is already registered");
			logger.debug("Excpetion at 257 is :"+ex);
		}
		return errors;
	}

}
