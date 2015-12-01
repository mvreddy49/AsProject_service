package com.wow.webapp.domain.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

public class BookingReturnModel extends ApiReturnModel{

	public BookingReturnModel() {
		super();
	}

	public Integer code;
	public String message;
	private List<BookingModel> bookings = new ArrayList<BookingModel>(0);
	
	
	public BookingReturnModel(Integer code, String message,String desc, List<BookingModel> bookings) {
		super();
		this.code = code;
		this.message = message;
		this.bookings = bookings;
		this.desc = desc;
	}
	public BookingReturnModel(Integer code, String message, String desc) {
		super();
		this.code = code;
		this.message = message;
		this.desc = desc;
	}

	public List<BookingModel> getBookings() {
		return bookings;
	}
	public void setBookings(List<BookingModel> bookings) {
		this.bookings = bookings;
	}

	public String desc;
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
