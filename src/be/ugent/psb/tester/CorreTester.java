package be.ugent.psb.tester;

import java.util.Arrays;

import org.apache.commons.math3.stat.correlation.Covariance;
import org.apache.commons.math3.stat.correlation.KendallsCorrelation;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.apache.commons.math3.stat.correlation.SpearmansCorrelation;

import be.ugent.psb.clr.CLR2EnigmaPearson;

public class CorreTester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		double[] x = new double[1000];
	    double[] y = new double[1000];
	    for (int i = 0; i < x.length; i++) {
	        x[i] = Math.random()*100;
	        y[i] = Math.random()*100;
	    }
	    long startTime;
	    long finishTime;
	    System.out.println("x: " + Arrays.toString(x));
	    System.out.println("y: " + Arrays.toString(y));
	    System.out.println("Covariance: " + new Covariance().covariance(x,y));
	    
	    startTime = System.currentTimeMillis();
	    System.out.println("Pearson's Correlation: " + new PearsonsCorrelation().correlation(x,y));
	    finishTime = System.currentTimeMillis();
	    System.out.println("That took: " + (finishTime - startTime) + " ms");
	    
	    startTime = System.currentTimeMillis();
	    System.out.println("Pearson's CorrelationManual: " + CLR2EnigmaPearson.pearsonCorrelation(x,y));
	    finishTime = System.currentTimeMillis();
	    System.out.println("That took: " + (finishTime - startTime) + " ms");
	    
	    System.out.println("Spearman's Correlation: " + new SpearmansCorrelation().correlation(x,y));
	    System.out.println("Kendall's Correlation: " + new KendallsCorrelation().correlation(x,y));
	}

}
