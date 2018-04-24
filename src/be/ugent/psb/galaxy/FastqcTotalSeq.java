package be.ugent.psb.galaxy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jsoup.nodes.Element;

import org.jsoup.Jsoup;

/*
 * This program takes an output folder with the HTMLs from fastQC and extract the sample name and the total reads
 * !!!!!Be carefull with the sample name given it is very variable
 * arg 0 HTMLs folder
 * arg 1 full output file
 */

public class FastqcTotalSeq {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String inFolder = args[0];
		String fileOut = args[1];
		String filename;

		File file = null;
		File[] files = null;

		try(PrintWriter outFile = new PrintWriter(new FileOutputStream(fileOut))){

			if (inFolder != null && !inFolder.equals("")) {
				file = new File(inFolder);
				if (file.exists()) {
					files = file.listFiles();	
				}
			}

			List<File> listFiles = new ArrayList<File>(Arrays.asList(files));

			for (int i=0;i<listFiles.size();i++) {
				filename = listFiles.get(i).getAbsolutePath();
//				Forward
//				printTotalSeqs(filename,outFile);
//				Forward cleaned			
				printTotalSeqsCleaned(filename,outFile);
				outFile.println();
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}	
	}
	
	public static void printTotalSeqs(String filePath, PrintWriter outFile) throws IOException {
		// TODO Auto-generated method stub
		File input = new File(filePath);
	    org.jsoup.nodes.Document doc = Jsoup.parse(input, "UTF-8");
        org.jsoup.select.Elements rows = doc.select("tr");
        
        String stmp;
        String totalSeq = null;
        String sampleName = null;
        String sampleGZ = null;
        Element column;
        int coli;
        String arle[];
        
        for(org.jsoup.nodes.Element row :rows)
        {
            org.jsoup.select.Elements columns = row.select("td");
//          for (org.jsoup.nodes.Element column:columns){
            for (coli=0;coli<columns.size();coli++){
            	column = columns.get(coli);
            	stmp = column.text();
            	
            	if(stmp.equalsIgnoreCase("Filename")){
            		sampleGZ = columns.get(++coli).text();
            		arle = sampleGZ.split("_");
            		if(arle[1].equals("1")){
            			sampleName = arle[0]; 
            		}else{
            			sampleName = arle[1];
            		}
            		outFile.print(sampleName+"\t");
            	}
            	
            	if(stmp.equalsIgnoreCase("Total Sequences")){
            		totalSeq = columns.get(++coli).text();
            		outFile.print(totalSeq);
            	}
            		
            	
            	
            }
            
        }
        
        
	}
	
	public static void printTotalSeqsCleaned(String filePath, PrintWriter outFile) throws IOException {
		// TODO Auto-generated method stub
		File input = new File(filePath);
	    org.jsoup.nodes.Document doc = Jsoup.parse(input, "UTF-8");
        org.jsoup.select.Elements rows = doc.select("tr");
        
        String stmp;
        String totalSeq = null;
        String sampleName = null;
        String sampleGZ = null;
        Element column;
        int coli;
        String arle[];
        
        for(org.jsoup.nodes.Element row :rows)
        {
            org.jsoup.select.Elements columns = row.select("td");
//          for (org.jsoup.nodes.Element column:columns){
            for (coli=0;coli<columns.size();coli++){
            	column = columns.get(coli);
            	stmp = column.text();
            	
            	if(stmp.equalsIgnoreCase("Filename")){
            		sampleGZ = columns.get(++coli).text();
            		arle = sampleGZ.split("_");
            		if(arle[3].equals("1")){
            			sampleName = arle[2]; 
            		}else{
            			sampleName = arle[3];
            		}
            		outFile.print(sampleName+"\t");
            	}
            	
            	if(stmp.equalsIgnoreCase("Total Sequences")){
            		totalSeq = columns.get(++coli).text();
            		outFile.print(totalSeq);
            	}
            		
            	
            	
            }
            
        }
        
        
	}


}
