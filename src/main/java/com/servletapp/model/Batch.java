package com.servletapp.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class Batch {
    private String time;
    private int count;
    private String date;
    private String batchId;
    private List<String> participants;
    private List<String> participantEmails;
    private List<BatchParticipant> batchParticipants;
    
    public Batch() {
    	super();
    }

    public Batch(String time, String date) {
    	super();
        this.time = time;
        this.date = date;
        this.count = 0;
        this.batchId = batchId;
        this.participants = participants;
        this.participantEmails = participantEmails;
    }
    
    public Batch(String time, String date, String batchId) {
    	super();
        this.time = time;
        this.date = date;
        this.count = 0;
        this.batchId = batchId;
        this.participants = participants;
        this.participantEmails = participantEmails;
    }
    
    public String getBatchId() {
    	return batchId;
    }

    public void setBatchId(String batchId) {
    	this.batchId = batchId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public void addParticipant(List<String> participantEmails) {
        participants.addAll(participantEmails);
    }

	public void setParticipants(List<BatchParticipant> emailBatchList) {
		// TODO Auto-generated method stub
		this.batchParticipants = emailBatchList;
	}
	
	public List<BatchParticipant> getBatchParticipants(){
		return batchParticipants;
	}
	
	public void setParticipantEmails(List<String> participantEmails) {
		// TODO Auto-generated method stub
		this.participantEmails = participantEmails;
		count = participantEmails.size();
	}
	
	
	public List<String> getParticipantEmails(){
		return participantEmails;
	}
	
}

