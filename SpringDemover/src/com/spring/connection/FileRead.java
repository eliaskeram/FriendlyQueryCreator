package com.spring.connection;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;
import org.springframework.web.context.support.ServletContextResource;

/**
 * Java Class for reading a file with user properties - username, password
 * 
 * @author Elias Keramefs
 *
 */
public class FileRead {
	private File file;
	
	private List<String> listuUsername = new ArrayList<String>();
	
	private List<String> listPassword = new ArrayList<String>();
	
	private Map<String,String> listMapUser = new HashMap<String,String>();
	
	private  List<String> listDatabasesname = new ArrayList<String>();
	
	// Constructor
	public FileRead(){}
	
	// set username and password in web application
	public void readNamePassword(ServletContext servletContext){
		Properties properties = new Properties();
		InputStream inputFile = null;
		try {
			ServletContextResource resource = new ServletContextResource(servletContext, 
				    "/WEB-INF/config.properties");
			inputFile = resource.getInputStream();
			properties.load(inputFile);
			String userName = properties.getProperty("dbuser");
			String[] splitname = userName.split("\\;");
			
			for (int i=0; i < splitname.length; i++) {
				listuUsername.add(splitname[i]);
			  }
			
			if (properties.getProperty("dbpassword") != null) {
				String password = properties.getProperty("dbpassword");
				String[] splitpass = password.split("\\;");
				for (int i=0; i < splitpass.length; i++) {
					listPassword.add(splitpass[i]);
				  }
			   }
		   for(int i=0;i<listuUsername.size();i++){
			   listMapUser.put(listuUsername.get(i), listPassword.get(i));
		   }

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (inputFile != null) {
				try {
					inputFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				  }
				}
			}
	}
	
	// read path within the web application
	public void readNameDatabases(ServletContext servletContext){
		Properties properties = new Properties();
		InputStream inputDatabases = null;
		try {
			ServletContextResource resource = new ServletContextResource(servletContext, 
				    "/WEB-INF/server.properties");
			inputDatabases = resource.getInputStream();
			properties.load(inputDatabases);
			String userName = properties.getProperty("dBaseName");
			String[] splitname = userName.split("\\;");
			
			for (int i=0; i < splitname.length; i++) {
				listDatabasesname.add(splitname[i]);
			  }

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (inputDatabases != null) {
				try {
					inputDatabases.close();
				} catch (IOException e) {
					e.printStackTrace();
				  }
				}
			}
	}
	
	// get with the current File.class 
	public File getFile() {
		return file;
	}
	
	// get with usernames 
	public List<String> getListUsername() {
		return listuUsername;
	}
	
	// get with passwords
	public List<String> getListPassword() {
		return listPassword;
	}

    // get with Mapper <Username, Password> 	
	public Map<String,String> getListMapUser() {
		return listMapUser;
	}

	// get with list of databases in our mysql server
	public List<String> getListDatabasesname() {
		return listDatabasesname;
	}
}
