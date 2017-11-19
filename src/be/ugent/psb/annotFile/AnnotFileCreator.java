package be.ugent.psb.annotFile;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.HashMap;

public class AnnotFileCreator {

	/**
	 * @param args
	 * This program creates an annotation file that can be used in PINGO, using the following parameters from PLAZA:
	 * Arg 0 functional Annotation file
	 * Arg 1 Conversion IDs file
	 * Arg 2 output file
	 * Arg 3 need conversion s or n
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub


		String fileInAnnot = args[0];
		String fileInConvers = args[1];
		String outputFile = args[2];
		String str;
		HashMap<String, String> conversionIds = new HashMap<>();
		String[] arrayLineFile;
		String[] arrayOntology;
		char conver =  args[3].charAt(0);
		boolean conversion = false;
		if(conver == 's')
			conversion = true;


		try(BufferedReader inFileAnnot = new BufferedReader(new FileReader(fileInAnnot));BufferedReader inFileConvers = new BufferedReader(new FileReader(fileInConvers));
				PrintWriter outFile = new PrintWriter(new FileOutputStream(outputFile))){
			//skip header
			inFileAnnot.readLine();
			
			//Print header
			outFile.println("(species=Maize)(type=Biological Process)(curator=GO)");


			if(conversion){
				//Load name conversion
				//skip header
				inFileConvers.readLine();

				while ((str = inFileConvers.readLine()) != null) {

					arrayLineFile = str.split("\t");
					conversionIds.put(arrayLineFile[0], arrayLineFile[2]);

				}


				while ((str = inFileAnnot.readLine()) != null) {
					arrayLineFile = str.split("\t");
					arrayOntology= arrayLineFile[3].split(":");

					outFile.println(conversionIds.get(arrayLineFile[2])+" = "+arrayOntology[1]);

				}

			}else{
			
				while ((str = inFileAnnot.readLine()) != null) {
					arrayLineFile = str.split("\t");
					
					outFile.println(arrayLineFile[2]+" = "+arrayLineFile[3]);
					
				}
				
				
			}



		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

}
