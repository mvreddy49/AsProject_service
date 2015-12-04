package com.wow.webapp.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.wow.webapp.entitymodel.User;

public class Utils {
	public Date convertStringToDate(String time) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
			// sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
			Date date = sdf.parse(time);
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
			SimpleDateFormat sdf = new SimpleDateFormat(
					"yyyy-MM-dd'T'HH:mm:ss'Z'");
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
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			Date d = sdf.parse(sdf.format(date));
			return d;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<String> getRangeTimes(Date startTime, Date endTime) {
		List<String> times = null;
		try {
			times = new ArrayList<String>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
			Calendar calendar = GregorianCalendar.getInstance();
			calendar.setTime(startTime);
			while (calendar.getTime().before(endTime)) {
				calendar.add(Calendar.MINUTE, 30);
				times.add(sdf.format(calendar.getTime()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return times;
	}
	
	public String getEncryptedPassword(String str)
	{
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.encode(str);
	}
	
}
