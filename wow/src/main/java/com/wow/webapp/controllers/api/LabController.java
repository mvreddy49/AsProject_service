package com.wow.webapp.controllers.api;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wow.webapp.domain.model.ApiReturnLab;
import com.wow.webapp.domain.model.ApiReturnModel;
import com.wow.webapp.domain.model.CreateLabBookingModel;
import com.wow.webapp.domain.model.CreateLabSubTypeModel;
import com.wow.webapp.services.LabService;
import com.wow.webapp.util.Constants;
import com.wow.webapp.util.Responses;
import com.wow.webapp.util.Utils;

@RestController
@RequestMapping("/api/lab")
public class LabController {
	private static final Logger logger = LoggerFactory.getLogger(LabController.class);
	
	@Autowired
	LabService labService;
	
	@RequestMapping(value = "/type", method = RequestMethod.POST)
	public ApiReturnModel addLabType(@RequestParam(value = "name", required = true) String name){
		ApiReturnModel returnModel=null;
		UserDetails ud = Utils.getUserSession();
		if(ud == null) return Responses.invaliedSession();
		else
		{
			String role=null;
			for(GrantedAuthority auth :ud.getAuthorities())
				role=auth.getAuthority();
			if(!(role!=null && Constants.ROLE_LAB_ACCESS.contains(role)))
			{
				return new ApiReturnModel(Responses.FAILURE_CODE,Responses.ERROR_STATUS,"only recp can insert lab Types");
			}
		}
		List<String> errors = labService.addLabType(name);
		if(errors.size()==0)
		{
			List<Object> labTypes=labService.getLabTypes();
			returnModel=new ApiReturnLab(Responses.SUCCESS_CODE, Responses.SUCCESS_STATUS, "labType insertion success" , labTypes);
		
		}else
		{
			returnModel = new ApiReturnModel(Responses.FAILURE_CODE,Responses.ERROR_STATUS,"labType insertion failed",errors);
		}
		return returnModel;
	}
	
	@RequestMapping(value = "/type", method = RequestMethod.GET)
	public ApiReturnModel getLabTypes()
	{
		List<Object> labTypes=labService.getLabTypes();
		return new ApiReturnLab(Responses.SUCCESS_CODE, Responses.SUCCESS_STATUS,Responses.SUCCESS_MSG, labTypes);
	}
	
	@RequestMapping(value = "/subType", method = RequestMethod.POST)
	public ApiReturnModel addLabSubType(@Valid CreateLabSubTypeModel model, BindingResult bindingResult){
		
		ApiReturnModel returnModel=null;
		
		List<String> errors=new ArrayList<String>();
		UserDetails ud = Utils.getUserSession();
		if(ud == null) return Responses.invaliedSession();
		else
		{
			String role=null;
			for(GrantedAuthority auth :ud.getAuthorities())
				role=auth.getAuthority();
			if(!(role!=null && Constants.ROLE_LAB_ACCESS.contains(role)))
			{
				return new ApiReturnModel(Responses.FAILURE_CODE,Responses.ERROR_STATUS,"only recp can insert labSubTypes",errors);
			}
		}
		
		if(bindingResult.hasFieldErrors()){
			logger.debug("Invalid Data");
			for(FieldError e : bindingResult.getFieldErrors()){
				logger.debug("Field Name : " + e.getField() + ", Error : " + e.getDefaultMessage() );
				errors.add(e.getDefaultMessage());
			}
			
		}
		else
			errors=labService.addLabSubType(model);
		
		if(errors.size() == 0)
		{
			List<Object> labSubTypes=labService.getLabSubTypes(Integer.parseInt(model.getTypeId()));
			returnModel=new ApiReturnLab(Responses.SUCCESS_CODE, Responses.SUCCESS_STATUS, "labSubType insertion success" , labSubTypes);
		
		}
		else
			returnModel = new ApiReturnModel(Responses.FAILURE_CODE,Responses.ERROR_STATUS,"labSubType insertion failed",errors);
			
		return returnModel;
	}
	
	@RequestMapping(value = "/subType", method = RequestMethod.GET)
	public ApiReturnModel getLabSubTypes()
	{
		ApiReturnModel returnModel=null;
		try
		{
			List<Object> labSubTypes=labService.getLabSubTypes(null);
			returnModel= new ApiReturnLab(Responses.SUCCESS_CODE, Responses.SUCCESS_STATUS,Responses.SUCCESS_MSG, labSubTypes);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("exception occured while getting subtypes:::"+e.toString());
			returnModel = new ApiReturnModel(Responses.FAILURE_CODE,Responses.ERROR_STATUS,e.toString());
		}
		return returnModel; 
	}
	
	@RequestMapping(value = "/subType/{typeId}", method = RequestMethod.GET)
	public ApiReturnModel getLabSubTypes(@PathVariable("typeId") String typeId)
	{
		ApiReturnModel returnModel=null;
		try
		{
			List<Object> labSubTypes=labService.getLabSubTypes(Integer.parseInt(typeId));
			returnModel= new ApiReturnLab(Responses.SUCCESS_CODE, Responses.SUCCESS_STATUS,Responses.SUCCESS_MSG, labSubTypes);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.info("exception occurs while getting subtypes:::"+e.toString());
			returnModel = new ApiReturnModel(Responses.FAILURE_CODE,Responses.ERROR_STATUS,e.toString());
		}
		return returnModel; 
	}
	
	@RequestMapping(value = "/slots" , method = RequestMethod.POST)
	public ApiReturnModel addLabSlots(@RequestParam("subTypeId") String subTypeId , @RequestParam("startTime") String startTime , @RequestParam("endTime") String endTime)
	{
		ApiReturnModel returnModel=null;
		
		UserDetails ud = Utils.getUserSession();
		if(ud == null) return Responses.invaliedSession();
		else
		{
			String role=null;
			for(GrantedAuthority auth :ud.getAuthorities())
				role=auth.getAuthority();
			if(!(role!=null && Constants.ROLE_LAB_ACCESS.contains(role)))
			{
				return new ApiReturnModel(Responses.FAILURE_CODE,Responses.ERROR_STATUS,"only recp can insert lab slots");
			}
		}
		
		List<String> errors = labService.addLabSlots(subTypeId, startTime, endTime);
		
		if(errors.size() == 0)
		{
			List<Object> slots= new ArrayList<Object>();//labService.getSlots(subTypeId);
			returnModel=new ApiReturnLab(Responses.SUCCESS_CODE, Responses.SUCCESS_STATUS, "lab slots insertion success" , slots);
		
		}
		else
			returnModel = new ApiReturnModel(Responses.FAILURE_CODE,Responses.ERROR_STATUS,"lab slots insertion failed",errors);
			
		return returnModel;

	}
	
	@RequestMapping(value = "/slots", method = RequestMethod.GET)
	public ApiReturnModel getLabSlots(@RequestParam("subTypeId") String subTypeId,@RequestParam("date") String date)
	{
		logger.info("enter into slotsBydate");
		ApiReturnModel returnValue = null;
		List<String> errors = new ArrayList<String>(); 
			if(!subTypeId.isEmpty() && !date.isEmpty())
				returnValue = labService.getSlots(subTypeId,date);
			else
			{	errors.add("subtype id and date cant be empty");
				returnValue=new ApiReturnModel(Responses.FAILURE_CODE, Responses.FAILURE_STATUS, "slots not available",errors);
			}
		return returnValue;
	}
	
	@RequestMapping(value = "/booking" , method = RequestMethod.POST)
	public ApiReturnModel labBooking(@Valid CreateLabBookingModel model , BindingResult bindingResult)
	{
		ApiReturnModel returnModel=null;
		
		List<String> errors=new ArrayList<String>();
		if(bindingResult.hasFieldErrors()){
			logger.debug("Invalid Data");
			for(FieldError e : bindingResult.getFieldErrors()){
				logger.debug("Field Name : " + e.getField() + ", Error : " + e.getDefaultMessage() );
				errors.add("Field Name : " + e.getField() + ", Error : " + e.getDefaultMessage());
			}
			return new ApiReturnModel(Responses.FAILURE_CODE,Responses.ERROR_STATUS,Responses.ERROR_MSG,errors);
			
		}
		
		returnModel = labService.labBooking(model);
		
		return returnModel;
	}
	
	@RequestMapping(value = "/booking" , method = RequestMethod.GET)
	public ApiReturnModel myLabBooking(HttpServletRequest request)
	{
		ApiReturnModel returnModel=null;
		try{
			returnModel = labService.getBookings(request);
		}
		catch(Exception ex){
			
		}
		return returnModel;
	}
	
}
