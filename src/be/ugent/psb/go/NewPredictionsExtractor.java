package be.ugent.psb.go;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class NewPredictionsExtractor {
	/* arg 0 dir with files predictions
	 * arg 1 ontology file
	 * arg 2 annotation file
	 * arg 3 convertion table
	 * arg 4 additional info
	 * arg 5 Interest GOs coma separated
*/
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		String goCats = "D:/DanielVIB/Maize/PredictGenes/PingORunComparison/GO-ID.txt";
//		String dir1 = "D:/DanielVIB/Maize/PredictGenes/PingORunComparison/CLR_allPlants_FDR_0.001_bingo0.01_Run/runFilter/GO";
		
		
		String goCats = "/home/dfcruz/Midas/biocomp/groups/group_esb/dacru/Maize/reMapV3/PingoCheck/PingORunComparison/GO-ID.txt";
		String dir1 = args[0];

		
		String str;
		String argsFile[];
		try(BufferedReader fileGoCats = new BufferedReader(new FileReader(goCats));
			PrintWriter outAllFile = new PrintWriter(new FileOutputStream("/home/dfcruz/Midas/biocomp/groups/group_esb/dacru/Maize/reMapV3/PingoCheck/PingORunComparison/merged/AllCLR207.tsv"));){
			outAllFile.println("TargetGO_ID"+"\t"+"TargetGO_Name"+"\t"+"GeneName"+"\t"+"PredGO_ID"+"\t"+"PredGO_Name"+"\t"+"CLR_Pval"+"\t"+"CurrentAnnot");
			while ((str = fileGoCats.readLine()) != null) {
				
				BufferedReader file1 = new BufferedReader(new FileReader(dir1+str+"/CLR_0.001EnigmaFormatOnlyPos.txt.pgo"));
//				PrintWriter outFile = new PrintWriter(new FileOutputStream("D:/DanielVIB/Maize/PredictGenes/PingORunComparison/GO"+str+".tsv"));
				PrintWriter outGoFile = new PrintWriter(new FileOutputStream("/home/dfcruz/Midas/biocomp/groups/group_esb/dacru/Maize/reMapV3/PingoCheck/PingORunComparison/merged/GO"+str+".tsv"));
				outGoFile.println("GeneName"+"\t"+"PredGO_ID"+"\t"+"PredGO_Name"+"\t"+"CLR_Pval"+"WithTF"+"OtherNames"+"CurrentAnnot");
				while ((str = file1.readLine()) != null) {
					argsFile=str.split("\t");
					
					if(argsFile[0].equalsIgnoreCase("GO ID"))
						break;
				}
				
				while ((str = file1.readLine()) != null) {
					argsFile=str.split("\t");
					
					
				}	
				
			}
			
			
			
			
			 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
