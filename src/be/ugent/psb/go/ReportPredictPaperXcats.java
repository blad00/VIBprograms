package be.ugent.psb.go;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ReportPredictPaperXcats {
/*
this program take a input list with a division of GO categories, to create a file for each division.
 * arg 0 Folder with predictions x file
 * arg 1 file with GO division
 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		String inFolder = args[0];
		String divGOfile = args[1];
		String filename;

		File file = null;
		File[] files = null;

		BufferedReader inFileDivGoCats = null;
		
		BufferedReader inFileExpTable = null;
		PrintWriter outFilePaper = null;
		
		String str = null;
		String arFi[];
		
		String cuGoCatFile = null;
		String cuGoCatCod = null;
		String cuGoCatName;
		
		int i;
		String geneName;

		
				
		String targetGO_ID;
		String predGO_ID;
		String predGO_Name;
		//String semSim;
		String pval;
		String currentAnnot;
		String onlyName = null;
		
		if (inFolder != null && !inFolder.equals("")) {
			file = new File(inFolder);
			if (file.exists()) {
				files = file.listFiles();
			}
		}
		//load all single GO cats files
		List<File> listFiles = new ArrayList<File>(Arrays.asList(files));
		//more useful as hashmap
		HashMap<String, String> goCats = new HashMap<String,String>();
		for (i=0;i<listFiles.size();i++) {
			
			filename = listFiles.get(i).getAbsolutePath();
			//get Target info
			onlyName = listFiles.get(i).getName();
			targetGO_ID = onlyName.split("\\.")[0];
			targetGO_ID = targetGO_ID.split("O")[1];
			goCats.put(targetGO_ID, filename);
		}
		
		

		try{
			//iterate the div cats file
			inFileDivGoCats = new BufferedReader(new FileReader(divGOfile));
			while ((str = inFileDivGoCats.readLine()) != null) {
				arFi = str.split("\t");
				if(arFi[0].equals("GO ID")) {
					if(outFilePaper!=null)
						outFilePaper.close();
					
					outFilePaper = new PrintWriter(new FileOutputStream(inFolder+arFi[1]+".tsv"));
					outFilePaper.println("TargetGO_ID"+"\t"+"TargetGO_Name"+"\t"+"Gene"+"\t"+"PredGO_ID"+"\t"+"PredGO_Name"+"\t"+"Pval"+"\t"+"CurrentAnnot");
					continue;
				}
				//get file with current cat
				cuGoCatCod = arFi[0];
				cuGoCatFile = goCats.get(cuGoCatCod);
				cuGoCatName = arFi[1];
				
				inFileExpTable = new BufferedReader(new FileReader(cuGoCatFile));
				//skip first line
				inFileExpTable.readLine();
				
				while ((str = inFileExpTable.readLine()) != null) {
					
					
					arFi = str.split("\t");
					//check if is not empty prediction
					if(arFi[1].equals("NA"))
						break;
					//Modified for No SemSim
					//get gene name
					geneName = arFi[0];
					//get pred info
					predGO_ID = arFi[1];
					predGO_Name = arFi[2];
					//semantic similarity
					//semSim = arFi[0];
					//pval
					pval = arFi[3];
					if(arFi.length==8)
						currentAnnot = arFi[7];
					else
						currentAnnot = null;
					
					outFilePaper.println(cuGoCatCod+"\t"+cuGoCatName+"\t"+geneName+"\t"+predGO_ID+"\t"+predGO_Name+"\t"+pval+"\t"+currentAnnot);
					
				}
				inFileExpTable.close();
					
			}
			inFileDivGoCats.close();
			outFilePaper.close();
			
			
			
		}catch (Exception e) {
			// TODO: handle exception
			
			e.printStackTrace();
			System.out.println(str);
		}	
	}

}
