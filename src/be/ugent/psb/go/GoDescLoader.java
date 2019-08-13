
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
		 * //this program takes a list of genes and adds different features such as Ids, descriptions and annotation.
		 * Arg 0 folder with all the correlations
		 * Arg 1 file with the pairs from CLR
		 * Arg 2 output file
		 *
		 * /home/dfcruz/Midas/biocomp/groups/group_esb/dacru/Maize/TMP/GetAnotDesc/list.tsv
			/home/dfcruz/Midas/biocomp/groups/group_esb/dacru/maizeEnrich/filesV3/go-basic.obo
			/home/dfcruz/Midas/biocomp/groups/group_esb/dacru/maizeEnrich/filesV3/go.zma.txt
			/home/dfcruz/Midas/biocomp/groups/group_esb/dacru/maizeEnrich/filesV3/PlazaFilesDescCommonName/id_conversion.zma.csv
			/home/dfcruz/Midas/biocomp/groups/group_esb/dacru/maizeEnrich/filesV3/PlazaFilesDescCommonName/annotation_extra.zma.csv
		 * 
		 */


		try {


			loadShownAnnotPlusCommonNamePlusDesc(args[0], args[1], args[2],args[3],args[4]);
//			loadShownAnnotPlusCommonNamePlusDescWithTargetGOs(args[0], args[1], args[2],args[3],args[4],args[5]);


			

			// TODO Auto-generated catch block

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	public static void loadShownAnnotPlusCommonNamePlusDesc(String geneListFilePath, String ontologyFilePath,String annotFilePath,String idFile, String annotExtraFile){
		try {
			HashMap<Integer, GoTerm> ontology = loadOntology(ontologyFilePath);
			HashMap<String, ArrayList<AnnotationDetail>> notat = loadAnnotation(annotFilePath);
			HashMap<String, GoID> ids = loadIdentifiers(idFile);
			HashMap<String, ArrayList<String>> descripts = loadDescriptions(annotExtraFile);

			String str;
			ArrayList<AnnotationDetail> annoTmp = null;
			GoTerm got = null;
			boolean multipleAnnot = false;

			String currPlazaName = null;
			GoID goi;
			ArrayList<String> descs;


			try(BufferedReader inFile = new BufferedReader(new FileReader(geneListFilePath));
					PrintWriter outFile = new PrintWriter(new FileOutputStream(geneListFilePath+"PlazaAnotOut.tsv"))){

				//outFile.println("GeneName"+"\t"+"Go"+"\t"+"GoName"+"\t"+"NameSpace");
				outFile.println("Names"+"\t"+"Descs"+"\t"+"Go"+"\t"+"GoName"+"\t"+"NameSpace");

				while ((str = inFile.readLine()) != null) {
					//Print name as reference
					outFile.print("Name:"+str);
					//first get others ids
					goi = ids.get(str);
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


					annoTmp = notat.get(str);

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
						outFile.println("\t"+"UnAnnot"+"\t"+null+"\t"+null);
					}
					annoTmp=null;

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
	
	public static void loadShownAnnotPlusCommonNamePlusDescWithTargetGOs(String geneListFilePath, String ontologyFilePath,String annotFilePath,String idFile, String annotExtraFile, String targetGOs){
		try {
			HashMap<Integer, GoTerm> ontology = loadOntology(ontologyFilePath);
			HashMap<String, ArrayList<AnnotationDetail>> notat = loadAnnotation(annotFilePath);
			HashMap<String, GoID> ids = loadIdentifiers(idFile);
			HashMap<String, ArrayList<String>> descripts = loadDescriptions(annotExtraFile);

			String str;
			ArrayList<AnnotationDetail> annoTmp = null;
			GoTerm got = null;
			boolean multipleAnnot = false;

			String currPlazaName = null;
			GoID goi;
			ArrayList<String> descs;
			ArrayList<Integer> targetGos = new ArrayList<>();
			String targetsFound;
			String arFi[];


			try(BufferedReader inFile = new BufferedReader(new FileReader(geneListFilePath));
					PrintWriter outFile = new PrintWriter(new FileOutputStream(geneListFilePath+"PlazaAnotOut.tsv"))){
				
				//get target go as arrayList
				arFi = targetGOs.split(",");
				for (String stGo : arFi) {
					targetGos.add(Integer.parseInt(stGo));
				}

				//outFile.println("GeneName"+"\t"+"Go"+"\t"+"GoName"+"\t"+"NameSpace");
				outFile.println("Names"+"\t"+"Descs"+"\t"+"WithTF"+"\t"+"Go"+"\t"+"GoName"+"\t"+"NameSpace");

				while ((str = inFile.readLine()) != null) {
					//Print name as reference
					outFile.print("Name:"+str);
					//first get others ids
					goi = ids.get(str);
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

					outFile.print("\t");
					annoTmp = notat.get(str);
					
					if(annoTmp!=null){

						targetsFound = GoDescLoader.contentGO(targetGos, annoTmp);
						outFile.print(targetsFound);

					}else{
						outFile.print("None");
					}

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
			}


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static HashMap<String, ArrayList<String>> loadDescriptions(String descFile) throws Exception, IOException{
		String str = null;
		String[] arrayLineFile;
		HashMap<String, ArrayList<String>> goDescs = new HashMap<>();
		ArrayList<String> currentDescs;
		String plazaName=null;
		try(BufferedReader inFile = new BufferedReader(new FileReader(descFile))){
			while ((str = inFile.readLine()) != null) {
				str = str.replaceAll("\"","");
				arrayLineFile=str.split(";");
				plazaName = arrayLineFile[0];
				//Get only interesting things

				/*
				 * Not included: 
				 * name
					pid
					tid
					GeneName

					Inluded:
					description
					PlnTFDB
					fatm_description
					CommonName
				 */

				if(!arrayLineFile[2].equalsIgnoreCase("name")&&!arrayLineFile[2].equalsIgnoreCase("pid")&&!arrayLineFile[2].equalsIgnoreCase("tid")
						&&!arrayLineFile[2].equalsIgnoreCase("GeneName")){


					if(goDescs.containsKey(plazaName)){
						currentDescs = goDescs.get(plazaName);
						currentDescs.add(arrayLineFile[1]+":"+arrayLineFile[2]);
					}else{
						currentDescs =  new ArrayList<>();
						currentDescs.add(arrayLineFile[1]+":"+arrayLineFile[2]);
						goDescs.put(plazaName, currentDescs);
					}
				}	

			}

		}


		return goDescs;
	}

	public static HashMap<String, GoID> loadIdentifiers(String idFile) throws Exception, IOException{

		String str = null;
		String[] arrayLineFile;
		HashMap<String, GoID> goIds = new HashMap<>();
		String plazaName=null;
		GoID goi = null;

		try(BufferedReader inFile = new BufferedReader(new FileReader(idFile))){
			//skip header
			inFile.readLine();
			while ((str = inFile.readLine()) != null) {
//				str = str.replaceAll("\"","");
				arrayLineFile=str.split("\t");

				//first gene
				if(plazaName==null){
					goi = new GoID();
					plazaName = arrayLineFile[0];
				}

				//new gene
				if(!plazaName.equals(arrayLineFile[0])){
					goIds.put(goi.getName(), goi);
					goi = new GoID();
					plazaName = arrayLineFile[0];
				}

				//more of same gene
				if(plazaName.equals(arrayLineFile[0])){


					if(goi.getPlazaName()==null)
						goi.setPlazaName(plazaName);

					if(arrayLineFile[1].equals("name"))
						goi.setName(arrayLineFile[2]);

					if(arrayLineFile[1].equals("pid"))
						goi.setPid(arrayLineFile[2]);

					if(arrayLineFile[1].equals("uniprot"))
						goi.setUniprot(arrayLineFile[2]);

					if(arrayLineFile[1].equals("CommonName"))
						goi.setCommonName(arrayLineFile[2]);

				}



			}

			//final gene
			goIds.put(goi.getName(), goi);


		}
		return goIds;
	}

	public static void loadShownAnnotation(String geneListFilePath, String ontologyFilePath,String annotFilePath){

		try {
			HashMap<Integer, GoTerm> ontology = loadOntology(ontologyFilePath);
			HashMap<String, ArrayList<AnnotationDetail>> notat = loadAnnotation(annotFilePath);

			String str;
			ArrayList<AnnotationDetail> annoTmp = null;
			GoTerm got = null;
			boolean multipleAnnot = false;
			try(BufferedReader inFile = new BufferedReader(new FileReader(geneListFilePath));
					PrintWriter outFile = new PrintWriter(new FileOutputStream(geneListFilePath+".PlazaAnotout.tsv"))){

				outFile.println("GeneName"+"\t"+"Go"+"\t"+"GoName"+"\t"+"NameSpace");

				while ((str = inFile.readLine()) != null) {

					annoTmp = notat.get(str);

					//check if there is any annotation
					if(annoTmp!=null){


						for (AnnotationDetail annoDetail : annoTmp) {
							//check only the shown ones
							if(annoDetail.isIs_shown()){
								got = ontology.get(annoDetail.getGo());	
								if(!multipleAnnot){
									outFile.print("\t"+annoDetail.getGo()+"\t"+got.getName()+"\t"+got.getNameSpace());
									// are there many?
									if(annoTmp.size()>1)
										multipleAnnot = true;
								}else{
									//for appending the rest
									outFile.print("\t"+annoDetail.getGo()+"\t"+got.getName()+"\t"+got.getNameSpace());
								}
							}	
						}
						multipleAnnot = false;
						outFile.println();


					}else{
						//there is no annotation
						outFile.println(str+"\t"+null+"\t"+null+"\t"+null);
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
	
	public static HashMap<String, GoTerm> loadOntologyNameKey(String ontologyFile) throws Exception, IOException{
		String str = null;

		String[] arrayLineFile;
		HashMap<String, GoTerm> goTerms = new HashMap<>();
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

					goTerms.put(name, gotem);
				}
			}
		}

		return goTerms;
	}
	
	public static String contentGO(ArrayList<Integer> targetGOs, ArrayList<AnnotationDetail> annots) throws Exception{
		
//		int total2CheckGo = targetGOs.size();
		String foundtargetGo = "";
		
		for (AnnotationDetail annoDetail : annots) {
//			if(targetGOs.contains(annoDetail.getGo())){
//				numFoundtargetGo++;
//			}
			for(Integer go : targetGOs){
				if(go.intValue() == annoDetail.getGo())
					foundtargetGo = foundtargetGo+"~"+annoDetail.getGo();
			}
			
				
		}
		
		return foundtargetGo;
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

