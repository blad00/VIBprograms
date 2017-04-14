package be.ugent.psb.other;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;



public class OriCounterParam {

	/**
	 * @param args
	 * @throws Exception 
	 */
	
	static ArrayList<String> labelRows = new ArrayList<>();
	static ArrayList<String [] > listData = new ArrayList<>();
	static ArrayList <String> labelColumns =new ArrayList<>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String str = null;
		String param = args[2];
		String arrayLineFile[];
		
		double [][] matrixStr  = null;
		
		boolean header = true;
		
		try(BufferedReader inFile = new BufferedReader(new FileReader(args[0]));PrintWriter outFile = new PrintWriter(new FileOutputStream(args[1]))){
			
			while ((str = inFile.readLine()) != null) {
				
				arrayLineFile = str.split("\t");
				
				if(header){
					for (String sd : arrayLineFile) {
						if(!sd.equals(""))
						labelColumns.add(sd);
					}
					 
					header = false;
				}
				
				labelRows.add(arrayLineFile[0]);
				
				listData.add(arrayLineFile);
											
			}
			
			matrixStr = operRowsNoParam(outFile, true, param, matrixStr);
			
			operColsNoParam(outFile, true, param, matrixStr);
			

			
			
		}catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			// TODO: handle exception
		}
		
		
		
		
	}
	
	
	public static double [][] operRowsNoParam(PrintWriter outFile, boolean skipHeader, String param, double [][] matrixStr){
		
		
		int i = 0;
		int j = 0;
		
		int numValidElements = 0;
		double sum = 0;
		double value = 0;
		double avg = 0;
		//Load in matrix for further counts
		
		outFile.println("Rows------------------------------------------");
		outFile.println("Name"+"\t"+"SumValidElements"+"\t"+"NumValidElements"+"\t"+"TotalElements"+"\t"+"AvgValidElements");
		
						
		for (String strRow[] : listData) {
						
			
			if(skipHeader){
				skipHeader=false;
				matrixStr = new double [labelRows.size()][labelColumns.size()];
				continue;
			}
			
			outFile.print(strRow[0]+"\t");
			
			for(j=1;j<strRow.length;j++){
				
				if(!strRow[j].equals(param)){
					value=Double.parseDouble(strRow[j]);
					matrixStr [i][j-1]=value;
					numValidElements++;
					sum+=value;
				}else{
					matrixStr [i][j-1]=0;
				}
			}
			
			i++;
			avg = sum/numValidElements;
			
			outFile.print(sum+"\t"+numValidElements+"\t"+(strRow.length-1)+"\t"+avg);
			outFile.println();
			
			numValidElements = 0;
			sum = 0;
			value = 0;
			avg = 0;
					
												
		}
		
		return matrixStr;
		
	}
	
	public static void operColsNoParam(PrintWriter outFile, boolean skipHeader, String param, double [][] matrixStr){
		int i= 0;
		int j = 0;
		int numValidElements = 0;
		double sum = 0;
		double value = 0;
		double avg = 0;
		
		outFile.println("Columns------------------------------------------");
		outFile.println("Name"+"\t"+"SumValidElements"+"\t"+"NumValidElements"+"\t"+"TotalElements"+"\t"+"AvgValidElements");
		for (j=0;j<labelColumns.size();j++) {
			outFile.print(labelColumns.get(j)+"\t");
			
			for(i=0;i<labelRows.size();i++){
				if(matrixStr[i][j]!=0){
					value=matrixStr[i][j];
					
					numValidElements++;
					sum+=value;
				}			
				
				
			}
			
			avg = sum/numValidElements;
			
			outFile.print(sum+"\t"+numValidElements+"\t"+labelRows.size()+"\t"+avg);
			outFile.println();
			
			numValidElements = 0;
			sum = 0;
			value = 0;
			avg = 0;
			
		}
	}

}
