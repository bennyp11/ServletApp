package com.servletapp.model;

public class Participant {
	private String firstName;
	private String lastName;
	private String email;
	private long id;
	
	public Participant() {
		super();
	}
	
	public Participant(String firstName, String lastName, String email) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.id = id;
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
	
	public long getParticipantId() {
		return id;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		return "Participant [firstName=" + firstName +", lastName=" + lastName + ", email=" + email + ", id=" + id + "]";
	}
	
}
