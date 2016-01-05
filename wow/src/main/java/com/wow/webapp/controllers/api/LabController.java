package com.wow.webapp.controllers.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wow.webapp.domain.model.ApiReturnLabType;
import com.wow.webapp.domain.model.ApiReturnModel;
import com.wow.webapp.domain.pojo.LabTypeModel;
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
			List<LabTypeModel> labTypes=labService.getLabTypes();
			returnModel=new ApiReturnLabType(Responses.SUCCESS_CODE, Responses.SUCCESS_STATUS, "labType insertion success" , labTypes);
		
		}else
		{
			returnModel = new ApiReturnModel(Responses.FAILURE_CODE,Responses.ERROR_STATUS,"labType insertion failed",errors);
		}
		return returnModel;
	}
	
	@RequestMapping(value = "/type", method = RequestMethod.GET)
	public ApiReturnModel getLabTypes()
	{
		List<LabTypeModel> labTypes=labService.getLabTypes();
		return new ApiReturnLabType(Responses.SUCCESS_CODE, Responses.SUCCESS_STATUS,Responses.SUCCESS_MSG, labTypes);
	}
	
}
