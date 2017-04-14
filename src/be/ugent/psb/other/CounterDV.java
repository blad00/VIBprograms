package be.ugent.psb.other;

import java.io.FileOutputStream;
import java.io.PrintWriter;

public class CounterDV {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String fileIn = args[0];
		String fileOut = args[1];
		String param = args[2];
		String list1 = args[3];
		String list2 = args[4];
		int specificNumberValid = Integer.parseInt(args[5]);
		
		MatrixLoader matla =  new MatrixLoader();
		
		double [][] dataMatrix  = null;

		try(PrintWriter outFile = new PrintWriter(new FileOutputStream(fileOut))){

			dataMatrix = matla.loadMatrixWithHeaderXYinvalidItem(fileIn, param);
//			dataMatrix = matla.loadMatrixWithHeaderXY(fileIn);
			
//			matla.loadSetsForRows(list1, list2);
			
			
//			matla.operRowsNoParam(dataMatrix);
			matla.operRowsNoParamSimple(dataMatrix);
//			matla.operColsNoParamSimple(dataMatrix);
			
//			matla.printItems(outFile, matla.rowItemsStats);
//			matla.printItems(outFile, matla.columnItemsStats);
			
			for(int i=1;i<15;i++){
				PrintWriter outFileCell = new PrintWriter(new FileOutputStream(fileOut+i));
				matla.printItemsSpecificNumberValidItem(outFileCell, matla.rowItemsStats, i);
				outFileCell.close();
			}
			
//			matla.printItemsSpecificNumberValidItem(outFile, matla.rowItemsStats, specificNumberValid);
			
			
		}catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			// TODO: handle exception
		}




	}

	

}
