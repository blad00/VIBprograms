package be.ugent.psb.network;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;






public class NetworkStats {

	/*
	 * Class that print stats of a folder with networks in Enigma format
	 * 
	 input:
	 * args[0] = path with networks
	 * args[1] = Annotation file
	 * args[2] = outputfile
	 * 
	 */

	public static void main(String[] args) {

		printTableStats4Path(args[0], args[1], args[2]);


	}
	
	public static void printTableStats4Path(String path, String annotFile, String outFile){

		String inFolder = path;
		File file = null;
		File[] files = null;
		String filename;

		NetworkImpl netTMP;
		Set <String> annotGenes;

		try(PrintWriter outFileNets = new PrintWriter(new FileOutputStream(outFile))){

			outFileNets.println("NetWork"+"\t"+"numNodes"+"\t"+"numEdges"+"\t"+"ClusterCoe"+"\t"+"Density"+"\t"+"Unannot");

			if (inFolder != null && !inFolder.equals("")) {
				file = new File(inFolder);
				if (file.exists()) {
					files = file.listFiles();
				}
			}

			List<File> listFiles = new ArrayList<File>(Arrays.asList(files));
			annotGenes = loadIfExistAnnotation(annotFile);

			// iter list of files
			for (int i=0;i<listFiles.size();i++) {
				file = listFiles.get(i);

				//skip folder or any other weird file
				if(file.isDirectory()||isBinaryFile(file))
					continue;

				filename = listFiles.get(i).getAbsolutePath();
				
				System.out.println("processing "+file.getName());

				netTMP = loadNetwork(filename, annotGenes);
				
				outFileNets.println(file.getName()+"\t"+netTMP.getNumNodes()+"\t"+netTMP.getNumEdges()+"\t"+netTMP.getClusterCoefficient()+
						"\t"+netTMP.getDensity()+"\t"+netTMP.getUnannot());


			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}


	public static NetworkImpl loadNetwork(String filePath, Set <String> genesWithAnnot){

		NetworkImpl net = new NetworkImpl();

		int numEdges=0;

		String str = null;
		String arile[];
		
		Graph<String, DefaultEdge> g = new SimpleGraph<>(DefaultEdge.class);
		LocalClusteringCoefficientResult<String> resultCluCoe;
		ArrayList<String> nodeList = loadNodeList(filePath);
		net.setNumNodes(nodeList.size());
		System.out.println("Done setNumNodes");

		try(BufferedReader inFile = new BufferedReader(new FileReader(filePath))){



			//add all nodes
			for (String node : nodeList) {
				g.addVertex(node);
			}


			//Skip header
			inFile.readLine();

			//add all edges
			while ((str = inFile.readLine()) != null) {
				arile = str.split("\t");
				//avoid self loop
				if(!arile[0].equals(arile[1])){
					g.addEdge(arile[0], arile[1]);

				}
				numEdges++;

			}

			net.setNumEdges(numEdges);
			System.out.println("Done setNumEdges");
			
			net.setNetwork(g);
			System.out.println("Done setNetwork");
			
			net.setDensity(calculateDensity(nodeList.size(), numEdges));
			System.out.println("Done calculateDensity");
			//for unannotated fraction
			net.setUnannot(unannotFract(genesWithAnnot, nodeList));
			System.out.println("Done unannotFract");

			resultCluCoe = new LocalClusteringCoefficientMetric<>(g).calculate();
			System.out.println("Done LocalClusteringCoefficientMetric");
			
			net.setClusterCoefficient(resultCluCoe.getAverageClusteringCoefficient());
			System.out.println("Done getAverageClusteringCoefficient");

			



		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return net;

	}

	public static double calculateDensity(double numNodes, double numEdges){

		double potentialCons = (numNodes*(numNodes-1))/2;
		return numEdges/potentialCons;

	}

	public static ArrayList<String> loadNodeList(String filePath){

		String str = null;
		String arile[];
		ArrayList<String> nodeList = new ArrayList<>();

		try(BufferedReader inFile = new BufferedReader(new FileReader(filePath))){
			//Skip header
			inFile.readLine();

			while ((str = inFile.readLine()) != null) {
				arile = str.split("\t");
				if(!nodeList.contains(arile[0]))
					nodeList.add(arile[0]);

				if(!nodeList.contains(arile[1]))
					nodeList.add(arile[1]);


			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return nodeList;
	}

	

	public static Set <String> loadIfExistAnnotation(String annotFile) throws Exception, IOException{

		String str = null;
		String[] arrayLineFile;
		Set <String> annots = new TreeSet<String>();
		String gene_id = null;

		try(BufferedReader inFile = new BufferedReader(new FileReader(annotFile))){
			//skip header
			inFile.readLine();
			while ((str = inFile.readLine()) != null) {
				arrayLineFile=str.split("\t");

				gene_id = arrayLineFile[2];

				if(!annots.contains(gene_id)){
					annots.add(gene_id);
				}



			}



		}

		return annots;
	}

	public static double unannotFract(Set <String> annots, ArrayList<String> geneList){

		double notAnnot=0;

		for (String gene : geneList) {
			if(!annots.contains(gene))
				notAnnot++;
		}



		return notAnnot/geneList.size();
	}



	public static boolean isBinaryFile(File f) throws FileNotFoundException, IOException {
		FileInputStream in = new FileInputStream(f);
		int size = in.available();
		if(size > 1024) size = 1024;
		byte[] data = new byte[size];
		in.read(data);
		in.close();

		int ascii = 0;
		int other = 0;

		for(int i = 0; i < data.length; i++) {
			byte b = data[i];
			if( b < 0x09 ) return true;

			if( b == 0x09 || b == 0x0A || b == 0x0C || b == 0x0D ) ascii++;
			else if( b >= 0x20  &&  b <= 0x7E ) ascii++;
			else other++;
		}

		if( other == 0 ) return false;

		return 100 * other / (ascii + other) > 95;
	}

}
