package com.wow.webapp.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.wow.webapp.entitymodel.User;

public class Utils {
	public Date convertStringToDate(String time) 
	{
		try{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		//sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date date = sdf.parse(time);
		return date;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public User getUserSession(){
		User user = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth instanceof AnonymousAuthenticationToken){
		}
		else{
			
		}
		return user;
	}
	
	public String convertDateToUTCFormat(Date date)
	{
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
			//sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
			String utcFormat = sdf.format(date);
			return utcFormat;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public Date getTimeFromDate(Date date)
	{
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			Date d=sdf.parse(sdf.format(date));
			return d;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
