package be.ugent.psb.clr;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CLR2EnigmaFormat {

	/**
//this program load all the correlations results from Dorota's r method and then build the Enigma graph from the CLR couples.
	 * Arg 0 folder with all the correlations
	 * Arg 1 file with the pairs from CLR
	 * Arg 2 output file
	 * */

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String inFolder = args[0];
		String filename;

		String pairFile = args[1];
		BufferedReader inFilePair = null;

		File file = null;
		File[] files = null;

		BufferedReader inFileExpTable = null;
		String str;
		String argsFile[];

		HashMap<String, Double> pairMap = new HashMap<>();


		Double relval;

		//load all correlations
		if (inFolder != null && !inFolder.equals("")) {
			file = new File(inFolder);
			if (file.exists()) {
				files = file.listFiles();
			}
		}

		List<File> listFiles = new ArrayList<File>(Arrays.asList(files));


		try{
			PrintWriter outFile = new PrintWriter(new FileOutputStream(args[2]));
			inFilePair = new BufferedReader(new FileReader(pairFile));

			outFile.println("Gene_1"+"\t"+"Gene_2"+"\t"+"PosNeg"+"\t"+"P-value");
			//load all pairs from CLR
			while ((str = inFilePair.readLine()) != null) {
				argsFile=str.split("\t");

				//the relatedness
				relval=Double.parseDouble(argsFile[2]);

				pairMap.put(argsFile[0]+argsFile[1],relval);
			}

			double corval = 0;

			for (int i=0;i<listFiles.size();i++) {
				filename = listFiles.get(i).getAbsolutePath();
				inFileExpTable = new BufferedReader(new FileReader(filename));

				while ((str = inFileExpTable.readLine()) != null) {

					argsFile=str.split("\t");
					//check if that pair exists in both directions
					relval = pairMap.get(argsFile[1]+argsFile[2]);
					if(relval==null){
						relval = pairMap.get(argsFile[2]+argsFile[1]);
					}

					if(relval!=null){

						corval = Double.parseDouble(argsFile[3]); 

						if(corval>0){						
							//outFile.println(argsFile[1]+"\t"+argsFile[2]+"\t+\t"+relval);
							//we put as pval 0 given Pingo need something small to work with
							outFile.println(argsFile[1]+"\t"+argsFile[2]+"\t+\t"+0);
						}
						// only interested in the pos links
//						else{
//							outFile.println(argsFile[1]+"\t"+argsFile[2]+"\t-\t"+relval);
//						}


					}

				}

				inFileExpTable.close();
			}

			
			outFile.close();

		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}	
	}

}
