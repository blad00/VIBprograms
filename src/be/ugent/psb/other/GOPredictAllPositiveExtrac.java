package be.ugent.psb.other;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GOPredictAllPositiveExtrac {
	/*
	 * This program will take all the ROC files which have the prediction across the whole 
	 * ontology and filter out only the rage of 10E-2 to 10E-11 to be plotted
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String inFolder = args[0];
		String outFile = args[1];
		int whi = Integer.parseInt(args[2]);//for deciding which one to use 0=all,1=true,2=false
		String filename;
		String onlyname;


		File file = null;
		File[] files = null;

		String splitLine [];
		String str;
		double threshold = 0.01;
		double thres;
		double pval;
		
		int sumPosVals [] = null;
		boolean inAnnot;
		 
		
		
		try(PrintWriter outFilePOS = new PrintWriter(new FileOutputStream(outFile))){
			if (inFolder != null && !inFolder.equals("")) {
				file = new File(inFolder);
				//look for specific extension
				if (file.exists()) {
				//	files = file.listFiles();
					files = file.listFiles(new FilenameFilter() {
						public boolean accept(File dir, String name) {
							return name.toString().endsWith(".txtROC");
						}
					});

				}
			}

			List<File> listFiles = new ArrayList<File>(Arrays.asList(files));
			
			for (int i=0;i<listFiles.size();i++) {
//				System.out.println("File: "+i);
				sumPosVals = new int[10];
				filename = listFiles.get(i).getAbsolutePath();
				onlyname = listFiles.get(i).getName().split("\\.")[0];
				
				try(BufferedReader inFile = new BufferedReader(new FileReader(filename))){
					//skip header
					inFile.readLine();
					while ((str = inFile.readLine()) != null) {
						splitLine = str.split("\t");
						//add values into arrays
						pval = Double.parseDouble(splitLine[2]);
						inAnnot = Boolean.parseBoolean(splitLine[3]);
						int ind = 0;
						//check if pval is in which range
						//it is accumulative
						for(thres = threshold; thres > Math.pow(10,-11); thres/=10.0){
							switch (whi){
								case 0:
									if(pval<=thres)
										sumPosVals[ind]=sumPosVals[ind]+1;
									break;
								case 1:
									if(pval<=thres&&inAnnot==true)
										sumPosVals[ind]=sumPosVals[ind]+1;
									break;
								case 2:
									if(pval<=thres&&inAnnot==false)
										sumPosVals[ind]=sumPosVals[ind]+1;
									break;
							}
							
							//if(pval<=thres&&inAnnot==true)
//							if(pval<=thres)
//								sumPosVals[ind]=sumPosVals[ind]+1;
							
							ind++;
						}
						
					}
					//print POSTIVES as a line
					outFilePOS.print(onlyname);
					for (int cpos : sumPosVals) {
						outFilePOS.print("\t"+cpos);	
					}
					outFilePOS.println();
				}
			}
				
				
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
