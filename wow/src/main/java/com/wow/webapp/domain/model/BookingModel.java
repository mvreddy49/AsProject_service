package com.wow.webapp.domain.model;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

public class BookingModel extends ApiReturnModel{


	public BookingModel() {
		super();
	}

	public String booking_status;
	public String getBooking_status() {
		return booking_status;
	}
	public void setBooking_status(String booking_status) {
		this.booking_status = booking_status;
	}
	public String getBooking_message() {
		return booking_message;
	}
	public void setBooking_message(String booking_message) {
		this.booking_message = booking_message;
	}
	public String getBooking_desc() {
		return booking_desc;
	}
	public void setBooking_desc(String booking_desc) {
		this.booking_desc = booking_desc;
	}

	public String booking_message;
	public String booking_desc;	
}
