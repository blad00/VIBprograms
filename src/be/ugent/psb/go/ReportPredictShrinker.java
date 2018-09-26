package be.ugent.psb.go;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ReportPredictShrinker {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String inFolder = args[0];
		String filename;

		File file = null;
		File[] files = null;

		BufferedReader inFileExpTable = null;
		PrintWriter outFilePaper = null;
		PrintWriter outFileShort = null;
		String str;
		String arFi[];
		
		int i;
		String geneName;
		
		
		ArrayList<AnnotationDetail> annoTmp = null;
		GoTerm got = null;
		boolean multipleAnnot = false;

		String currPlazaName = null;
		GoID goi;
		ArrayList<String> descs;
		
		ArrayList<Integer> targetGos = new ArrayList<>();
		
		String targetsFound;
		
		if (inFolder != null && !inFolder.equals("")) {
			file = new File(inFolder);
			if (file.exists()) {
				files = file.listFiles();
			}
		}

		List<File> listFiles = new ArrayList<File>(Arrays.asList(files));
		try{
			
			HashMap<String, ArrayList<AnnotationDetail>> notat = GoDescLoader.loadAnnotation(args[1]);
			
			for (i=0;i<listFiles.size();i++) {
				filename = listFiles.get(i).getAbsolutePath();
				inFileExpTable = new BufferedReader(new FileReader(filename));
				
				outFilePaper = new PrintWriter(new FileOutputStream(filename+"Out.tsv"));
				outFileShort = new PrintWriter(new FileOutputStream(filename+"Out.tsv"));
				//make a change
			}
		
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}

}
