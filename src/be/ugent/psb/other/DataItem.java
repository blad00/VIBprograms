package be.ugent.psb.other;

public class DataItem {
	
	private String dataName;
	
	private double sumValidElements;
	private double avgValidElements;
	private int numValidElements;
	private int totalElements;
	private int numInList1;
	private int numInList2;
	private int num0;
	
	private double StdDev;
	private double coeVar;
	
	
	public double getSumValidElements() {
		return sumValidElements;
	}
	public void setSumValidElements(double sumValidElements) {
		this.sumValidElements = sumValidElements;
	}
	public double getAvgValidElements() {
		return avgValidElements;
	}
	public void setAvgValidElements(double avgValidElements) {
		this.avgValidElements = avgValidElements;
	}
	public int getNumValidElements() {
		return numValidElements;
	}
	public void setNumValidElements(int numValidElements) {
		this.numValidElements = numValidElements;
	}
	public int getTotalElements() {
		return totalElements;
	}
	public void setTotalElements(int totalElements) {
		this.totalElements = totalElements;
	}
	public int getNumInList1() {
		return numInList1;
	}
	public void setNumInList1(int numInList1) {
		this.numInList1 = numInList1;
	}
	public int getNumInList2() {
		return numInList2;
	}
	public void setNumInList2(int numInList2) {
		this.numInList2 = numInList2;
	}
	public double getStdDev() {
		return StdDev;
	}
	public void setStdDev(double stdDev) {
		StdDev = stdDev;
	}
	public double getCoeVar() {
		return coeVar;
	}
	public void setCoeVar(double coeVar) {
		this.coeVar = coeVar;
	}
/*	public String getDataName() {
		return dataName;
	}
	public void setDataName(String dataName) {
		this.dataName = dataName;
	}*/
	public int getNum0() {
		return num0;
	}
	public void setNum0(int num0) {
		this.num0 = num0;
	}
	
	

}
