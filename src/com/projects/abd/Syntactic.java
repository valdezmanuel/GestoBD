package com.projects.abd;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Syntactic {

	
	List<Matches> matches;
	List<Pattern_type> allowedSequences = new ArrayList<Pattern_type>();
	
	
	
	public Syntactic(List<Matches> matches) {
		this.matches = matches;
		init();
	}
	
	

	/*
	 *  INITIAL INTRUCTION SEQUENCE
	 */
	public void init(){
		
		// CREATE DATABASE 
		allowedSequences.add(new Pattern_type("^(CREATE)(DATABASE)(VAL)(END)$","CREATE_DATABASE"));
		// CREATE TABLE
		allowedSequences.add(new Pattern_type("^(CREATE)(TABLE)(VAL)(LEFT_PAR)(VAL)(DATA_TYPE)(COMA(VAL)(DATA_TYPE))*(RIGHT_PAR)(END)$","CREATE_TABLE"));
		// USE DATABAS
		allowedSequences.add(new Pattern_type("^(USE)(DATABASE)(VAL)(END)$","USE_DATABASE"));
		// SHOW DATABASES
		allowedSequences.add(new Pattern_type("^(SHOW)(DATABASES)(END)$","SHOW_DATABASES"));
		// SHOW TABLES
		allowedSequences.add(new Pattern_type("^(SHOW)(TABLES)(END)$","SHOW_TABLES"));
		
	}

	
	
	public void analizeInitial(){
		
		String text_to_val = "";
		for(Matches wo:matches){
			text_to_val += wo.pattern.type;
		}
		
		//System.out.println(text_to_val);
		
		boolean isCorrect = false;
		for(Pattern_type allowedP: allowedSequences)
		{
			Matcher mc = Pattern.compile(allowedP.pattern).matcher(text_to_val);
			
			if(mc.find()){
				analizeMatches(allowedP);
				isCorrect = true;
				break;
			}
			
		}
		
		if(!isCorrect)
			System.out.println(" - - ERROR SINTACTICO - -");
		
		
		
		
	}
	
	
	public void analizeMatches(Pattern_type match_sequence){
		
		String token = match_sequence.type;
		
		//System.out.println(token);
		
		if(token.equals("CREATE_DATABASE")){
			CREATE_DATABASE();
			
		}else if(token.equals("CREATE_TABLE")){
			
			if(Gestor.ActualBD != null){
				CREATE_TABLE();
			}else{
				System.out.println("ERROR, NO HAY NINGUNA BD SELECCIONADA");
			}
			
			
		}else if(token.equals("USE_DATABASE")){
			USE();
			
		}else if(token.equals("SHOW_DATABASES")){
			SHOW_DATABASES();
			
		}else if(token.equals("SHOW_TABLES")){
			if(Gestor.ActualBD != null){
				SHOW_TABLES();
			}else{
				System.out.println("ERROR, NO HAY NINGUNA BD SELECCIONADA");
			}
		
		}
	}
	
	/*
	 * SHOW | DATABASES
	 */
	public void SHOW_DATABASES(){
		List<Database> dbs = Gestor.databases;
		
		
		System.out.println("***********************");
		System.out.println("*      DATABASES      *");
		System.out.println("***********************");
		for(Database db:dbs){
			System.out.println("* "+db.getDBName());
			System.out.println("* -------------");
		}
		System.out.println("***********************");
	}
	

	/*
	 *  SHOW | TABLES
	 */
	public void SHOW_TABLES(){
		
		Gestor.ActualBD = Gestor.getBD(Gestor.ActualBD.getDBName());
	
		List<Table> tables = Gestor.ActualBD.getTables();
		
		
		System.out.println("***********************");
		System.out.println("*  TABLES | "+Gestor.ActualBD.getDBName()+"   *");
		System.out.println("***********************");
		for(Table tb:tables){
			System.out.println("* "+tb.tbl_name);
			System.out.println("* -------------");
		}
		System.out.println("***********************");
		
	}
	
	
	
	/*
	 *  USE Database | Check if the db exists
	 */
	public void USE(){
		String name = matches.get(2).lexem;
		if(Gestor.checkIfDBExists(name)){
			Gestor.ActualBD = Gestor.getBD(name);
			System.out.println("***********************");
			System.out.println("* La base de datos actual se ha cambiado a "+name);
			System.out.println("***********************");
		}else{
			System.out.println("***********************");
			System.out.println("* La base de datos "+name+" no existe");
			System.out.println("***********************");
		}
		
	}
	
	
	/*
	 * Create DATABASE | TABLE - MATCH
	 */
	public void CREATE_DATABASE(){
		String name = matches.get(2).lexem;
		
		Database ndb = new Database(name,"/");	
		ndb.SaveDB();
	}
	

	
	/*
	 * CREATE TABLE
	 */
	public void CREATE_TABLE(){
		String nTable = matches.get(2).lexem;
		List<Attributes> attributes = new ArrayList<Attributes>();
		
		// ADD ALL THE ATTRIBUTES TO THE LIST
		for(int i=4;i<matches.size();i++){
			String type = matches.get(i).pattern.type;
			String lexem = matches.get(i).lexem;
			//System.out.println(type);
			if(!type.equals("END") && !type.equals("COMA") && !type.equals("RIGHT_PAR") ){
				if(type.equals("VAL")){
					attributes.add(new Attributes(lexem,""));
				}else{
					attributes.get(attributes.size()-1).type = lexem;
				}
			}
		}
		
		System.out.println("----------\nNombre tabla: "+nTable);
		for(Attributes at:attributes){
			System.out.println(at.name+" - "+at.type);
		}
		
		Table ntbl = new Table(nTable,attributes);
		ntbl.saveTable();
		
	}

	
	
//	/*
//	 * Check end word in the sentence
//	 */
//	public boolean endSentence(int position){
//		String token = matches.get(position).pattern.type;
//		if(token.equals("END") && position == matches.size()-1){
//			System.out.println("CORRECTO");
//			return true;
//		}else{
//			System.out.println("ERROR END - SE ESPERABA ';'");
//			return false;
//		}
//		
//	}
	
	
}
