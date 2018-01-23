package be.ugent.psb.other;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;

public class NetworkPerformanceMerger {
	/**
	 * This program will merge the process specific info from predictions made by PINGO 
	 * @param args
	 * arg 0 infile
	 * arg 1 init index to search
	 * arg 3... keywords
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try(BufferedReader inRec = new BufferedReader(new FileReader(args[0]+"ProcessesRecall.txt"));
				BufferedReader inPrec = new BufferedReader(new FileReader(args[0]+"ProcessesPrecision.txt"));
				BufferedReader inFmes = new BufferedReader(new FileReader(args[0]+"ProcessesFmeasure.txt"));
				PrintWriter outFile = new PrintWriter(new FileOutputStream(args[0]+args[1]+".tsv"))){
			
			
			String strRec,strPrec,strFmes = null;
			
			String arRec[];
			String descAnnot = "";
			int i;
			boolean firstLine = true;
			boolean hayRecall = false;
			
			
			
			while ((strRec = inRec.readLine()) != null&&(strPrec = inPrec.readLine()) != null&&(strFmes = inFmes.readLine()) != null) {
				
				//print header
				if(firstLine){
					outFile.println("Network"+"\t"+"Measure"+"\t"+strRec);
					firstLine = false;
					continue;
				}
				
				arRec=strRec.split("\t");
				
				for(i = 1;i<arRec.length;i++){
					if(!arRec[i].equals("0")){
						hayRecall = true;
						break;
					}	
				}
				
				if(!hayRecall)
					continue;
								
				outFile.print(args[1]+"\t"+"Recall"+"\t");
				outFile.println(strRec);
				
				outFile.print(args[1]+"\t"+"Precision"+"\t");
				outFile.println(strPrec);
				
				outFile.print(args[1]+"\t"+"Fmeasure"+"\t");
				outFile.println(strFmes);
				
				hayRecall = false;
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	

}
