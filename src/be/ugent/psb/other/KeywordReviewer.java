package be.ugent.psb.other;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class KeywordReviewer {

	/**
	 * This program will go across a file printing every line which is with or without the keyword or many keywords
	 * @param args
	 * arg 0 infile
	 * arg 1 init index to search
	 * arg 3... keywords
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//printWithOutkeyWord(args[0], args[1]);
		try {
			printWithManykeyWordsIndex(args);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void printWithManykeyWordsIndex(String[] args) throws FileNotFoundException, IOException{
		
		try(BufferedReader inFile = new BufferedReader(new FileReader(args[0]));
				PrintWriter outFile = new PrintWriter(new FileOutputStream(args[0]+"out.tsv"))){
			
			int startIndex = Integer.parseInt(args[1]);
			
			//get keywords
			
			int i;
			
			ArrayList<String> keywords = new ArrayList<>(); 
			
			for(i=2;i<args.length;i++){
				keywords.add(args[i]);
				
			}
			
			String[] arrKeys = keywords.toArray(new String[keywords.size()]);
			
			String str = null;
			String arrayFile[];
			
			boolean firstLine =true;
			
			String descAnnot = "";
			
			int line=0;
			while ((str = inFile.readLine()) != null) {
				
				line++;
				
				//print header
				if(firstLine){
					outFile.println(str);
					firstLine = false;
					continue;
				}
				arrayFile=str.split("\t");
				
				if(line==173){
					System.out.println("aaaaaaaaaaaa");
				}
				
				for(i=startIndex;i<arrayFile.length;i++){
					descAnnot+=arrayFile[i];
				}
				if(stringContainsItemFromList(descAnnot,arrKeys)){
					outFile.println(str);
				}
				
				descAnnot = "";
			}
			
			
		}
		
	}
	
	public static void printWithManykeyWords(String[] args) throws FileNotFoundException, IOException{
		
		try(BufferedReader inFile = new BufferedReader(new FileReader(args[0]));
				PrintWriter outFile = new PrintWriter(new FileOutputStream(args[0]+"out.tsv"))){
			
			String str = null;
			
			
			boolean firstLine =true;
			
			
			while ((str = inFile.readLine()) != null) {
				
				//print header
				if(firstLine){
					outFile.println(str);
					firstLine = false;
					continue;
				}
				
				
				
				if(stringContainsItemFromListLoop(str, args)){
					outFile.println(str);
				}
				
			}
			
			
		}
		
	}
	
	public static void printWithOutkeyWord(String inPathfile, String keyWord){
		

		try(BufferedReader inFile = new BufferedReader(new FileReader(inPathfile));
				PrintWriter outFile = new PrintWriter(new FileOutputStream(inPathfile+"out.tsv"))){
			
			
			String str = null;
			
			
			boolean firstLine =true;
			
			
			while ((str = inFile.readLine()) != null) {
				
				//print header
				if(firstLine){
					outFile.println(str);
					firstLine = false;
					continue;
				}
				
				if(!str.contains(keyWord)){
					outFile.println(str);
				}
				
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static boolean stringContainsItemFromList(String inputStr, String[] items) {
	    return Arrays.stream(items).parallel().anyMatch(inputStr::contains);
	}
	
	public static boolean stringContainsItemFromListLoop(String inputStr, String[] items)
	{
	    for(int i =0; i < items.length; i++)
	    {
	        if(inputStr.contains(items[i]))
	        {
	            return true;
	        }
	    }
	    return false;
	}
	
}

