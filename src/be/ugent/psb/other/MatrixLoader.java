package be.ugent.psb.other;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;



public class MatrixLoader {


	/**
	 * @param args
	 * @throws Exception 
	 */

	ArrayList<String> labelRows;
	ArrayList<String [] > listData;
	ArrayList <String> labelColumns;

	HashMap<String, DataItem> rowItemsStats;
	HashMap<String, DataItem> columnItemsStats;
	
	Set <String> list1;
	Set <String> list2;
	
	Statistics stat;
	

	public MatrixLoader() {
		super();

		labelRows = new ArrayList<>();
		listData = new ArrayList<>();
		labelColumns =new ArrayList<>();

		rowItemsStats = new HashMap<>();
		columnItemsStats = new HashMap<>();
		
		Set <String> list1 = null;
		Set <String> list2 = null;
		
		stat = new Statistics();
		// TODO Auto-generated constructor stub
	}
	

	public double [][] loadMatrixWithHeaderXYinvalidItem(String fileIn, String invalid){

		String str = null;
		String param = invalid;
		String arrayLineFile[];

		double [][] dataMatrix  = null;

		boolean header = true;

		int i = 0;
		int j = 0;

		//Load in matrix for further counts
		try(BufferedReader inFile = new BufferedReader(new FileReader(fileIn))){

			while ((str = inFile.readLine()) != null) {

				arrayLineFile = str.split("\t");

				if(header){
					for (String sd : arrayLineFile) {
						if(!sd.equalsIgnoreCase("GeneName"))
							labelColumns.add(sd);
//							columnItemsStats.put(sd,null);
					}
					header = false;
					continue;
				}

				labelRows.add(arrayLineFile[0]);
				rowItemsStats.put(arrayLineFile[0], null);

				listData.add(arrayLineFile);

			}

			double value = 0;

			dataMatrix = new double [labelRows.size()][labelColumns.size()];
			for (String strRow[] : listData) {

				for(j=1;j<strRow.length;j++){

					if(!strRow[j].equals(param)){
						try {
							value=Double.parseDouble(strRow[j]);
							dataMatrix [i][j-1]=value;
						} catch (Exception e) {
							dataMatrix [i][j-1]=0;
							// TODO: handle exception
						}

					}else{
						dataMatrix [i][j-1]=0;
					}	

				}

				i++;
			}



		}catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			// TODO: handle exception
		}

		return dataMatrix;

	}
	
	public double [][] loadMatrixWithHeaderXY(String fileIn){

		String str = null;
		
		String arrayLineFile[];

		double [][] dataMatrix  = null;

		boolean header = true;

		int i = 0;
		int j = 0;

		//Load in matrix for further counts
		try(BufferedReader inFile = new BufferedReader(new FileReader(fileIn))){

			while ((str = inFile.readLine()) != null) {

				arrayLineFile = str.split("\t");

				if(header){
					for (String sd : arrayLineFile) {
						if(!sd.equalsIgnoreCase("GeneName"))
							labelColumns.add(sd);
//							columnItemsStats.put(sd,null);
					}
					header = false;
					continue;
				}

				labelRows.add(arrayLineFile[0]);
				rowItemsStats.put(arrayLineFile[0], null);

				listData.add(arrayLineFile);

			}

			double value = 0;

			dataMatrix = new double [labelRows.size()][labelColumns.size()];
			for (String strRow[] : listData) {

				for(j=1;j<strRow.length;j++){

					
						try {
							value=Double.parseDouble(strRow[j]);
							dataMatrix [i][j-1]=value;
						} catch (Exception e) {
							dataMatrix [i][j-1]=0;
							// TODO: handle exception
						}
				}

				i++;
			}



		}catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			// TODO: handle exception
		}

		return dataMatrix;

	}


	public void operRowsNoParam(double [][] dataMatrix){

		ArrayList<Double> actualValues = new ArrayList<>(); 

		int i = 0;
		int j = 0;

		DataItem itemTmp=null;
		int numList1 = 0;
		int numList2 = 0;


		for(i=0;i<labelRows.size();i++){
			for(j=0;j<labelColumns.size();j++){
				
				
				
				if(dataMatrix[i][j]!=0){

					actualValues.add(dataMatrix[i][j]);
					if(list1.contains(labelColumns.get(j))){
						numList1++;
					}
					
					if(list2.contains(labelColumns.get(j))){
						numList2++;
					}
				}

			}	

			stat.setData(actualValues.toArray(new Double[actualValues.size()]));

			itemTmp = new DataItem();
			itemTmp.setAvgValidElements(stat.getMean());
			itemTmp.setNumValidElements(stat.getSize());
			itemTmp.setSumValidElements(stat.getSum());
			itemTmp.setTotalElements(labelColumns.size());
			itemTmp.setStdDev(stat.getStdDev());
			itemTmp.setCoeVar(stat.getCV());
			
			
			
			itemTmp.setNumInList1(numList1);
			itemTmp.setNumInList2(numList2);

			rowItemsStats.put(labelRows.get(i), itemTmp);

			numList1 = 0;
			numList2 = 0;
			
			actualValues.clear();
			
			
			
		}	




	}
	//this methos excludes 0 from the calculation mean sd etc
	public void operRowsNoParamSimple(double [][] dataMatrix){

		ArrayList<Double> actualValues = new ArrayList<>(); 

		int i = 0;
		int j = 0;

		DataItem itemTmp=null;
		

		for(i=0;i<labelRows.size();i++){
			for(j=0;j<labelColumns.size();j++){
				
				
				
				if(dataMatrix[i][j]!=0){

					actualValues.add(dataMatrix[i][j]);
					
				}

			}	

			stat.setData(actualValues.toArray(new Double[actualValues.size()]));

			itemTmp = new DataItem();
			itemTmp.setAvgValidElements(stat.getMean());
			itemTmp.setNumValidElements(stat.getSize());
			itemTmp.setSumValidElements(stat.getSum());
			itemTmp.setTotalElements(labelColumns.size());
			itemTmp.setStdDev(stat.getStdDev());
			itemTmp.setCoeVar(stat.getCV());
			
			
			


			rowItemsStats.put(labelRows.get(i), itemTmp);

		
			actualValues.clear();
			
			
			
		}	




	}
	
	//this method includes 0 in the calculations but still count the numbers of 0s in the numvalid elements !!carefull!!
	public void operRowsNoParamSimpleNumbers0(double [][] dataMatrix){

		ArrayList<Double> actualValues = new ArrayList<>(); 

		int i = 0;
		int j = 0;

		DataItem itemTmp=null;
		int num0=0;

		for(i=0;i<labelRows.size();i++){
			for(j=0;j<labelColumns.size();j++){

				
				if(dataMatrix[i][j]==0){
					num0++;
				}
				actualValues.add(dataMatrix[i][j]);

			}	

			stat.setData(actualValues.toArray(new Double[actualValues.size()]));

			itemTmp = new DataItem();
			itemTmp.setAvgValidElements(stat.getMean());
			itemTmp.setNumValidElements(num0);
			itemTmp.setSumValidElements(stat.getSum());
			itemTmp.setTotalElements(labelColumns.size());
			itemTmp.setStdDev(stat.getStdDev());
			itemTmp.setCoeVar(stat.getCV());
			
			
			


			rowItemsStats.put(labelRows.get(i), itemTmp);

		
			actualValues.clear();
			num0=0;
			
			
			
		}	




	}
	
	public void operRowsNoParamSimpleWith0(double [][] dataMatrix){

		ArrayList<Double> actualValues = new ArrayList<>(); 

		int i = 0;
		int j = 0;

		DataItem itemTmp=null;
		

		for(i=0;i<labelRows.size();i++){
			for(j=0;j<labelColumns.size();j++){
				
				
				
				

				actualValues.add(dataMatrix[i][j]);
					
				

			}	

			stat.setData(actualValues.toArray(new Double[actualValues.size()]));

			itemTmp = new DataItem();
			itemTmp.setAvgValidElements(stat.getMean());
			itemTmp.setNumValidElements(stat.getSize());
			itemTmp.setSumValidElements(stat.getSum());
			itemTmp.setTotalElements(labelColumns.size());
			itemTmp.setStdDev(stat.getStdDev());
			itemTmp.setCoeVar(stat.getCV());
			
			
			


			rowItemsStats.put(labelRows.get(i), itemTmp);

		
			actualValues.clear();
			
			
			
		}	




	}



	public void operColsNoParam(double [][] dataMatrix){
		int i = 0;
		int j = 0;
		
		DataItem itemTmp=null;
		
		ArrayList<Double> actualValues = new ArrayList<>(); 
		
		for(j=0;j<labelColumns.size();j++){
			for(i=0;i<labelRows.size();i++){
				if(dataMatrix[i][j]!=0){
					actualValues.add(dataMatrix[i][j]);
				}

			}

			stat.setData(actualValues.toArray(new Double[actualValues.size()]));

			itemTmp = new DataItem();
//			itemTmp.setDataName(labelColumns.get(j));
			itemTmp.setAvgValidElements(stat.getMean());
			itemTmp.setNumValidElements(stat.getSize());
			itemTmp.setSumValidElements(stat.getSum());
			itemTmp.setTotalElements(labelRows.size());
			itemTmp.setStdDev(stat.getStdDev());
			itemTmp.setCoeVar(stat.getCV());


			columnItemsStats.put(labelColumns.get(j), itemTmp);
			actualValues.clear();
						
		}	

	}
	
	public void operColsNoParamSimple(double [][] dataMatrix){
		int i = 0;
		int j = 0;
		
		DataItem itemTmp=null;
		
		ArrayList<Double> actualValues = new ArrayList<>(); 
		
		for(j=0;j<labelColumns.size();j++){
			for(i=0;i<labelRows.size();i++){
				
					actualValues.add(dataMatrix[i][j]);
				

			}

			stat.setData(actualValues.toArray(new Double[actualValues.size()]));

			itemTmp = new DataItem();
//			itemTmp.setDataName(labelColumns.get(j));
			itemTmp.setAvgValidElements(stat.getMean());
			itemTmp.setNumValidElements(stat.getSize());
			itemTmp.setSumValidElements(stat.getSum());
			itemTmp.setTotalElements(labelRows.size());
			itemTmp.setStdDev(stat.getStdDev());
			itemTmp.setCoeVar(stat.getCV());


			columnItemsStats.put(labelColumns.get(j), itemTmp);
			actualValues.clear();
						
		}	

	}
	
	
	
	public void printItems(PrintWriter outFile, HashMap<String, DataItem> items){
		
		Class<DataItem> dataClass = DataItem.class; // Get Class	
		Method[] dataMethods = dataClass.getMethods();
			
		ArrayList<Method> infoMethods = new ArrayList<>();
		
		Set<String> keys = items.keySet();
		
//		Arrays.sort(keys);
		
		
		DataItem dataTmp;
		
		
		
		for (Method method : dataMethods) {
			if((method.getName().charAt(0)=='g')&&(!method.getName().equals("getClass"))){
				infoMethods.add(method);
			}
		}
		
		//Titles
		for (Method methodTmp : infoMethods) {
			outFile.print("\t"+methodTmp.getName());
		}
		outFile.println();
		
		
		
		
		for (String key : keys) {
			
			outFile.print(key);
			dataTmp = items.get(key);
			
			for (Method methodTmp : infoMethods) {
				try {
					
					outFile.print("\t"+methodTmp.invoke(dataTmp, null));
					
					
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			outFile.println();
			
		}
		
	}
	
	
	public void printItemsSpecificNumberValidItem(PrintWriter outFile, HashMap<String, DataItem> items,int specificValidItems){
		Class<DataItem> dataClass = DataItem.class; // Get Class	
		Method[] dataMethods = dataClass.getMethods();
			
		ArrayList<Method> infoMethods = new ArrayList<>();
		
		Set<String> keys = items.keySet();
			
		DataItem dataTmp;
		
		
		
		for (Method method : dataMethods) {
			if((method.getName().charAt(0)=='g')&&(!method.getName().equals("getClass"))){
				infoMethods.add(method);
			}
		}
		
		//Titles
		for (Method methodTmp : infoMethods) {
			outFile.print("\t"+methodTmp.getName());
		}
		outFile.println();
		
		
		
		
		for (String key : keys) {
			
			
			dataTmp = items.get(key);
								
			if(dataTmp.getNumValidElements()==specificValidItems){
//			if(dataTmp.getNumValidElements()==specificValidItems&&dataTmp.getNumInList2()==8){
//			if(dataTmp.getNumInList1()==specificValidItems&&dataTmp.getNumInList2()==0){
				outFile.print(key);
			
				for (Method methodTmp : infoMethods) {
					try {
						
						outFile.print("\t"+methodTmp.invoke(dataTmp, null));
						
						
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
				outFile.println();
			}	
			
		}
		
	}
	
	public void loadSetsForRows(String fileSet1, String fileSet2){
		
		list1=new TreeSet<String>();
		list2=new TreeSet<String>();
		String str;
		try(BufferedReader inFile1 = new BufferedReader(new FileReader(fileSet1));BufferedReader inFile2 = new BufferedReader(new FileReader(fileSet2))){
			
			while ((str = inFile1.readLine()) != null) {
				list1.add(str);
			}
			
			while ((str = inFile2.readLine()) != null) {
				list2.add(str);
			}
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
