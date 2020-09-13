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

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

/*
 * Program to load a network and get the neighbors of specific nodes
 * args 0 network file
 * args 1 output file
 * args 2 interest list of nodes
 */

public class NeighborhoodFinderListCount {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String str = null;
		List <String> neighbours; 
		String arile[];
		try(BufferedReader inListFile = new BufferedReader(new FileReader(args[2]));
				PrintWriter outFile = new PrintWriter(new FileOutputStream(args[1]))){
			
			//load Network
			Graph<String, DefaultEdge> network = loadNetwork(args[0]);
			//skip header
			//inListFile.readLine();
			
			Set <String> vertexList = network.vertexSet();
			String genetf;
			//header
			outFile.println("gene"+"\t"+"counLinks");
			while ((str = inListFile.readLine()) != null) {
				//because it is in enigma format 2 columns
				arile = str.split("\t");
				genetf = arile[0];
				
				if(vertexList.contains(genetf)){
					neighbours = Graphs.neighborListOf(network, genetf);
					outFile.println(genetf+"\t"+neighbours.size());
				
				}else{
					outFile.println(genetf+"\t"+0);
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
