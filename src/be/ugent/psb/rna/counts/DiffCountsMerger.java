package be.ugent.psb.rna.counts;

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
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiffCountsMerger {
/*
 * program to put together all gene counts from diferent method following the same genes 
 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String listSamplesFile = args[0];
		String pathSamples = args[1];
		
		String strSample = null;
		String strFile = null;
		List<File> listFiles;
		File[] sampleFiles;
		String fullfilename;
		String fileName;
		BufferedReader inFileExpTable = null;
		PrintWriter outFile;
		String arLine[];
		
		String geneName;
		
		HashMap<String, ArrayList<String>> allCountxSample = new HashMap<>();
		ArrayList<String> filesName =  new ArrayList<String>();
		ArrayList<String> tmpCont;
		
		try(BufferedReader inSamples = new BufferedReader(new FileReader(listSamplesFile))){
			while ((strSample = inSamples.readLine()) != null) {
				
				sampleFiles = getFilesPattern(pathSamples, strSample);
				listFiles = new ArrayList<File>(Arrays.asList(sampleFiles));
				try{
					for (int i=0;i<listFiles.size();i++) {
						fullfilename = listFiles.get(i).getAbsolutePath();
						fileName = listFiles.get(i).getName();
						filesName.add(fileName);
						inFileExpTable = new BufferedReader(new FileReader(fullfilename));
						
						//Salmon has header so skip it
						if(fileName.contains("Salmon"))
							inFileExpTable.readLine();
						
						while ((strFile = inFileExpTable.readLine()) != null) {
							arLine = strFile.split("\t");
							//name first pos
							geneName = cleanName(arLine[0]);
							//skip final counts HTseq
							if(geneName.equals("__no_feature")||geneName.equals("__ambiguous")||geneName.equals("__too_low_aQual")||geneName.equals("__not_aligned")||geneName.equals("__alignment_not_unique"))
								continue;
								
							if(!allCountxSample.containsKey(geneName)){
								tmpCont = new ArrayList<>();
								allCountxSample.put(geneName, tmpCont);
							}
							tmpCont = allCountxSample.get(geneName);
							tmpCont.add(arLine[arLine.length-1]);

						}
						inFileExpTable.close();
					}
					
					outFile = new PrintWriter(new FileOutputStream(pathSamples+"/"+strSample+"allCounts.tsv"));
					printCombine(allCountxSample, filesName, outFile);
					outFile.close();
					allCountxSample.clear();
					
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}

			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void printCombine(HashMap<String, ArrayList<String>> map, ArrayList<String> titles,PrintWriter outFile){
		int i=0;
		String geneName;
		ArrayList<String> info;
		
		
		for(i=0;i<titles.size();i++){
			outFile.print(titles.get(i)+"\t");
		}
		outFile.println();
		for (Map.Entry<String, ArrayList<String>> entry : map.entrySet()) {
//		    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			geneName = entry.getKey();
			info = entry.getValue();
			outFile.print(geneName+"\t");
			for (String str : info) {
				outFile.print(str+"\t");
			}
			outFile.println();
		}	
		
	}
	
	public static File [] getFilesPattern(String pathSamples,String pat){

		File dir = new File(pathSamples);
		File[] files = dir.listFiles((d, name) -> name.startsWith(pat));
		
//		File dir = new File(pathSamples);
//		File [] files = dir.listFiles(new FilenameFilter() {
//		    @Override
//		    public boolean accept(File dir, String name) {
//		        return name.startsWith(pathSamples);
//		    }
//		});
//		
		return files;
		
	}
	
	public static String cleanName(String orgName){
		
		String newName = orgName.replace("transcript:", "").replace(".1","").replace("gene:","");
		return newName;
		
	}
	
	
}
