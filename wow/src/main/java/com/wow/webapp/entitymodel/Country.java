package com.wow.webapp.entitymodel;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="countries")
public class Country {
		
	@Id
	@Column(name="country_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int country_id;
	
	@Column(name="name", unique = true ,length=100, nullable=false)
	private String name;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "country", cascade = {CascadeType.ALL})
	private Set<State> states = new HashSet<State>(0);

	
	
	public int getCountry_id() {
		return country_id;
	}

	public void setCountry_id(int country_id) {
		this.country_id = country_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<State> getStates() {
		return states;
	}

	public void setStates(Set<State> states) {
		this.states = states;
	}

	
	

}
