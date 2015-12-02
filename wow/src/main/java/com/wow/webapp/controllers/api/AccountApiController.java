package com.wow.webapp.controllers.api;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.apache.poi.ss.formula.functions.Address;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wow.webapp.dao.UserDAO;
import com.wow.webapp.domain.model.ApiReturnModel;
import com.wow.webapp.domain.model.ApiReturnModelTests;
import com.wow.webapp.domain.model.ClinicTestModel;
import com.wow.webapp.domain.model.ClinicTestSearchModel;
import com.wow.webapp.domain.model.LoginModel;
import com.wow.webapp.entitymodel.Authority;
import com.wow.webapp.entitymodel.User;
import com.wow.webapp.util.Responses;

@RestController
public class AccountApiController {
	private static final Logger logger = LoggerFactory.getLogger(AccountApiController.class);
	
	@Autowired
    private UserDAO userDao;
	
	@RequestMapping(value = "/api/login", method = RequestMethod.POST)
	public ApiReturnModel login(@Valid LoginModel model, BindingResult bindingResult){
		logger.debug("api/login start");
		ApiReturnModel returnValue = new ApiReturnModel(Responses.SUCCESS_CODE,Responses.SUCCESS_STATUS);
		if(bindingResult.hasFieldErrors()){
			returnValue.setStatus("ERROR");
			List<String> errors = new ArrayList<String>();
			for(FieldError e : bindingResult.getFieldErrors()){
				logger.debug("Field Name : " + e.getField() + ", Error : " + e.getDefaultMessage() );
				errors.add(e.getDefaultMessage());
			}
			returnValue.setErrors(errors);
		}

		logger.debug("api/login end");
		return returnValue;
	}

	@RequestMapping(value = "/api/register-user", method = RequestMethod.POST)
	public ApiReturnModel register_user(@Valid LoginModel model, BindingResult bindingResult){
		logger.debug("api/register_user start");
		ApiReturnModel returnValue = new ApiReturnModel(Responses.SUCCESS_CODE,Responses.SUCCESS_STATUS);
		List<String> errors = new ArrayList<String>();
		if(bindingResult.hasFieldErrors()){
			returnValue.setStatus("ERROR");
			for(FieldError e : bindingResult.getFieldErrors()){
				logger.debug("Field Name : " + e.getField() + ", Error : " + e.getDefaultMessage() );
				errors.add(e.getDefaultMessage());
			}
			returnValue.setErrors(errors);
		}
		else{
			try{
				userDao.findByUserName(model.getUsername());
				returnValue.setStatus("ERROR");
				errors.add(String.format("User %s already exists", model.getUsername()));
				returnValue.setErrors(errors);				
			}
			catch(Exception e){
				User u = new User();
				u.setUsername(model.getUsername());
				BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				u.setPassword(passwordEncoder.encode(model.getPassword()));
				u.setEnabled(true);
				u.setMobile("1212");
				Set<Authority> authorities = new HashSet<Authority>();
				authorities.add(new Authority(u, "ROLE_USER"));
				u.setUserRole(authorities);
				userDao.save(u);
			}
		}

		logger.debug("api/register-user end");
		return returnValue;
	}

	@RequestMapping(value = "/api/protected/tests", method = RequestMethod.GET)
	public ApiReturnModel tests(ClinicTestSearchModel searchModel){
		logger.debug("api/tests start");
		ApiReturnModelTests returnValue = new ApiReturnModelTests();
		returnValue.setTests(userDao.getTests());
		logger.debug("api/tests end");
		return returnValue;
	}
	
	
	
	
}
