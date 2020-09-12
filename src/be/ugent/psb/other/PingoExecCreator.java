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
		
		for(int i=1;i<=20;i++){
			
			PrintWriter outFileExec = new PrintWriter(new FileOutputStream(inFolder+"BIG_SRAexec"+i+".sh"));
			outFileExec.println("#! /bin/bash");
			outFileExec.println("#$ -S /bin/bash");
			outFileExec.println("#$ -N SRAPINGO"+i);
			outFileExec.println("#$ -cwd");
			outFileExec.println("#$ -l h_vmem=40g");
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
			outFileExec.println("echo \"endT2=0.0001\" >> ${outF}");
			outFileExec.println("echo \"step2=0.01\" >> ${outF}");
			outFileExec.println("echo \"coolingRate2=0.995\" >> ${outF}");
			outFileExec.println("echo \"dataDir=../\" >> ${outF}");
			outFileExec.println("echo \"expressionData=samples_${i}.tsv\" >> ${outF}");
			outFileExec.println("echo \"pvalData=samples_${i}.tsv\" >> ${outF}");
			outFileExec.println("echo \"geneDescriptionFile=false\" >> ${outF}");
			outFileExec.println("echo \"chipData=null\" >> ${outF}");
			outFileExec.println("echo \"interactionData=null\" >> ${outF}");
			outFileExec.println("echo \"clusterExpressionData=false\" >> ${outF}");
			outFileExec.println("echo \"clusterInteractionData=true\" >> ${outF}");
			outFileExec.println("echo \"pvalThreshold=0.6296976\" >> ${outF}");
			outFileExec.println("echo \"usePvals=false\" >> ${outF}");
			outFileExec.println("echo \"fdr=0.01\" >> ${outF}");
			outFileExec.println("echo \"conditionSelection=false\" >> ${outF}");
			outFileExec.println("echo \"regulatorData=TF3_V3_MergedPLAZA_ENIformat.tsv\" >> ${outF}");
			outFileExec.println("echo \"regulatoryGoCats=30528 4672 79\" >> ${outF}");
			outFileExec.println("echo \"fdrTF=0.05\" >> ${outF}");
			outFileExec.println("echo \"cosCorrThreshold=0.80\" >> ${outF}");
			outFileExec.println("echo \"drawModules=png\" >> ${outF}");
			outFileExec.println("echo \"goCats=3 160 302 1666 2831 5975 6091 6139 6259 6412 6464 6629 6810 6950 6952 6970 6972 6974 6979 6984 7049 7154 7165 8150 8152 9056 9058 9266 9269 9314 9408 9409 9411 9414 9415 9416 9605 9607 9611 9612 9617 9620 9625 9628 9637 9639 9642 9648 9651 9719 9723 9725 9733 9737 9753 9755 9790 9791 9863 9908 9965 9966 9987 15979 16043 16049 19538 19725 19748 30154 31347 33554 34976 35556 35966 40007 40029 42594 48364 48366 48367 48827 51707 70482 80134 2239 6971 7166 7231 7267 7602 8219 9268 9270 9413 9606 9608 9615 9624 9629 9630 9635 9638 9652 9704 9735 9739 9741 9756 9819 9835 9838 9856 9861 9875 10017 10019 10117 10150 10196 10212 10335 10865 23014 30522 31930 33194 34059 43434 47484 48545 51409 52173 75136 80027 80167 80183 90436 2000024 2000070 19684 9767 10200 10243 1901698 1901700 9657 44710 71554 71555 45229 5976 42546 71669 1901421 71472 9832 44264 9834 33692 271 44262 34637 10383 30243 30244 44036 44042 6073 16051 51273 51274 9698 55114 22900 51188 51186 9644 71489 70592 44038 70589 10410 45491 1901564 6518 43603 43604 1901566 43043 6260 42742 1101 97306 71215 51301 9760 1902347 9751 10162 80187 48316 80006 48527 48830 10154 1562\" >> ${outF}");
			outFileExec.println("echo \"BiNGO=true\" >> ${outF}");
			outFileExec.println("echo \"conditionBiNGO=false\" >> ${outF}");
			outFileExec.println("echo \"fdrBiNGO=0.01\" >> ${outF}");
			outFileExec.println("echo \"annotationFile=MaizeV3MergedDiMonPropPlusC4.anno\" >> ${outF}");
			outFileExec.println("echo \"ontologyFile=go-basic.obo\" >> ${outF}");
			outFileExec.println("echo \"annotationDefault=false\" >> ${outF}");
			outFileExec.println("echo \"ontologyDefault=false\" >> ${outF}");
			outFileExec.println("echo \"graphFile=samples_${i}.tsv.ENI\" >> ${outF}");
			outFileExec.println("echo \"deleteCodes=ISS RCA IEA\" >> ${outF}");
			outFileExec.println("echo \"namespace=biological_process\" >> ${outF}");
			outFileExec.println("echo \"refDefault=true\" >> ${outF}");
			outFileExec.println("echo \"networkThreshold=0.01\" >> ${outF}");
			outFileExec.println("java -Xms5000m -Xmx35g -classpath /group/esb/dacru/Maize/reMapV3/PiNGO_Maize_Ext/ENIGMA_stmae_sept2020.jar agronomics.PrecisionRecall4ROC ../propFiles/samples_${i}_PINGO.properties > ../samples_${i}_Pingo.out");
			outFileExec.println("done");
						
			outFileExec.close();
			
			iniNet+=5;
			finNet+=5;
			
			
		}
		
	}

}
