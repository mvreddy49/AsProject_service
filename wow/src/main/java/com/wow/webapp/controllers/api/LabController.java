package com.wow.webapp.controllers.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wow.webapp.domain.model.ApiReturnModel;
import com.wow.webapp.services.LabService;

@RestController
@RequestMapping("/api/lab")
public class LabController {
	private static final Logger logger = LoggerFactory.getLogger(LabController.class);
	
	@RequestMapping(value = "/type", method = RequestMethod.POST)
	public ApiReturnModel addLabType(@RequestParam(value = "name", required = true) String name){
		List<String> errors = new LabService().addLabType(name);
		if(errors.size()==0)
			return(new ApiReturnModel());
		else
			return(new ApiReturnModel());
	}
}
