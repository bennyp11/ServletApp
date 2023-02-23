package com.servletapp.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.servletapp.model.Participant;
import com.servletapp.utility.DAO;
import com.servletapp.utility.DB;

public class ParticipantDAO implements DAO<Participant>{
	DB db = new DB();
	
	@Override
	public List<Participant> getAll(){
		db.init();
		List<Participant> participantList = new ArrayList<Participant>();
		try {
			String sql = "select * from java.participants";
			ResultSet set = db.executeQuery(sql);
			while(set.next()) {
				Participant participant = new Participant();
				participant.setFirstName(set.getString("firstName"));
				participant.setLastName(set.getString("lastName"));
				participant.setEmail(set.getString("email"));
				participantList.add(participant);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error when getting all participants: " + e);
		} finally {
			db.destroy();
		}
		return participantList;
	}
	
	@Override
	public Participant getOne(String email) {
		db.init();
		Participant participant = new Participant();
		try {
			String sql = "select * from java.participants where email = " + email;
			ResultSet set = db.executeQuery(sql);
			if(set.next()) {
				participant.setFirstName(set.getString("firstName"));
				participant.setLastName(set.getString("lastName"));
				participant.setEmail(set.getString("email"));
			}
		} catch (Exception e) {
			System.out.println("Error when getting one participant: " + e);
		}
		return participant;
	}
	
	@Override 
	public int save(Participant obj) {
		db.init();
		int rowsAffected = 0;
		try {
			String sql = "insert into java.participants(firstName, lastName, email) values('" + obj.getFirstName() + "', '" + obj.getLastName() + "', '" + obj.getEmail() + "')";
			rowsAffected = db.executeUpdate(sql);
			String message = (rowsAffected > 0) ? "Update made to participants" : "Unable to make update";
			System.out.println(message);
		} catch (Exception e) {
			System.out.println("Error updating participants: " + e);
		}
		return rowsAffected;
	}
	
	@Override
	public int update(Participant obj) {
		db.init();
		int rowsAffected = 0;
		try {
			String sql = "update java.participants set firstName = '" + obj.getFirstName() + "', lastName = '" + obj.getLastName() + "', email = '" + obj.getEmail() + "' where email = '" + obj.getEmail() + "'";
			rowsAffected = db.executeUpdate(sql);
			String message = (rowsAffected > 0) ? "Participant updated successfully" : "Unable to update Participant";
			System.out.println(message);
		} catch (Exception e) {
			System.out.println("Exception is: " + e);
		}
		return rowsAffected;
	}
	
	@Override
	public int delete(String email) {
		db.init();
		int rowsAffected = 0;
		try {
			String sql = "delete from java.participants where email = '" + email + "'";
			rowsAffected = db.executeUpdate(sql);
			String message = (rowsAffected > 0) ? "Participant id deleted" : "Participant cannot be deleted";
			System.out.println(message);
		} catch (Exception e) {
			System.out.println("Exception is: " + e);
		}
		return rowsAffected;
	}
}
