package be.ugent.psb.cv;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.HashMap;


public class GeneLengthMeter {
/*
 * this program will find the genomic length of a list of genes in the GFF file
 * D:\DanielVIB\Maize\reMapV3\atLeast1Samples\CVsent\AdditionalAnalyses\LOWcv.tsv
D:\DanielVIB\Maize\reMapV3\atLeast1Samples\CVsent\AdditionalAnalyses\zea_mays.b73.version3.longest_transcript.exon_features.gff3
 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String fileIn = args[0];
		String annotFile = args[1];
		String str;
		
		int curLength;
		
		try(BufferedReader inFile = new BufferedReader(new FileReader(fileIn));
				PrintWriter outFile = new PrintWriter(new FileOutputStream(fileIn+".length"))){
			
			HashMap<String, Integer> mapFullLenght = getLengthMapCDS(annotFile);
			
			while ((str = inFile.readLine()) != null) {
				
				curLength = mapFullLenght.get(str);
				outFile.println(str+"\t"+curLength);
				
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static HashMap<String, Integer> getLengthMap(String pathAnnotFile){
		
		String str;
		String arfi[];
		HashMap<String, Integer> mapLenght = new HashMap<>();
		
		String geneName;
		int iniPos=0;
		int finPos=0;
		int geneLength=0;
		
		try(BufferedReader annotsFile = new BufferedReader(new FileReader(pathAnnotFile))){
			while ((str = annotsFile.readLine()) != null) {
				arfi = str.split("\t");
				if(arfi[2].equals("gene")){
					geneName=arfi[8].split(";")[0].split("=")[1];
					iniPos = Integer.parseInt(arfi[3]);
					finPos = Integer.parseInt(arfi[4]);
					geneLength = finPos-iniPos;
					mapLenght.put(geneName, geneLength);
				}	
			}
			
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return mapLenght;
	}
	
	public static HashMap<String, Integer> getLengthMapCDS(String pathAnnotFile){
		
		String str;
		String arfi[];
		HashMap<String, Integer> mapLenght = new HashMap<>();
		
		String geneName = "";
		int iniPos=0;
		int finPos=0;
		int geneLength=0;
		
		try(BufferedReader annotsFile = new BufferedReader(new FileReader(pathAnnotFile))){
			while ((str = annotsFile.readLine()) != null) {
				arfi = str.split("\t");
				if(arfi[2].equals("CDS")){
					geneName=arfi[8].split(";")[1].split("=")[1];
					iniPos = Integer.parseInt(arfi[3]);
					finPos = Integer.parseInt(arfi[4]);
					geneLength = finPos-iniPos;
					
					if(mapLenght.containsKey(geneName)){
						geneLength += mapLenght.get(geneName);
						mapLenght.put(geneName, geneLength);
					}else{
						mapLenght.put(geneName, geneLength);
					}
				}	
			}
			
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return mapLenght;
	}

}
