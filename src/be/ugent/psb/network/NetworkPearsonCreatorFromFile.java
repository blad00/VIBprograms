package be.ugent.psb.network;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

//import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

public class NetworkPearsonCreatorFromFile {
	/*
	 * This program loads an expression matrices and based on a correlation threshold will output the Enigma format network only positive relationships
	 * loading the network file name,  
	 * arg 0 input file path for Expression matrices file with thresholds
	 * arg 1 input folder with matrices
	 * arg 1 output path
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub

		String matsFile = args[0];
		String amatFile = null;
		String inputPath = args[1];
		String outputPath = args[2];

		//		Set <String> pairs = new TreeSet<String>();
		double[] exp1;
		double[] exp2;
		double corr;
		String arfi[];
		String str;
		double pcoe;
		String gene1;
		String gene2;
		int i,j=0;

		//save header
		try(BufferedReader matricesFile = new BufferedReader(new FileReader(matsFile))){
			//skip header 
			matricesFile.readLine();

			while ((str = matricesFile.readLine()) != null) {

				
				gene1 = null;
				gene2 = null;

				arfi = str.split("\t");
				//get exp file name
				amatFile = arfi[1];
				//get coefficient cutoff
				pcoe = Double.parseDouble(arfi[2]);

				//load all gene names to iterate
				ArrayList <String> geneNames = getGeneNameList(inputPath+amatFile);

				//load all expression
				HashMap<String,double[]> mapExp = getAllGeneExp(inputPath+amatFile);

				PrintWriter outFileEnigma = new PrintWriter(new FileOutputStream(outputPath+amatFile+".ENI"));
				outFileEnigma.println("Gene_1"+"\t"+"Gene_2"+"\t"+"PosNeg"+"\t"+"P-value");

				for(i=0;i<geneNames.size();i++){
					gene1 = geneNames.get(i);
					exp1 = mapExp.get(gene1);
					for(j=i+1;j<geneNames.size();j++){
						gene2 = geneNames.get(j);
						exp2 = mapExp.get(gene2);
						corr = pearsonCorrelation(exp1, exp2);

						//check corr ths and only positives
						if(corr>=pcoe){
							//outFileEnigma.println(gene1+"\t"+gene2+"\t"+"+"+"\t"+"0");
							outFileEnigma.println(gene1+"\t"+gene2+"\t"+"+"+"\t"+corr);
							//System.out.println(gene1+"\t"+gene2+"\t"+corr);
						}

					}
				}
				
				outFileEnigma.close();
			}
		}

	}

	public static HashMap<String,double[]> getAllGeneExp(String expFile) throws FileNotFoundException, IOException{
		String str;
		String geneName;
		String[] ar;
		double[] arExp;
		HashMap<String, double[]> mapGeneExp = new HashMap<>(); 

		try(BufferedReader exFile = new BufferedReader(new FileReader(expFile))){
			//skip header
			exFile.readLine();


			while ((str = exFile.readLine()) != null) {
				ar = str.split("\t");
				//get geneName
				geneName = ar[0];
				arExp = new double[ar.length-1];
				//get only the expression
				for(int i=1;i<ar.length;i++){
					arExp[i-1]=Double.parseDouble(ar[i]);
				}
				mapGeneExp.put(geneName, arExp);
			}
		}

		return mapGeneExp;
	}

	public static ArrayList <String> getGeneNameList(String expFile) throws FileNotFoundException, IOException{
		String str;
		String geneName;
		String[] ar;
		ArrayList<String> genes = new ArrayList<>(); 

		try(BufferedReader exFile = new BufferedReader(new FileReader(expFile))){
			//skip header
			exFile.readLine();


			while ((str = exFile.readLine()) != null) {
				ar = str.split("\t");
				//get geneName
				geneName = ar[0];
				genes.add(geneName);
			}
		}
		return genes;
	}

	public static double pearsonCorrelation(double[] x2, double[] y2) {
		//TODO: check here that arrays are not null, of the same length etc

		double sx = 0.0;
		double sy = 0.0;
		double sxx = 0.0;
		double syy = 0.0;
		double sxy = 0.0;

		int n = x2.length;

		for(int i = 0; i < n; ++i) {
			double x = x2[i];
			double y = y2[i];

			sx += x;
			sy += y;
			sxx += x * x;
			syy += y * y;
			sxy += x * y;
		}

		// covariation
		double cov = sxy / n - sx * sy / n / n;
		// standard error of x
		double sigmax = Math.sqrt(sxx / n -  sx * sx / n / n);
		// standard error of y
		double sigmay = Math.sqrt(syy / n -  sy * sy / n / n);

		// correlation is just a normalized covariation
		return cov / sigmax / sigmay;
	}
}
