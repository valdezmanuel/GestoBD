package com.projects.abd;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;


public class Table {
	
	// Name of table
	public String tbl_name;
	
	// Columns
	List<Attributes> list_columns;


	public Table(String tbl_name, List<Attributes> list_columns) {
		this.tbl_name = tbl_name;
		this.list_columns = list_columns;
	}
	
	public void saveTable(){
		String dir_root = Gestor.ActualBD.getDir()+"/"+this.tbl_name;
		File table = new File(dir_root);
		if(!table.exists()){
			
			try{
				
				table.mkdir();
				
				// Main json
				JSONObject structure = new JSONObject();
				// add the name of table
				structure.accumulate("name", this.tbl_name);
				
				// create the JSONArray that is going to contain the columns
				JSONArray array_attributes = new JSONArray();
				
				for(Attributes at:list_columns){
					
					JSONObject newAttribute = new JSONObject();
					newAttribute.accumulate("column_name", at.name);
					newAttribute.accumulate("column_type", at.type);
					array_attributes.put(newAttribute);
				}
				
				// add the columns to the main json
				structure.accumulate("columns", array_attributes);
				
				Writer writer = null;

				try {
				    writer = new BufferedWriter(new OutputStreamWriter(
				          new FileOutputStream(dir_root+"/structure.json"), "utf-8"));
				    writer.write(structure.toString());
				    
				    System.out.println("************************************************");
					System.out.println("* lA TABLA "+this.tbl_name+" SE CREO CORRECTAMENTE");
					System.out.println("************************************************");
				    
				} catch (IOException ex) {
				  // report
				} finally {
				   try {writer.close();} catch (Exception ex) {/*ignore*/}
				}
			
				Gestor.getAllListOfDBs();
			
			
			}catch(Exception e){e.printStackTrace();}
			
		}else{
			System.out.println("** ERROR, LA TABLA YA EXISTE **");
		}
		
		

		
	}
	

}
