package com.wow.webapp.seeddb;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.wow.webapp.dao.UserDAO;
import com.wow.webapp.entitymodel.Authority;
import com.wow.webapp.entitymodel.User;

public class SeedDatabase {

private static final Logger logger = LoggerFactory.getLogger(SeedDatabase.class);
	
	public SeedDatabase(UserDAO userDao){
		logger.debug("SeedDatabase start");
		logger.debug("Creating Admin User");
		String adminUserName = "admin@wow.com";
		
		
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
					u.setMobile("1212121");
					u.setEnabled(true);
					Set<Authority> authorities = new HashSet<Authority>();
					authorities.add(new Authority(u, "ROLE_USER"));
					authorities.add(new Authority(u, "ROLE_ADMIN"));
					u.setUserRole(authorities);
					userDao.save(u);
				}
			}
		}
		catch(Exception e){
			logger.debug("Exception raised");
		}		
		logger.debug("SeedDatabase end");
	}
}
