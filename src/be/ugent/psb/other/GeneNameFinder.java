package be.ugent.psb.other;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class GeneNameFinder {
	
	/*
	 * Inneficient program to find the genenames in the genome annottation, based on genomic position
	 * arg 1 = Tassel stats file
	 * arg 2 = genome annotation
	 * 
	 */

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader peaksFile = null;
		peaksFile = new BufferedReader(new FileReader(args[0]));

		BufferedReader maizeDB = null;



		PrintWriter outFile = new PrintWriter(new FileOutputStream(args[0]+".peakNames"));


		String str, strDB;
		String arfi[], arDB[];
		String chrPeak;
		int posPeak;

		String chrDB;
		int posIni;
		int posFin;


		boolean header = true;
		boolean found = false;
		while ((str = peaksFile.readLine()) != null) {
			if(header){
				outFile.println("GeneName"+"\t"+str);
				header = false;
				continue;
			}
			arfi = str.split("\t");
			if(!arfi[2].equals("")&&!arfi[3].equals("")){
				chrPeak = arfi[2];
				posPeak = Integer.parseInt(arfi[3]);
				maizeDB = new BufferedReader(new FileReader(args[1]));
				maizeDB.readLine();//skip header
				while ((strDB = maizeDB.readLine()) != null) {
					arDB = strDB.split("\t");
					chrDB = arDB[0];
					posIni = Integer.parseInt(arDB[1]);
					posFin = Integer.parseInt(arDB[2]);

					if(chrPeak.equals(chrDB)&&(posIni<=posPeak&&posPeak<=posFin)){
						outFile.println(arDB[3]+"\t"+str);
						found = true;
						break;						
					}

				}
				if(!found)
					outFile.println("NoGene"+"\t"+str);
				else
					found =false;
				
				maizeDB.close();

			}else{
				outFile.println("NoGene"+"\t"+str);
			}
		}
		outFile.close();
		peaksFile.close();
	}
}
