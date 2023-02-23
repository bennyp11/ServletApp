package com.servletapp.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.servletapp.model.Batch;
import com.servletapp.model.BatchParticipant;
import com.servletapp.model.Participant;
import com.servletapp.utility.DB;

public class BatchDAO {
	DB db = new DB();
	
	public List<Batch> getAll() {
	    db.init();
	    List<Batch> batchList = new ArrayList<>();

	    try {
	        String sql = "select * from java.batch";
	        ResultSet set = db.executeQuery(sql);

	        while (set.next()) {
	            Batch batch = new Batch();
	            batch.setBatchId(set.getString("id"));
	            batch.setDate(set.getString("date"));
	            batch.setTime(set.getString("time"));
	            batch.setCount(set.getInt("count"));

	            batchList.add(batch);
	        }
	        System.out.println(batchList);

	        // Load the email addresses from the second SQL query into a list
	        String sql2 = "select * from java.batch_participants";
	        ResultSet set2 = db.executeQuery(sql2);
	        List<BatchParticipant> emailBatchList = new ArrayList<>();
	        while (set2.next()) {
	            String batchId = set2.getString("batch_id");
	            String participantEmail = set2.getString("participant_email");
	            BatchParticipant batchParticipant = new BatchParticipant(batchId, participantEmail);
	            System.out.println("BATCH ID IN SET2: " + batchId);
	            System.out.println("EMAIL IN SET2: " + participantEmail);
	            emailBatchList.add(batchParticipant);
	        }

	        // Create an Executor with a thread pool size of 2
	        Executor executor = Executors.newFixedThreadPool(2);

	        // Submit the task to the executor to add participants to each batch
	        for (Batch batch : batchList) {
	            Future<Void> future = ((ExecutorService) executor).submit(() -> {
	            	String idChecker = batch.getBatchId();
	            	System.out.println("IDCHECKER1: "+idChecker);
	            	List<String> participantEmails = new ArrayList<>();
	            	for (BatchParticipant batchParticipant: emailBatchList) {
	            		String idChecker2 = batchParticipant.getBatchId();
	            		System.out.println("IDCHECKER2: "+idChecker2);
	            		if(idChecker.equals(idChecker2)) {
	            			String participantEmail = batchParticipant.getParticipantEmail();
	            			System.out.println("BATCH IDS MATCHED, HERE IS THE EMAIL: " + participantEmail);
	            			participantEmails.add(participantEmail);
	            		}
	            	}
	            	
	                batch.setParticipantEmails(participantEmails);
	                List<String> demEmails = batch.getParticipantEmails();
	                System.out.println("HERE LIES THE EMAILS FOR THE CURRENT BATCH: "+demEmails);
	                return null;
	            });

	            // Wait for the task to complete
	            try {
	                future.get();
	            } catch (InterruptedException | ExecutionException e) {
	                e.printStackTrace();
	            }
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("Error when getting all batches: " + e);
	    } finally {
	        db.destroy();
	    }
	    

	    return batchList;
	}

	public int save(Batch obj) {
		db.init();
		int rowsAffected = 0;
		try {
			String sql = "insert into java.batch(time, count, date) values('" + obj.getTime() + "', '" + obj.getCount() + "', '" + obj.getDate() +"')";
			rowsAffected = db.executeUpdate(sql);
			String message = (rowsAffected > 0) ? "Update made to batch" : "Unable to make update to batch";
			System.out.println(message);
			
			String batch_id = null;
			String sql2 = "select id from java.batch where time = '" + obj.getTime() + "' AND date = '" + obj.getDate() + "'";
			ResultSet rs = db.executeQuery(sql2);
			if (rs.next()) {
			    batch_id = rs.getString("id");
			    System.out.println("UUID of inserted batch: " + batch_id);
			}
			List<String> participantJson = obj.getParticipants();
			for (String participantEmail : participantJson) {
				String sql3 = "insert into java.batch_participants(batch_id, participant_email) values('" + batch_id + "','" + participantEmail + "')";
				rowsAffected = db.executeUpdate(sql3);
				String message2 = (rowsAffected > 0) ? "Update made to batch_participants" : "Unable to make update to batch_participants";
				System.out.println(message2);
			}
			
		} catch (Exception e) {
			System.out.println("Error updating batch: " + e);
		}
		return rowsAffected;
	}
	
	public int update(Batch obj, List<String> participantEmails) {
		db.init();
		int rowsAffected = 0;
		try {
			String sql = "update java.batch set date = '" + obj.getDate() + "', time = '" + obj.getTime() + "' where id = '" + obj.getBatchId() + "'";
			rowsAffected = db.executeUpdate(sql);
			String message = (rowsAffected > 0) ? "Batch updated successfully" : "Unable to update Batch";
			System.out.println(message);
			String sql2 = "DELETE FROM java.batch_participants WHERE batch_id = '"+ obj.getBatchId() +"'";
			rowsAffected = db.executeUpdate(sql2);
			String message2 = (rowsAffected > 0) ? "Batch participants table cleaned successfully" : "Unable to clean batch participants for new additons";
			System.out.println(message2);
			for(String email : participantEmails ) {
				String sql3 = "insert into java.batch_participants(batch_id, participant_email) values('" + obj.getBatchId() + "','" + email + "')";
				rowsAffected = db.executeUpdate(sql3);
				String message3 = (rowsAffected > 0) ? "Batch participants updated successfully" : "Unable to update Batch participants";
				System.out.println(message3);
			}
		} catch (Exception e) {
			System.out.println("Exception is: " + e);
		}
		return rowsAffected;
	}
	
	public int delete(String batchId) {
		db.init();
		int rowsAffected = 0;
		try {
			String sql = "delete from java.batch where id = '" + batchId + "'";
			rowsAffected = db.executeUpdate(sql);
			String message = (rowsAffected > 0) ? "Batch id deleted" : "Batch cannot be deleted";
			System.out.println(message);
		} catch (Exception e) {
			System.out.println("Exception is: " + e);
		}
		return rowsAffected;
	}

}