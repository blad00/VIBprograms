package be.ugent.psb.cluster;

import java.io.BufferedReader;
import java.io.File;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class GOIExtractor2Enrich {

	/**
	 * @param args
	 * @throws Exception 
	 */
//	This program pass through all gene files and generates one consolidate without duplicates
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		String inFolder = args[0];
		String fileOut = args[1];
		Set <String> listGOI = new TreeSet<String>();
		String filename;
		BufferedReader inFileExpTable = null;
		String str;
		String argsFile[];
		
		
		//take all files
		File file = null;
		File[] files = null;
		if (inFolder != null && !inFolder.equals("")) {
			file = new File(inFolder);
			if (file.exists()) {
				files = file.listFiles();
			}
		}
		
		
		//Go through all files extracting  (Genes) located in the first column
		List<File> listFiles = new ArrayList<File>(Arrays.asList(files));
		
		PrintWriter outFileAll = new PrintWriter(new FileOutputStream(fileOut+"all"));
		
		for (int i=0;i<listFiles.size();i++) {
			filename = listFiles.get(i).getAbsolutePath();
			if(!filename.endsWith(".txt"))
				continue;
			
			inFileExpTable = new BufferedReader(new FileReader(filename));

			//Skip Header
//			inFileExpTable.readLine();
			outFileAll.println(filename);
			while ((str = inFileExpTable.readLine()) != null) {
				argsFile=str.split("\t");
				outFileAll.println(argsFile[0]);
				if(argsFile[0]!=null&&!argsFile[0].equals("")&&!listGOI.contains(argsFile[0])){
					listGOI.add(argsFile[0]);
				}	
			}
			
			inFileExpTable.close();
		}
		
		try(PrintWriter outFile = new PrintWriter(new FileOutputStream(fileOut))){

			for(String goi:listGOI){
				outFile.println(goi);
			}
			
		}
		
		outFileAll.close();
		
	}

}
