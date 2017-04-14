package be.ugent.psb.cluster;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class ExtractorFromSAMBA2BiNGO {
//This program goes through the SAMBA file and extracts every 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String fileIn = args[0];
		String str;
		ArrayList<String> geneList = new ArrayList<>();

		
		try(BufferedReader inFile = new BufferedReader(new FileReader(fileIn))){
			
			while ((str = inFile.readLine()) != null) {
				
				
				
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
