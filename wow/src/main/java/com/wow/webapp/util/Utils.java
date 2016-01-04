package com.wow.webapp.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import ch.qos.logback.classic.Logger;

public class Utils {
	public Date convertStringToDate(String time) {
		try {
			DateFormat sdf =setUTCFormat();
			// sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
			Date date = sdf.parse(time);
			return date;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Date convertStringToDateOnly(String time)
	{
		try {
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			// sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
			Date date = sdf.parse(time);
			return date;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public String convertStringToDateOnly(Date time)
	{
		try {
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			// sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
			String date = sdf.format(time);
			return date;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static final UserDetails getUserSession() {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (auth instanceof AnonymousAuthenticationToken) {
			return null;
		}
		UserDetails ud = (UserDetails) auth.getPrincipal();
		return ud;
	}

	public String convertDateToUTCFormat(Date date) {
		try {
			DateFormat sdf =setUTCFormat(); 
			// sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
			String utcFormat = sdf.format(date);
			return utcFormat;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Date getTimeFromDate(Date date) {
		try {
			DateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			Date d = sdf.parse(sdf.format(date));
			return d;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public  List<String> getRangeTimes(Date startTime, Date endTime,Integer duration) {
		List<String> times = null;
		try {
			times = new ArrayList<String>();
			DateFormat sdf = setUTCFormat();
			Calendar calendar = GregorianCalendar.getInstance();
			calendar.setTime(startTime);
			times.add(sdf.format(startTime));
			Date startDate=getTimeFromDate(startTime);
			Date endDate=getTimeFromDate(endTime);
			while (calendar.getTime().before(endTime)) {
				calendar.add(Calendar.MINUTE, duration);
				Date time=getTimeFromDate(calendar.getTime());
				int comp = startDate.compareTo(time);
				int comp1 = endDate.compareTo(time);
				if ((comp == -1 || comp == 0) && comp1 == 1) 
					{
						times.add(sdf.format(calendar.getTime()));
					}
			}
					//times.remove(times.size()-1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return times;
	}
	
	
	public Date addDateAndTime(Date date,Date time)
	{
		try
		{
			   DateFormat df =setUTCFormat();
			   Calendar cal = Calendar.getInstance();
			   cal.setTime(date);
			   cal.add(Calendar.HOUR,time.getHours());
			   cal.add(Calendar.MINUTE,time.getMinutes());
			   return df.parse(df.format(cal.getTime()));
			   
		}
		catch(Exception e)
		{
			e.printStackTrace();
			
		}
		return null;
	}
	
	public String getEncryptedPassword(String str)
	{
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.encode(str);
	}
	public SimpleDateFormat setUTCFormat()
	{
		
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
			// sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
			return sdf;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
}
