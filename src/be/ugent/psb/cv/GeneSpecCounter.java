package be.ugent.psb.cv;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.HashMap;

public class GeneSpecCounter {
	
	/*
	 * This program search in the full genome annotation a list of gene, to count all their specifications
	 * D:\DanielVIB\Maize\reMapV3\atLeast1Samples\CVsent\AdditionalAnalyses\HIGHcv.tsv
D:\DanielVIB\maizeEnrich\superAnnotV3\zea_mays.b73.version3.all_transcripts.all_features.gff3 
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//load list of genes 
		
		String fileIn = args[0];
		String annotFile = args[1];
		String str;
		String arfi[];
		String geneName;
		String spec;
		GenesSpecs specsTmp;
		
		try(BufferedReader inFile = new BufferedReader(new FileReader(fileIn));
				BufferedReader inFile4Print = new BufferedReader(new FileReader(fileIn));
				BufferedReader allAnnots = new BufferedReader(new FileReader(annotFile));
				PrintWriter outFile = new PrintWriter(new FileOutputStream(fileIn+".specs"))){
			
			HashMap<String, GenesSpecs> geneListSpec = new HashMap<>();
			//load list of genes 
			while ((str = inFile.readLine()) != null) {
				geneListSpec.put(str, new GenesSpecs());
			}
			
			//go through the annotation file checking if the gene is in our list, if that so add the spec we are interested  
			while ((str = allAnnots.readLine()) != null) {
				arfi = str.split("\t");
				//take the gene name from the alias column #8
				geneName = arfi[8].split(";")[1].split("=")[1];
				//check if is in our list
				if(geneListSpec.containsKey(geneName)){
					spec = arfi[2];
					specsTmp = geneListSpec.get(geneName);
					switch (spec) {
					case "mRNA":
						specsTmp.plusMrnas();
						break;
					case "exon":
						specsTmp.plusExones();
						break;
					case "intron":
						specsTmp.plusIntrones();
						break;	
					case "CDS":
						specsTmp.plusCds();
						break;	
					default:
						break;
					}
				}
			}
			
			// print Specs file in the same order
			outFile.println("GeneName"+"\t"+"mRNAs"+"\t"+"exons"+"\t"+"introns"+"\t"+"CDSs");
			while ((str = inFile4Print.readLine()) != null) {
				specsTmp = geneListSpec.get(str);
				outFile.println(str+"\t"+specsTmp.getMrnas()+"\t"+specsTmp.getExones()+"\t"+specsTmp.getIntrones()+"\t"+specsTmp.getCds());
			}
			
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
