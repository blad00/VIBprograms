package be.ugent.psb.other;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NonEnrichDeleter {

	/**
	 * @param args
	 */

	//	This class will delete any file without any enrichment 
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String inFolder = args[0];
		String filename;

		File file = null;
		File[] files = null;

		BufferedReader inFileExpTable = null;
		String str;
		String argsFile[];

		boolean delete = false;

		if (inFolder != null && !inFolder.equals("")) {
			file = new File(inFolder);
			if (file.exists()) {
				files = file.listFiles();
			}
		}

		List<File> listFiles = new ArrayList<File>(Arrays.asList(files));
		try{
			for (int i=0;i<listFiles.size();i++) {
				filename = listFiles.get(i).getAbsolutePath();
				inFileExpTable = new BufferedReader(new FileReader(filename));

				while ((str = inFileExpTable.readLine()) != null) {
					argsFile=str.split("\t");
					if(argsFile[0].equalsIgnoreCase("# GO_code")){
						if ((str = inFileExpTable.readLine()) == null) {
							delete = true;
						}else{
							break;
						}
					}
				}
				inFileExpTable.close();

				if(delete){
					File deleteFile = new File(filename);
					
					if(deleteFile.delete())
						System.out.println("deleted "+filename);
					else
						System.out.println("problems deleting "+filename);

					delete = false;
				}


			}	

		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}

	}

}
