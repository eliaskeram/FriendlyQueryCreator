package com.spring.connection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

/**
 * Java Class for processing primaries and foreigs keys
 * 
 * @author Elias Keramefs
 *
 */
public class TableKeys {
	//Inner Class Mapper with primary and foreign key 
	public class MapperTables {
		private String primaryT;
		
		private String primaryC;
		
		private String foreignT;
		
		private String foreignC;
		
		// Constructor
		public MapperTables(){
			primaryT = StringUtils.EMPTY;
			primaryC = StringUtils.EMPTY;
			foreignT = StringUtils.EMPTY;
			foreignC = StringUtils.EMPTY;
		}

		// setter with primary key table 
		public void setPrimaryT(String primaryT) {
			this.primaryT = primaryT;
		}

		// setter with primary key column
		public void setPrimaryC(String primaryC) {
			this.primaryC = primaryC;
		}

		// setter with foreign key table
		public void setForeignT(String foreignT) {
			this.foreignT = foreignT;
		}

		// setter with foreign key column
		public void setForeignC(String foreignC) {
			this.foreignC = foreignC;
		}

		// getter with primary key table 
		public String getPrimaryT() {
			return primaryT;
		}
		
		// getter with primary key column
		public String getPrimaryC() {
			return primaryC;
		}

		// getter with foreign key table
		public String getForeignT() {
			return foreignT;
		}
		
		// getter with foreign key column
		public String getForeignC() {
			return foreignC;
		}
		
	}
	
	private ArrayList<String> primaryTable = new ArrayList<String>();
	
	private ArrayList<String> primaryColumn = new ArrayList<String>() ;
	
	private ArrayList<String> foreignTable = new ArrayList<String>();
	
	private ArrayList<String> foreignColumn = new ArrayList<String>();
	
	private List<HashMap<HashMap<HashMap<String, String>, String>, String>> listTableKeys = new ArrayList<HashMap<HashMap<HashMap<String,String>,String>,String>>();
	
	// Constructor
	public TableKeys(){
		primaryTable.clear();
		primaryColumn.clear();
		foreignTable.clear();
		foreignColumn.clear();
		listTableKeys.clear();
	}

	// add table with table and column name from primary key and foreign key
	public void addTable(String prTable, String prColumn, String frTable, String frColumn){
		primaryTable.add(prTable);
		primaryColumn.add(prColumn);
		foreignTable.add(frTable);
		foreignColumn.add(frColumn);
		
		
		HashMap<HashMap<HashMap<String, String>, String>, String> myKeyList = new HashMap<HashMap<HashMap<String,String>, String>, String>();
		HashMap<String,String> myprimary = new HashMap<String, String>();
		myprimary.put(prTable, prColumn);
		
		HashMap<HashMap<String, String>, String> myforeigT = new HashMap<HashMap<String, String>, String>();
		myforeigT.put(myprimary, frTable);
		myKeyList.put(myforeigT, frColumn);
		
		listTableKeys.add(myKeyList);
	}
	
	// getter with List of primary key -> foreign key
	public List<HashMap<HashMap<HashMap<String, String>, String>, String>> getTablesKeys(){
		return listTableKeys;
	}
	
	// print the list of values with primary key -> foreign key
	public void printMapTableKeys(){
		System.out.println("-------------------------");
		System.out.println("Foreign Table.Foreign Columns --> Primary Table.Primary Columns");
		System.out.println("-------------------------");
		
		for(HashMap<HashMap<HashMap<String, String>, String>, String> tableKeys : getTablesKeys()){
			for(HashMap<HashMap<String, String>, String> keyF :tableKeys.keySet()){
				for(HashMap<String, String> keyP: keyF.keySet()){
					Set<String> setKey = keyP.keySet();
					ArrayList<String> list = new ArrayList<String>();
					list.addAll(setKey);
					for (String key : list) {
					    System.out.println(keyF.get(keyP) + "." + tableKeys.get(keyF) + " --> " + key.toUpperCase() + "." + keyP.get(key));
					}
				}
			}
		}
	}
	
	// getter with list of inner mappers
	public List<MapperTables> getMapperTables(){
		List<MapperTables> listMappertables = new ArrayList<MapperTables>();
		for(HashMap<HashMap<HashMap<String, String>, String>, String> tableKeys : getTablesKeys()){
			for(HashMap<HashMap<String, String>, String> keyF :tableKeys.keySet()){
				for(HashMap<String, String> keyP: keyF.keySet()){
					Set<String> setKey = keyP.keySet();
					ArrayList<String> list = new ArrayList<String>();
					list.addAll(setKey);
					for (String key : list) {
						MapperTables mapperTables = new MapperTables();
						mapperTables.setForeignT(keyF.get(keyP));
						mapperTables.setForeignC(tableKeys.get(keyF) );
						mapperTables.setPrimaryT(key.toUpperCase() );
						mapperTables.setPrimaryC(keyP.get(key));
						listMappertables.add(mapperTables);
					}
				}
			}
		}
		return listMappertables;
	}
	
	// getter with inner Mapper
	public MapperTables getMapperTable(String result){
		String temp = result.replaceAll("[.=]",",");
		List<String> listTemp= new ArrayList<String>(Arrays.asList(temp.split("\\,")));	
		MapperTables mapperTables = new MapperTables();
		mapperTables.setPrimaryT(listTemp.get(0));
		mapperTables.setPrimaryC(listTemp.get(1));
		mapperTables.setForeignT(listTemp.get(2));
		mapperTables.setForeignC(listTemp.get(3));
		return mapperTables;
	}
}
 