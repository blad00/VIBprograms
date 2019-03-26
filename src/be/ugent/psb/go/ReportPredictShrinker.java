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

/*Creates a short version of the final tables of new prediction merged in one table 
 * D:\DanielVIB\Maize\00Predictions\TrueMRRFullReportNewPredictions
D:\DanielVIB\maizeEnrich\superAnnotV3\go-basic.obo
 */

public class ReportPredictShrinker {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String inFolder = args[0];
		String filename;

		File file = null;
		File[] files = null;

		BufferedReader inFileExpTable = null;
		PrintWriter outFilePaper = null;
		PrintWriter outFileShort = null;
		String str;
		String arFi[];
		
		int i,u;
		String geneName;

		
				
		String targetGO_ID;
		String targetGO_Name;
		String predGO_ID;
		String predGO_Name;
		String semSim;
		String clr_Pval;
		String currentAnnot;
		String onlyName;
		
		if (inFolder != null && !inFolder.equals("")) {
			file = new File(inFolder);
			if (file.exists()) {
				files = file.listFiles();
			}
		}

		List<File> listFiles = new ArrayList<File>(Arrays.asList(files));
		
		
		
		try{
			
			HashMap<Integer, GoTerm> ontology = GoDescLoader.loadOntology(args[1]);
			
			
			outFilePaper = new PrintWriter(new FileOutputStream("GenePredForPub.tsv"));
			outFilePaper.println("TargetGO_ID"+"\t"+"TargetGO_Name"+"\t"+"Gene"+"\t"+"PredGO_ID"+"\t"+"PredGO_Name"+"\t"+"SemSim"+"\t"+"CLR_Pval"
								+"\t"+"CurrentAnnot");
			
			outFileShort = new PrintWriter(new FileOutputStream("GenePredShort.tsv"));
			outFileShort.println("TargetGO_ID"+"\t"+"TargetGO_Name"+"\t"+"Gene"+"\t"+"PredGO_ID"+"\t"+"PredGO_Name");
			
			for (i=0;i<listFiles.size();i++) {
				filename = listFiles.get(i).getAbsolutePath();
				inFileExpTable = new BufferedReader(new FileReader(filename));
				onlyName = listFiles.get(i).getName(); 
				
				//get Target info
				targetGO_ID = onlyName.split("ove")[0].split("O")[1];
				targetGO_Name = ontology.get(Integer.parseInt(targetGO_ID)).getName();
	
				//skip first line
				inFileExpTable.readLine();
				
				while ((str = inFileExpTable.readLine()) != null) {
					arFi = str.split("\t");
					//check if it is a valid CLR line, 9 means that was not detected in CLR
					if(arFi[4].equals("9"))
						continue;
					
					//get gene name
					geneName = arFi[2];
					//get pred info
					predGO_ID = arFi[1];
					predGO_Name = arFi[3];
					//semantic similarity
					semSim = arFi[0];
					//pval
					clr_Pval = arFi[4];
					
					currentAnnot = "";
					
					//check if there are annotations
					if(arFi.length>15){
						for(u=16;u<arFi.length;u+=3){
							currentAnnot = currentAnnot+"("+arFi[u]+")"+arFi[u+1]+"|";
						}
					}
					
					outFilePaper.println(targetGO_ID+"\t"+targetGO_Name+"\t"+geneName+"\t"+predGO_ID+"\t"+predGO_Name+
							"\t"+semSim+"\t"+clr_Pval+"\t"+currentAnnot);
					outFileShort.println(targetGO_ID+"\t"+targetGO_Name+"\t"+geneName+"\t"+predGO_ID+"\t"+predGO_Name);
					
				}

			}
			
			outFilePaper.close();
			outFileShort.close();
		
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}

}
