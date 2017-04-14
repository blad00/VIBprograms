package be.ugent.psb.other;

import java.util.Arrays;

public class Statistics{
	
	private Double[] data;
	private int size;   
	
	

	public Statistics() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Statistics(Double[] data){
		this.data = data;
		size = data.length;
	}   
	
	public double getSum(){
		double sum = 0.0;
		for(double a : data)
			sum += a;
		
		return sum;
	}

	public double getMean(){
		double sum = 0.0;
		for(double a : data)
			sum += a;
		return sum/size;
	}

	public double getVariance(){
		double mean = getMean();
		double temp = 0;
		for(double a :data)
			temp += (mean-a)*(mean-a);
		return temp/size;
	}

	public double getStdDev(){
		return Math.sqrt(getVariance());
	}
	
	public double getCV(){
		return getStdDev()/getMean();
	}

	public double median(){
		Arrays.sort(data);

		if (data.length % 2 == 0){
			return (data[(data.length / 2) - 1] + data[data.length / 2]) / 2.0;
		} 
		else{
			return data[data.length / 2];
		}
	}

	public Double[] getData() {
		return data;
	}

	public void setData(Double[] data) {
		this.data = data;
		setSize(this.data.length);
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}
