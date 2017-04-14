package be.ugent.psb.corr2graph;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Corre2Graph {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/**
		 * @param args
		 * This program gets the couples that overcome a specific threshold from Corr matrix made by Sam, to make an Enigma Graph
		 * , self connections are not allowed neighter negative corrs.
		 * Arg 0 matrix file
		 * Arg 1 gene names
		 * Arg 2 threshold
		 * Arg 3 output file
		 **/
		String corrMatFile = args[0];
		String genNamesFile = args[1];
		double ths = Double.parseDouble(args[2]);
		String outputFile = args[3];
		String str;
		String[] arrayl;
		//		Set <String> pairs = new TreeSet<String>();
		ArrayList<String> geneNames = new ArrayList<>();

		try(BufferedReader cmat = new BufferedReader(new FileReader(corrMatFile));
				BufferedReader bgeneNames = new BufferedReader(new FileReader(genNamesFile));
				PrintWriter outFile = new PrintWriter(new FileOutputStream(outputFile))){
			//load geneNames

			while ((str = bgeneNames.readLine()) != null) {
				geneNames.add(str);
			}

			int i = 0;
			int j = 0;
			double val;

			//skip header
			cmat.readLine();
			String geneA;
			String geneB;
			


			while ((str = cmat.readLine()) != null) {
				arrayl=str.split("\t");

				for(j=i+2;j<arrayl.length;j++){
					val=Double.parseDouble(arrayl[j]);
					//ths definition
					if(val<=ths){
						geneA=geneNames.get(i);
						geneB=geneNames.get(j-1);
						outFile.println(geneA+"\t"+geneB+"\t"+(1-val));
					}

				}
				i++;
			}


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	}

}
