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

public class GoDescLoader {

	public static void main(String[] args) {
		/*
		 * /home/dfcruz/Midas/biocomp/groups/group_esb/dacru/maizeEnrich/filesV3/go-basic.obo
/home/dfcruz/Midas/biocomp/groups/group_esb/dacru/Maize/TMP/GetAnotDesc/list.tsv
plazaNoConvert
/home/dfcruz/Midas/biocomp/groups/group_esb/dacru/maizeEnrich/filesV3/go.zma.txt
		 */
		
		
		try {

			loadShownAnnotation(args[0], args[1], args[2]);
			
//			HashMap<String, ArrayList<AnnotationDetail>> notat = loadAnnotation(args[0]);
//			System.out.println(notat.isEmpty());
//			
//			HashMap<Integer, GoTerm> ontology = loadOntology(args[1]);
//			System.out.println(ontology.isEmpty());
		
			// TODO Auto-generated catch block
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static void loadShownAnnotation(String geneListFilePath, String ontologyFilePath,String annotFilePath){
		
		try {
			HashMap<Integer, GoTerm> ontology = loadOntology(ontologyFilePath);
			HashMap<String, ArrayList<AnnotationDetail>> notat = loadAnnotation(annotFilePath);
			
			String str;
			ArrayList<AnnotationDetail> annoTmp = null;
			GoTerm got = null;

			try(BufferedReader inFile = new BufferedReader(new FileReader(geneListFilePath));
					PrintWriter outFile = new PrintWriter(new FileOutputStream(geneListFilePath+".Plazaout"))){
				
				outFile.println("GeneName"+"\t"+"Desc"+"\t"+"Go"+"\t"+"GoName"+"\t"+"NameSpace");
				
				while ((str = inFile.readLine()) != null) {

					annoTmp = notat.get(str);
					if(annoTmp!=null){
						//check only the shown ones
						for (AnnotationDetail annoDetail : annoTmp) {
							if(annoDetail.isIs_shown()){
								got = ontology.get(annoDetail.getGo());							
								outFile.println(str+"\t"+annoDetail.getDesc()+"\t"+annoDetail.getGo()+"\t"+got.getName()+"\t"+got.getNameSpace());
							}
						}
						
					}

				}
			}
			

			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	public static HashMap<String, ArrayList<AnnotationDetail>> loadAnnotation(String annotFile) throws Exception, IOException{

		String str = null;
		String[] arrayLineFile;
		String[] arrayLineFileOpt;
		HashMap<String, ArrayList<AnnotationDetail>> annots = new HashMap<>();
		AnnotationDetail annota = null;
		
		String gene_id = "";
		int go;
		boolean is_shown;
		String desc;
		
		ArrayList<AnnotationDetail> details = new ArrayList<>();
		
		try(BufferedReader inFile = new BufferedReader(new FileReader(annotFile))){
			//skip header
			inFile.readLine();
			while ((str = inFile.readLine()) != null) {
				arrayLineFile=str.split("\t");
				
			
				
				if(arrayLineFile[2].equalsIgnoreCase(gene_id)){
					details.add(annota);
				}else{
					if(!gene_id.equals("")){
						details.add(annota);
						annots.put(gene_id, details);
						details = new ArrayList<>();
					}	
				}
				
				
				annota = new AnnotationDetail();
				//gene id
				gene_id = arrayLineFile[2];
								
				annota.setGene_id(gene_id);
				
				//go_id
				arrayLineFileOpt = arrayLineFile[3].split(":");
				go = Integer.parseInt(arrayLineFileOpt[1]);
				annota.setGo(go);
				
				//is_shown
				is_shown = arrayLineFile[8].equals("1");
				annota.setIs_shown(is_shown);
				
				//desc
				desc = arrayLineFile[9];
				annota.setDesc(desc);
				
				
					
			}
			details.add(annota);
			annots.put(gene_id, details);
			
			
		}
		
		return annots;
	}

	public static HashMap<Integer, GoTerm> loadOntology(String ontologyFile) throws Exception, IOException{
		String str = null;
		
		String[] arrayLineFile;
		HashMap<Integer, GoTerm> goTerms = new HashMap<>();
		int id;
		String name;
		String namespace;
		try(BufferedReader inFile = new BufferedReader(new FileReader(ontologyFile))){
			while ((str = inFile.readLine()) != null) {
				
				if(str.equalsIgnoreCase("[Term]")){
					GoTerm gotem = new GoTerm();
					//ID
					str = inFile.readLine();
					arrayLineFile = str.split(":");
					id = Integer.parseInt(arrayLineFile[2]);
					gotem.setId(id);
					//name
					str = inFile.readLine();
					arrayLineFile = str.split(":");
					name = arrayLineFile[1].replaceFirst("\\s+","");
					gotem.setName(name);
					//namespace
					str = inFile.readLine();
					arrayLineFile = str.split(":");
					namespace = arrayLineFile[1].replaceFirst("\\s+","");
					gotem.setNameSpace(namespace);
					
					goTerms.put(id, gotem);
				}
			}
		}
		
		return goTerms;
	}
	
	public static void main2(String[] args) {
		// Arg 0 Gene List file
		// Arg 1 Source: plaza, phyto, plazaNoConvert
		// Arg 2 source file
		// If plaza, Arg 3 idconverter file
		// Arg 4 inputindex
		// Arg 5 outputindex

		String fileIn = args[0];
		String str;
		ArrayList<String> geneList = new ArrayList<>();

		try(BufferedReader inFile = new BufferedReader(new FileReader(fileIn))){

			while ((str = inFile.readLine()) != null) {

				geneList.add(str);

			}
			if (args[1].equals("plazaNoConvert")) 
				addPlazaNoConverter(geneList, args[2], fileIn);
			else if(args[1].equals("phyto"))
				addPhyto(geneList, args[2], fileIn);
			else if (args[1].equals("plaza")) 
				addPlaza(geneList, args[2], fileIn, args[3], Integer.parseInt(args[4]), Integer.parseInt(args[5]));
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	

	public static void addPhyto(ArrayList<String> geneList, String phytoFile, String outputFile){

		String str;
		String[] arrayLineFile;
		HashMap<String, String> goDesc = new HashMap<>();
		try(BufferedReader inFile = new BufferedReader(new FileReader(phytoFile));PrintWriter outFile = new PrintWriter(new FileOutputStream(outputFile+".Phyout"))){

			while ((str = inFile.readLine()) != null) {

				arrayLineFile = str.split(",");

				goDesc.put(arrayLineFile[0], str);

			}

			for (String gene : geneList) {
				outFile.println(gene+"\t"+goDesc.get(gene));
			}			


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	}
	
	public static void addPlazaNoConverter(ArrayList<String> geneList, String plazaFile, String outputFile){

		/*
		 * D:\DanielVIB\Maize\Annotations\Enzymes_C4.txt
plazaNoConvert
\\psb.ugent.be\shares\biocomp\groups\group_esb\dacru\maizeEnrich\filesV3\go.zma.txt
		 */
		
		String str;
		String[] arrayLineFile;
		HashMap<String, String> goDesc = new HashMap<>();
		try(BufferedReader inFile = new BufferedReader(new FileReader(plazaFile));PrintWriter outFile = new PrintWriter(new FileOutputStream(outputFile+".Plazaout"))){

			while ((str = inFile.readLine()) != null) {

				arrayLineFile = str.split("\t");

				goDesc.put(arrayLineFile[2], arrayLineFile[3]+"\t"+arrayLineFile[9]);

			}

			for (String gene : geneList) {
				outFile.println(gene+"\t"+goDesc.get(gene));
			}			


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	}
	
	
	
	

	public static void addPlaza(ArrayList<String> geneList, String plazaFile, String outputFile, 
			String plazaIndexFile, int inputIndex, int outputIndex){
		
		HashMap<String, String> plazaDesc = new HashMap<>();
		HashMap<String, String> idChange = new HashMap<>();
		String str;
		String[] arrayLineFile;
		
		try(BufferedReader inPlazaFile = new BufferedReader(new FileReader(plazaFile));
				BufferedReader inPlazaIndexFile = new BufferedReader(new FileReader(plazaIndexFile));
				PrintWriter outFile = new PrintWriter(new FileOutputStream(outputFile+"Plaza.out"))){

			while ((str = inPlazaFile.readLine()) != null) {
				arrayLineFile = str.split("\t");
				plazaDesc.put(arrayLineFile[0], arrayLineFile[16]);
			}
			
			while ((str = inPlazaIndexFile.readLine()) != null) {
				arrayLineFile = str.split("\t");
				idChange.put(arrayLineFile[inputIndex], arrayLineFile[outputIndex]);
			}
			
			String changedGene;
			
			for (String gene : geneList) {
				changedGene = idChange.get(gene);
				outFile.println(gene+"\t"+plazaDesc.get(changedGene));
			}		
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
