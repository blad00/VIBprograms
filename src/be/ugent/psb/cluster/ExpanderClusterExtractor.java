package be.ugent.psb.cluster;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class ExpanderClusterExtractor {
/*
 * This program will take the default output from expander bioclustering tools  eg SAMBA ISA
 * and extract per line # cluster and its genes involved 2 cells
 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String fileIn = args[0];
		String str;
		String arfi[];
		String cluster;
		String gene;
		ArrayList<String> listGeneTmp;
		
		try(BufferedReader inFile = new BufferedReader(new FileReader(fileIn));
				PrintWriter outFile = new PrintWriter(new FileOutputStream(fileIn+"_suppl.tsv"))){
			
			HashMap<String, ArrayList<String>> mapClusters = new HashMap<>();
			//skipheader
			inFile.readLine();
			
			while ((str = inFile.readLine()) != null) {
				arfi = str.split("\t");
				
				cluster = arfi[0].split("_")[1];
				gene = arfi[2];
				
				if(mapClusters.containsKey(cluster)){
					listGeneTmp = mapClusters.get(cluster);
					listGeneTmp.add(gene);
					mapClusters.put(cluster, listGeneTmp);
				}else{
					listGeneTmp = new ArrayList<>();
					listGeneTmp.add(gene);
					mapClusters.put(cluster, listGeneTmp);
				}
			
			}
			
			// now printin the file
			Set<String> clusterKeys = mapClusters.keySet();
			//title
			outFile.println("Module_number"+"\t"+"Member_Genes");
			for(String clusterTmp : clusterKeys){
				listGeneTmp = mapClusters.get(clusterTmp);
				outFile.print(clusterTmp+"\t");
				for (String geneTmp : listGeneTmp) {
					outFile.print(geneTmp+"\t");
				}
				outFile.println();
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
