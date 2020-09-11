package be.ugent.psb.other;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class PingoExecCreator {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		String inFolder = args[0];
		int iniNet=1;
		int finNet=5;		
		
		for(int i=1;i<=10;i++){
			
			PrintWriter outFileExec = new PrintWriter(new FileOutputStream(inFolder+"SRAexec"+i+".sh"));
			outFileExec.println("#! /bin/bash");
			outFileExec.println("#$ -S /bin/bash");
			outFileExec.println("#$ -N SRAPINGO"+i);
			outFileExec.println("#$ -cwd");
			outFileExec.println("#$ -l h_vmem=25g");
			outFileExec.println("module load java");
			outFileExec.println("for i in {"+iniNet+".."+finNet+"};");
			outFileExec.println("do ");
			outFileExec.println("echo \"Net${i}\";");
			outFileExec.println("outF=../propFiles/samples_${i}_PINGO.properties");
			outFileExec.println("echo \"clusterPar1=NaN\" > ${outF}");
			outFileExec.println("echo \"clusterPar2=NaN\" >> ${outF}");
			outFileExec.println("echo \"nrMCSAruns=3\" >> ${outF}");
			outFileExec.println("echo \"beginT1=0.1\" >> ${outF}");
			outFileExec.println("echo \"endT1=0.001\" >> ${outF}");
			outFileExec.println("echo \"step1=0.05\" >> ${outF}");
			outFileExec.println("echo \"beginT2=0.01\" >> ${outF}");
			
			outFileExec.close();
			
			iniNet+=5;
			finNet+=5;
			
			
		}
		
	}

}
