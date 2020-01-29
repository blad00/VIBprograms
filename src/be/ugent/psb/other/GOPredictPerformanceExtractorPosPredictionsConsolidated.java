package be.ugent.psb.other;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GOPredictPerformanceExtractorPosPredictionsConsolidated {

	/**
	 * This program will take the counts of positives predictions and will create one file whit the sum of all prediction for each network
	 * @param args
	 * arg 0 folder with the *POS files
	 * /home/dacru/Midas/research/projects/project_syngenta_c4/single_maize_plants/MoreDataSets/PearsonNetworks/ConsolidateCut/ResultsCutat1Pred/forPosPreds/samples_1.tsv.ENI_globalF.POS
	 * /home/dacru/Midas/research/projects/project_syngenta_c4/single_maize_plants/MoreDataSets/PearsonNetworks/ConsolidateCut/ResultsCutat1Pred/posPredCounts/abscissionPOSOUT.tsv
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String inFolder = args[0];
		//		String fileOut = args[1];//.replace(" ", "");
		String outFile = args[1];
		//change here for only one GO cat
		String godesc = null;
		String filename;
		String onlyname;


		File file = null;
		File[] files = null;

		String splitLine [];
		String str;
		
		int sumPosVals [] = null;


		
			
				
		try(PrintWriter outFilePOS = new PrintWriter(new FileOutputStream(outFile))){
			if (inFolder != null && !inFolder.equals("")) {
				file = new File(inFolder);
				//look for specific extension
				if (file.exists()) {
					files = file.listFiles(new FilenameFilter() {
						public boolean accept(File dir, String name) {
							return name.toString().toLowerCase().endsWith(".pos");
						}
					});

				}
			}

			List<File> listFiles = new ArrayList<File>(Arrays.asList(files));

			for (int i=0;i<listFiles.size();i++) {
				
				
				sumPosVals = new int[10];
				filename = listFiles.get(i).getAbsolutePath();
				onlyname = listFiles.get(i).getName().split("\\.")[0];

				try(BufferedReader inFile = new BufferedReader(new FileReader(filename))){
					while ((str = inFile.readLine()) != null) {
//								if(str.contains("C4 photosynthesis")){}
						splitLine = str.split("\t");
						
						//add values into arrays to operate them
						for(int o=0;o<10;o++){
							sumPosVals[o]=sumPosVals[o]+Integer.parseInt(splitLine[o+6]);
						}
						
					}
				}
				
				//print POSTIVES as a line
				outFilePOS.print(onlyname);
				for (int cpos : sumPosVals) {
					outFilePOS.print("\t"+cpos);	
				}
				outFilePOS.println();

			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

			


	}


}


