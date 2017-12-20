
package be.ugent.psb.annotFile;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RankerPerFunctions {
/*
 * This program creates a consolidate from different predictions for each GO cat
 */
	public static void main2(String[] args) {
		// TODO Auto-generated method stub
		try(BufferedReader file0 = new BufferedReader(new FileReader("D:/DanielVIB/Maize/PredictGenes/PingORunComparison/CLR_allPlants_FDR_0.001_bingo0.01_Run/runFilter/GO3/CLR_0.001EnigmaFormatOnlyPos.txt.pgo"));
				BufferedReader file1 = new BufferedReader(new FileReader("D:/DanielVIB/Maize/PredictGenes/PingORunComparison/Enigma_allPlants_FDR_0.001_bingo0.01_Run/runFilter/GO3/AllPlantsENIGMA_FC.txt_filter0.001graphOnlyPos.pgo"));
				BufferedReader file2 = new BufferedReader(new FileReader("D:/DanielVIB/Maize/PredictGenes/PingORunComparison/Pearson_allPlants_corr0.7_bingo0.01_Run/runFilter/GO3/corr_Graph_0.7.tsv.pgo"));
				PrintWriter outFile = new PrintWriter(new FileOutputStream("D:/DanielVIB/Maize/PredictGenes/PingORunComparison/GO3.tsv"))){
			
			ArrayList<BufferedReader> fil = new ArrayList<>();
			fil.add(file0);
			fil.add(file1);
			fil.add(file2);
			
			HashMap<String, ArrayList<Double>> m = combineRanking(fil);
			
			ArrayList<String> titles = new ArrayList<>(Arrays.asList("CLR","ENIGMA","PEARSON"));;
			
			printCombineCat(m, titles, outFile);
			
		 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		String goCats = "D:/DanielVIB/Maize/PredictGenes/PingORunComparison/GO-ID.txt";
//		String dir1 = "D:/DanielVIB/Maize/PredictGenes/PingORunComparison/CLR_allPlants_FDR_0.001_bingo0.01_Run/runFilter/GO";
//		String dir2 = "D:/DanielVIB/Maize/PredictGenes/PingORunComparison/Enigma_allPlants_FDR_0.001_bingo0.01_Run/runFilter/GO";
//		String dir3 = "D:/DanielVIB/Maize/PredictGenes/PingORunComparison/Pearson_allPlants_corr0.7_bingo0.01_Run/runFilter/GO";
		
		String goCats = "/home/dfcruz/Midas/biocomp/groups/group_esb/dacru/Maize/reMapV3/PingoCheck/PingORunComparison/GO-ID.txt";
		String dir1 = "/home/dfcruz/Midas/biocomp/groups/group_esb/dacru/Maize/reMapV3/PingoCheck/PingORunComparison/CLR_allPlants_FDR_0.001_bingo0.01_Run/runFilter/GO";
		String dir2 = "/home/dfcruz/Midas/biocomp/groups/group_esb/dacru/Maize/reMapV3/PingoCheck/PingORunComparison/Enigma_allPlants_FDR_0.001_bingo0.01_Run/runFilter/GO";
		String dir3 = "/home/dfcruz/Midas/biocomp/groups/group_esb/dacru/Maize/reMapV3/PingoCheck/PingORunComparison/Pearson_allPlants_corr0.7_bingo0.01_Run/runFilter/GO";
		
		String str;
		
		try(BufferedReader file0 = new BufferedReader(new FileReader(goCats))){
			ArrayList<BufferedReader> fil = new ArrayList<>();
			while ((str = file0.readLine()) != null) {
				
				BufferedReader file1 = new BufferedReader(new FileReader(dir1+str+"/CLR_0.001EnigmaFormatOnlyPos.txt.pgo"));
				BufferedReader file2 = new BufferedReader(new FileReader(dir2+str+"/AllPlantsENIGMA_FC.txt_filter0.001graphOnlyPos.pgo"));
				BufferedReader file3 = new BufferedReader(new FileReader(dir3+str+"/corr_Graph_0.7.tsv.pgo"));
				
//				PrintWriter outFile = new PrintWriter(new FileOutputStream("D:/DanielVIB/Maize/PredictGenes/PingORunComparison/GO"+str+".tsv"));
				PrintWriter outFile = new PrintWriter(new FileOutputStream("/home/dfcruz/Midas/biocomp/groups/group_esb/dacru/Maize/reMapV3/PingoCheck/PingORunComparison/merged/GO"+str+".tsv"));
				
				fil.add(file1);
				fil.add(file2);
				fil.add(file3);
				
				HashMap<String, ArrayList<Double>> m = combineRanking(fil);
				
				ArrayList<String> titles = new ArrayList<>(Arrays.asList("CLR","ENIGMA","PEARSON"));;
				
				printCombineCat(m, titles, outFile);		
				
				fil.clear();
				
				file1.close();
				file2.close();
				file3.close();
				
				outFile.close();
			}
			
			
			
				
		 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static HashMap<String, ArrayList<Double>> combineRanking(ArrayList<BufferedReader> files) throws Exception{
		
		String str;
		String argsFile[];
		String geneName;
		ArrayList<Double> info;
		
		
		HashMap<String, ArrayList<Double>> allRanks = new HashMap<>();

		int ile = 0;
		
		 for (BufferedReader file : files) {
			
		
		
			while ((str = file.readLine()) != null) {
				argsFile=str.split("\t");
				
				if(argsFile[0].equalsIgnoreCase("GO ID"))
					break;
			}
			
			while ((str = file.readLine()) != null) {
				argsFile=str.split("\t");
				geneName = argsFile[0]+"\t"+argsFile[2]+"\t"+argsFile[1];
				if(!allRanks.containsKey(geneName)){
					 info = new ArrayList<>();
					 for (int i=0;i<files.size();i++) {
						info.add(9.0);
					}
					 info.set(ile,Double.parseDouble(argsFile[5]));
					 allRanks.put(geneName, info);
				}else{
					info = allRanks.get(geneName);
					info.set(ile,Double.parseDouble(argsFile[5]));
				}
				
			}
			
			ile++;
			
		 }
		 
		 
		return allRanks;
		
	}
	
	public static void printCombineCat(HashMap<String, ArrayList<Double>> map, ArrayList<String> titles,PrintWriter outFile){
		ArrayList<Double> info;
		String geneName;
		outFile.print("GO_ID"+"\t"+"Gene"+"\t"+"Desc");
		for (String title : titles) {
			outFile.print("\t");
			outFile.print(title);
		}
		
		outFile.println();
		
		for (Map.Entry<String, ArrayList<Double>> entry : map.entrySet()) {
//		    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			geneName = entry.getKey();
			info = entry.getValue();
			outFile.print(geneName);
			
			for (Double pval : info) {
//				if(pval==null){
//					outFile.print("\t");
//					outFile.print(11);
//				}else{
					outFile.print("\t");
					outFile.print(pval);
//				}
			}
			
			outFile.println();
		}
		
	}
	

}


