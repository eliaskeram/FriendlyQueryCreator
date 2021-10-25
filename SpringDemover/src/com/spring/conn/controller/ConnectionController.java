package com.spring.conn.controller;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.connection.CreateConnection;
import com.spring.connection.FileRead;
import com.spring.connection.Loginbean;
import com.spring.connection.MapResults;
import com.spring.connection.Returnable;
import com.spring.connection.Returnable.Selections;
import com.spring.connection.TableKeys;
import com.spring.connection.TableKeys.MapperTables;

/**
 * Controller for MVC System Model, interface between Model and View element
 * 
 * @author Elias Keramefs
 *
 */
@Controller
public class ConnectionController {
	// Global variables for my Controller
	@Autowired
	CreateConnection createConnection;
	Connection connection1;
	FileRead fileRead;
	String fileNameServer;
	
	List<String>infoTable;
	List<String> keys;
	String message;
	String fileName;
	List<String> sizeofT;
	List<String> columnT;
	List<String> listDatabases;
	TableKeys tableKeys;
	Loginbean loginBean;
	Returnable returnableInit;
	Returnable returnResults;
	List<String> mapResultsofQuery;
	List<String> mapResultsofForm;
	int numberofColumns;
	int numberofList;
	String selectGlobal;
	String fromGlobal;
	String whereGlobal;
	String alternQueryGlobal;
	String alternFromGlobal;
	String alternWhereGlobal;
	
	MapResults mapResults;
	
	private @Autowired ServletContext servletContext;
	
	/* 1st Page for web application */
	
	// 1. Create Login Form
	@RequestMapping(value = "/")
	public String init(ModelMap model) {
		loginBean = new Loginbean();
	    model.addAttribute("loginBean", loginBean);
	    return "loginPage";
	}
	
	// 2. Send to upload Database Page
	@RequestMapping(value = "/loginPage", method = RequestMethod.POST)
	public String submit(ModelMap model, @ModelAttribute("loginBean") Loginbean loginBean2) {
		loginBean = loginBean2;
		fileRead = new FileRead();
		fileRead.readNamePassword(servletContext);
		Map<String,String> listMapUsers = fileRead.getListMapUser();
		boolean findUser = false;
		if (loginBean2 != null && loginBean2.getUserName() != null & loginBean2.getPassword() != null) {
			for(Entry<String, String> entry : listMapUsers.entrySet()){
				if(loginBean2.getUserName().equals(entry.getKey()) && loginBean2.getPassword().equals(entry.getValue())){
					findUser = true;
				}
			}
	        
			if (findUser) {
				return "redirect:fileUpload";
	        }
			else {
	                model.addAttribute("error", "Invalid Details");
	                return "loginPage";
	          }
	      }
		else {
	            model.addAttribute("error", "Please enter Details");
	            return "loginPage";
	        }
	}
	
	/* 2nd Page for web application */
	
	// 3. Initialize FileUpload Page 
	@RequestMapping(value="/fileUpload", method = RequestMethod.GET)
	public String setFile(ModelMap model2) {
		message = "Connection Server Successfully!!";
		fileName = "File Name";
		// a. Read list all Databases of the server 
		fileRead.readNameDatabases(servletContext);
		listDatabases = new ArrayList<String>();
		String username = loginBean.getUserName();
		String password = loginBean.getPassword();
		fileNameServer = fileRead.getListDatabasesname().get(0);
		Connection conAll = createConnection.getConnection(fileNameServer, username, password);
		try {
			DatabaseMetaData metaAll = conAll.getMetaData();
			ResultSet resAll = metaAll.getCatalogs();
			while (resAll.next()) {
				// b. Table Catalog
				   String dbs=resAll.getString("TABLE_CAT");
				   listDatabases.add(dbs);
				}
			resAll.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// c. Set variables to model
		model2.addAttribute("name", message);
		model2.addAttribute("fileName", fileName);
		model2.addAttribute("listDatabases", listDatabases);
		model2.addAttribute("fileServer", fileNameServer);
		return "fileUpload";
	}
	
	// 4. Create Connection to JDBC
	@RequestMapping(value="/fileUpload", method = RequestMethod.POST)
	public String submitDatabaseConnection(ModelMap model, @ModelAttribute("fileNameS") String fileData){
		 /* mysql://localhost:3306/ */
		String username = loginBean.getUserName();
		String password = loginBean.getPassword();
		connection1 = createConnection.getConnection(fileData, username, password);
		if (connection1 == null){
			model.addAttribute("name", message);
			model.addAttribute("fileName", fileName);
			model.addAttribute("error", "Invalid File Name");
			model.addAttribute("listDatabases", listDatabases);
			model.addAttribute("fileServer", fileNameServer);
			return "fileUpload";
		}
		else{
			// 1. Session keep all standard variables during our session
			String table = " ";
			String columns = " ";
		    infoTable = new ArrayList<String>();
			sizeofT = new ArrayList<String>();
			columnT= new ArrayList<String>();
			returnableInit = new Returnable();
			
			String catalog = null;
			String schema = null;
			String tableName = null;
			String columnName = null;
			String[] types = null;
			
			  if(connection1!=null){
				  System.out.println(message);
				  			  
				  try {
					// 2. Process the result set
					  // 2.a. Get the name of Tables
					  DatabaseMetaData databaseMetaData = connection1.getMetaData();
					  ResultSet res = databaseMetaData.getTables(catalog,schema,tableName,types);
					  System.out.print("Tables: ");
					  while(res.next()){
						  table = res.getString("TABLE_NAME");
						  infoTable.add(table.toUpperCase());
						  System.out.println(table);
						  System.out.println("Columns:");
						  // 2.b. Get the name of Columns
						  ResultSet resu1 = databaseMetaData.getColumns(catalog, schema,table,columnName);
						  int i = 0;
						  while(resu1.next()){
							  columns = resu1.getString("COLUMN_NAME");
							  System.out.println(columns);
							  columnT.add(columns);
							  String tableColumn = table.toUpperCase() + "." + columns;
							  returnableInit.setColumns(tableColumn);
							  i++;
						  }
						  sizeofT.add(Integer.toString(i));
						  System.out.println(sizeofT);
					  }
					  
					  // 3. Get foreign keys and set to tableKeys
					  tableKeys = new TableKeys();
					  for(String tablename: infoTable){
					  ResultSet foreignKeys = databaseMetaData.getImportedKeys(connection1.getCatalog(), null, tablename);
					    while (foreignKeys.next()) {
					        String fkTableName = foreignKeys.getString("FKTABLE_NAME");
					        String fkColumnName = foreignKeys.getString("FKCOLUMN_NAME");
					        String pkTableName = foreignKeys.getString("PKTABLE_NAME");
					        String pkColumnName = foreignKeys.getString("PKCOLUMN_NAME");
		
					        tableKeys.addTable(pkTableName, pkColumnName, fkTableName, fkColumnName) ;				       
					    }}
					  tableKeys.printMapTableKeys();
				  } catch (SQLException e) {e.printStackTrace();}
			}
		}
	    //4. Add & tables, columns and keys to new.jsp
	    model.addAttribute("name", message);
	    model.addAttribute("infoTable", infoTable);
	    model.addAttribute("sizeofT", sizeofT);
	    model.addAttribute("columnT", columnT);
	    model.addAttribute("returnable", returnableInit);
	    model.addAttribute("tableKeys", tableKeys.getTablesKeys());
	    
		return "redirect:new";	
	}
	
	/* 3rd Page for web application */
	
	// 5. Initialize variables to database view page
	@RequestMapping(value="/new", method = RequestMethod.GET)
	public String setNew(ModelMap model) {
		System.out.println(message);
		System.out.println(infoTable);
		System.out.println(columnT);
		System.out.println(sizeofT);
		System.out.println(tableKeys);
		model.addAttribute("returnable", returnableInit);
		model.addAttribute("name", message);
		model.addAttribute("infoTable", infoTable);
	    model.addAttribute("sizeofT", sizeofT);
	    model.addAttribute("columnT", columnT);
	    model.addAttribute("tableKeys", tableKeys.getTablesKeys());
	    model.addAttribute("mapperTables", tableKeys.getMapperTables());
		
		return "new";
	}
	
	// 6. Create results to my SQL Query
	@RequestMapping(value="/new", method = RequestMethod.POST)
	public String getStatements(@ModelAttribute("Returnable") Returnable returnable, ModelMap model) {	
		List<String> SendQuery = new ArrayList<String>();
		List<String> results = new ArrayList<String>();		

		int noColumns = 0;
		int noList = 0;
		String Query = StringUtils.chomp(returnable.getSelect());
		String selectQ = "";
		if(Query.endsWith(", ")){
			selectQ = Query.trim();
			selectQ = selectQ.substring(0, selectQ.length()-1);
		}
		else{
			selectQ = Query;
		}
		
		selectQ = returnable.removeDuplicates(selectQ).replace("[","").replace("]", "").replace("\r","").replace("\n","").replace("\\s+","");
		
		// 1. create form to create SQL QUERY 
		  // a. get the values from SELECT query 
		List<Selections> lSelections = new ArrayList<Selections>();
		List<String> listSelections = new ArrayList<String>(Arrays.asList(selectQ.split(", ")));
		for(String selectOpt: listSelections){
			Selections selections = returnable.getSelection();
			List<String> listtemp = new ArrayList<String>(Arrays.asList(selectOpt.split("\\.")));	
			selections.setTableN(listtemp.get(0));
			selections.setTableC(listtemp.get(1));
			lSelections.add(selections);
		}
		
		// b. primary and foreign keys
		List<MapperTables> listRelations = tableKeys.getMapperTables();
		List<String> listWher = new ArrayList<String>();
		List<String> listFr = new ArrayList<String>(); 
		// c. add values to FROM and WHERE section
		for(Selections selections: lSelections){
		    for(MapperTables mapperTables: listRelations){
		    	if(((selections.getTableN()).equals(mapperTables.getPrimaryT())) || ((selections.getTableN()).equals(mapperTables.getForeignT()))){
		    	
		    			listFr.add(mapperTables.getPrimaryT());

			    		String temp = mapperTables.getPrimaryT() + "." + mapperTables.getPrimaryC();
			    		String temp2 = temp + " = "+ mapperTables.getForeignT() + "." + mapperTables.getForeignC();
			    		listWher.add(temp2);
			    		listFr.add(mapperTables.getPrimaryT());
			    		listFr.add(mapperTables.getForeignT());
		    	}
		    }    
		}
		List<String> resultWhere1 = returnable.removeDuplicateListItems(listWher);
		List<String> resultWhere = new ArrayList<String>();
		List<String> listTablesSel = new ArrayList<String>();
		 for(Selections selections: lSelections){
			 if(!listTablesSel.contains(selections.getTableN())) listTablesSel.add(selections.getTableN());
		 }
		 if(listTablesSel.size()>1){
			  for(Selections selections: lSelections){
				  int a = 0;
				  for(String wheres: resultWhere1) {
			     	if(wheres.contains(selections.getTableN()))  {
			     		a++;
			     		if(a==1) resultWhere.add(wheres);
				}
			}
		  }
		 }
		resultWhere = returnable.removeDuplicateListItems(resultWhere);
		StringBuilder sb = new StringBuilder();
		for (String s : resultWhere){
			sb.append(s); 
			if(resultWhere.size()>1) sb.append(" AND ");
		}		
		String whereFunction = sb.toString();
		if(whereFunction.contains(" AND "))  whereFunction = whereFunction.substring(0, whereFunction.length() - 5);

		List<String> resultFrom = new ArrayList<String>();
		if(resultWhere.size()==0){
		for(String tablesSel: listTablesSel){
			    	resultFrom.add(tablesSel);
			}
		}
		for(String whereS: resultWhere){
			MapperTables mapperTables = tableKeys.getMapperTable(whereS);
			resultFrom.add(mapperTables.getPrimaryT());
			resultFrom.add(mapperTables.getForeignT());
		}
		
		resultFrom = returnable.removeDuplicateListItems(resultFrom);
		StringBuilder sb2 = new StringBuilder();
		for (String s : resultFrom){
			sb2.append(s); 
			if(resultFrom.size()>1) sb2.append(", ");
		}		
		String fromFunction = sb2.toString();
		 if(fromFunction.contains(", ")) fromFunction = fromFunction.substring(0, fromFunction.length() - 2);
		 
		// 2. create SQL QUERY
		String temp = StringUtils.EMPTY;
		if(whereFunction.isEmpty()) temp = "SELECT " + selectQ + " FROM " + fromFunction;
		else temp = "SELECT " + selectQ + " FROM " + fromFunction +" WHERE " + whereFunction;
		
		System.out.println("-------------------");
		System.out.println(selectQ);
		System.out.println(resultFrom);
		if(!resultWhere.isEmpty()){
			System.out.println(resultWhere);System.out.println(whereFunction.length());	
		}
		System.out.println("-------------------");
		System.out.println(temp);
		model.addAttribute("Query", temp);
		
		// 3. create alternative SQL QUERY
		String alternQuery = "";
		if(!whereFunction.isEmpty()){
			int i = 0;
			for(String whereS: resultWhere){
				MapperTables mapperTables = tableKeys.getMapperTable(whereS);
				if(i==0){
					alternQuery = alternQuery + mapperTables.getPrimaryT() + " INNER JOIN " + mapperTables.getForeignT();
					i++;
				}
				else{
					alternQuery = alternQuery + " INNER JOIN " + mapperTables.getPrimaryT();
					i++;
				}
				alternQuery += " ON (" + whereS +")";
			}
			returnable.setAlternFrom(alternQuery);
			returnable.setAlternWhere(StringUtils.EMPTY);
			alternQuery = "SELECT " + selectQ + " FROM " + alternQuery;
			System.out.println("-------------------");
			System.out.println(alternQuery);
		}
		model.addAttribute("AlternativeQuery", alternQuery);	
		returnable.setAlternQuery(alternQuery);
		
		// 4. create results from SQL QUERY
		try {
			Connection con2 = connection1;
			Statement state2 = con2.createStatement();
			// a. send query to server
		    ResultSet results2 = state2.executeQuery(temp);
		    ResultSetMetaData metaData = results2.getMetaData();
		    int numberOfColumns = metaData.getColumnCount();
		    noColumns = numberOfColumns;
		    String st = "";
		    
		    // b. retrieve data from server
		    for(int i=1; i<=numberOfColumns;i++){
				System.out.printf("%-8s\t",metaData.getColumnName(i));
				SendQuery.add(metaData.getColumnName(i));
			}
		    System.out.println();
		    while(results2.next()){
		      for(int i=1; i<=numberOfColumns;i++){
			    	st = st+results2.getString(i)+"\t";
			    	results.add(results2.getString(i));
				}
			    noList = noList+1;
				System.out.println(st);
				st = "";
			}
		} catch (SQLException e) {e.printStackTrace();}

		// 5. send data results to queries.jsp
		returnable.setSelect(selectQ);
		returnable.setFrom(fromFunction);
		returnable.setWhere(whereFunction);
			
		selectGlobal = selectQ;
		fromGlobal = fromFunction;
		whereGlobal = whereFunction;
		alternQueryGlobal = alternQuery;
		if(returnable.getAlternFrom()==null) {
			alternFromGlobal = fromFunction;
			returnable.setAlternFrom(alternFromGlobal);
		}
		else alternFromGlobal = returnable.getAlternFrom();
		alternWhereGlobal = returnable.getAlternWhere();
		
		// 6. default values for page
		returnResults = returnable;
		mapResultsofQuery = results;
		mapResultsofForm = SendQuery;
		numberofColumns = noColumns;
		numberofList = noList;
		
		// 7. result values to Mapper
		mapResults = new MapResults();
		mapResults.setMapResultsofQuery(mapResultsofQuery);
		mapResults.setMapResultsofForm(mapResultsofForm);
		mapResults.setNumberofColumns(numberofColumns);
		mapResults.setNumberofList(numberofList);
		
		model.addAttribute("returnable", returnable);
		model.addAttribute("returnableInit",returnableInit);
		model.addAttribute("SendQuery", SendQuery);
		model.addAttribute("Map", mapResults);
		model.addAttribute("NumberColumns", noColumns);
		model.addAttribute("NumberList", noList);
		
		return "redirect:queries";
	}
	
	/* 4th Page for web application */
	int sizeOfResults =0;
	// 7. Initialize variables to database results view page
	@RequestMapping(value="/queries", method = RequestMethod.GET)
	public String setResultsForFilter(ModelMap model) {	
		sizeOfResults = numberofColumns;
		model.addAttribute("returnable", returnResults);
		model.addAttribute("returnableInit",returnableInit);
		model.addAttribute("SendQuery", mapResultsofForm);
		model.addAttribute("Map", mapResultsofQuery);
		model.addAttribute("NumberColumns", numberofColumns);
		model.addAttribute("NumberList", numberofList);
		
		return "queries";
	}
	

	
	// 8. Create results filter from my SQL Query
	@RequestMapping(value="/queries", method = RequestMethod.POST)
	public String getResultsForFilter(@ModelAttribute("Returnable") Returnable returnable, ModelMap model, BindingResult result) {	
		// 1. get the filter for SQL QUERY
		returnResults.setFilter(returnable.getFilter());
		
		String tableFilter = returnResults.getFilter();
		String whereFuction = returnResults.getWhere();
		List<String> operatorFilter = new ArrayList<String>();
		String whereNew = StringUtils.EMPTY;
		
		List<String> resultWhere = new ArrayList<String>();
		
		// 2. create WHERE section from filter
		if(!tableFilter.equals(StringUtils.EMPTY) && sizeOfResults>0){
			if(!whereFuction.equals(StringUtils.EMPTY)){
				operatorFilter = returnResults.getOperatorForFilter(tableFilter);
				for(int a=0; a<operatorFilter.size(); a++){
					if(operatorFilter.get(a).equalsIgnoreCase("OR") || operatorFilter.get(a).equalsIgnoreCase("AND")){
						whereNew = whereNew + " " + operatorFilter.get(a);
					}
					else{
						if(operatorFilter.get(a+1).contains("LIKE")){
							whereNew = whereNew + " (" + operatorFilter.get(a) + operatorFilter.get(a+1)+ ")";
							a++;
						}
						else{
							whereNew = whereNew + " (" + operatorFilter.get(a) + operatorFilter.get(a+1)+ "'"+ operatorFilter.get(a+2)+"')";
					    	a=a+2;
						}
					}
				}
				   whereFuction = "(" + whereFuction + ") AND " + whereNew; 
				   String whereFunctionWithFilter = whereFuction;
				   returnResults.setWhere(whereFunctionWithFilter);
			}
			else{
				operatorFilter = returnResults.getOperatorForFilter(tableFilter);
				int a =0;
				while(a<(operatorFilter.size()-1)){
					if(operatorFilter.get(a).equalsIgnoreCase("OR") || operatorFilter.get(a).equalsIgnoreCase("AND")){
						whereNew = whereNew + " " + operatorFilter.get(a);
						a++;
					}
					else{
						if(((a+1)<operatorFilter.size()) && ((a+2)<operatorFilter.size())  && returnable.containsDigit(operatorFilter.get(a+2))){
							whereNew = whereNew + " (" + operatorFilter.get(a) + operatorFilter.get(a+1)+ operatorFilter.get(a+2)+")";
							a=a+3;
						}
						else if(!operatorFilter.get(a+1).contains("LIKE")){
					    	whereNew = whereNew + " (" + operatorFilter.get(a) + operatorFilter.get(a+1)+ "'"+ operatorFilter.get(a+2)+"')";
					    	a=a+3;
						}
						else{
							whereNew = whereNew + " (" + operatorFilter.get(a) + operatorFilter.get(a+1)+ ")";
							a=a+2;
						}
					}
				}
							   
				   returnResults.setWhere(whereNew);
			}
		
		// 4. continue Query by creating filter
		String queryWithFilter = "SELECT " + returnResults.getSelect() + " FROM " + returnResults.getFrom() + " WHERE " + returnResults.getWhere();
		List<String> SendQueryF = new ArrayList<String>();
		List<String> resultsF = new ArrayList<String>();	
		int noColumnsF = 0;
		int noListFilter = 0;
		
		// 5. create results from SQL QUERY
		try {
			Connection con2 = connection1;
			Statement state2 = con2.createStatement();
			// a. send query to server
		    ResultSet results2 = state2.executeQuery(queryWithFilter);
		    ResultSetMetaData metaData = results2.getMetaData();
		    int numberOfColumns = metaData.getColumnCount();
		    noColumnsF = numberOfColumns;
		    String st = "";
		    
		    // b. retrieve data from server
		    for(int i=1; i<=numberOfColumns; i++){
				System.out.printf("%-8s\t",metaData.getColumnName(i));
				SendQueryF.add(metaData.getColumnName(i));
			}
		    System.out.println();
		    while(results2.next()){
		      for(int i=1; i<=numberOfColumns; i++){
			    	st = st+results2.getString(i)+"\t";
			    	resultsF.add(results2.getString(i));
				}
		        noListFilter = noListFilter + 1;
				System.out.println(st);
				st = "";
			}
		} catch (SQLException e) {e.printStackTrace(); }

		// 6. send data results to queries.jsp		
		mapResultsofQuery = resultsF;
		mapResultsofForm = SendQueryF;
		numberofColumns = noColumnsF;
		numberofList = noListFilter;
		sizeOfResults = numberofList;
		
		model.addAttribute("returnable", returnResults);
		model.addAttribute("returnableInit", returnableInit);
		model.addAttribute("SendQuery", SendQueryF);
		model.addAttribute("Map", resultsF);
		model.addAttribute("NumberColumns", noColumnsF);
		model.addAttribute("NumberList", noListFilter);
		
		// 7. create alternative SQL QUERY
		String alternQuery = "";
		if(!resultWhere.isEmpty() ){
			int i = 0;
			for(String whereS: resultWhere){
				MapperTables mapperTables = tableKeys.getMapperTable(whereS);
				if(i==0){
					alternQuery = alternQuery + mapperTables.getPrimaryT() + " INNER JOIN " + mapperTables.getForeignT();
					i++;
				}
				else{
					alternQuery = alternQuery + " INNER JOIN " + mapperTables.getPrimaryT();
					i++;
				}
				alternQuery += " ON (" + whereS +")";
			}
			returnResults.setAlternFrom(alternQuery);
			returnResults.setAlternWhere(operatorFilter.stream().map(Object::toString).collect(Collectors.joining(" ")));
			alternQuery = "SELECT " + returnResults.getSelect() + " FROM " + alternQuery + " WHERE " +  operatorFilter.stream().map(Object::toString).collect(Collectors.joining(" "));
			System.out.println("-------------------");
			System.out.println(alternQuery);
		}
		else{
			String alt = returnResults.getAlternQuery();
			returnResults.setAlternFrom(alternFromGlobal);
			returnResults.setAlternWhere(operatorFilter.stream().map(Object::toString).collect(Collectors.joining(" ")));
			alternQuery = alt + " WHERE " +  operatorFilter.stream().map(Object::toString).collect(Collectors.joining(" "));
			System.out.println("-------------------");
			System.out.println(alternQuery);
		}
		model.addAttribute("AlternativeQuery",alternQuery);
		returnResults.setAlternQuery(alternQuery);
	}
		else{
			// 8. default values to SQL SQUERY
			String selectFunctionR = selectGlobal;
			String fromFunctionR = fromGlobal;
			String whereFunctionR = whereGlobal;
			String alternQueryFunctionR = alternQueryGlobal;
			
			returnResults.setSelect(selectFunctionR);
			returnResults.setFrom(fromFunctionR);
			returnResults.setWhere(whereFunctionR);
			returnResults.setAlternQuery(alternQueryFunctionR);
			returnResults.setAlternFrom(alternFromGlobal);
			returnResults.setAlternWhere(alternWhereGlobal);
			returnResults.setFilter(StringUtils.EMPTY);
			
			// 9. send data results to queries.jsp	
			model.addAttribute("returnable", returnResults);
			model.addAttribute("returnableInit",returnableInit);
			model.addAttribute("SendQuery", mapResults.getMapResultsofForm());
			model.addAttribute("Map", mapResults.getMapResultsofQuery());
			model.addAttribute("NumberColumns", mapResults.getNumberofColumns());
			model.addAttribute("NumberList", mapResults.getNumberofList());
		}

		return "queries";
	}	
	
}