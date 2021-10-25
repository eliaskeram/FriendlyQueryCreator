package com.spring.connection;

import java.sql.DriverManager;  

import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;  

/**
 * Java Class for creating connection between server and sql database 
 * 
 * @author Elias Keramefs
 *
 */
public class CreateConnection {
	
	// defining driver name, MYSQL-JDBC driver in this case  
	 String driverName = "com.mysql.jdbc.Driver";  
	 Connection con = null; 
	 String database = StringUtils.EMPTY;
	  
	 // database URL string with HOSTNAME and port on which DB is running  
	 String url = "jdbc:mysql:";
	
	 // Constructor 
	 public CreateConnection(){};
	 
	 // gettter with the current Connection.class
	 public Connection getConnection(String dbName, String username, String password) {  
	  // creating connection URL  
	  String connectionUrl = url + dbName;  
	  database = dbName;
	  
	  try {  
	   // registers the specified driver class into memory  
	   Class.forName(driverName);  
	  } catch (ClassNotFoundException e) {e.printStackTrace();}  
	  
	  try {  
	   // returns a connection object by selecting an appropriate driver  
	   // for specified connection URL
	   con = DriverManager.getConnection(connectionUrl, username, password);  
	  } catch (SQLException e) {e.printStackTrace();}  
	  
	  return con;  
	  
	 } 
	 
	 // getter with Database name
	 public String getdatabase(){
		 return  database;
	 }
	 
}
