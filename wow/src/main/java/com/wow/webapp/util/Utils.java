package com.wow.webapp.util;

import java.text.SimpleDateFormat;
import java.util.Date;

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
}
