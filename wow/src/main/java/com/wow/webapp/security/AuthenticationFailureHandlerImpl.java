package com.wow.webapp.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wow.webapp.domain.model.ApiReturnModel;


public class AuthenticationFailureHandlerImpl extends SimpleUrlAuthenticationFailureHandler {
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationFailureHandlerImpl.class);
	
	public AuthenticationFailureHandlerImpl(String defaultFailureUrl) {
		super(defaultFailureUrl);
	}
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		logger.debug("Ajax Authentication Failure Handler Start");
		
		if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
			response.setContentType("application/json;charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			ApiReturnModel ret = new ApiReturnModel("ERROR","Unauthorized", 
					new ArrayList<String>(Arrays.asList("Login Fail")));
			ObjectMapper mapper = new ObjectMapper();
			response.getWriter().print(mapper.writeValueAsString(ret));			
	        response.getWriter().flush();
	    } else {
	    	super.onAuthenticationFailure(request, response, exception);
	    }
		logger.debug("Ajax Authentication Failure Handler End");
	}
}
