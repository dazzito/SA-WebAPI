package com.mkyong.rest;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.HashSet;

public class WordCache extends HashMap<String, Integer>{

	
	//HashSet cache = new HashSet<String>();
	
	public void initializeCache() throws FileNotFoundException, IOException{
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("/thai-wordlist.txt");
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
		String line = bufferedReader.readLine(); 
		while(line != null)
		{
			//System.out.println(line);
			 if(this.containsKey(line)){
				System.out.println("#Word '" + line + "'" + " already exists in HashMap.");
			} else {
				this.put(line, 0);
			}
			
			line = bufferedReader.readLine();
		}
		initializeValue();
	}
	
	private void initializeValue() throws FileNotFoundException, IOException{
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("/values.txt");
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
		String line = bufferedReader.readLine(); 
		
		int totalLine = 0, totalMatch = 0;
		while(line != null)
		{
			 ++totalLine;
			 String[] keyvalue = line.split("\t");
			 //System.out.println(keyvalue[0]);
			 if(this.containsKey(keyvalue[0])){
				 ++totalMatch;
				 System.out.println(keyvalue[0] + " set as " + Integer.parseInt(keyvalue[1]));
				this.put(keyvalue[0], Integer.parseInt(keyvalue[1]));
			 } 
			
			line = bufferedReader.readLine();
		}
		
		System.out.println(totalMatch + " out of " + totalLine + " words found.");
	}

}
