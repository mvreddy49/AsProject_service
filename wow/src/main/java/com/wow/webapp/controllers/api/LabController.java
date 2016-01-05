package com.wow.webapp.controllers.api;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wow.webapp.domain.model.ApiReturnLab;
import com.wow.webapp.domain.model.ApiReturnModel;
import com.wow.webapp.domain.model.CreateLabSubTypeModel;
import com.wow.webapp.services.LabService;
import com.wow.webapp.util.Responses;

@RestController
@RequestMapping("/api/lab")
public class LabController {
	private static final Logger logger = LoggerFactory.getLogger(LabController.class);
	
	@Autowired
	LabService labService;
	
	@RequestMapping(value = "/type", method = RequestMethod.POST)
	public ApiReturnModel addLabType(@RequestParam(value = "name", required = true) String name){
		ApiReturnModel returnModel=null;
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
	
}