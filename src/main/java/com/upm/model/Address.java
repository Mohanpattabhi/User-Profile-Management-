package com.upm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "ADDRESS")
public class Address {

	/*@Id
	@Column(name = "ADDRESS_ID", nullable = false)*/
	@Id
	@SequenceGenerator(name = "UPMSEQ", sequenceName = "UPMSEQ", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UPMSEQ")
	@Column(name = "ADDRESS_ID", nullable = false)
	private Integer addressId;

	@Column(name = "HOUSE_NO")
	private String houseNumber;

	@Column(name = "STREET")
	private String street;

	@Column(name = "CITY")
	private String city;

	@Column(name = "COUNTRY")
	private String country;
	
	@ManyToOne
    @JoinColumn(name="LOGIN_ID")
	private User user;

	
	
	@Column(name = "STATE_ID")
	private Integer stateId;
	


	public Integer getAddressId() {
		return addressId;
	}

	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public User ad() {
		return user;
	}

	public void addUser(User user) {
		  this.user= user;
		  this.user.getAddresses().add(this);
	}



}
