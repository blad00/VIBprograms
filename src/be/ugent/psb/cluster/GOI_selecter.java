package be.ugent.psb.cluster;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class GOI_selecter {

	/**
	 * @param args
	 * @throws Exception 
	 */
	//This program takes all the output from CAST and analyses all the files in a folder regarding a list of GOI to calculates its proportions.
	//If the file has an specific number of GOI will be saved in format to be enriched.
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		//arg 0 GOI table
		//arg 1 Report File
		//arg 2 Tables dir
		
		BufferedReader inFileExpTable = null;
		BufferedReader inFileGOI = new BufferedReader(new FileReader(args[0]));
		PrintWriter outFile = new PrintWriter(new FileOutputStream(args[1]));
		
		String str = null;

		Set <String> listGOI=new TreeSet<String>();

		String argsGOIfile[];
		String argsTablefile[];
		ArrayList<String> geneList = new ArrayList<>();
		
		PrintWriter outFileGenes = null;

		//load GOIs
		boolean header = true;
		while ((str = inFileGOI.readLine()) != null) {
			//skip header
			if(header){
				header = false;
				continue;
			}
			argsGOIfile = str.split("\t");
			listGOI.add(argsGOIfile[0]);
		}

		//take all table files
		File file = null;
		File[] files = null;
		if (args[2] != null && !args[2].equals("")) {
			file = new File(args[2]);
			if (file.exists()) {
				files = file.listFiles();
			}
		}

		boolean toSave = false;
//		boolean toSave = true;
		double numGOI = 0;
		double totalGenes = 0;
		outFile.println("FileName"+"\t"+"Total Genes"+"\t"+"Total GOI"+"\t"+"%");

		List<File> listFiles = new ArrayList<File>(Arrays.asList(files));
		for (int i=0;i<listFiles.size();i++) {
			String filename = listFiles.get(i).getAbsolutePath();
			inFileExpTable = new BufferedReader(new FileReader(filename));

			//Skip Header
			inFileExpTable.readLine();

			while ((str = inFileExpTable.readLine()) != null) {
				argsTablefile=str.split("\t");
				geneList.add(argsTablefile[1]);
				totalGenes++;
/*				if(listGOI.contains(argsTablefile[1])){
					toSave=true;
					numGOI++;
				}	*/
			}
			
			if(toSave){
				
				outFile.println(filename+"\t"+totalGenes+"\t"+numGOI+"\t"+(numGOI/totalGenes)*100);
				
				if(totalGenes>=15){

					outFileGenes = new PrintWriter(new FileOutputStream(args[2]+File.separator+"GeneNames_"+listFiles.get(i).getName()));
					for (String geneTmp : geneList) {
						outFileGenes.println(geneTmp);					
					}
					
					outFileGenes.close();
				}
				
			}
			
			geneList.clear();

			toSave = false;
			numGOI = 0;
			totalGenes = 0;

		}
		
		inFileGOI.close();
		inFileExpTable.close();
		outFile.close();
		

	}

}
