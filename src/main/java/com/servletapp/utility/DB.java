package com.servletapp.utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

public class DB {

    private static final Logger log;

    static {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%4$-7s] %5$s %n");
        log =Logger.getLogger(DB.class.getName());
    }

    public static Connection makeConnection() throws Exception {
        Connection connection=null;
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
        	connection = DriverManager.getConnection();
        } catch (Exception e) {
        	e.printStackTrace();
        }
		return connection;
    }

}