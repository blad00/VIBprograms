package be.ugent.psb.network;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

/*
 * Program to load a network and get the neighbors of specific nodes, but also counts the number of coincidences in 2 sets 
 * args 0 network file
 * args 1 output file1
 * args 2 output file2
 * args 3 interest list of nodes
 * args 4 first set to count
 * args 5 second set to count
 * /home/dfcruz/Midas/biocomp/groups/group_esb/dacru/Maize/reMapV3/PingoCheck/GraphStats/3GraphsUsed/CLR_0.001EnigmaFormatOnlyPos.txt
/home/dfcruz/Midas/biocomp/groups/group_esb/dacru/Maize/reMapV3/atLeast1Samples/01ManW_CV/AdditionalAnalyses/HVG_TFs.Nei
/home/dfcruz/Midas/biocomp/groups/group_esb/dacru/Maize/reMapV3/atLeast1Samples/01ManW_CV/AdditionalAnalyses/LVG_TFs.Nei
/home/dfcruz/Midas/biocomp/groups/group_esb/dacru/Maize/reMapV3/atLeast1Samples/01ManW_CV/AdditionalAnalyses/TF3_V3_MergedPLAZA_ENIformat.tsv
/home/dfcruz/Midas/biocomp/groups/group_esb/dacru/Maize/reMapV3/atLeast1Samples/01ManW_CV/AdditionalAnalyses/HIGHcv.tsv
/home/dfcruz/Midas/biocomp/groups/group_esb/dacru/Maize/reMapV3/atLeast1Samples/01ManW_CV/AdditionalAnalyses/LOWcv.tsv
 */

public class NeighborhoodFinderListIn2sets {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String str = null;
		List <String> neighbours; 
		String arile[];
		try(BufferedReader inListFile = new BufferedReader(new FileReader(args[3]));
				PrintWriter outFile1 = new PrintWriter(new FileOutputStream(args[1]));
				PrintWriter outFile2 = new PrintWriter(new FileOutputStream(args[2]));
				BufferedReader inSet1 = new BufferedReader(new FileReader(args[4]));
				BufferedReader inSet2 = new BufferedReader(new FileReader(args[5]))){
			
			Set <String> set1 = new TreeSet<String>();;
			Set <String> set2 = new TreeSet<String>();;
			int count1=0;
			int count2=0;
			
			
			//load set 1 
			while ((str = inSet1.readLine()) != null) {
				set1.add(str);
			}
			//load set 2 
			while ((str = inSet2.readLine()) != null) {
				set2.add(str);
			}
			
			
			
			
			//load Network
			Graph<String, DefaultEdge> network = loadNetwork(args[0]);
			//skip header
			inListFile.readLine();
			
			Set <String> vertexList = network.vertexSet();
			String genetf;
			
			while ((str = inListFile.readLine()) != null) {
				//because it is in enigma format 2 columns
				arile = str.split("\t");
				genetf = arile[0];
				
				if(vertexList.contains(genetf)){
					neighbours = Graphs.neighborListOf(network, genetf);
					
					for (String stri : set1) {
						if(neighbours.contains(stri))
							outFile1.println(genetf);
					}
					for (String stri : set2) {
						if(neighbours.contains(stri))
							outFile2.println(genetf);
					}
					
				}
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
//		System.out.println(Graphs.neighborListOf(network, args[1]));

	}

	public static Graph<String, DefaultEdge> loadNetwork(String filePath){
		String str = null;
		String arile[];
		Graph<String, DefaultEdge> g = new SimpleGraph<>(DefaultEdge.class);
		ArrayList<String> nodeList = loadNodeList(filePath);

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
				g.addEdge(arile[0], arile[1]);				
			}


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return g;

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

}
