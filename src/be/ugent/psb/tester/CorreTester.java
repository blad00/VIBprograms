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

		double[] x = new double[10];
	    double[] y = new double[10];
	    for (int i = 0; i < x.length; i++) {
	        x[i] = Math.random()*100;
	        y[i] = Math.random()*100;
	    }
	    System.out.println("x: " + Arrays.toString(x));
	    System.out.println("y: " + Arrays.toString(y));
	    System.out.println("Covariance: " + new Covariance().covariance(x,y));
	    System.out.println("Pearson's Correlation: " + new PearsonsCorrelation().correlation(x,y));
	    System.out.println("Pearson's CorrelationManual: " + CLR2EnigmaPearson.pearsonCorrelation(x,y));
	    System.out.println("Spearman's Correlation: " + new SpearmansCorrelation().correlation(x,y));
	    System.out.println("Kendall's Correlation: " + new KendallsCorrelation().correlation(x,y));
	}

}
