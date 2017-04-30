package com.projects.abd;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexical {
	
	// Patterns List
	List<Pattern_type> patterns = new ArrayList<Pattern_type>();
	List<String> words;
	List<Matches> matches = new ArrayList<Matches>();

	public Lexical(List<String> words) {
		this.words = words;
		init();
	}
	
	
	// Analize the words list of the input text
	public void AnalizeText(){
		
		for(int i =0;i<words.size();i++)
		{
			String w = words.get(i);
			globalFor: {
					for(Pattern_type p: patterns){
				
						Matcher mc = Pattern.compile(p.pattern).matcher(w);
						
						while(mc.find()){
							matches.add(new Matches(p,w));
							break globalFor;
							
						}
						
					
				}
			}
		}
		
//		for(Matches m:matches){
//			System.out.println(m.lexem);
//		}
		
		// Syntactic
		Syntactic syn = new Syntactic(matches);
		syn.analizeInitial();
		
		
		
	}
	
	

	
	
	// Initialize the patterns in the list
	public void init(){
		  patterns.add(new Pattern_type("^(CREATE|create)$","CREATE"));
		  patterns.add(new Pattern_type("^(DATABASE|database)$","DATABASE"));
		  patterns.add(new Pattern_type("^(DATABASES|databases)$","DATABASES"));
		  patterns.add(new Pattern_type("^(USE|use)$","USE"));
		  patterns.add(new Pattern_type("^(SHOW|show)$","SHOW"));
		  patterns.add(new Pattern_type("^(TABLE|table)$","TABLE"));
		  patterns.add(new Pattern_type("^(TABLES|tables)$","TABLES"));
		  patterns.add(new Pattern_type("^(int|varchar|date|char)$","DATA_TYPE"));
		  patterns.add(new Pattern_type("^(,)$","COMA"));
		  patterns.add(new Pattern_type("^(([a-zA-Z]|_)([a-zA-Z0-9]|_)*)$","VAL"));
		  patterns.add(new Pattern_type("^(;)$","END"));
		  patterns.add(new Pattern_type("^(\\()$","LEFT_PAR"));
		  patterns.add(new Pattern_type("^(\\))$","RIGHT_PAR"));
	}
	
	

	

}
