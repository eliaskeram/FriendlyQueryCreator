package com.spring.connection;

import org.apache.commons.lang3.StringUtils;

/**
 * Java Class for creating bean with the current user in sql server
 * 
 * @author Elias Keramefs
 *
 */
public class Loginbean {
	// Login Model Creation
	private String userName;
	
	private String password;
	
	// Constructor
	public Loginbean(){
		userName = StringUtils.EMPTY;
		password = StringUtils.EMPTY;
	}
	
	// setter with userName
	public void setUserName(String userName) {
		this.userName = userName;
	}

	// getter with userName
	public String getUserName() {
		return userName;
	}
	
	// setter with password
	public void setPassword(String password) {
	    this.password = password;
	}
	
	// getter with password
	public String getPassword() {
	    return password;
	}
	
}
