package com.wow.webapp.domain.model;

import java.util.ArrayList;
import java.util.List;

public class ApiReturnModelUserBookings extends ApiReturnModel {
	
	private List<BookingReturnModel> bookings = new ArrayList<BookingReturnModel>(0);

	
	
	public ApiReturnModelUserBookings() {
		super();
	}

	public ApiReturnModelUserBookings(String status, String message,
			List<String> errors) {
		super(status, message, errors);
	}

	public ApiReturnModelUserBookings(String status, String message) {
		super(status, message);
	}

	public ApiReturnModelUserBookings(String status) {
		super(status);
	}

	public ApiReturnModelUserBookings(List<BookingReturnModel> bookings) {
		super();
		this.bookings = bookings;
	}

	public List<BookingReturnModel> getBookings() {
		return bookings;
	}

	public void setBookings(List<BookingReturnModel> bookings) {
		this.bookings = bookings;
	}
	
	

}
