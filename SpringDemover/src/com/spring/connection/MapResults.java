package com.spring.connection;

import java.util.ArrayList;
import java.util.List;

/**
 * Java Class for creating bean with the current sql query results 
 * 
 * @author Elias Keramefs
 *
 */
public class MapResults {
	// Results Model 
	private List<String> mapResultsofQuery;
	
	private List<String> mapResultsofForm;
	
	private int numberofColumns;
	
	private int numberofList;

	// Contructor
	public MapResults(){
		mapResultsofQuery = new ArrayList<String>();
		mapResultsofForm = new ArrayList<String>();
		numberofColumns = 0;
		numberofList = 0;
	}
	
	// setter with results of query
	public void setMapResultsofQuery(List<String> mapResultsofQuery) {
		this.mapResultsofQuery = mapResultsofQuery;
	}
		
	// getter with results of query
	public List<String> getMapResultsofQuery() {
		return mapResultsofQuery;
	}
	
	// setter with results into the form state
	public void setMapResultsofForm(List<String> mapResultsofForm) {
		this.mapResultsofForm = mapResultsofForm;
	}
	
	// getter with results for the form format
	public List<String> getMapResultsofForm() {
		return mapResultsofForm;
	}
	
	// setter with the number of Columns in our query results 
	public void setNumberofColumns(int numberofColumns) {
		this.numberofColumns = numberofColumns;
	}

	//getter with the number of Columns in our query results 
	public int getNumberofColumns() {
		return numberofColumns;
	}
	
	// setter with the number in the List of results
	public void setNumberofList(int numberofList) {
		this.numberofList = numberofList;
	}
	
	// getter with the number in the List of results
	public int getNumberofList() {
		return numberofList;
	}


}
