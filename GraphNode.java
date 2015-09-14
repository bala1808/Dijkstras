import java.util.ArrayList;
import java.util.HashMap;

// Graph Node class contains the basic structure of each of the nodes in the Graph.

public class GraphNode {
	int vertexId;							// Indicates the vertex ID of the node.
	int minDist;							// Indicates the minimum distance of this node from the source node. 
	HashMap<Integer,Integer> adjacencyList; // Contains the adjacency list of the node. This list has Nodes connected to this node along with the distance to the node i.e weight. 
	ArrayList<Integer> path;				// Contains a list of nodes that traverse the minimum path from this node to the source node.
	
	
	// Constructor that initializes default values for class members.
	GraphNode(int Id){
		vertexId = Id;
		minDist = Integer.MAX_VALUE;
		adjacencyList = new HashMap<Integer,Integer>();
		path = new ArrayList<Integer>();
	}
	
	// Function to add a node to the adjacency list of this node.
	void addAdjacencyList(int vertex, int weight){
		adjacencyList.put(vertex, weight);
	}
	

}
