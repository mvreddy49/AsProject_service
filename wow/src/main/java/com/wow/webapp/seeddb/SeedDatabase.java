package com.wow.webapp.seeddb;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.wow.webapp.dao.ContentDAO;
import com.wow.webapp.dao.UserDAO;
import com.wow.webapp.entitymodel.Authority;
import com.wow.webapp.entitymodel.Clinic;
import com.wow.webapp.entitymodel.ClinicAddress;
import com.wow.webapp.entitymodel.ClinicPhoneNo;
import com.wow.webapp.entitymodel.Profile;
import com.wow.webapp.entitymodel.User;

public class SeedDatabase {

private static final Logger logger = LoggerFactory.getLogger(SeedDatabase.class);
	
	public SeedDatabase(UserDAO userDao,ContentDAO contentDao){
		logger.debug("SeedDatabase start");
		logger.debug("Creating Admin User");
		String adminUserName = "9999999999";
		
		
		try{
			if(userDao == null){
				logger.debug("UserDAO null");
			}
			else{
				User u = userDao.findByUserName(adminUserName);
				logger.debug("Admin user exists " + adminUserName);	
				if(u == null){
					logger.debug("In user insertion");
					u = new User();
					u.setUsername(adminUserName);
					BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
					u.setPassword(passwordEncoder.encode("password@1234"));
					
					//u.setMobile("1212121");
					u.setEnabled(true);
					logger.debug("before clinic insertion");
					Clinic c = new Clinic();
					c.setName("Astra");
					c.setDescription("Astra Clinics");
					c.setEnabled(true);
					c.setType("clinic");
					logger.debug("after setting clinic");
					Set<Authority> authorities = new HashSet<Authority>();
					//authorities.add(new Authority(u, "ROLE_USER"));
					authorities.add(new Authority(u, "ROLE_ADMIN"));
					u.setUserRole(authorities);
					u.setClinic(c);
					logger.debug("after setting authorities");
					ClinicAddress addr = new ClinicAddress();
					addr.setLine1("Hyderabad");
					addr.setLine2("Hyderabad");
					addr.setCity("Hyderabad");
					addr.setState("A.P");
					addr.setCountry("India");
					addr.setZip("534343");
					addr.setClinic(c);
					
					ClinicPhoneNo ph = new ClinicPhoneNo();
					ph.setPhoneNo("9999999999");
					ph.setType("OFFICE");
					ph.setClinic(c);
					
					c.getAddresses().add(addr);
					c.getUsers().add(u);
					c.getPhoneNos().add(ph);

					contentDao.save(c);
					logger.debug("Before inserting");
					Profile userProfile = null;
					logger.info("User profile is : " + u.getUserProfile().getId());
					if(u.getUserProfile().getId() != null){
						userProfile = new Profile(u,"Admin",u.getUserProfile().getId());
					}
					else{
						userProfile = new Profile(u,"Admin");
					}
					u.setUserProfile(userProfile);
					userDao.save(u);

				}
			}
		}
		catch(Exception e){
			logger.debug("Exception raised"+e.toString());
		}		
		logger.debug("SeedDatabase end");
	}
}
