package com.wow.webapp.entitymodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="authorities")
public class Authority {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "username", nullable = true)
	private User user;
	
	@Column(name = "authority", nullable = false, length = 45)
	private String authority;

	public Authority() {
	}

	public Authority(User user, String authority) {
		this.user = user;
		this.authority = authority;
	}
	
	public Authority(User user, String authority, int id) {
		this.user = user;
		this.authority = authority;
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getRole() {
		return authority;
	}
	public void setRole(String role) {
		this.authority = role;
	}
}
