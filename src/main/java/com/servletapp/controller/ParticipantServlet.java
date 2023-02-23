package com.servletapp.controller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.servletapp.dao.ParticipantDAO;
import com.servletapp.model.Participant;
import com.servletapp.utility.DB;
import com.servletapp.utility.Response;

public class ParticipantServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ParticipantServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    ParticipantDAO participantDAO = new ParticipantDAO();
    Response responseDto = new Response();
    
    @Override
    public void init(ServletConfig config) throws ServletException {
    	super.init(config);
    	System.out.println("ParticipantServlet - init was executed!");
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	List<Participant> participantList = new ArrayList<Participant>();
    	participantList = participantDAO.getAll();
    	String jsonResponse = new Gson().toJson(participantList);
    	PrintWriter out = response.getWriter();
    	response.setContentType("application/json");
    	response.setCharacterEncoding("UTF-8");
    	out.print(jsonResponse);
    	out.flush();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    	InputStream inputStream = request.getInputStream();
    	  
    	  // Parse the JSON object using Gson
    	  Gson gson = new Gson();
    	  Reader reader = new InputStreamReader(inputStream, "UTF-8");
    	  JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
    	  
    	String firstName = jsonObject.get("firstName").getAsString();
        String lastName = jsonObject.get("lastName").getAsString();
        String email = jsonObject.get("email").getAsString();
        System.out.println(email + firstName + lastName);

				try {
					Participant participant = new Participant(firstName, lastName, email);
		            participantDAO.save(participant);
		            responseDto.setStatus("Success");
		            responseDto.setMessage("Participant inserted!");
		            System.out.println("Participant inserted successfully!");
				} catch (Exception e) {
					e.printStackTrace();
					responseDto.setStatus("Failed");
		            responseDto.setMessage("Participant inserted FAILED!");
				}
				String jsonResponse = new Gson().toJson(responseDto);
				PrintWriter out = response.getWriter();
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				out.print(jsonResponse);
				out.flush();
        // ... the rest of the code remains the same
    }
    
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
    	    throws ServletException, IOException {
    	  // Get the request body as an InputStream
    	  InputStream inputStream = request.getInputStream();
    	  
    	  // Parse the JSON object using Gson
    	  Gson gson = new Gson();
    	  Reader reader = new InputStreamReader(inputStream, "UTF-8");
    	  JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
    	  
    	  // Get the values from the JSON object
    	  String email = jsonObject.get("email").getAsString();
    	  String firstName = jsonObject.get("firstName").getAsString();
    	  String lastName = jsonObject.get("lastName").getAsString();

    	  try {
    	    Participant participant = new Participant(firstName, lastName, email);
    	    participantDAO.update(participant);
    	    responseDto.setStatus("Success");
    	    responseDto.setMessage("Participant is updated successfully!");
    	  } catch (Exception e) {
    	    responseDto.setStatus("Failed");
    	    responseDto.setMessage("Failed updated participant data");
    	  }
    	  
    	  String jsonResponse = new Gson().toJson(responseDto);
    	  PrintWriter out = response.getWriter();
    	  response.setContentType("application/json");
    	  response.setCharacterEncoding("UTF-8");
    	  out.print(jsonResponse);
    	  out.flush();
    	}
    
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
    	    throws ServletException, IOException {
    	  // Get the request body as an InputStream
    	  InputStream inputStream = request.getInputStream();
    	  
    	  // Parse the JSON object using Gson
    	  Gson gson = new Gson();
    	  Reader reader = new InputStreamReader(inputStream, "UTF-8");
    	  JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
    	  
    	  // Get the values from the JSON object
    	  String email = jsonObject.get("email").getAsString();

    	  try {
    	    participantDAO.delete(email);
    	    responseDto.setStatus("Success");
    	    responseDto.setMessage("Participant is deleted!");
    	  } catch (Exception e) {
    	    responseDto.setStatus("Failed");
    	    responseDto.setMessage("Failed to delete participant data");
    	  }
    	  
    	  String jsonResponse = new Gson().toJson(responseDto);
    	  PrintWriter out = response.getWriter();
    	  response.setContentType("application/json");
    	  response.setCharacterEncoding("UTF-8");
    	  out.print(jsonResponse);
    	  out.flush();
    	}
	
	@Override
	public void destroy() {
		super.destroy();
		System.out.println("ParticipantServlet - destroy executed!");
	}

}
