package be.ugent.psb.other;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// this program will find the best enrichment from a path
public class BestEnrichFinder {

	// Arg 0 enriched path
	// Arg 1 desired pvalue
	// Arg 2 key word
	// Arg 3 lowest pvalue
	// Arg 4 output file

	public static void main(String[] args)  {


		String inFolder = args[0];
		String filename;

		File file = null;
		File[] files = null;

		BufferedReader inFileExpTable = null;
		String str;
		String argsFile[];
		
		double desiPvalue = Double.parseDouble(args[1]);
		double lowPvalue = Double.parseDouble(args[3]);
		
		double currentPvalue;
		String currentCategory;
		
		String keyWord = args[2];
		


		if (inFolder != null && !inFolder.equals("")) {
			file = new File(inFolder);
			if (file.exists()) {
				files = file.listFiles();
			}
		}

		List<File> listFiles = new ArrayList<File>(Arrays.asList(files));
		try{
			PrintWriter outFile = new PrintWriter(new FileOutputStream(args[4]));
			
			for (int i=0;i<listFiles.size();i++) {
				filename = listFiles.get(i).getAbsolutePath();
				inFileExpTable = new BufferedReader(new FileReader(filename));

				while ((str = inFileExpTable.readLine()) != null) {
					argsFile=str.split("\t");
					
					if(argsFile[0].equalsIgnoreCase("# GO_code"))
						break;
				}
				
				while ((str = inFileExpTable.readLine()) != null) {
					argsFile=str.split("\t");
					currentPvalue = Double.parseDouble(argsFile[5]);
					if(currentPvalue<=desiPvalue){
						outFile.println(listFiles.get(i).getName()+"\t"+str);
					}else{
						currentCategory = argsFile[6];
						if(currentCategory.contains(keyWord)&&currentPvalue<=lowPvalue){
							outFile.println(listFiles.get(i).getName()+"\t"+str);
						}
					}
				
				}
				
				
				inFileExpTable.close();

				


			}	
			
			outFile.close();

		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}

}
