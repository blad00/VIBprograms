package be.ugent.psb.annotFile;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.HashMap;

public class CoverterV4_V3Maize {
	/*
	 * based on a converter look for new IDs
	 * 
	 * D:\DanielVIB\maizeEnrich\V3vsV4\gene_V4_V3.txt
D:\DanielVIB\Maize\Correlation\Cesar_dN\Zea_mays_dN.txt
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String fileIn = args[1];
		
		String str;
		String geneName;
		String geneNameAlt;
		
		
		try(BufferedReader inFile = new BufferedReader(new FileReader(fileIn));
				PrintWriter outFile = new PrintWriter(new FileOutputStream(fileIn+".Out"))){
			
			HashMap<String, String> convertMap = getConvertMap(args[0]);
			String arfi[];
			//skip header
			inFile.readLine();
			outFile.println("Gene"+"\t"+"dN"+"\t"+"GeneV3");
			while ((str = inFile.readLine()) != null) {
				arfi = str.split("\t");
				geneName = arfi[0];
				geneNameAlt = convertMap.get(geneName);
				outFile.println(str+"\t"+geneNameAlt);
				
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static HashMap<String, String> getConvertMap(String pathOrgFile){

		String str;
		String arfi[];
		HashMap<String, String> mapCovert = new HashMap<>();

		String geneName;
		String geneNameAlt;


		try(BufferedReader annotsFile = new BufferedReader(new FileReader(pathOrgFile))){
			//skip header
			annotsFile.readLine();
			
			while ((str = annotsFile.readLine()) != null) {
				arfi = str.split("\t");

				geneName=arfi[0];
				geneNameAlt=arfi[1];
				mapCovert.put(geneName, geneNameAlt);

			}


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mapCovert;
	}
}
