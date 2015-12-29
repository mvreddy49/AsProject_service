package org.wow.webapp.seeddb;

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
		String adminUserName = "admin@wow.com";
		
		
		try{
			if(userDao == null){
				logger.debug("UserDAO null");
			}
			else{
				userDao.findByUserName(adminUserName);
				logger.debug("Admin user exists " + adminUserName);				
			}
		}
		catch(Exception e){
			User u = new User();
			u.setUsername(adminUserName);
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			u.setPassword(passwordEncoder.encode("password@1234"));
			u.setEnabled(true);
			Set<Authority> authorities = new HashSet<Authority>();
			//authorities.add(new Authority(u, "ROLE_USER"));
			authorities.add(new Authority(u, "ROLE_ADMIN"));
			u.setUserRole(authorities);
		
			userDao.save(u);
		}		
		logger.debug("SeedDatabase end");
	}
}
