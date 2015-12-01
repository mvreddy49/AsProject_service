package com.wow.webapp.domain.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.wow.webapp.entitymodel.Booking;

public class BookingModel extends ApiReturnModel{


	public BookingModel() {
		super();
	}

	public Integer code;
	public String message;
	private Set<Booking> bookings = new HashSet<Booking>(0);
	
	public Set<Booking> getBookings() {
		return bookings;
	}
	public void setBookings(Set<Booking> bookings) {
		this.bookings = bookings;
	}
	public BookingModel(Integer code, String message,String desc, Set<Booking> bookings) {
		super();
		this.code = code;
		this.message = message;
		this.bookings = bookings;
		this.desc = desc;
	}
	public BookingModel(Integer code, String message, String desc) {
		super();
		this.code = code;
		this.message = message;
		this.desc = desc;
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
