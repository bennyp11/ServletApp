package com.servletapp.controller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mysql.cj.xdevapi.JsonArray;
import com.servletapp.dao.BatchDAO;
import com.servletapp.model.Batch;
import com.servletapp.utility.Response;

/**
 * Servlet implementation class BatchServlet
 */
public class BatchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BatchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    BatchDAO batchDAO = new BatchDAO();
    Response responseDto = new Response();

	/**
	 * @see Servlet#init(ServletConfig)
	 */
    @Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		System.out.println("BatchServlet - init executed!");
	}

	/**
	 * @see Servlet#destroy()
	 */

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String queryParamGet = request.getParameter("getbatchparticipants");
		if(queryParamGet != null) {
			List<Batch> batchList = new ArrayList<Batch>();
			batchList = batchDAO.getAll();
			System.out.println(batchList);
			String queryString = "batchList=" + URLEncoder.encode(new Gson().toJson(batchList), "UTF-8");
			response.sendRedirect("/ServletApp/viewparticipants.jsp?" + queryString);

		} else {
			List<Batch> batchList = new ArrayList<Batch>();
			batchList = batchDAO.getAll();
			String jsonResponse = new Gson().toJson(batchList);
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			out.print(jsonResponse);
			out.flush();
		}
		// TODO Auto-generated method stub
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
  	  
  	  // Get the values from the JSON object
  	  String time = jsonObject.get("time").getAsString();
  	  String date = jsonObject.get("date").getAsString();
  	  com.google.gson.JsonArray participants = jsonObject.getAsJsonArray("participants");
  	  try {
  		  Batch batch = new Batch(time, date);
 
	  		System.out.println("PARTICIPANTS IN BATCH TO BE LOADED:");
	  		List<String> participantEmails = new CopyOnWriteArrayList<>();
	  		for (JsonElement participantEmail : participants) {
	  			String email = participantEmail.getAsString();
	  		    participantEmails.add(email);
	  		    System.out.println(participantEmail);
	  		}
	  		batch.setParticipantEmails(participantEmails);
			System.out.println("TIME FOR BATCH: " + time);
			System.out.println("DATE FOR BATCH: " + date);
			
  		  batchDAO.save(batch);
  		  responseDto.setStatus("Success");
  		  responseDto.setMessage("Batch inserted!");
  		  System.out.println("Batch inserted successfully!");
  	  } catch (Exception e) {
  		e.printStackTrace();
		responseDto.setStatus("Failed");
        responseDto.setMessage("Batch inserted FAILED!");
  	  }
	  	String jsonResponse = new Gson().toJson(responseDto);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		out.print(jsonResponse);
		out.flush();
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		InputStream inputStream = request.getInputStream();
  	  
  	  // Parse the JSON object using Gson
  	  Gson gson = new Gson();
  	  Reader reader = new InputStreamReader(inputStream, "UTF-8");
  	  JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
  	  String batchId = jsonObject.get("batchId").getAsString();
  	  String date = jsonObject.get("date").getAsString();
  	  String time = jsonObject.get("time").getAsString();
  	  System.out.println("RECEIVED 'DATE' IN PUT REQUEST: "+date);
  	  System.out.println("RECEIVED TIME IN PUT REQUEST: "+time);
  	  System.out.println("JSON OBJECT IS RIGHT HERE: "+jsonObject);
  	  com.google.gson.JsonArray participants = jsonObject.getAsJsonArray("participantEmails");
  	  List<String> participantEmails = new CopyOnWriteArrayList<>();
  	  for (JsonElement participantEmail : participants) {
			String email = participantEmail.getAsString();
		    participantEmails.add(email);
		    System.out.println(participantEmail);
		}
  	  try {
  		  Batch batch = new Batch(time, date, batchId);
  		  batchDAO.update(batch, participantEmails);
  		  responseDto.setStatus("Success");
  		  responseDto.setMessage("Batch is updated successfully!");
  	  } catch (Exception e) {
  		  responseDto.setStatus("Failed");
  		  responseDto.setMessage("Failed updated batch data");
  	  }
  	  String jsonResponse = new Gson().toJson(responseDto);
	  PrintWriter out = response.getWriter();
	  response.setContentType("application/json");
	  response.setCharacterEncoding("UTF-8");
	  out.print(jsonResponse);
	  out.flush();
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		InputStream inputStream = request.getInputStream();
  	  
  	  // Parse the JSON object using Gson
  	  Gson gson = new Gson();
  	  Reader reader = new InputStreamReader(inputStream, "UTF-8");
  	  JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
  	  
  	  // Get the values from the JSON object
  	  String batchId = jsonObject.get("batchId").getAsString();
  	  System.out.println("DELETE OPERATION BATCHID CHECK THIS OUTTTTTTTTTTTT:"+batchId);

  	  try {
  	    batchDAO.delete(batchId);
  	    responseDto.setStatus("Success");
  	    responseDto.setMessage("Batch is deleted!");
  	  } catch (Exception e) {
  	    responseDto.setStatus("Failed");
  	    responseDto.setMessage("Failed to delete batch data");
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
		System.out.println("BatchServlet - destroy executed!");
		// TODO Auto-generated method stub
	}

}
