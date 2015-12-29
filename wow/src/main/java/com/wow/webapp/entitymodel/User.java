package com.wow.webapp.entitymodel;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name="users")
public class User {
	

	@Id
	@Column(name="username", unique = true ,length=50, nullable=false)
	private String username;
	
	@Column(name="password", length=60, nullable=false)
	private String password;
	
	
	@Column(name="enabled", nullable = false)
	private boolean enabled;
	
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = {CascadeType.ALL})
	private Set<Authority> userRole = new HashSet<Authority>(0);
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = { CascadeType.ALL})
	private Set<Booking> bookings = new HashSet<Booking>(0);
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "user", cascade = { CascadeType.ALL})
	private Profile userProfile = new Profile();
	
	public Set<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(Set<Booking> bookings) {
		this.bookings = bookings;
	}

	public Clinic getClinic() {
		return clinic;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_on", updatable = true, insertable = false,columnDefinition="TIMESTAMP default CURRENT_TIMESTAMP")
	private Date modified_on;
	
	public Date getModified_on() {
		return modified_on;
	}

	

	public void setModified_on(Date modified_on) {
		this.modified_on = modified_on;
	}

	public Date getInserted_on() {
		return inserted_on;
	}

	public void setInserted_on(Date inserted_on) {
		this.inserted_on = inserted_on;
	}

	
	public User(String username) {
		super();
		this.username = username;
	}

	

	@Temporal(TemporalType.TIMESTAMP)
	//@Column(name = "inserted_on", updatable = false, insertable = true,columnDefinition="TIMESTAMP default CURRENT_TIMESTAMP") 
	@Column(name = "inserted_on", updatable = false,insertable = false)
	private Date inserted_on;
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "clinic_id", nullable = true)
	private Clinic clinic;
	
	public User() {
	}

	public User(String username, String password, boolean enabled) {
		this.username = username;
		this.password = password;
		this.enabled = enabled;
	}

	public User(String username, String password, boolean enabled, Set<Authority> userRole) {
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.userRole = userRole;
	}
	
	public User(String username, String password, boolean enabled, Set<Authority> userRole, Clinic clinic) {
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.userRole = userRole;
		this.clinic = clinic;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<Authority> getUserRole() {
		return userRole;
	}

	public void setUserRole(Set<Authority> userRole) {
		this.userRole = userRole;
	}

	public Profile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(Profile userProfile) {
		this.userProfile = userProfile;
	}

	

		
	
}
