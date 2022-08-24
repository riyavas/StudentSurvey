package com.entity;

import jakarta.persistence.*;

/**
 * This class is a JPA Entity class which defines the fields 
 * of a Survey object that is to be mapped into a single row 
 * in the database.
 * 
 *
 */
@Entity
public class Survey {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	// DATE 
	private String date;
	@Column(nullable=false)
	public String getDate() {
		return this.date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	// FIRST NAME
	private String fname;
	@Column(nullable=false)
	public String getFirstName() {
		return this.fname;
	}
	public void setFirstName(String fname) {
		this.fname = fname;
	}
	
	// LAST NAME
	private String lname;
	@Column(nullable=false)
	public String getLastName() {
		return this.lname;
	}
	public void setLastName(String lname) {
		this.lname = lname;
	}
	// STREET 
	private String street;
	@Column(nullable=false)
	public String getStreetName() {
		return this.street;
	}
	public void setStreetName(String street) {
		this.street = street;
	}
	
	// CITY
	private String city;
	@Column(nullable=false)
	public String getCity() {
		return this.city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	// STATE
	private String state;
	@Column(nullable=false)
	public String getState() {
		return this.state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	// ZIP CODE
	private String zip;
	@Column(nullable=false)
	public String getZipCode() {
		return this.zip;
	}
	public void setZipCode(String zip) {
		this.zip = zip;
	}
	
	// PHONE NUMBER 
	private String pnumber;
	@Column(nullable=false)
	public String getPhoneNumber() {
		return this.pnumber;
	}
	public void setPhoneNumber(String pnumber) {
		this.pnumber = pnumber;
	}
	
	// EMAIL
	private String email;
	@Column(nullable=false)
	public String getEmail() {
		return this.email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	/* OPTIONAL DATA */
	
	// CHECKED BOXES 
	private String checkboxes;
	public String getCheckedBoxes() {
		return this.checkboxes;
	}
	public void setCheckedBoxes(String checkboxes) {
		this.checkboxes = checkboxes;
	}
	
	// CHOICE 
	private String choice;
	public String getChoice() {
		return this.choice;
	}
	public void setChoice(String choice) {
		this.choice = choice;
	}
	
	// RECOMMENDATION
	private String recommendation;
	public String getRecommendation() {
		return this.recommendation;
	}
	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}
	
	@Override
	public String toString() {
		return "Survey [ fname =" + this.fname + 
				" lname = " + this.lname + 
				" date = " + this.date +
				" street = " + this.street +
				" city = " + this.city +
				" state = " + this.state +
				" zip = " + this.zip +
				" email = " + this.email +
				" phone number = " + this.pnumber +
				" checkboxes = " + this.checkboxes +
				" choice = " + this.choice +
				" recommendation = " + this.recommendation + "]";
	}

}
