package com.projects.abd;

import java.io.File;
import java.util.*;
import java.util.regex.*;

public class Gestor {
	
	// List of databases
	public static List<Database> databases = new ArrayList<Database>();
	public static Database ActualBD;
	public String query = "";
	public Scanner in;
	public Pattern regexp;
	
	
	// Lexical Object
	Lexical lex;
	
	
	public Gestor() {
		in = new Scanner(System.in);
		regexp = Pattern.compile("([A-Za-z][A-Z0-9a-z]*|;|(\\())|(\\)|,)");
		getAllListOfDBs();
		menu();
	}
	
	
	/*
	 * Get and update de dbs list
	 */
	public static void getAllListOfDBs(){
		databases.clear();
		
		File root = new File("DodoDB");
		File[] dbs = root.listFiles();
		
		for(File f:dbs){
			Database ndb = new Database(f.getName(),f.getPath());
			
			File[] tables = new File(f.getPath()).listFiles();
			for(File table:tables){
				ndb.addTable(new Table(table.getName(),null));;
				
			}
			
			
			
			databases.add(ndb);
		}
	}
	
	/*
	 * Check if the db exists in the list of dbs
	 */
	public static boolean checkIfDBExists(String name){
		Gestor.getAllListOfDBs();
		boolean resp = false;
		for(Database f:databases){
			//System.out.print(f.getDBName()+" - "+name);
			
			if(name.equals(f.getDBName())){
				resp = true;
			}
		}
		
		return resp;
	}
	
	/*
	 * return the DB by name
	 */
	public static Database getBD(String name){
		Gestor.getAllListOfDBs();
		
		for(Database f:databases){
			if(name.equals(f.getDBName())){
				return f;
			}
		}
		
		return null;
		
	}
	
	public void menu(){
		System.out.println("******* DodoDB ********");
		do{
			System.out.print("DodoDB > ");
			query=in.nextLine();
			Matcher mc=regexp.matcher(query);
			
			List<String> wordsFound = new ArrayList<String>();
			
			while(mc.find())
			{
				wordsFound.add(mc.group());
				
			}
			
			// add the list of words found to the lex object
			lex = new Lexical(wordsFound);
			
			// Lexical Analisis
			lex.AnalizeText();
			
			
		}while(!query.equals("exit"));
		System.out.println(":)");
		System.exit(1);
		
	}

}
