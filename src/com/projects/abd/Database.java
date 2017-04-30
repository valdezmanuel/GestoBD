package com.projects.abd;

import java.io.File;
import java.util.*;
public class Database {
	
	// List of tables of database
	private List<Table> tables = new ArrayList<Table>();
	// Name of database
	private String db_name;
	
	// DIR of database
	private String dir_db;

	public Database(String db_name, String dir_db) {
		this.db_name = db_name;
		this.dir_db = dir_db;
		
	}
	
	public void SaveDB(){
		File nf = new File("DodoDB/"+this.db_name);
		
		if(!nf.exists()){
			if(nf.mkdir()){
				System.out.println("LA BD '"+this.db_name+"' SE HA CREADO EXITOSAMENTE");
			}else{
				System.out.println("OCURRIO UN ERROR AL CREAR LA BASE DE DATOS");
			}
			
		}
		
		Gestor.getAllListOfDBs();
	}
	
	
	public void addTable(Table table){ tables.add(table); }
	
	public List<Table> getTables(){ return tables; }
	
	public String getDBName(){return db_name; }
	
	public String getDir(){ return dir_db;}
	
	public void setDBName(String db_name) {this.db_name = db_name;}
	
	
	
	public void changeName(){
		
	}
	
	public void dropDB(){
		
	}
	
	

}
