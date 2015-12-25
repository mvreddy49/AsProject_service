package com.wow.webapp.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;


public class EmailUtility {
	private static final Logger logger = LoggerFactory.getLogger(EmailUtility.class);
	
	public void sendEmail(String name, String contactemail, String phone, String text){
		SimpleMailMessage preConfiguredMessage = new SimpleMailMessage();
		preConfiguredMessage.setTo("ashokreddy450@gmail.com");
		preConfiguredMessage.setFrom("ashokreddy450@gmail.com");
		preConfiguredMessage.setText(text);
		preConfiguredMessage.setSubject("Enquiry from " + name + "," + contactemail + "," + phone);
		SimpleMailMessage email = new SimpleMailMessage(preConfiguredMessage);
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);
		mailSender.setUsername("ashokreddy450@gmail.com");
		mailSender.setPassword("9493112016");
		mailSender.getJavaMailProperties().setProperty("mail.transport.protocol", "smtp");
		mailSender.getJavaMailProperties().setProperty("mail.smtp.auth", "true");
		mailSender.getJavaMailProperties().setProperty("mail.smtp.starttls.enable", "true");
		
		mailSender.send(email);
		logger.debug("In Send mail");
	

}
}
