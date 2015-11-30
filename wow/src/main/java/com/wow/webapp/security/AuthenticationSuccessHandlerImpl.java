package com.wow.webapp.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wow.webapp.domain.model.ApiReturnModel;

public class AuthenticationSuccessHandlerImpl extends SimpleUrlAuthenticationSuccessHandler {
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationSuccessHandlerImpl.class);
	
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication) throws IOException,
			ServletException {
		logger.debug("Ajax Authentication Success Handler Start");
		if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
			response.setContentType("application/json;charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			
			ApiReturnModel ret = new ApiReturnModel("OK",this.getDefaultTargetUrl(), new ArrayList<String>(0));
			ObjectMapper mapper = new ObjectMapper();
			response.getWriter().print(mapper.writeValueAsString(ret));			
	        response.getWriter().flush();
	    } else {
	        super.onAuthenticationSuccess(request, response, authentication);
	    }
		logger.debug("Ajax Authentication Success Handler End");
	}
}
