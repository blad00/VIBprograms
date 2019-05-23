package be.ugent.psb.go;


import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.HashMap;

public class GOinfoFinder {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try(BufferedReader inFile = new BufferedReader(new FileReader(args[0]));
				PrintWriter outFile = new PrintWriter(new FileOutputStream(args[0]+".ID"))){
			HashMap<String, GoTerm> ontology = GoDescLoader.loadOntologyNameKey(args[1]);
			
			String str;
			String go_name;
			GoTerm tmpTerm;
			
			while ((str = inFile.readLine()) != null) {
				go_name = str.split("\t")[0];
				tmpTerm = ontology.get(go_name);
				outFile.println(tmpTerm.getId()+"\t"+str);
			}
			
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
