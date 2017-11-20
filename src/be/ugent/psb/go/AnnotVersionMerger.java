package be.ugent.psb.go;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class AnnotVersionMerger {

	/**
	 * This program will take an annotation version and then look for missing annotation in another file
	 * Plaza files, the second one has different ids so Id converter is required 
	 * 
	 * arg 0 file annot to be completed (Target)
	 * arg 1 file annot (source)
	 * arg 2 id conversion file
	 * arg 3 outputfile
	 * arg 4 listTargetGenes
	 */


	public static void main(String[] args) {
		// TODO Auto-generated method stub

		HashMap<String, String> idChange = new HashMap<>();
		String str;
		String[] arrayLine;

		HashMap<String, HashMap<String,String>> annotTarget = new HashMap<>();
		HashMap<String, HashMap<String,String>> annotSource = new HashMap<>();

		HashMap<String, String> mapTmp;
		HashMap<String, String> mapTarget;
		HashMap<String, String> mapSource;

		String geneName;

		try(BufferedReader inAnnotTargetFile = new BufferedReader(new FileReader(args[0]));
				BufferedReader inAnnotSourceFile = new BufferedReader(new FileReader(args[1]));
				BufferedReader inPlazaIndexFile = new BufferedReader(new FileReader(args[2]));
				PrintWriter outFile = new PrintWriter(new FileOutputStream(args[3]));
				PrintWriter outFileCounts = new PrintWriter(new FileOutputStream(args[3]+".MissCounts"))){

			// load id converter
			while ((str = inPlazaIndexFile.readLine()) != null) {
				arrayLine = str.split("\t");
				idChange.put(arrayLine[2], arrayLine[0]);

			}

			//load inAnnot file
			//skip header Target
			outFile.println(inAnnotTargetFile.readLine());
			while ((str = inAnnotTargetFile.readLine()) != null) {
				arrayLine = str.split("\t");
				geneName = arrayLine[2];
				//existing gene
				if(annotTarget.containsKey(geneName)){
					mapTmp = annotTarget.get(geneName);
					//add @category
					mapTmp.put(arrayLine[3], str);
				}else{
					//new gene
					mapTmp = new HashMap<>();
					mapTmp.put(arrayLine[3], str);
					annotTarget.put(geneName, mapTmp);
				}

			}

			//load inAnnotSource file
			//skip header
			inAnnotSourceFile.readLine();
			while ((str = inAnnotSourceFile.readLine()) != null) {
				arrayLine = str.split("\t");
				geneName = arrayLine[2];
//				if(geneName.equals("GRMZM2G000964"))
//					System.out.println("aaaaaaa");
				//existing gene
				if(annotSource.containsKey(geneName)){
					mapTmp = annotSource.get(geneName);
					//add @category
					mapTmp.put(arrayLine[3], str);
				}else{
					//new gene
					mapTmp = new HashMap<>();
					mapTmp.put(arrayLine[3], str);
					annotSource.put(geneName, mapTmp);
				}

			}

			//now comparison we are going to do it in the target order

			int totalAdded = 0;
			boolean allFromSource = false;
			Iterator<Entry<String, String>> it;
			String go;
			String annotLine;
			
			String [] forPub;

			geneName = "";

			String plazaName;

			BufferedReader listGenes = new BufferedReader(new FileReader(args[4]));		
			listGenes.readLine();
			
			outFileCounts.println("GeneName	GenesFromSource	allFromSource");
			
			while ((str = listGenes.readLine()) != null) {
				arrayLine = str.split("\t");

				if(!geneName.equals(arrayLine[1])){
					geneName = arrayLine[1];


					
					//GO target
					mapTarget = annotTarget.get(geneName);

					if(mapTarget!=null){
						//print all target Annots
						it = mapTarget.entrySet().iterator();
						while (it.hasNext()) {
							HashMap.Entry<String,String> pair = it.next();
							//go = pair.getKey();
							annotLine = pair.getValue();
	//						it.remove(); // avoids a ConcurrentModificationException
							outFile.println(annotLine);
	
						}
					}else{
						// if the gene doesn't have anything in target
						allFromSource = true;
					}

					//check source file
					//first change id
					plazaName = idChange.get(geneName);

					//GO source
					mapSource = annotSource.get(plazaName);
					
//					System.out.println(geneName);
					
					if(mapSource==null){
						allFromSource = false;
						continue;
					}	
					//print missing Annots from source
					it = mapSource.entrySet().iterator();
					while (it.hasNext()) {
						HashMap.Entry<String,String> pair = it.next();
						go = pair.getKey();
						annotLine = pair.getValue();
						//checking that the source doesn't have already the same annot
						if(mapTarget==null||!mapTarget.containsKey(go)){
							annotLine = annotLine.replaceFirst(plazaName, geneName);
							if(annotLine.contains("publications")){
								forPub = annotLine.split("\t");
								outFile.print(forPub[0]);
								for(int i=1;i<forPub.length;i++){
									if(i!=8)
										outFile.print("\t"+forPub[i]);
								}
								outFile.println();
								
							}else{
								outFile.println(annotLine);
							}
							
							totalAdded++;
						}

	//					it.remove(); // avoids a ConcurrentModificationException


					}

					outFileCounts.println(geneName+"\t"+totalAdded+"\t"+allFromSource);
					totalAdded = 0;
					allFromSource = false;

				}

			}

			listGenes.close();


		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

}
