package com.wow.webapp.entitymodel;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name="profile")
public class Profile {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="name")
	private String name;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "username", nullable = true)
	private User user;

	@Temporal(TemporalType.TIMESTAMP)
	//@Column(name = "inserted_on", length = 19,columnDefinition="TIMESTAMP default CURRENT_TIMESTAMP")
	@Column(name = "inserted_on", updatable = false,insertable = false)
	private Date inserted_on;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_on", length = 19,columnDefinition="TIMESTAMP default CURRENT_TIMESTAMP")
	private Date updated_on;
	
	public Profile(){
		
	}
	
	public Profile(User user,String name){
		this.user = user;
		this.name = name;
	}
	
	public Profile(User user,String name,int id){
		this.user = user;
		this.name = name;
		this.id = id;
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getInserted_on() {
		return inserted_on;
	}

	public void setInserted_on(Date inserted_on) {
		this.inserted_on = inserted_on;
	}

	public Date getUpdated_on() {
		return updated_on;
	}

	public void setUpdated_on(Date updated_on) {
		this.updated_on = updated_on;
	}
	
}
