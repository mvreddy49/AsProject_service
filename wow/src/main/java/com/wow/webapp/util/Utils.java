package com.wow.webapp.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.wow.webapp.entitymodel.User;

public class Utils {
	public Date convertStringToDate(String time) 
	{
		try{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date date = sdf.parse(time);
		return date;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static final UserDetails getUserSession(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth instanceof AnonymousAuthenticationToken){
			return null;
		}
		UserDetails ud = (UserDetails)auth.getPrincipal();
		return ud;
	}
}
