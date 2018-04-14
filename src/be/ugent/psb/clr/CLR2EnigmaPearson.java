package be.ugent.psb.clr;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

//import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

public class CLR2EnigmaPearson {
	/**
	 * @param args
	 * This program gets the couples that overcome a specific threshold from CLR matrix , self connections are not allowed.
	 * Arg 0 matrix file
	 * Arg 1 threshold
	 * Arg 2 output file
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		String amatFile = args[0];
		double ths = Double.parseDouble(args[1]);
		String outputFile = args[2];
		String str;
		String[] arrayl;
		int i;
		Set <String> pairs = new TreeSet<String>();
		double[] exp1;
		double[] exp2;
		double corr;
		
		try(BufferedReader amat = new BufferedReader(new FileReader(amatFile));
				PrintWriter outFile = new PrintWriter(new FileOutputStream(outputFile));
				PrintWriter outFileEnigma = new PrintWriter(new FileOutputStream(outputFile+".ENI"))){
			
			//load all expression
			HashMap<String,double[]> mapExp = getAllGeneExp(args[3]);
			
			//save header
			str = amat.readLine();
			String header [] = str.split("\t");
			double val;
			outFileEnigma.println("Gene_1"+"\t"+"Gene_2"+"\t"+"PosNeg"+"\t"+"P-value");
			while ((str = amat.readLine()) != null) {
				arrayl = str.split("\t");
				for(i = 1;i<arrayl.length;i++){
					val = Double.parseDouble(arrayl[i]);
					if(val>ths){
						if(!pairs.contains(arrayl[0]+header[i])){
							pairs.add(arrayl[0]+header[i]);
							pairs.add(header[i]+arrayl[0]);
							outFile.println(arrayl[0]+"\t"+header[i]+"\t"+val);
							
							//get exp from current genes
							exp1 = mapExp.get(arrayl[0]);
							exp2 = mapExp.get(header[i]);
							//calc corr
							corr = pearsonCorrelation(exp1, exp2); 
							if(corr>=0){
								outFileEnigma.println(arrayl[0]+"\t"+header[i]+"\t"+"+"+"\t"+"0");
							}
						}	
					}
				}
			}
			

			
//			double[] x = {1, 2, 89, 8};
//		    double[] y = {2, 4, 8, 16};
//		    double corr = new PearsonsCorrelation().correlation(y, x);
//		    System.out.println(corr);
//		    System.out.println("-----");
//		    System.out.println(Correlation(x, y));
		    
			
		}
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
	
}
