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
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wow.webapp.domain.model.ApiReturnModel;

public class AuthenticationEntryPointImpl extends LoginUrlAuthenticationEntryPoint{
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationEntryPointImpl.class);
	public AuthenticationEntryPointImpl(String loginFormUrl) {
		super(loginFormUrl);
		logger.debug("In entry");
	}
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException auth) throws IOException, ServletException {
		logger.info(getLoginFormUrl());
		logger.debug("Ajax Authentication EntryPoint Handler Start");
		
		if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
			response.setContentType("application/json;charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			ApiReturnModel ret = new ApiReturnModel("ERROR","Login to continue", 
					new ArrayList<String>(Arrays.asList("Unauthorized")));
			ObjectMapper mapper = new ObjectMapper();
			response.getWriter().print(mapper.writeValueAsString(ret));			
	        response.getWriter().flush();
	    } else {
	    	super.commence(request, response, auth);
	    }
		logger.debug("Ajax Authentication EntryPoint Handler End");
		
		
	}

}
