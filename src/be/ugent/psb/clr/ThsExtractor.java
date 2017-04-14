package be.ugent.psb.clr;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
import java.util.TreeSet;

public class ThsExtractor {
	/**
	 * @param args
	 * This program gets the couples that overcome a specific threshold from CLR matrix , self connections are not allowed.
	 * Arg 0 matrix file
	 * Arg 1 threshold
	 * Arg 2 output file
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		String amatFile = args[0];
		double ths = Double.parseDouble(args[1]);
		String outputFile = args[2];
		String str;
		String[] arrayl;
		int i;
		Set <String> pairs = new TreeSet<String>();
		Set <String> geneList = new TreeSet<String>();
		try(BufferedReader amat = new BufferedReader(new FileReader(amatFile));PrintWriter outFile = new PrintWriter(new FileOutputStream(outputFile));
				PrintWriter outFileList = new PrintWriter(new FileOutputStream(outputFile+".list"))){
			
			//save header
			str = amat.readLine();
			String header [] = str.split("\t");
			double val;
			while ((str = amat.readLine()) != null) {
				arrayl = str.split("\t");
				for(i = 1;i<arrayl.length;i++){
					val = Double.parseDouble(arrayl[i]);
					if(val>ths){
						if(!pairs.contains(arrayl[0]+header[i])){
							pairs.add(arrayl[0]+header[i]);
							pairs.add(header[i]+arrayl[0]);
							outFile.println(arrayl[0]+"\t"+header[i]+"\t"+val);
							//to create the whole list and send the correlations
							if(!geneList.contains(arrayl[0]))
								geneList.add(arrayl[0]);
							if(!geneList.contains(header[i]))
								geneList.add(header[i]);	
							

						}	
					}
				}
			}
			
			for(String gene:geneList){
				outFileList.println(gene);
			}
			
		}
	}

}
