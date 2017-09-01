package be.ugent.psb.go;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

import BiNGO.AnnotationParser;
import BiNGO.BingoParameters;
import agronomics.FunCat;
import cytoscape.data.annotation.OntologyTerm;
import enigma.GoImport;
import enigma.ModuleNetwork;


public class GOAnnotLoader {
	//This class is to load ontology file and annotation file based on Bingo Classes.
	//	
	public String dataDir;
    public static ModuleNetwork M ;
    public HashMap<OntologyTerm,FunCat> funCats;
    public BingoParameters bp ;
    
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
        GOAnnotLoader goan = new GOAnnotLoader();
        goan.loadGOannot(args);
        
        
	}
	
	
	public void loadGOannot(String[] args) throws Exception{
		HashMap<String,String> hp = new HashMap();
		
		     
        if (args.length > 0){
             getProperties (args[0],hp);
        }
        
        dataDir = setProperty("dataDir",hp);
        
        String graphFile = setProperty ("graphFile", hp);
        String timestamp = (new Date()).toString().replace(' ', '_');
        File oDir = new File(dataDir,timestamp);
        String outputDir = oDir.toString();
        boolean dirCreated = oDir.mkdir();
        if(!dirCreated){
            System.out.println("Couldn't make output directory...\n");
        }
	 
	    String expressionData = setProperty("expressionData",hp);
	    String pvaldata = setProperty("pvalData",hp);
	    String regulatordata = setProperty("regulatorData",hp);
	    String geneDescriptionFile = setProperty("geneDescriptionFile",hp);
	    String signif_def = setProperty("fdrBiNGO",hp);
        
        String annotation_file_def = setProperty("annotationFile",hp);
        String ontology_file_def = setProperty("ontologyFile",hp);
        Boolean annotation_default = Boolean.parseBoolean(setProperty("annotationDefault",hp));
        Boolean ontology_default = Boolean.parseBoolean(setProperty("ontologyDefault",hp));
        String namespace = setProperty("namespace",hp);
        
        bp = new BingoParameters(dataDir);
        bp.setAnnotationFile(dataDir+System.getProperty("file.separator")+annotation_file_def);
        bp.setSpecies(bp.getSpeciesNameFromFilename(annotation_file_def));
        bp.setOntologyFile(dataDir+System.getProperty("file.separator")+ontology_file_def);
        String deleteCodes = setProperty("deleteCodes",hp);

        M = new ModuleNetwork(hp, dataDir, outputDir, expressionData, pvaldata, regulatordata, geneDescriptionFile);
        GoImport go = new GoImport(M, dataDir, annotation_file_def, ontology_file_def, annotation_default, ontology_default, namespace, deleteCodes);
        
        AnnotationParser annParser = bp.initializeAnnotationParser();
        
        if (annParser.getStatus()) {
            bp.setAnnotation(annParser.getAnnotation());
            bp.setOntology(annParser.getOntology());
            bp.setAlias(annParser.getAlias());
        }
        if (bp.getAnnParser().getOrphans()){
            System.out.println("WARNING : Some category labels in the annotation file" + "\n" +
                    "are not defined in the ontology. Please check the compatibility of" + "\n" +
                    "these files. For now, these labels will be ignored and calculations" + "\n" +
                    "will proceed.");
        }
        
        
        
	}
	
	public void getProperties(String filename, HashMap<String,String> h) {
        try {
            
            BufferedReader file = new BufferedReader(new FileReader(filename));
            String inputline;
            
            // Read data from file
            while ((inputline = file.readLine()) != null) {
                inputline = inputline.trim();
                if (!inputline.startsWith("#")) {
                    Scanner s = new Scanner(inputline).useDelimiter("=");
                    if (s.hasNext()) {
                        String key = s.next().trim();
                        String value = s.next().trim();
                        //System.out.println(key + "<=>" + value);
                        h.put(key,value);
                    }
                }
            }
            file.close();
            
            
        } catch (IOException e) {
            System.out.println("Error: IOexception: " + e);
            System.exit(1);
        }
        
        
        
    }
	
	public String setProperty(String name,HashMap<String,String> h) {
        String ret;
        if (h.get(name) == null)
            Die("property " + name + " not found in parameter file.");
        ret = h.get(name);
        return(ret);
    }
	
	public void Die(String str) {
        System.out.println("Error: " + str);
        System.exit(1);
    }
	
	 private String openResourceFile(String name) {
	        System.out.println(name);
		 	return bp.getClass().getResource("/"+name).toString();
	    }      

}
