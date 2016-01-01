package com.wow.webapp.domain.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

public class ApiBookingReturnModel extends ApiReturnModel{

	public ApiBookingReturnModel() {
		super();
	}
	
	
	public ApiBookingReturnModel(Integer code,String status, String message)
	{
		super(code,status,message);
	}
	
	public ApiBookingReturnModel(Integer code,String status, String message,List<BookingModel> bookings)
	{
		super(code,status,message);
		this.bookings=bookings;
		
	}
	
	private List<BookingModel> bookings = new ArrayList<BookingModel>(0);
	
	

	public List<BookingModel> getBookings() {
		return bookings;
	}
	public void setBookings(List<BookingModel> bookings) {
		this.bookings = bookings;
	}

	
}
