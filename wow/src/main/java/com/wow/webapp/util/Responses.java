
package com.wow.webapp.util;

import com.wow.webapp.domain.model.ApiReturnModel;

public class Responses {
	
	public static final Integer SUCCESS_CODE=200;
	public static final Integer FAILURE_CODE=500;
	
	public static final Integer INVALID_PARAM_CODE=400;
	public static final Integer USER_NOTLOGGEDIN=401;
	
	public static final Integer CUSTOM_CODE=210;
	
	public static final String SUCCESS_MSG="SUCCESS";
	public static final String ERROR_MSG="ERROR";
	public static final String INVALID_PARAMS_MSG="INVALID_PARAMS";
	public static final String INVALIED_SESSION_MSG = "INVALIED_SESSION";
	public static final String NO_ACCESS_MSG = "NO access to add user";
	

	public static final String SUCCESS_STATUS="OK";
	public static final String ERROR_STATUS="ERROR";

	public static final String FAILURE_STATUS="FAIL";

	public static final String INVALID_USER_PARAM="Invalid user parameters";
	public static final String NORECORDS="No Records availble";
	
	
	
	public static final ApiReturnModel invaliedSession(){
		return new ApiReturnModel(USER_NOTLOGGEDIN,INVALIED_SESSION_MSG,"User not logged in, Please login and try");
	}
}
