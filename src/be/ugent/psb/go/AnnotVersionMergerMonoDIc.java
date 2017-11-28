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

public class AnnotVersionMergerMonoDIc {

	/**
	 * This program will take an annotation version and then look for missing annotation in another file
	 * Plaza files, the second one has different ids so Id converter is required 
	 * 
	 * arg 0 file annot to be completed (Target)
	 * arg 1 file annot (source)Dicot
	 * arg 2 file annot (source)Mono
	 * arg 3 id conversion file
	 * arg 4 outputfile
	 * arg 5 listTargetGenes
	 */


	public static void main(String[] args) {
		// TODO Auto-generated method stub

		HashMap<String, String> idChange = new HashMap<>();
		String str;
		String[] arrayLine;

		HashMap<String, ArrayList<String>> annotTarget = new HashMap<>();
		HashMap<String, ArrayList<String>> annotSource = new HashMap<>();
		HashMap<String, ArrayList<String>> annotSourceMono = new HashMap<>();

		ArrayList<String> mapTmp;
		ArrayList<String> mapTarget;
		ArrayList<String> mapSource;
		
		int V3Counts=0;
		int V2DicotsCounts=0;
		int V2MonoCounts=0;

		String geneName;

		try(BufferedReader inAnnotTargetFile = new BufferedReader(new FileReader(args[0]));
				BufferedReader inAnnotSourceFile = new BufferedReader(new FileReader(args[1]));
				BufferedReader inAnnotSourceFileMono = new BufferedReader(new FileReader(args[2]));
				BufferedReader inPlazaIndexFile = new BufferedReader(new FileReader(args[3]));
				PrintWriter outFile = new PrintWriter(new FileOutputStream(args[4]));
				PrintWriter outFileCounts = new PrintWriter(new FileOutputStream(args[4]+".MissCounts"));
				){

			// load id converter
			while ((str = inPlazaIndexFile.readLine()) != null) {
				arrayLine = str.split("\t");
				idChange.put(arrayLine[2], arrayLine[0]);
			}

			//load inAnnot file
			//skip header Target target V3
			outFile.println(inAnnotTargetFile.readLine()+"\t"+"Origin");
			while ((str = inAnnotTargetFile.readLine()) != null) {
				arrayLine = str.split("\t");
				geneName = arrayLine[2];
				//existing gene
				if(annotTarget.containsKey(geneName)){
					mapTmp = annotTarget.get(geneName);
					//add @category
					mapTmp.add(str+"\t"+"V3");
				}else{
					//new gene
					mapTmp = new ArrayList<>();
					mapTmp.add(str+"\t"+"V3");
					annotTarget.put(geneName, mapTmp);
				}
			}

			//load inAnnotSource file Dicots V2
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
					//add @annotRecord
					mapTmp.add(str+"\t"+"V2_Dicot");
				}else{
					//new gene
					mapTmp = new ArrayList<>();
					mapTmp.add(str+"\t"+"V2_Dicot");
					annotSource.put(geneName, mapTmp);
				}

			}


			//load inAnnotSource file Monocots V2
			//skip header
			inAnnotSourceFileMono.readLine();
			while ((str = inAnnotSourceFileMono.readLine()) != null) {
				arrayLine = str.split("\t");
				geneName = arrayLine[2];
				if(annotSourceMono.containsKey(geneName)){
					mapTmp = annotSourceMono.get(geneName);
					//add @annotRecord
					mapTmp.add(str+"\t"+"V2_Monocot");
				}else{
					//new gene
					mapTmp = new ArrayList<>();
					mapTmp.add(str+"\t"+"V2_Monocot");
					annotSourceMono.put(geneName, mapTmp);
				}

			}

			//now comparison we are going to do it with the full Gene List


			Iterator<String> it;
			
			String annotLine;

			String [] forPub;

			geneName = "";

			String plazaName;

			BufferedReader listGenes = new BufferedReader(new FileReader(args[5]));		
			
			outFileCounts.println("GeneName"+"\t"+"V3Annots"+"\t"+"V2DicotAnnots"+"\t"+"V2MonocotAnnots");
			
			//skip header
			listGenes.readLine();

			

			while ((str = listGenes.readLine()) != null) {
				arrayLine = str.split("\t");

				if(!geneName.equals(arrayLine[1])){
					geneName = arrayLine[1];



					//GO target V3
					mapTarget = annotTarget.get(geneName);

					if(mapTarget!=null){
						//print all target Annots
						it = mapTarget.iterator();
						while (it.hasNext()) {
							annotLine = it.next();
							outFile.println(annotLine);
							V3Counts++;
						}
					}

					//check source file
					//first change id
					plazaName = idChange.get(geneName);

					//GO source Dicots V2
					mapSource = annotSource.get(plazaName);

					if(mapSource!=null){



						//print missing Annots from source
						it = mapSource.iterator();
						while (it.hasNext()) {
							annotLine = it.next();
							annotLine = annotLine.replaceFirst(plazaName, geneName);
							if(annotLine.contains("publications")){
								forPub = annotLine.split("\t");
								outFile.print(forPub[0]);
								for(int i=1;i<forPub.length;i++){
									if(i!=8)
										outFile.print("\t"+forPub[i]);
								}
								outFile.println();
								V2DicotsCounts++;

							}else{
								outFile.println(annotLine);
								V2DicotsCounts++;
							}

						}

					}
					
					//GO source Monocots V2
					mapSource = annotSourceMono.get(plazaName);

					if(mapSource!=null){



						//print missing Annots from source
						it = mapSource.iterator();
						while (it.hasNext()) {
							annotLine = it.next();
							annotLine = annotLine.replaceFirst(plazaName, geneName);
							if(annotLine.contains("publications")){
								forPub = annotLine.split("\t");
								outFile.print(forPub[0]);
								for(int i=1;i<forPub.length;i++){
									if(i!=8)
										outFile.print("\t"+forPub[i]);
								}
								outFile.println();
								V2MonoCounts++;
							}else if(annotLine.contains("node_id")&&annotLine.contains("V2_Monocot")){
								forPub = annotLine.split("\t");
								outFile.print(forPub[0]);
								for(int i=1;i<forPub.length;i++){
									if(i==7){
										outFile.print("\t"+forPub[7]+";"+forPub[8]+";"+forPub[9]);
										i=9;
									}else{
										outFile.print("\t"+forPub[i]);
									}
								}
								outFile.println();
								V2MonoCounts++;
								
							}else{
								outFile.println(annotLine);
								V2MonoCounts++;
							}

						}

					}
					
					outFileCounts.println(geneName+"\t"+V3Counts+"\t"+V2DicotsCounts+"\t"+V2MonoCounts);
					V3Counts=0;
					V2DicotsCounts=0;
					V2MonoCounts=0;
					
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
