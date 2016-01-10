package com.wow.webapp.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.wow.webapp.dao.UserDAO;
import com.wow.webapp.domain.model.LoginModel;
import com.wow.webapp.util.Constants;

@Controller
public class HomeController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	@Autowired
    private UserDAO userDao;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView index(){
		logger.debug("/ get start");
		String home = "HomeOne";
		ModelAndView mv = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth instanceof AnonymousAuthenticationToken){
			mv = new ModelAndView(home);
			
		}
		else{
			
			logger.info("Home page is :" + home);
			logger.info("Role is :" + auth.getAuthorities());
			String role = "";
			for(GrantedAuthority grantAuth :auth.getAuthorities())
			{
				role=grantAuth.getAuthority();
				if(Constants.ROLE_RECP.equalsIgnoreCase(role)){
					home = "recp"; break;
				}
				else if(Constants.ROLE_ADMIN.equalsIgnoreCase(role)){
					home = "admin"; break;
				}
				else if(Constants.ROLE_LAB.equalsIgnoreCase(role)){
					home = "lab"; break;
				}
				else if(Constants.ROLE_DOCTOR.equalsIgnoreCase(role)){
					home = "doctor"; break;
				}
			}
			logger.info("Home page is :" + home);
			mv = new ModelAndView("redirect:"+home);
			
		}
		
		return mv;
	}
	
	@RequestMapping(value = "/clinics", method = RequestMethod.GET)
	public ModelAndView clinics(){
		logger.debug("/clinics get start");
		ModelAndView mv = new ModelAndView("clinics");
		mv.addObject("clinics",userDao.getClinics(null, null));
		return mv;
	}	
	@RequestMapping(value = "/tests", method = RequestMethod.GET)
	public ModelAndView tests(){
		logger.debug("/tests get start");
		ModelAndView mv = new ModelAndView("tests");
		mv.addObject("tests",userDao.getTests());
		return mv;
	}
	@RequestMapping(value = "/HomeOne", method = RequestMethod.GET)
	public ModelAndView HomeOne(){
		logger.debug("home One get start");
		ModelAndView mv = new ModelAndView("HomeOne");
		return mv;
	}
	
	@RequestMapping(value = "/clinic-booking", method = RequestMethod.GET)
	public ModelAndView ClinicBooking(){
		logger.debug("clinic-booking get start");
		ModelAndView mv = new ModelAndView("clinic-booking");
		return mv;
	}
	
	@RequestMapping(value = "/diagnostics-booking", method = RequestMethod.GET)
	public ModelAndView DiagnosticsBooking(){
		logger.debug("diagnostics-booking get start");
		ModelAndView mv = new ModelAndView("diagnostics-booking");
		return mv;
	}
	
	@RequestMapping(value = "/pharmacy-booking", method = RequestMethod.GET)
	public ModelAndView PharmacyBooking(){
		logger.debug("pharmacy-booking get start");
		ModelAndView mv = new ModelAndView("pharmacy-booking");
		return mv;
	}
	
	@RequestMapping(value = "/SecondOpinion-talk", method = RequestMethod.GET)
	public ModelAndView SecondOpinionTalk(){
		logger.debug("SecondOpinion-talk get start");
		ModelAndView mv = new ModelAndView("SecondOpinion-talk");
		return mv;
	}
	

	@RequestMapping(value = "/CarporateHealth-booking", method = RequestMethod.GET)
	public ModelAndView CarporateHealthBooking(){
		logger.debug("CarporateHealth-booking get start");
		ModelAndView mv = new ModelAndView("CarporateHealth-booking");
		return mv;
	}
	
	
	
	@RequestMapping(value = "/AboutUs", method = RequestMethod.GET)
	public ModelAndView AboutUS(){
		logger.debug("AboutUS get start");
		ModelAndView mv = new ModelAndView("AboutUS");
		return mv;
	}
	
	@RequestMapping(value = "/Services", method = RequestMethod.GET)
	public ModelAndView Services(){
		logger.debug("Services get start");
		ModelAndView mv = new ModelAndView("Services");
		return mv;
	}
	
	@RequestMapping(value = "/PartnerWithUs", method = RequestMethod.GET)
	public ModelAndView PartnerWithUs(){
		logger.debug("PartnerWithUs get start");
		ModelAndView mv = new ModelAndView("PartnerWithUs");
		return mv;
	}
	
	@RequestMapping(value = "/Career", method = RequestMethod.GET)
	public ModelAndView Career(){
		logger.debug("Career get start");
		ModelAndView mv = new ModelAndView("Career");
		return mv;
	}
	
	@RequestMapping(value = "/Contact", method = RequestMethod.GET)
	public ModelAndView Contact(){
		logger.debug("Contact get start");
		ModelAndView mv = new ModelAndView("Contact");
		return mv;
	}
	@RequestMapping(value = "/recp", method = RequestMethod.GET)
	public ModelAndView recp(){
		logger.debug("recp get get start");
		ModelAndView mv = new ModelAndView("recp");
		return mv;
	}
	@RequestMapping(value = "/lab", method = RequestMethod.GET)
	public ModelAndView lab(){
		logger.debug("lab get get start");
		ModelAndView mv = new ModelAndView("lab");
		return mv;
	}
	@RequestMapping(value = "/doctor", method = RequestMethod.GET)
	public ModelAndView doctor(){
		logger.debug("recp get get start");
		ModelAndView mv = new ModelAndView("doctor");
		return mv;
	}
	@RequestMapping(value = "/MedicalTourism", method = RequestMethod.GET)
	public ModelAndView MedicalTourism(){
		logger.debug("MedicalTourism get get start");
		ModelAndView mv = new ModelAndView("MedicalTourism");
		return mv;
	}
	
	@RequestMapping(value = "homeclinic", method = RequestMethod.GET)
	public ModelAndView homeclinic(){
		logger.debug("homeclinic get get start");
		ModelAndView mv = new ModelAndView("homeclinic");
		return mv;
	}

}
