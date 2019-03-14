package be.ugent.psb.network;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class SAMsacToENIGMAconverter {
	/*
	 * This program will read the SAC corrected network which has many fields and only gene indexes, to be formatted into ENIGMA format network
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String exprPath = args[0];
		String SACPath = args[1];
		String outputFile = args[2];
		
		String str;
		String[] ar;
		
		String gene1;
		String gene2;
		
		int index1;
		int index2;
		
		
		ArrayList<String> geneIndex = new ArrayList<>();
		
		try(BufferedReader inFileExp = new BufferedReader(new FileReader(exprPath));BufferedReader inFileSAC = new BufferedReader(new FileReader(SACPath));
				PrintWriter outFileENI = new PrintWriter(new FileOutputStream(outputFile))){

			//1) load gene indexes
			
			//skip header
			inFileExp.readLine();
			
			while ((str = inFileExp.readLine()) != null) {
				ar = str.split("\t");
				geneIndex.add(ar[0]);

			}
			
			//2) read SAC file and print ENIGMA
			
			outFileENI.println("Gene_1"+"\t"+"Gene_2"+"\t"+"PosNeg"+"\t"+"P-value");
			
			//skip header
			inFileSAC.readLine();
			
			while ((str = inFileSAC.readLine()) != null) {
				ar = str.split("\t");
				
				index1 = (int)Double.parseDouble(ar[1]);
				index2 = (int)Double.parseDouble(ar[2]);
				//-1 because SAC network is based 1
				gene1 = geneIndex.get(index1-1);
				gene2 = geneIndex.get(index2-1);

				outFileENI.println(gene1+"\t"+gene2+"\t"+"+"+"\t"+"0");
			}
					
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
