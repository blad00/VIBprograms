package be.ugent.psb.go;


import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Set;

public class GOinfoFinder {
/*
 * Class to get data from GO terms, options in arg 2 name or code
 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//For Heike favor I moved args 0 - 1 
		try(BufferedReader inFile = new BufferedReader(new FileReader(args[0]));
				PrintWriter outFile = new PrintWriter(new FileOutputStream(args[0]+".ID"))){
			
			
			String choice = args[2];
			
			String str;
			String go_key;
			GoTerm tmpTerm;
			HashMap<String, GoTerm> ontologyName = null;
			HashMap<Integer, GoTerm> ontologyId = null;
			
			if(choice.equals("name")) {
				ontologyName = GoDescLoader.loadOntologyNameKey(args[1]);
				while ((str = inFile.readLine()) != null) {
					go_key = str.split("\t")[0];
					tmpTerm = ontologyName.get(go_key);
					outFile.println(tmpTerm.getId()+"\t"+str);
				}	
			}
			if(choice.equals("id")) {
				ontologyId = GoDescLoader.loadOntology(args[1]);
				while ((str = inFile.readLine()) != null) {
					go_key = str.split("\t")[0];
					tmpTerm = ontologyId.get(Integer.parseInt(go_key));
					outFile.println(tmpTerm.getName()+"\t"+str);
				}
			}
			
			if(choice.equals("allOnto")) {
				ontologyId = GoDescLoader.loadOntology(args[1]);
				Set<Integer> ids = ontologyId.keySet();
				
				for(int id : ids) {
					tmpTerm = ontologyId.get(id);
					outFile.println(tmpTerm.getId()+"\t"+tmpTerm.getName()+"\t"+tmpTerm.getNameSpace());
				}
				
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
