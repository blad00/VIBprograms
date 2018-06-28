package be.ugent.psb.annotFile;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class RILannot {
	/**
	 * @param args
	 * This program generates a division for each phenotype which is related in 2ways ANOVA and 8ways ANOVA
	 * 
	 * Arg 0 input hilde's file
	 * Arg 1 output file
	 **/
	public static void main(String[] args) {
		String rilFileName = args[0];
		String outputFile = args[1];
		String str;
		String[] arrayl;

		HashMap<String, ArrayList<String>> phenoGenes = new HashMap<>();

		ArrayList<String> arrPhe2w;
		ArrayList<String> arrPhe4w;

		ArrayList<String> tmpGenes;

		try(BufferedReader rilFile = new BufferedReader(new FileReader(rilFileName));
				PrintWriter outFile = new PrintWriter(new FileOutputStream(outputFile))){

			//skip header
			rilFile.readLine();
			int line = 1;

			while ((str = rilFile.readLine()) != null) {
//
//				System.out.println(line++); 
//				System.out.println(str);

				tmpGenes = new ArrayList<>();

				arrayl=str.split("\t");
				//first check +				
				if(!arrayl[1].equals("")&&!arrayl[3].equals("")){
					arrPhe2w = new ArrayList<>(Arrays.asList(arrayl[1].split(" ")));
					arrPhe4w = new ArrayList<>(Arrays.asList(arrayl[3].split(" ")));

					//iterate each pheno
					for (String pheno : arrPhe2w) {
						if(arrPhe4w.contains(pheno)){
							if(!phenoGenes.containsKey(pheno)){
								//add gene
								tmpGenes.add(arrayl[0]);
								phenoGenes.put(pheno, tmpGenes);
							}else{
								tmpGenes = phenoGenes.get(pheno);
								tmpGenes.add(arrayl[0]);
							}
						}
						tmpGenes = new ArrayList<>();

					}					

				}

				//now check -				
				if(!arrayl[2].equals("")&&!arrayl[4].equals("")){
					arrPhe2w = new ArrayList<>(Arrays.asList(arrayl[2].split(" ")));
					arrPhe4w = new ArrayList<>(Arrays.asList(arrayl[4].split(" ")));

					//iterate each pheno
					for (String pheno : arrPhe2w) {
						if(arrPhe4w.contains(pheno)){
							if(!phenoGenes.containsKey(pheno)){
								//add gene
								tmpGenes.add(arrayl[0]);
								phenoGenes.put(pheno, tmpGenes);
							}else{
								tmpGenes = phenoGenes.get(pheno);
								tmpGenes.add(arrayl[0]);
							}
						}

						tmpGenes = new ArrayList<>();
					}			
				}





			}

			//printing
			Iterator<Entry<String, ArrayList<String>>> it = phenoGenes.entrySet().iterator();
			String pheno;
			while (it.hasNext()) {
				HashMap.Entry pair = (HashMap.Entry)it.next();
				pheno = (String) pair.getKey();
				tmpGenes = (ArrayList<String>) pair.getValue();
				it.remove(); // avoids a ConcurrentModificationException

				outFile.println(pheno);

				for (String gene : tmpGenes) {
					outFile.println(gene+"\t"+pheno);
				}


			}





		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

}
