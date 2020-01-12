package be.ugent.psb.cluster;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ExtractENIGMAModules {
/*
 * this program takes the file _modules from ENIGMA and produces separates files for enrichment 
 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String filename = args[0];
		String outPutFolder = args[1];
		PrintWriter outFile = null;
		String str = null;
		String ar[];
		String module;
		ArrayList<String> genes = null;
		int i;
		
		try(BufferedReader inFile = new BufferedReader(new FileReader(filename))){
			//skip header
			inFile.readLine();
			
			while ((str = inFile.readLine()) != null) {
				ar = str.split("\t");
				genes =  new ArrayList<>();
				module = ar[0];

				
				outFile = new PrintWriter(new FileOutputStream(outPutFolder+module+"ENI.tsv"));
				for (i = 2;i<ar.length;i ++) {
					outFile.println(ar[i]);
				}
				
				outFile.close();
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}

}
