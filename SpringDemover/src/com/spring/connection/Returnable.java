package com.spring.connection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * Java Class for creating, processing and forming our query as SQL query form
 * 
 * @author Elias Keramefs
 *
 */
public class Returnable {
	// Inner Class for the SELECT from query form
	public class Selections{
		private String tableN;

		private String tableC;
		
		// Constructor
		public Selections(){
			tableN = StringUtils.EMPTY;
			tableC = StringUtils.EMPTY;
		}
		
		// setter with table name
		public void setTableN(String tableN) {
			this.tableN = tableN;
		}
		
		// getter with table name
		public String getTableN() {
			return tableN;
		}

		// setter with column name
		public void setTableC(String tableC) {
			this.tableC = tableC;
		}
		
		// getter with column name
		public String getTableC() {
			return tableC;
		}
	}
	
	private List<String> columns;
	
	private String select;
	
	private String from;
	
	private String where;
	
	private String filter;
	
	private String alternQuery;
	
	private String alternFrom;
	
	private String alternWhere;
	
	// Contructor
	public Returnable(){
		columns = new ArrayList<String>();
		filter = StringUtils.EMPTY;
	}
    
	// setter for columns 
    public void setColumns(String column){
    	columns.add(column);
    }
    
    // getter for columns
    public List<String> getColumns(){
    	return columns;
    }
    
    // remove duplicates values from String
    public String removeDuplicates(String s){
    	List<String> myList = new ArrayList<String>(Arrays.asList(s.split(", ")));
    	String result = StringUtils.EMPTY;
    	ArrayList<String> results = new ArrayList<>();
    	HashSet<String> set = new HashSet<>();
    	for(String item: myList){
    		if (!set.contains(item)) {
    			results.add(item);
    			set.add(item);
    		}
    	}
    	result = results.toString();
    	return result;
    }
    
    // remove dublicate items from List
    public List<String> removeDuplicateListItems(List<String> listStrings){
    	ArrayList<String> resultWhere = new ArrayList<>();
    	HashSet<String> set = new HashSet<>();
    	for(String item: listStrings){
    		if (!set.contains(item)) {
    			resultWhere.add(item);
    			set.add(item);
    		}
    	}
    	return resultWhere;
    }
    
    // return the operators list 
    public List<String> getOperatorForFilter(String filterFrom){
    	List<String> listFilterFrom = Arrays.asList(filterFrom.split(" "));
    	List<String> listOperators = new ArrayList<String>();
    	String s = StringUtils.EMPTY;
    	for(int a=0;a<listFilterFrom.size();a++){
    	// when filter has AND 
    			if(listFilterFrom.get(a).contains(" = ")){
    	    		if(containsDigit(listFilterFrom.get(a+1))){
    	    			a++;
    	    			listOperators.add(filter);
    	    		}
    	    		else{
    	    			s += " = '" + listFilterFrom.get(a+1) + "'";
    	    			a++;
    	    			listOperators.add(s);
    	    		}
    	    	}
    			else if(listFilterFrom.get(a).contains(" != ")){
    	    		if(containsDigit(listFilterFrom.get(a+1))){
    	    			a++;
    	    			listOperators.add(filter);
    	    		}
    	    		else{
    	    			s =  " != '" + listFilterFrom.get(a+1) + "'";
    	    			a++;
    	    			listOperators.add(s);
    	    		}
    	    	}
    			else if(listFilterFrom.get(a).contains("LIKEStart")){
    	    		s = " LIKE '" + listFilterFrom.get(a+1) + "%'";
    	    		a++;
    	    		listOperators.add(s);
    	    	}
    			else if(listFilterFrom.get(a).contains("LIKEEnd")){
    	    		s = " LIKE '%" + listFilterFrom.get(a+1) + "'";
    	    		a++;
    	    		listOperators.add(s);
    	    	}
    			else if(listFilterFrom.get(a).contains("LIKEContain")){
    	    		s = " LIKE '%" + listFilterFrom.get(a+1) + "%'";
    	    		a++;
    	    		listOperators.add(s);
    	    	}
    			else if(listFilterFrom.get(a).contains("NOTLIKEContain")){
    	    		s =  " NOT LIKE '%" + listFilterFrom.get(a+1) + "%'";
    	    		a++;
    	    		listOperators.add(s);
    	    	}
    			else if(listFilterFrom.get(a).contains("IN")){
    	    		List<String> listInFilter =Arrays.asList(listFilterFrom.get(a+1).split(","));
    	    		String s1 = "";
    	    		for(int i=0; i<listInFilter.size();i++){
    	    			if(i>0){
    	    				s1 = s1 + ", '" + listInFilter.get(i) + "'";
    	    			}
    	    			else{
    	    				s1 = s1 + "'" + listInFilter.get(i) + "'";
    	    			}
    	    		}
    	    		s = " IN (" + s1 + ")";
    	    		a++;
    	    		listOperators.add(s);
    	    	}
    	    	else{
   	    		listOperators.add(listFilterFrom.get(a));
  	    	}
    	}
    	return listOperators;
    }

    // true if String contains a Digit
    public  boolean containsDigit(String s) {
        if (s != null && !s.isEmpty()) {
            for (char c : s.toCharArray()) {
                if (Character.isDigit(c)) {
                    return true;
                }
            }
        }
        return false;
    }
    
	// setter with SELECT
	public void setSelect(String select) {
		this.select = select;
	}
	
    // getter with SELECT
	public String getSelect() {
		return select;
	}
	
	// setter with FROM
	public void setFrom(String from) {
		this.from = from;
	}

	// getter with FROM
	public String getFrom() {
		return from;
	}

	// setter with WHERE
	public void setWhere(String where) {
		this.where = where;
	}
	
	// getter with WHERE
	public String getWhere() {
		return where;
	}

	// getter with all values from SELECT
	public Selections getSelection(){
		Selections selections = new Selections();
		return selections;
	}
	
	// setter with filter
	public void setFilter(String filter) {
		this.filter = filter;
	}
	
	// getter with filter
	public String getFilter() {
		return filter;
	}

	// setter with alternative form query
	public void setAlternQuery(String alternQuery) {
		this.alternQuery = alternQuery;
	}

	// getter with alternative form query
	public String getAlternQuery() {
		return alternQuery;
	}

	// setter with alternative FROM form query
	public void setAlternFrom(String alternFrom) {
		this.alternFrom = alternFrom;
	}

	// getter with alternative FROM form query
	public String getAlternFrom() {
		return alternFrom;
	}

	// setter with alternative WHERE form query
	public void setAlternWhere(String alternWhere) {
		this.alternWhere = alternWhere;
	}

	// getter with alternative WHERE form query
	public String getAlternWhere() {
		return alternWhere;
	}

}