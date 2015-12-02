package com.wow.webapp.domain.model;

import java.util.ArrayList;
import java.util.List;

import com.wow.webapp.util.Responses;

public class ApiReturnModelUserBookings extends ApiReturnModel {
	
	private List<ApiBookingReturnModel> bookings = new ArrayList<ApiBookingReturnModel>(0);

	
	
	public ApiReturnModelUserBookings() {
		super();
	}

	public ApiReturnModelUserBookings(String status, String message,
			List<String> errors) {
		super(status, message, errors);
	}

	public ApiReturnModelUserBookings(String status, String message) {
		super(Responses.SUCCESS_CODE,status, message);
	}

	public ApiReturnModelUserBookings(String status) {
		super(Responses.SUCCESS_CODE,status);
	}

	public ApiReturnModelUserBookings(List<ApiBookingReturnModel> bookings) {
		super();
		this.bookings = bookings;
	}

	public List<ApiBookingReturnModel> getBookings() {
		return bookings;
	}

	public void setBookings(List<ApiBookingReturnModel> bookings) {
		this.bookings = bookings;
	}
	
	

}
