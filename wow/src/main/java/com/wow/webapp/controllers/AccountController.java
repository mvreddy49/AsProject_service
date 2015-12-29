package com.wow.webapp.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.wow.webapp.controllers.api.ContentInsertController;
import com.wow.webapp.dao.ContentDAO;
import com.wow.webapp.dao.UserDAO;
import com.wow.webapp.domain.model.CreateAccountModel;
import com.wow.webapp.domain.model.CreateDoctorModel;
import com.wow.webapp.domain.model.LoginModel;
import com.wow.webapp.entitymodel.Authority;
import com.wow.webapp.entitymodel.Clinic;
import com.wow.webapp.entitymodel.ClinicAddress;
import com.wow.webapp.entitymodel.ClinicPhoneNo;
import com.wow.webapp.entitymodel.ClinicTest;
import com.wow.webapp.entitymodel.Doctor;
import com.wow.webapp.entitymodel.Slot;
import com.wow.webapp.entitymodel.User;
import com.wow.webapp.util.Utils;


@Controller
//@RequestMapping("account")
public class AccountController {

	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
	
	@Autowired
    private UserDAO userDao;
	
	@Autowired
	private ContentDAO contentDao;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(){
		logger.debug("login start");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth instanceof AnonymousAuthenticationToken){
			LoginModel model = new LoginModel();
			ModelAndView mv = new ModelAndView("login");
			mv.addObject("model", model);
			return mv;
		}
		else{
			ModelAndView mv = new ModelAndView("redirect:home");
			return mv;
		}
	}
	
	@RequestMapping(value = "/home-user", method = RequestMethod.GET)
	public ModelAndView home_user(){
		logger.debug("home_user get start");
		ModelAndView mv = new ModelAndView("home-user");
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		logger.debug(auth.toString());
		logger.debug(auth.getPrincipal().toString());
		logger.debug(auth.getAuthorities().toString());
		logger.debug(auth.getName());
		if(auth.getPrincipal() instanceof UserDetails){
			UserDetails ud = (UserDetails)auth.getPrincipal();
			logger.debug(ud.toString());			
		}
		logger.debug("home_user get end");
		return mv;
	}

	//Adding home-clinic for custom screen for clinic
	@RequestMapping(value = "/home-clinic", method = RequestMethod.GET)
	public ModelAndView home_clinic(){
		logger.debug("home_clinic get start");
		ModelAndView mv = new ModelAndView("home-clinic");
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		logger.debug(auth.toString());
		logger.debug(auth.getPrincipal().toString());
		logger.debug(auth.getAuthorities().toString());
		logger.debug(auth.getName());
		if(auth.getPrincipal() instanceof UserDetails){
			UserDetails ud = (UserDetails)auth.getPrincipal();
			logger.debug(ud.toString());			
		}
		logger.debug("home_clinic get end");
		return mv;
	}
	
	@RequestMapping(value = "/register-clinic", method = RequestMethod.GET)
	public ModelAndView register(){
		logger.debug("register get start");
		CreateAccountModel model= new CreateAccountModel();
		ModelAndView mv = new ModelAndView("register-clinic");
		mv.addObject("model", model);
		mv.addObject("errors", new ArrayList<String>());
		return mv;
	}

	@RequestMapping(value = "/register-doctor", method = RequestMethod.GET)
	public ModelAndView register_doctor(){
		logger.debug("register get start");
		CreateDoctorModel model= new CreateDoctorModel();
		ModelAndView mv = new ModelAndView("register-doctor");
		mv.addObject("model", model);
		mv.addObject("errors", new ArrayList<String>());
		return mv;
	}
	
	@RequestMapping(value = "/register-user", method = RequestMethod.GET)
	public ModelAndView register_user(){
		logger.debug("register-user get start");
		ModelAndView mv = new ModelAndView("register-user");
		logger.debug("register-user get end");
		return mv;
	}
	
	@RequestMapping(value = "/test-data-upload", method = RequestMethod.POST)
	public ModelAndView tests_from_file(
			@RequestParam("testdatafile") MultipartFile testdatafile){
		logger.debug("test_from_file post start");
		if(testdatafile != null && !testdatafile.isEmpty()){
			logger.debug("processing file : " + testdatafile.getOriginalFilename());
			try {
				
				Workbook wb = new XSSFWorkbook(testdatafile.getInputStream());
				Sheet sheet = wb.getSheetAt(0);
				int rowNo = -1;
				for (Row row : sheet){
					rowNo++;
					if(rowNo == 0) continue;
					logger.debug("Adding test " + row.getCell(1).toString());
					//Clinic c = userDao.findByClinicName(row.getCell(0).toString());
					ClinicTest t = new ClinicTest();
					t.setTest_name(row.getCell(1).toString());
					t.setTest_description(row.getCell(1).toString());
					t.setOnline_reports(Boolean.valueOf(row.getCell(2).toString()));
					t.setHome_pickup(Boolean.valueOf(row.getCell(3).toString()));
					t.setActual_price(Double.parseDouble(row.getCell(4).toString()));
					t.setCurrent_price(Double.parseDouble(row.getCell(4).toString()));
					t.setSpeciality_name(row.getCell(5).toString());
					
					userDao.AddTestToClinic(row.getCell(0).toString(), t);						
				}
				wb.close();
				logger.debug("Reading completed");
			}
			catch (IOException e) {
				logger.debug(e.toString());
			}
		}
		
		ModelAndView mv = new ModelAndView("admin");
		return mv;
	}
	
	@RequestMapping(value = "/clinic-data-upload", method = RequestMethod.POST)
	public ModelAndView register_from_file(
			@RequestParam("clinicdatafile") MultipartFile clinicdatafile){
		logger.debug("register_from_file post start");
		if(clinicdatafile != null && !clinicdatafile.isEmpty()){
			logger.debug("processing file : " + clinicdatafile.getOriginalFilename());
			
			try {
				
				Workbook wb = new XSSFWorkbook(clinicdatafile.getInputStream());
				Sheet sheet = wb.getSheetAt(0);
				int rowNo = -1;
				for (Row row : sheet){
					rowNo++;
					if(rowNo == 0) continue;
					logger.debug("Adding Clinic" + row.getCell(0).toString());
					Clinic c = new Clinic();
					c.setName(row.getCell(0).toString());
					c.setDescription(row.getCell(0).toString());
					c.setEnabled(true);
					
					User u = new User();
					//u.setUsername(row.getCell(1).toString());
					u.setUsername(row.getCell(3).toString());
					BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
					u.setPassword(passwordEncoder.encode(row.getCell(2).toString()));
					u.setEnabled(true);
					//u.setMobile(row.getCell(3).toString());
					Set<Authority> authorities = new HashSet<Authority>();
					//authorities.add(new Authority(u, "ROLE_USER"));
					authorities.add(new Authority(u, "ROLE_CLINIC"));
					u.setUserRole(authorities);
					u.setClinic(c);
					
					ClinicAddress addr = new ClinicAddress();
					addr.setLine1(row.getCell(4).toString());
					addr.setLine2(row.getCell(5).toString());
					addr.setCity(row.getCell(6).toString());
					addr.setState(row.getCell(7).toString());
					addr.setCountry(row.getCell(8).toString());
					addr.setZip("000000");
					addr.setClinic(c);
					
					ClinicPhoneNo ph = new ClinicPhoneNo();
					ph.setPhoneNo(row.getCell(3).toString());
					ph.setType("OFFICE");
					ph.setClinic(c);
					
					c.getAddresses().add(addr);
					c.getUsers().add(u);
					c.getPhoneNos().add(ph);
					userDao.save(c);
				}
				wb.close();
				logger.debug("Reading completed");
			} catch (IOException e) {
				logger.debug(e.toString());
			}

			
			
		}
		
		ModelAndView mv = new ModelAndView("admin");
		return mv;
	}
	
	
	@RequestMapping(value = "/doctor-data-upload", method = RequestMethod.POST)
	public ModelAndView register_doctor_from_file(
			@RequestParam("doctordatafile") MultipartFile doctordatafile){
		logger.debug("register_from_file post start");
		UserDetails ud = Utils.getUserSession();
		//if(ud == null) return Responses.invaliedSession();
		if(doctordatafile != null && !doctordatafile.isEmpty()){
			logger.debug("processing file : " + doctordatafile.getOriginalFilename());
			List<String> errors = new ArrayList<String>();
			try {
				
				Workbook wb = new XSSFWorkbook(doctordatafile.getInputStream());
				Sheet sheet = wb.getSheetAt(0);
				int rowNo = -1;
				for (Row row : sheet){
					rowNo++;
					if(rowNo == 0) continue;
					logger.debug("Adding Clinic" + row.getCell(0).toString());
					Doctor d = new Doctor();
					logger.debug("Type is :");
					row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
					
					CreateDoctorModel model = new CreateDoctorModel();
					model.setName(row.getCell(0).toString());
					model.setMobile(row.getCell(1).toString());
					model.setSpeciality(row.getCell(2).toString());
					model.setStartTime(row.getCell(3).toString());
					model.setEndTime(row.getCell(4).toString());
					
					logger.debug("Persisting");
					try {
						Doctor doctor = contentDao.findDoctorByMobile(model.getMobile());
						logger.debug("After retriving doctor" + model.toString());
						if(doctor != null){
							throw new Exception("Not Found : " + model.getMobile());
						}
						logger.debug("After exception");
						errors = registerDoctorImpl(model,errors,ud);
					} catch (Exception e) {
						logger.debug("Exception is :" + e.toString());
						errors.add("Doctor Already registered");
					}
					
				}
				wb.close();
				logger.debug("Reading completed");
			} catch (IOException e) {
				logger.debug(e.toString());
			}

			
			
		}
		
		ModelAndView mv = new ModelAndView("admin");
		return mv;
	}
	
	
	public List<String> registerDoctorImpl(CreateDoctorModel model,List<String> errors,UserDetails ud){
		Utils u = new Utils();
		Date startTime,endTime;
		try{
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			startTime= u.convertStringToDate(model.getStartTime());
			endTime = u.convertStringToDate(model.getEndTime());
			Doctor d = new Doctor();
			//d.setMobile(model.getMobile());
			//d.setName(model.getName());
			d.setSpeciality(model.getSpeciality());
			
			Slot s = new Slot();
			try{
				Clinic c = userDao.getClinicByUserName(ud.getUsername());
				s.setClinic(c);
			}
			catch(Exception ex){
				logger.debug("Exception is : "+ ex);
			}
			s.setDoctor(d);
			//s.setStartTime(startTime);
			//s.setEndTime(endTime);
			
			d.getSlots().add(s);
			contentDao.save(d);
			
			logger.debug("StaetTime :"+startTime);
		}
		catch(Exception ex){
			errors.add("Mobile number is already registered");
		}
		return errors;
	}
	
	
	@RequestMapping(value = "/register-clinic", method = RequestMethod.POST)
	public ModelAndView register(@Valid CreateAccountModel model, BindingResult bindingResult){
		logger.debug("register post start");
		List<String> errors = new ArrayList<String>();
		if(bindingResult.hasFieldErrors()){
			ModelAndView mv = new ModelAndView("register-clinic");	
			mv.addObject("model", model);
			logger.debug("Invalid Data");
			for(FieldError e : bindingResult.getFieldErrors()){
				logger.debug("Field Name : " + e.getField() + ", Error : " + e.getDefaultMessage() );
				errors.add(e.getDefaultMessage());
			}
			mv.addObject("errors", errors);
			return mv;
		}
		else{
			logger.debug("Persisting");
			
			try {
				userDao.findByUserName(model.getClinicPhone1());
			} catch (Exception e) {
				ModelAndView mv = new ModelAndView("register-clinic");	
				mv.addObject("model", model);
				errors.add("Already registered");
				mv.addObject("errors", errors);
				return mv;
			}
			Clinic c = new Clinic();
			c.setName(model.getClinicName());
			c.setDescription(model.getClinicDesc());
			c.setEnabled(true);

			User u = new User();
			//u.setUsername(model.getEmail());
			u.setUsername(model.getClinicPhone1());
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			u.setPassword(passwordEncoder.encode(model.getPasswd()));
			u.setEnabled(true);
			//u.setMobile(model.getClinicPhone1());
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

			userDao.save(c);
			//userDao.save(u);
			logger.debug("Saved");
			
			ModelAndView mv = new ModelAndView("redirect:/");
			return mv;
		}
	}	
}
