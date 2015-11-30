package com.wow.webapp.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class AdminController {
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public ModelAndView index(){
		logger.debug("/admin get start");
		ModelAndView mv = new ModelAndView("admin");
		return mv;
	}
}
