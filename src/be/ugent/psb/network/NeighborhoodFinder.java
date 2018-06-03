package be.ugent.psb.network;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

/*
 * Program to load a network and get the neighbors of specific nodes
 * args 0 network file
 * args 1 interest node
 */

public class NeighborhoodFinder {

	public static void main(String[] args) {
		// TODO Auto-generated method stub


		//get neighbors
		Graph<String, DefaultEdge> network = loadNetwork(args[0]);

		System.out.println(Graphs.neighborListOf(network, args[1]));

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
