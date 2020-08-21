package be.ugent.psb.go;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class NewPredictionsExtractor {
	/* arg 0 dir with files predictions
	 * arg 1 ontology file
	 * arg 2 annotation file
	 * arg 3 conversion table
	 * arg 4 additional info
	 * arg 5 Interest GOs comma separated
*/
	
	/*
D:\DanielVIB\Maize\PredictGenes\PingORunComparison\rawCLRpred\GO
D:\DanielVIB\maizeEnrich\superAnnotV3\go-basic.obo
D:\DanielVIB\maizeEnrich\superAnnotV3\AnnotV3UniqueGeneGOShown.tsv
D:\DanielVIB\maizeEnrich\superAnnotV3\id_conversion.zmaWithGeneModelV3.tsv
D:\DanielVIB\maizeEnrich\superAnnotV3\annotation_extra.zma.csv
3700,4871,6355

/home/dacru/Midas/biocomp/groups/group_esb/dacru/Maize/reMapV3/PingoCheck/PingORunComparison/SAC_corr_0.724_FDR_0.01_bingo0.01/runFilter/GO
/home/dacru/Midas/biocomp/groups/group_esb/dacru/maizeEnrich/superAnnotV3/go-basic.obo
/home/dacru/Midas/biocomp/groups/group_esb/dacru/maizeEnrich/superAnnotV3/AnnotV3UniqueGeneGOShown.tsv
/home/dacru/Midas/biocomp/groups/group_esb/dacru/maizeEnrich/superAnnotV3/id_conversion.zmaWithGeneModelV3.tsv
/home/dacru/Midas/biocomp/groups/group_esb/dacru/maizeEnrich/superAnnotV3/annotation_extra.zma.csv
3700,4871,6355
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

//		String dir1 = "D:/DanielVIB/Maize/PredictGenes/PingORunComparison/CLR_allPlants_FDR_0.001_bingo0.01_Run/runFilter/GO";
//		String goCats = "D:/DanielVIB/Maize/PredictGenes/PingORunComparison/GO-ID.txt";		
		String goCats = args[6];
		String dir1 = args[0];
		String arfi[];
		
		ArrayList<Integer> targetGos = new ArrayList<>();
		
		String str;
		GoID goi;
		ArrayList<AnnotationDetail> annoTmp = null;
		String targetsFound;
		String currPlazaName = null;
		ArrayList<String> descs;
		GoTerm got = null;
		boolean multipleAnnot = false;
		
		try(BufferedReader fileGoCats = new BufferedReader(new FileReader(goCats))){
			
			HashMap<Integer, GoTerm> ontology = GoDescLoader.loadOntology(args[1]);
			HashMap<String, ArrayList<AnnotationDetail>> notat = GoDescLoader.loadAnnotation(args[2]);
			HashMap<String, GoID> ids = GoDescLoader.loadIdentifiers(args[3]);
			HashMap<String, ArrayList<String>> descripts = GoDescLoader.loadDescriptions(args[4]);
			String geneName;
			//get target go as arrayList
			arfi = args[5].split(",");
			for (String stGo : arfi) {
				targetGos.add(Integer.parseInt(stGo));
			}
			
			while ((str = fileGoCats.readLine()) != null) {
				
				BufferedReader file1 = new BufferedReader(new FileReader(dir1+str+"/SAC2_Pearson_MaizeNetwork.tsv.pgo"));
//				PrintWriter outFile = new PrintWriter(new FileOutputStream("D:/DanielVIB/Maize/PredictGenes/PingORunComparison/rawCLRpredOut/GO"+str));
				//PrintWriter outFile = new PrintWriter(new FileOutputStream("/home/dacru/Midas/biocomp/groups/group_esb/dacru/Maize/reMapV3/PingoCheck/PingORunComparison/SAC_corr_0.724_FDR_0.01_bingo0.01/merged/GO"+str+".tsv"));
				PrintWriter outFile = new PrintWriter(new FileOutputStream("/home/dacru/Midas/biocomp/groups/group_esb/dacru/Maize/reMapV3/PingoCheck/PingORunComparison/SAC2FDR_0.01_bingo0.01/merged/GO"+str+".tsv"));
				//header
				outFile.println("GeneName"+"\t"+"PredGO_ID"+"\t"+"PredGO_Name"+"\t"+"SAC_Pval"+"\t"+"WithTF"+"\t"+"OtherNames"+"\t"+"Descrip"+"\t"+"CurrentAnnot");
				//skip until the header
				while ((str = file1.readLine()) != null) {
					arfi=str.split("\t");
					if(arfi[0].equalsIgnoreCase("GO ID"))
						break;
				}
				
				while ((str = file1.readLine()) != null) {
					arfi=str.split("\t");
					geneName = arfi[2];
					outFile.print(geneName+"\t"+arfi[0]+"\t"+arfi[1]+"\t"+arfi[5]+"\t");
					
					//Print if has any TF from the list
					annoTmp = notat.get(geneName);
					
					if(annoTmp!=null){

						targetsFound = GoDescLoader.contentGO(targetGos, annoTmp);
						outFile.print(targetsFound);

					}else{
						outFile.print("None");
					}
					
					outFile.print("\t");
					
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
					
					//add description
					descs = descripts.get(currPlazaName);
					if(descs!=null){
						for (String sing : descs) {
							outFile.print(sing+"~");
						}
					}
					currPlazaName=null;
					descs=null;
					
					//add current annots
					if(annoTmp!=null){

						for (AnnotationDetail annoDetail : annoTmp) {
							//check only the shown ones
							if(annoDetail.isIs_shown()){
								got = ontology.get(annoDetail.getGo());
								if(got!=null){
									if(!multipleAnnot){
										outFile.print("\t("+annoDetail.getGo()+")"+got.getName());
										// are there many?
										if(annoTmp.size()>1)
											multipleAnnot = true;
									}else{
										//for appending the rest
										outFile.print("|("+annoDetail.getGo()+")"+got.getName());
									}
								}else{
									//print missing cats
									//System.out.println(annoDetail.getGo());
								}
							}	
						}
						multipleAnnot = false;
						outFile.println();


					}else{
						//there is no annotation
						outFile.println("\t"+null);
					}
					annoTmp=null;
					
				}	
				file1.close();
				outFile.close();
			}
			
			
			
			
			 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
