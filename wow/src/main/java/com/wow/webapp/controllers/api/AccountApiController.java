package com.wow.webapp.controllers.api;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wow.webapp.dao.UserDAO;
import com.wow.webapp.domain.account.UserModel;
import com.wow.webapp.domain.model.ApiReturnModel;
import com.wow.webapp.domain.model.ApiReturnModelTests;
import com.wow.webapp.domain.model.ApiReturnModelUsers;
import com.wow.webapp.domain.model.ClinicTestSearchModel;
import com.wow.webapp.domain.model.LoginModel;
import com.wow.webapp.domain.model.ResponseUserModel;
import com.wow.webapp.entitymodel.Authority;
import com.wow.webapp.entitymodel.Clinic;
import com.wow.webapp.entitymodel.Profile;
import com.wow.webapp.entitymodel.User;
import com.wow.webapp.util.Constants;
import com.wow.webapp.util.Responses;
import com.wow.webapp.util.SMS;
import com.wow.webapp.util.Utils;

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
				User u = userDao.findByUserName(model.getUsername());
				if(u != null)
				{	
				returnValue.setStatus("ERROR");
				errors.add(String.format("User %s already exists", model.getUsername()));
				returnValue.setErrors(errors);		
				}
				else{
					u = new User();
					u.setUsername(model.getUsername());
					
					Profile profile = new Profile(u,model.getName());
					u.setUserProfile(profile);
					//BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
					//u.setPassword(passwordEncoder.encode(model.getPassword()));
					u.setPassword(new Utils().getEncryptedPassword(model.getPassword()));
					u.setEnabled(true);
					
					Set<Authority> authorities = new HashSet<Authority>();
					authorities.add(new Authority(u, "ROLE_USER"));
					u.setUserRole(authorities);
					userDao.save(u);
				}
			}
			catch(Exception e){
				logger.error("Exception while creating user:" + e.toString());
				returnValue.setStatus(Responses.ERROR_STATUS);
				errors.add(Responses.ERROR_MSG);
				returnValue.setErrors(errors);	
			}
		}

		logger.debug("api/register-user end");
		return returnValue;
	}

	@RequestMapping(value = "/api/user", method = RequestMethod.POST)
	public ApiReturnModel addUser(@Valid UserModel model, BindingResult bindingResult){
		logger.debug("api/user start");
		ApiReturnModelUsers returnModel = new ApiReturnModelUsers(Responses.SUCCESS_CODE,
				Responses.SUCCESS_STATUS, "");
		List<String> errors = new ArrayList<String>();
		if(bindingResult.hasFieldErrors()){
			logger.debug("Invalid Data");
			for(FieldError e : bindingResult.getFieldErrors()){
				errors.add(e.getDefaultMessage());
			}
		}
		else{
			UserDetails ud=null;
			try {
				ud=Utils.getUserSession();
				if (ud == null) {
					returnModel.setCode(Responses.USER_NOTLOGGEDIN);
					returnModel.setStatus(Responses.SUCCESS_STATUS);
					returnModel.setMessage("user not logged in,please login");
					return returnModel;
				} 
				String role = null;
				for (GrantedAuthority auth : ud.getAuthorities())
					role = auth.getAuthority();
				if (Constants.ROLE_ADMIN.equalsIgnoreCase(role)
						&& Constants.ROLE_ADMIN_ACCESS
								.contains(model.getRole())) {
					System.out.println("Having access to create user  :"
							+ model.getRole());
				} else if (Constants.ROLE_RECP.equalsIgnoreCase(role)
						&& Constants.ROLE_RECP_ACCESS.contains(model.getRole())) {
					System.out.println("Having access to create user  :"
							+ model.getRole());
				} else {
					System.out.println("Not having access to add the user  :"
							+ model.getRole());
					returnModel.setCode(Responses.FAILURE_CODE);
					returnModel.setStatus(Responses.FAILURE_STATUS);
					returnModel.setMessage(Responses.NO_ACCESS_MSG);
					return returnModel;
				}
				User userDetails = userDao.findByUserName(model.getUsername());
				if (userDetails == null) {
					userDetails = new User();
				}
				userDetails.setUsername(model.getUsername());
				BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				userDetails.setPassword(passwordEncoder.encode(model.getUsername()));
				userDetails.setEnabled(true);
				Set<Authority> authorities = new HashSet<Authority>();
				Authority auth = null;
				// authorities = userDetails.getUserRole();
				Iterator it = userDetails.getUserRole().iterator();
				while (it.hasNext()) {
					auth = (Authority) it.next();
				}
				if (auth != null) {
					authorities.add(new Authority(userDetails, model.getRole(),
							auth.getId()));
				} else {
					authorities
							.add(new Authority(userDetails, model.getRole()));
				}
				Profile userProfile = null;
				if (userDetails.getUserProfile().getId() != null) {
					userProfile = new Profile(userDetails, model.getName(),
							userDetails.getUserProfile().getId());
				} else {
					userProfile = new Profile(userDetails, model.getName());
				}
				userDetails.setUserProfile(userProfile);
				userDetails.setUserRole(authorities);
				Clinic c = userDao.getClinicByUserName(ud.getUsername());
				if(Constants.ROLE_CLINIC_ACCESS.contains(model.getRole()))
					userDetails.setClinic(c);
				if(Constants.ROLE_RECP_ACCESS.contains(model.getRole()))
					userDetails.setClinic(null);
				userDao.save(userDetails);
				SMS sms = new SMS();
				sms.sendSMS(Constants.SMS_BOOKING_MSG, model.getUsername());
			} catch (Exception e) {
				logger.info("exception occurs :::" + e.toString());
				errors.add(e.getMessage());
				returnModel.setCode(Responses.FAILURE_CODE);
				returnModel.setStatus(Responses.FAILURE_STATUS);
				returnModel.setMessage(Responses.ERROR_MSG);
			}
		}
		logger.debug(errors.toString());
		if(errors.size() != 0){
			returnModel.setCode(Responses.FAILURE_CODE);
			returnModel.setStatus(Responses.FAILURE_STATUS);
			returnModel.setErrors(errors);
			returnModel.setMessage("Adding user unsuccessfullty");
		}
		else{
			logger.info("SUCCESS");
			List<User> userList = userDao.list();
			logger.info("User list is : " + userList.size());
			List<ResponseUserModel> usersList = new ArrayList<ResponseUserModel>();
			for(User user:userList){
				logger.debug("User details are :" + user);
				Authority au = (Authority)user.getUserRole().iterator().next();
				ResponseUserModel u = new ResponseUserModel(user.getUsername(),user.getUserProfile().getName(),au.getRole());
				u.setUpdatedOn(new Utils().convertDateToUTCFormat(user.getModified_on()));
				usersList.add(u);
				returnModel.setUsers(usersList);
			}
			returnModel.setMessage("Adding user successfullty");
		}
		return returnModel;
	}


	@RequestMapping(value = "/api/user", method = RequestMethod.GET)
	public ApiReturnModel getUser(@RequestParam(required=false) String role){
		logger.debug("api/user start");
		ApiReturnModelUsers returnModel = new ApiReturnModelUsers(Responses.SUCCESS_CODE,
				Responses.SUCCESS_STATUS, "");
		List<String> errors = new ArrayList<String>();
		UserDetails ud=null;
		ud=Utils.getUserSession();
			if (ud == null) {
				returnModel.setCode(Responses.USER_NOTLOGGEDIN);
				returnModel.setStatus(Responses.SUCCESS_STATUS);
				returnModel.setMessage("user not logged in,please login");
				return returnModel;
			} 
			List<User> userList = userDao.list();
			List<ResponseUserModel> usersList = new ArrayList<ResponseUserModel>();
			for(User user:userList){
				logger.debug("User details are :" + user);
				Authority au = (Authority)user.getUserRole().iterator().next();
				ResponseUserModel u = null;
				if(role != null && !role.equalsIgnoreCase(au.getRole())) continue;
				logger.info("Role is :" + au.getRole());
				logger.info("User name is : " + user.getUsername());
				logger.info("Name is : " + user.getUserProfile().getName());
				u = new ResponseUserModel(user.getUsername(),user.getUserProfile().getName(),au.getRole());
				//u.setUpdatedOn(new Utils().convertDateToUTCFormat(user.getModified_on()));
				usersList.add(u);
				returnModel.setUsers(usersList);
			}
			returnModel.setMessage("SUCCESS");
		return returnModel;
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
