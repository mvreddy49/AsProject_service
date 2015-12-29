package com.wow.webapp.seeddb;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.wow.webapp.dao.UserDAO;
import com.wow.webapp.entitymodel.Authority;
import com.wow.webapp.entitymodel.Profile;
import com.wow.webapp.entitymodel.User;

public class SeedDatabase {

private static final Logger logger = LoggerFactory.getLogger(SeedDatabase.class);
	
	public SeedDatabase(UserDAO userDao){
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
					u = new User();
					u.setUsername(adminUserName);
					BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
					u.setPassword(passwordEncoder.encode("password@1234"));
					//u.setMobile("1212121");
					u.setEnabled(true);
					Set<Authority> authorities = new HashSet<Authority>();
					//authorities.add(new Authority(u, "ROLE_USER"));
					authorities.add(new Authority(u, "ROLE_ADMIN"));
					u.setUserRole(authorities);
					logger.debug("Before inserting profile info");
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
