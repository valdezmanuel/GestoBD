package com.projects.abd;

import java.util.*;
public class Database {
	
	// List of tables of database
	private List<Table> tables = new ArrayList<Table>();
	// Name of database
	private String db_name;

	public Database(String db_name) {
		this.db_name = db_name;
		
	}

}
