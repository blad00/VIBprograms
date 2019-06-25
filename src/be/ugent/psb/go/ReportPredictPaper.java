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

public class ReportPredictPaper {
/*
 *  * /home/dfcruz/Midas/biocomp/groups/group_esb/dacru/Maize/reMapV3/PingoCheck/PingORunComparison/CLR_allPlants_FDR_0.001_bingo0.01_Run/CLRpredOutSemSim
   /home/dfcruz/Midas/biocomp/groups/group_esb/dacru/Maize/reMapV3/PiNGO_Maize_Ext/go-basic.obo
 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		PrintWriter outAllFile = new PrintWriter(new FileOutputStream("/home/dfcruz/Midas/biocomp/groups/group_esb/dacru/Maize/reMapV3/PingoCheck/PingORunComparison/merged/AllCLR207.tsv"));){
//		outAllFile.println("TargetGO_ID"+"\t"+"TargetGO_Name"+"\t"+"GeneName"+"\t"+"PredGO_ID"+"\t"+"PredGO_Name"+"\t"+"CLR_Pval"+"\t"+"CurrentAnnot");
		
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
		String onlyName = null;
		
		if (inFolder != null && !inFolder.equals("")) {
			file = new File(inFolder);
			if (file.exists()) {
				files = file.listFiles();
			}
		}

		List<File> listFiles = new ArrayList<File>(Arrays.asList(files));

		try{
			
			HashMap<Integer, GoTerm> ontology = GoDescLoader.loadOntology(args[1]);
			
			
			outFilePaper = new PrintWriter(new FileOutputStream(inFolder+"GenePredForPub.tsv"));
			outFilePaper.println("TargetGO_ID"+"\t"+"TargetGO_Name"+"\t"+"Gene"+"\t"+"PredGO_ID"+"\t"+"PredGO_Name"+"\t"+"SemSim"+"\t"+"CLR_Pval"
								+"\t"+"CurrentAnnot");
			
			outFileShort = new PrintWriter(new FileOutputStream(inFolder+"GenePredShort.tsv"));
			outFileShort.println("TargetGO_ID"+"\t"+"TargetGO_Name"+"\t"+"Gene"+"\t"+"PredGO_ID"+"\t"+"PredGO_Name");
			
			for (i=0;i<listFiles.size();i++) {
				filename = listFiles.get(i).getAbsolutePath();
				inFileExpTable = new BufferedReader(new FileReader(filename));
				onlyName = listFiles.get(i).getName();
				
				//get Target info
				targetGO_ID = onlyName.split("\\.")[0];
				targetGO_ID = targetGO_ID.split("O")[1];
				targetGO_Name = ontology.get(Integer.parseInt(targetGO_ID)).getName();
	
				//skip first line
				inFileExpTable.readLine();
				
				while ((str = inFileExpTable.readLine()) != null) {
					arFi = str.split("\t");
					//check if is not empty prediction
					if(arFi[1].equals("NA"))
						break;
					
					//get gene name
					geneName = arFi[1];
					//get pred info
					predGO_ID = arFi[2];
					predGO_Name = arFi[3];
					//semantic similarity
					semSim = arFi[0];
					//pval
					clr_Pval = arFi[4];
					if(arFi.length==9)
						currentAnnot = arFi[8];
					else
						currentAnnot = null;
					
					outFilePaper.println(targetGO_ID+"\t"+targetGO_Name+"\t"+geneName+"\t"+predGO_ID+"\t"+predGO_Name+
							"\t"+semSim+"\t"+clr_Pval+"\t"+currentAnnot);
					outFileShort.println(targetGO_ID+"\t"+targetGO_Name+"\t"+geneName+"\t"+predGO_ID+"\t"+predGO_Name);
					
				}
				inFileExpTable.close();
			}
			
			outFilePaper.close();
			outFileShort.close();
		}catch (Exception e) {
			// TODO: handle exception
			
			e.printStackTrace();
			System.out.println(onlyName);
		}	
	}

}
