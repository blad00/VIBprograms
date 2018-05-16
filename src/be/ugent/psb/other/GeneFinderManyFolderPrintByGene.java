package be.ugent.psb.other;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeneFinderManyFolderPrintByGene {
	/*
	 * This program find genes or any keyword from a list in every text file in a list of destination folders
	 * The output will be the same inputfile plus .found
	 * now the output can be random with the iteration of the hashmap or the same order of the input file (default)
	 * 
	 * Arg 0 list target folder
	 * Arg 1 list file to search
	 * 
	 * 
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub


		String inFolder;

		String filename;
		String shortfilename;

		File file = null;
		File[] files = null;

		BufferedReader inFileExpTable = null;
		BufferedReader inputFile4Printing = null;
		String str;
		String strFolder;
		ArrayList<String> listSearch =  new ArrayList<>();
		ArrayList<String> listFolders = new ArrayList<>();
		HashMap<String, ArrayList<String>> mapMatches = new HashMap<>();
		ArrayList<HashMap<String, ArrayList<String>>> listMaps = new ArrayList<>();

		try(BufferedReader listFoldersFile = new BufferedReader(new FileReader(args[0]));
				PrintWriter outFile = new PrintWriter(new FileOutputStream(args[1]+".found"))){


			//load list
			BufferedReader listFile = new BufferedReader(new FileReader(args[1]));
			while ((str = listFile.readLine()) != null) {
				listSearch.add(str);
				mapMatches.put(str, new ArrayList<>());
			}
			listFile.close();

			while ((strFolder = listFoldersFile.readLine()) != null) {

				inFolder = strFolder;
				listFolders.add(inFolder);

				//get list of files
				if (inFolder != null && !inFolder.equals("")) {
					file = new File(inFolder);
					if (file.exists()) {
						files = file.listFiles();
					}
				}

				List<File> listFiles = new ArrayList<File>(Arrays.asList(files));

				// iter list of files
				for (int i=0;i<listFiles.size();i++) {

					file = listFiles.get(i);

					//skip
					if(file.isDirectory()||isBinaryFile(file))
						continue;

					filename = listFiles.get(i).getAbsolutePath();

					inFileExpTable = new BufferedReader(new FileReader(filename));


					//read the file line per line
					while ((str = inFileExpTable.readLine()) != null) {
						//check if it has some of the key words					
						for (String sElement : listSearch) {
							if(str.contains(sElement)){
								shortfilename = listFiles.get(i).getName();
								mapMatches.get(sElement).add(shortfilename+"\t"+str);
							}
						}
					}

					inFileExpTable.close();

				}


				listMaps.add(mapMatches);


				//reset the map for next location
				//load list

				mapMatches = new HashMap<>();

				for (String keyGene : listSearch) {
					mapMatches.put(keyGene, new ArrayList<>());
				}



			}
			//Printing
			printByGene(listSearch, listMaps, listFolders, outFile);






		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}

	public static void printByGene(ArrayList<String> keyGenes, ArrayList<HashMap<String, ArrayList<String>>> maps, ArrayList<String> targets,PrintWriter outFile) throws IOException{
		ArrayList<String> listEle = null;

		int itar=0;


		//iterate each gene
		//while ((str = listFile.readLine()) != null) {
		for (String str : keyGenes) {


			//print gene with mark to search easily
			outFile.println(">>"+str);
			//search in every target and get the current 
			for (HashMap<String, ArrayList<String>> mapTMP : maps) {
				listEle = mapTMP.get(str);
				//print current target result with current gene
				outFile.println(targets.get(itar));
				itar++;
				for (String sElement : listEle) {
					outFile.println(sElement);
				}

			}

		}
	}

	public static void cleanMap(HashMap<String, ArrayList<String>> map){


		for (Map.Entry<String, ArrayList<String>> entry : map.entrySet()) {

			map.put(entry.getKey(), new ArrayList<>());
		}	

	}

	public static void printRandomOrder(HashMap<String, ArrayList<String>> mapMatches, PrintWriter outFile){
		String ele;
		ArrayList<String> listEle = null;

		for (Map.Entry<String, ArrayList<String>> entry : mapMatches.entrySet()) {
			ele = entry.getKey();
			listEle = entry.getValue();
			outFile.println(ele);

			for (String sElement : listEle) {
				outFile.println(sElement);
			}

			outFile.println();
		}

	}

	public static void printInputOrder(BufferedReader listFile, HashMap<String, ArrayList<String>> mapMatches, PrintWriter outFile) throws IOException{

		ArrayList<String> listEle = null;
		String str;

		while ((str = listFile.readLine()) != null) {
			listEle = mapMatches.get(str);
			outFile.println(str);

			for (String sElement : listEle) {
				outFile.println(sElement);
			}

			outFile.println();
		}



	}

	public static boolean isBinaryFile(File f) throws FileNotFoundException, IOException {
		FileInputStream in = new FileInputStream(f);
		int size = in.available();
		if(size > 1024) size = 1024;
		byte[] data = new byte[size];
		in.read(data);
		in.close();

		int ascii = 0;
		int other = 0;

		for(int i = 0; i < data.length; i++) {
			byte b = data[i];
			if( b < 0x09 ) return true;

			if( b == 0x09 || b == 0x0A || b == 0x0C || b == 0x0D ) ascii++;
			else if( b >= 0x20  &&  b <= 0x7E ) ascii++;
			else other++;
		}

		if( other == 0 ) return false;

		return 100 * other / (ascii + other) > 95;
	}

}
