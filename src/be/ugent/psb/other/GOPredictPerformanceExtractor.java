package be.ugent.psb.other;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GOPredictPerformanceExtractor {

	/**
	 * This program will take the performance output from PINGO agronomics.PrecisionRecall and creates 3 files per network, precition. recal and Fmeasure
	 * @param args
	 * arg 0 folder with the *_pingo.txt files
	 * arg 1 GO category
	 * arg 2 output prefix

	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String inFolder = args[0];
		String fileOut = args[2];
		String godesc = args[1];
		String filename;
		String onlyname;

		File file = null;
		File[] files = null;

		String splitLine [];
		String str;
		double recall [] = null;
		double precision [] = null;
		double fmeasure [] = null;

		try(PrintWriter outFileREC = new PrintWriter(new FileOutputStream(inFolder+file.separatorChar+fileOut+"REC.tsv"));
				PrintWriter outFilePREC = new PrintWriter(new FileOutputStream(inFolder+file.separatorChar+fileOut+"PREC.tsv"));
				PrintWriter outFileFMES = new PrintWriter(new FileOutputStream(inFolder+file.separatorChar+fileOut+"FMES.tsv"))){
			if (inFolder != null && !inFolder.equals("")) {
				file = new File(inFolder);
				//look for specific extension
				if (file.exists()) {
					files = file.listFiles(new FilenameFilter() {
						public boolean accept(File dir, String name) {
							return name.toString().toLowerCase().endsWith("_pingo.txt");
						}
					});

				}
			}

			List<File> listFiles = new ArrayList<File>(Arrays.asList(files));

			for (int i=0;i<listFiles.size();i++) {
				recall = new double[10];
				precision = new double[10];
				fmeasure = new double[10];
				filename = listFiles.get(i).getAbsolutePath();
				onlyname = listFiles.get(i).getName().split("_")[0];

				try(BufferedReader inFile = new BufferedReader(new FileReader(filename))){
					while ((str = inFile.readLine()) != null) {
//						if(str.contains("C4 photosynthesis")){
						if(str.contains(godesc)){
							splitLine = str.split("\t");

						}	

					}
				}
				
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}


}


