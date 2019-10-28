package be.ugent.psb.network;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class PositivesExtractor {
	/*
	 * this program goes trough all *.POS files and will generate a single file with one value of total positives per network
	 * /home/dacru/Midas/research/projects/project_syngenta_c4/single_maize_plants/MoreDataSets/PearsonNetworks/ConsolidateCut/ResultsCutat1Pred/forPosPreds/
	 */

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		String inFolder = args[0];
		String fileOut = args[1];
		String filename;
		String justName;
		BufferedReader inFileExpTable = null;
		String str;
		String argsFile[];
		int numPos = 0;
		
		
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
		
		PrintWriter outFileAll = new PrintWriter(new FileOutputStream(fileOut));
		//title
		outFileAll.println("Network\tNumPos");
		
		for (int i=0;i<listFiles.size();i++) {
			filename = listFiles.get(i).getAbsolutePath();
			justName = listFiles.get(i).getName().toString().split("\t")[0];
			
//			System.out.println(filename);
//			System.out.println(justName);
			
			if(!filename.endsWith(".POS"))
				continue;
		
			inFileExpTable = new BufferedReader(new FileReader(filename));
			
			outFileAll.print(justName+"\t");
			while ((str = inFileExpTable.readLine()) != null) {
//				System.out.println(str);
				argsFile=str.split("\t");
				if(!argsFile[6].equalsIgnoreCase("NaN"))
					numPos += Double.parseDouble(argsFile[6]);
			}
			outFileAll.println(numPos);
			numPos = 0;

			inFileExpTable.close();
			
			//break;

		}
		
		outFileAll.close();
		
	}

}
