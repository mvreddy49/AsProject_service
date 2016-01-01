package com.wow.webapp.util;

import java.util.Arrays;
import java.util.List;

public class Constants {
	
	public static final String ROLE_CLINIC="ROLE_CLINIC";
	public static final String ROLE_USER="ROLE_USER";
	public static final String ROLE_DOCTOR="ROLE_DOCTOR";
	public static final String ROLE_ADMIN="ROLE_ADMIN";
	public static final String ROLE_RECP="ROLE_RECP";
	
	public static final String CLINIC_TYPE="labs";
	public static final Integer LABS_BOOKING_LIMIt=10;
	
	public static final List<String>ROLE_RECP_ACCESS = Arrays.asList("ROLE_USER","ROLE_DOCTOR");
	public static final List<String>ROLE_ADMIN_ACCESS = Arrays.asList("ROLE_RECP", "ROLE_USER", "ROLE_ADMIN","ROLE_DOCTOR");
	
	//SMS Config
	public static final String SMS_URL="http://www.siegsms.com/PostSms.aspx";
	public static final String SMS_USERID="demo3";
	public static final String SMS_PASS=" demo@123";
	public static final String SMS_TITLE="ASTRAA";
	
	//BOOKING SUCCESS MESSSAGE
	public static final String SMS_BOOKING_MSG="BOOKING SUCCESS";
	
	
}
