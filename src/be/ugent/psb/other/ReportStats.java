package be.ugent.psb.other;


import java.io.FileOutputStream;
import java.io.PrintWriter;

public class ReportStats {
	/**
	 * @param args
	 * This program generates the basic statistics from tab file
	 * Arg 0 data file with expression for instance
	 * Arg 1 output file
	 * Arg 2 value that will be skipped during the calculation
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String fileIn = args[0];
		String fileOut = args[1];
		String param = args[2];
		
		MatrixLoader matla =  new MatrixLoader();
		double [][] dataMatrix  = null;
		
		try(PrintWriter outFile = new PrintWriter(new FileOutputStream(fileOut))){
			dataMatrix = matla.loadMatrixWithHeaderXY(fileIn);
			
			matla.operRowsNoParamSimpleWith0(dataMatrix);
//			matla.operColsNoParamSimple(dataMatrix);
//			matla.operRowsNoParamSimple(dataMatrix);
			
			matla.printItems(outFile, matla.rowItemsStats);
//			matla.printItems(outFile, matla.columnItemsStats);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
