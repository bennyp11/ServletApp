package com.servletapp.controller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import com.servletapp.model.Participant;
import com.servletapp.utility.DB;

public class ParticipantServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ParticipantServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void init(ServletConfig config) throws ServletException {
    	super.init(config);
    	System.out.println("ParticipantServlet - init was executed!");
    }
    

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");

        Connection connection = null;
            try {
				connection = DB.makeConnection();
				try {
					Participant participant = new Participant(firstName, lastName);
		            insertData(participant, connection);
		            System.out.println("Participant inserted successfully!");
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
		            if (connection != null) {
		            	try {
		                    connection.close();
		                } catch (SQLException e) {
		                    e.printStackTrace();
		                }
		            }
			}} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

        // ... the rest of the code remains the same
    }

	
    private static void insertData(Participant participant, Connection connection) throws SQLException {
        PreparedStatement insertStatement = connection
                .prepareStatement("INSERT INTO java.participants (firstName, lastName) VALUES (?, ?);");

        insertStatement.setString(1, participant.getFirstName());
        insertStatement.setString(2, participant.getLastName());
        insertStatement.executeUpdate();
    }
	
	@Override
	public void destroy() {
		super.destroy();
		System.out.println("ParticipantServlet - destroy executed!");
	}

}
