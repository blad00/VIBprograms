package be.ugent.psb.tester;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Testo {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
//		int num = Integer.parseInt("NaN");
//		System.out.println(0+num);
		
//		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		String params = br.readLine();
//		String para[] = params.split(" ");
//		int mDays = Integer.parseInt(para[0]);
//		int nProducts = Integer.parseInt(para[1]);
//		
//		String result= null;
//		
//		for(int i=0; i<mDays; i++){
//		    params = br.readLine();
//		    para = params.split(" ");
//		    int nbigger = 0;
//		    int nTmp = 0;
//		    for(int j=0;j<para.length; j++){
//		        nTmp = Integer.parseInt(para[j]);
//		        if(nTmp>nbigger){
//		            nbigger = nTmp;
//		        }
//		        result = result + " " +nbigger;
//		    }
//		}
//		
//		System.out.println(result);
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String size = br.readLine();
		int lSize = Integer.parseInt(size);
		
		//int arExp = new int[lSize];
		
		String params = br.readLine();
	    String[] para = params.split(" ");
	    String tmp;
	    for(int j=0;j<para.length; j+=2){
	        tmp = para[j];
	        para[j] = para[j+1];
	        para[j+1] = tmp;
	    }
	    
	    System.out.println(para);
	}

}
