package com.servletapp.model;

public class Participant {
	private String firstName;
	private String lastName;
	
	
	public Participant() {
		super();
	}
	
	public Participant(String firstName, String lastName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;		
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Override
	public String toString() {
		return "Participant [firstName=" + firstName +", lastName=" + lastName + "]";
	}
	
}
