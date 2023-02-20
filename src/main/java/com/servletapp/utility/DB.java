package com.servletapp.utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

public class DB {

    private static final Logger log;
    private Connection connection;
    private Statement statement;

    static {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%4$-7s] %5$s %n");
        log =Logger.getLogger(DB.class.getName());
    }

    public void init() {
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
        	connection = DriverManager.getConnection("xxx");
        	statement = connection.createStatement();
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }
    
    public void destroy() {
    	try {
    		if(connection != null) {
    			connection.close();
    			connection = null;
    			
    		}
    		System.out.println("--JDBC Driver Loaded--");
    	} catch (Exception e) {
    		System.out.println("--Error Loading Driver: " + e);
    	}
    }
    
    public Connection getConnection() {
    	return connection;
    }
    
    public ResultSet executeQuery(String sql) {
    	ResultSet rs = null;
    	try {
    		System.out.println("--Executing SQL--");
    		rs = statement.executeQuery(sql);
    		System.out.println("--Query SUCCESSFULLY Executed--");
    	} catch (Exception e) {
    		System.out.println("--Error Executing Query: " + e);
    	}
    	return rs;
    }
    
    public int executeUpdate(String sql) {
		int result =0;
		try {
			System.out.println("-- Executing SQL --"+sql);
			result = statement.executeUpdate(sql);
			System.out.println("-- Query Executed successfully --");
		} catch (Exception e) {
			System.out.println("-- Something Went Wrong: -- "+e);
		}
		return result;
	}

}