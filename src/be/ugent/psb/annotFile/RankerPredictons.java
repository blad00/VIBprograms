package be.ugent.psb.annotFile;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class RankerPredictons {
	/**
	 * @param args
	 * This program will create a combine ranked of sorted lists from predictions made by PiNGO
	 * 
	 * Arg 0 file 0
	 * Arg 1 file 1
	 * Arg 2 file 2
	 * Arg 3 output file
	 **/
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String str;
		String[] arrayl;
		
		String file0Name = args[0];
		String file1Name = args[1];
		String file2Name = args[3];
		String outputFile = args[4];
		
		HashMap<String, ArrayList<Object>> wholeRanked = new HashMap<>();
		// 0 count0, 1 count1,2 count2, 3 Comments 
		ArrayList<Object> info;
		
		String geneName;
		
		Integer rank;
		String function;
		
		
		try(BufferedReader file0 = new BufferedReader(new FileReader(file0Name));
				BufferedReader file1 = new BufferedReader(new FileReader(file1Name));
				BufferedReader file2 = new BufferedReader(new FileReader(file2Name));
				PrintWriter outFile = new PrintWriter(new FileOutputStream(outputFile))){
			
			
			
			while ((str = file0.readLine()) != null) {
				info = new ArrayList<>();
				
				arrayl=str.split("\t");
				geneName = arrayl[1];
				rank = Integer.parseInt(arrayl[9]);
				function = 	arrayl[8];
				//for correlation index 0
				if(!wholeRanked.containsKey(geneName)){
					 info = new ArrayList<>();
					 info.add(0,rank);
					 info.add(4,function);
				}
				
				
				
			}
			
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
