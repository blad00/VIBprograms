package be.ugent.psb.go;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class GoDescLoader {

	public static void main(String[] args) {
		// Arg 0 Gene List file
		// Arg 1 Source: plaza, phyto, plazaNoConvert
		// Arg 2 source file
		// If plaza, Arg 3 idconverter file
		// Arg 4 inputindex
		// Arg 5 outputindex

		String fileIn = args[0];
		String str;
		ArrayList<String> geneList = new ArrayList<>();

		try(BufferedReader inFile = new BufferedReader(new FileReader(fileIn))){

			while ((str = inFile.readLine()) != null) {

				geneList.add(str);

			}
			if (args[1].equals("plazaNoConvert")) 
				addPlazaNoConverter(geneList, args[2], fileIn);
			else if(args[1].equals("phyto"))
				addPhyto(geneList, args[2], fileIn);
			else if (args[1].equals("plaza")) 
				addPlaza(geneList, args[2], fileIn, args[3], Integer.parseInt(args[4]), Integer.parseInt(args[5]));
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	public static void addPhyto(ArrayList<String> geneList, String phytoFile, String outputFile){

		String str;
		String[] arrayLineFile;
		HashMap<String, String> goDesc = new HashMap<>();
		try(BufferedReader inFile = new BufferedReader(new FileReader(phytoFile));PrintWriter outFile = new PrintWriter(new FileOutputStream(outputFile+".Phyout"))){

			while ((str = inFile.readLine()) != null) {

				arrayLineFile = str.split(",");

				goDesc.put(arrayLineFile[0], str);

			}

			for (String gene : geneList) {
				outFile.println(gene+"\t"+goDesc.get(gene));
			}			


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	}
	
	public static void addPlazaNoConverter(ArrayList<String> geneList, String plazaFile, String outputFile){

		String str;
		String[] arrayLineFile;
		HashMap<String, String> goDesc = new HashMap<>();
		try(BufferedReader inFile = new BufferedReader(new FileReader(plazaFile));PrintWriter outFile = new PrintWriter(new FileOutputStream(outputFile+".Plazaout"))){

			while ((str = inFile.readLine()) != null) {

				arrayLineFile = str.split("\t");

				goDesc.put(arrayLineFile[2], arrayLineFile[9]);

			}

			for (String gene : geneList) {
				outFile.println(gene+"\t"+goDesc.get(gene));
			}			


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	}

	public static void addPlaza(ArrayList<String> geneList, String plazaFile, String outputFile, 
			String plazaIndexFile, int inputIndex, int outputIndex){
		
		HashMap<String, String> plazaDesc = new HashMap<>();
		HashMap<String, String> idChange = new HashMap<>();
		String str;
		String[] arrayLineFile;
		
		try(BufferedReader inPlazaFile = new BufferedReader(new FileReader(plazaFile));
				BufferedReader inPlazaIndexFile = new BufferedReader(new FileReader(plazaIndexFile));
				PrintWriter outFile = new PrintWriter(new FileOutputStream(outputFile+"Plaza.out"))){

			while ((str = inPlazaFile.readLine()) != null) {
				arrayLineFile = str.split("\t");
				plazaDesc.put(arrayLineFile[0], arrayLineFile[16]);
			}
			
			while ((str = inPlazaIndexFile.readLine()) != null) {
				arrayLineFile = str.split("\t");
				idChange.put(arrayLineFile[inputIndex], arrayLineFile[outputIndex]);
			}
			
			String changedGene;
			
			for (String gene : geneList) {
				changedGene = idChange.get(gene);
				outFile.println(gene+"\t"+plazaDesc.get(changedGene));
			}		
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
