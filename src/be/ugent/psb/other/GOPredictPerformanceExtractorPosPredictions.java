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

public class GOPredictPerformanceExtractorPosPredictions {

	/**
	 * This program will take the counts of positives predictions and will create a separated file for each category
	 * @param args
	 * arg 0 folder with the *POS files
	 * arg 1 GO category
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String inFolder = args[0];
		//		String fileOut = args[1];//.replace(" ", "");
		String godescFile = args[1];
		//change here for only one GO cat
		String godesc = null;
		String filename;
		String onlyname;


		File file = null;
		File[] files = null;

		String splitLine [];
		String str;
		String posVals [] = null;


		try(BufferedReader inDescFile = new BufferedReader(new FileReader(godescFile))){
			while ((godesc = inDescFile.readLine()) != null) {
				
				try(PrintWriter outFilePOS = new PrintWriter(new FileOutputStream(inFolder+file.separatorChar+godesc+"POSOUT.tsv"))){
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
						posVals = new String[10];
						filename = listFiles.get(i).getAbsolutePath();
						onlyname = listFiles.get(i).getName().split("\\.")[0];

						try(BufferedReader inFile = new BufferedReader(new FileReader(filename))){
							while ((str = inFile.readLine()) != null) {
//								if(str.contains("C4 photosynthesis")){}
								splitLine = str.split("\t");
								if(splitLine[2].equals(godesc)){
									

									//add values into arrays to operate them
									for(int o=0;o<10;o++){
										posVals[o]=splitLine[o+6];
									}

									//print POSTIVES 
									outFilePOS.print(onlyname);
									for (String cpos : posVals) {
										outFilePOS.print("\t"+cpos);	
									}
									outFilePOS.println();

								}	

							}
						}

					}
				}catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}


}


