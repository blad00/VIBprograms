package be.ugent.psb.network;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

public class NetworkImpl {

	private Graph<String, DefaultEdge> network;
	private int numNodes;
	private int numEdges;
	private double clusterCoefficient;
	private double density;
	private double unannot;
	
	
	
	public Graph<String, DefaultEdge> getNetwork() {
		return network;
	}
	public void setNetwork(Graph<String, DefaultEdge> network) {
		this.network = network;
	}
	public int getNumNodes() {
		return numNodes;
	}
	public void setNumNodes(int numNodes) {
		this.numNodes = numNodes;
	}
	public int getNumEdges() {
		return numEdges;
	}
	public void setNumEdges(int numEdges) {
		this.numEdges = numEdges;
	}
	public double getClusterCoefficient() {
		return clusterCoefficient;
	}
	public void setClusterCoefficient(double clusterCoefficient) {
		this.clusterCoefficient = clusterCoefficient;
	}
	public double getDensity() {
		return density;
	}
	public void setDensity(double density) {
		this.density = density;
	}
	public double getUnannot() {
		return unannot;
	}
	public void setUnannot(double unannot) {
		this.unannot = unannot;
	}
	
	
	
}
