package com.wow.webapp.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SMS {
	
	private static final Logger logger = LoggerFactory
			.getLogger(SMS.class);

	
	public void sendSMS(String message,String mobile)
	{
		logger.info("enter into sendSMS");
		try
		{
			logger.info("mobileNumber is :::"+mobile);
			logger.info("message is :::"+message);
			
			RestClient restClient = new RestClient();
			
			restClient.url = Constants.SMS_URL;
			logger.info("SMS URL :::: "+restClient.url);
			restClient.addParam("userid",Constants.SMS_USERID);
			restClient.addParam("pass",Constants.SMS_PASS);
			restClient.addParam("title",Constants.SMS_TITLE);
			
			restClient.addParam("phone",mobile);
			restClient.addParam("msg",message);
			
			String response=restClient.postRestApi();
			logger.info("send SMS Reponse format:::"+response);
					}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.info("exception occurs while send sms:::"+e.toString());
		}
	}

}
