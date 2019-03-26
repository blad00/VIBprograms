package be.ugent.psb.go;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ReportPredict {
/*
 * This program will iterate over all Ranked prediction cat files and
 * will add desc, current annotation, if it was found by 3 methods, finally if it is inside an specific list of TF
 * 
 * arg 0 folder with ranking files
 * arg 1 ontology file
 * arg 2 annotation file
 * arg 3 convertion table
 * arg 4 additional info
 * arg 5 Interest GOs coma separated
 *  
 * 
 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String inFolder = args[0];
		String filename;

		File file = null;
		File[] files = null;

		BufferedReader inFileExpTable = null;
		PrintWriter outFile = null;
		String str;
		String arFi[];
		
		int i;
		String geneName;
		
		ArrayList<AnnotationDetail> annoTmp = null;
		GoTerm got = null;
		boolean multipleAnnot = false;

		String currPlazaName = null;
		GoID goi;
		ArrayList<String> descs;
		
		ArrayList<Integer> targetGos = new ArrayList<>();
		
		String targetsFound;
		
		
		
		if (inFolder != null && !inFolder.equals("")) {
			file = new File(inFolder);
			if (file.exists()) {
				files = file.listFiles();
			}
		}

		List<File> listFiles = new ArrayList<File>(Arrays.asList(files));
		try{
			
			HashMap<Integer, GoTerm> ontology = GoDescLoader.loadOntology(args[1]);
			HashMap<String, ArrayList<AnnotationDetail>> notat = GoDescLoader.loadAnnotation(args[2]);
			HashMap<String, GoID> ids = GoDescLoader.loadIdentifiers(args[3]);
			HashMap<String, ArrayList<String>> descripts = GoDescLoader.loadDescriptions(args[4]);
			
			//get target go as arrayList
			arFi = args[5].split(",");
			for (String stGo : arFi) {
				targetGos.add(Integer.parseInt(stGo));
			}
			
			for (i=0;i<listFiles.size();i++) {
				filename = listFiles.get(i).getAbsolutePath();
				inFileExpTable = new BufferedReader(new FileReader(filename));
				outFile = new PrintWriter(new FileOutputStream(filename+"Out.tsv"));
				
//				System.out.println(filename+"Out.txt");
				
				outFile.println("GO_ID"+"\t"+"Gene"+"\t"+"GoCAT"+"\t"+"CLR"+"\t"+"ENIGMA"+"\t"+"PEARSON"+"\t"+"rankCLR"+"\t"+"rankENI"+"\t"+"rankPEA"+"\t"+"avgRanks"
						+"\t"+"combinedMRR"+"\t"+"AllDetec"+"\t"+"WithTF"+"\t"+"Names"+"\t"+"Descs"+"\t"+"Go"+"\t"+"GoName"+"\t"+"NameSpace");

				//skip first line
				inFileExpTable.readLine();
				
				while ((str = inFileExpTable.readLine()) != null) {
					arFi = str.split("\t");
					geneName = arFi[1];

					
					outFile.print(str);
					if((!arFi[3].equals("9"))&&(!arFi[4].equals("9"))&&(!arFi[5].equals("9"))){
						outFile.print("\t"+"Yes");
					}else{
						outFile.print("\t"+"No");
					}
					
					outFile.print("\t");
					
					
					//Print if has any TF from the list
					annoTmp = notat.get(geneName);
//					if(geneName.equals("GRMZM2G111216")){
//						System.out.println("El gen: "+geneName);
//					}
					if(annoTmp!=null){

						targetsFound = GoDescLoader.contentGO(targetGos, annoTmp);
						outFile.print(targetsFound);

					}else{
						outFile.print("None");
					}
					
					outFile.print("\t");
					
					outFile.print("Name:"+geneName);
					
					
					//first get others ids
					goi = ids.get(geneName);
					if(goi!=null){
						if(goi.getPlazaName()!=null)
							outFile.print(" "+"PlazaName:"+goi.getPlazaName());currPlazaName = goi.getPlazaName();
							if(goi.getCommonName()!=null)
								outFile.print(" "+"CommonName:"+goi.getCommonName());
							if(goi.getUniprot()!=null)
								outFile.print(" "+"Uniprot:"+goi.getUniprot());
					}
					goi=null;

					outFile.print("\t");
					
					
					//get descriptions
					descs = descripts.get(currPlazaName);
					if(descs!=null){
						for (String sing : descs) {
							outFile.print(sing+"~");
						}
					}
					currPlazaName=null;
					descs=null;

//					outFile.print("\t");
					//check if there is any annotation
					
					if(annoTmp!=null){

						for (AnnotationDetail annoDetail : annoTmp) {
							//check only the shown ones
							if(annoDetail.isIs_shown()){
								got = ontology.get(annoDetail.getGo());
								if(got!=null){
									if(!multipleAnnot){
										outFile.print("\t"+annoDetail.getGo()+"\t"+got.getName()+"\t"+got.getNameSpace());
										// are there many?
										if(annoTmp.size()>1)
											multipleAnnot = true;
									}else{
										//for appending the rest
										outFile.print("\t"+annoDetail.getGo()+"\t"+got.getName()+"\t"+got.getNameSpace());
									}
								}else{
									//print missing cats
									System.out.println(annoDetail.getGo());
								}
							}	
						}
						multipleAnnot = false;
						outFile.println();


					}else{
						//there is no annotation
						outFile.println("\t"+null+"\t"+null+"\t"+null);
					}
					annoTmp=null;
					
					
				}
				
				
				inFileExpTable.close();
				outFile.close();
			}
		}catch (Exception e) {
			// TODO: handle exception
		}	

	}

}
