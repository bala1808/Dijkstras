import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Scanner;


// Graph class contains the implementation of the directed Graph.

public class Graph {
	
	HashMap<Integer, GraphNode> Nodes = null;	// Contains all nodes with vertexId as key and node as value.
	int vertices = 0;							// Contains the number of vertices in the graph.
	int edges = 0;								// Contains the number of edges in the graph.
	
	// Function to generate the graph from the input file.
	void generateGraph(File iFile){
		try{
			if(iFile.exists()){
				Scanner scan = new Scanner(iFile);
				vertices = scan.nextInt();
				edges = scan.nextInt();
				int vertex1;
				int vertex2;
				int weight;
				Nodes = new HashMap<Integer, GraphNode>();		
				
				// Put each node into the map of nodes.
				for(int i=0; i<vertices; i++){
					Nodes.put(i, new GraphNode(i));
				}
				
				// Update adjacency list of each node by adding all other connected nodes to the list.
				for(int i=0; i<edges; i++){
					vertex1 = scan.nextInt();
					vertex2 = scan.nextInt();
					weight = scan.nextInt();
					Nodes.get(vertex1).addAdjacencyList(vertex2, weight);
					Nodes.get(vertex2).addAdjacencyList(vertex1, weight);
				}
				scan.close();
				//printGraph();
			}
			else{
				System.out.println("File does not exist");
			}
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
	
	// Function to print generated graph.
	
	void printGraph(){
		for(Integer i : Nodes.keySet()){
			GraphNode gNode = Nodes.get(i);
			System.out.print(gNode.vertexId + "->");
			for(Entry<Integer,Integer> adjList : gNode.adjacencyList.entrySet()){
				System.out.print(adjList.getKey() + " " + adjList.getValue() + " -- ");
			}
			System.out.println("");
		}
	}
	
	// Function to implement Dijkstra's algotithm.
	void shortestPath(int source, int dest){
		

		FibonacciHeap fibHeap = new FibonacciHeap();
		ArrayList<FibNode> fibNodes = new ArrayList<FibNode>();
		HashSet<Integer> visitedVertices = new HashSet<Integer>();
		
		// Initialize fibonacci heap nodes for each vertex.
		for(int i=0;i<vertices;i++){
			fibNodes.add(fibHeap.fibHeapInsert(i, Integer.MAX_VALUE));
		}
		
		// Set source vertex as minimum.
		Nodes.get(source).minDist = 0;
		
		// Add source node to path traversed.
		Nodes.get(source).path.add(source);
		
		// Perform decrease key operation on source node.
		fibHeap.decreaseKey(fibNodes.get(source) , Nodes.get(source).minDist );
		
		
		
		while(!fibHeap.isEmpty()){
			// Remove the next minimum node in the heap and add it to the list of vistied vertices.
			int newMin = fibHeap.removeMin();
			visitedVertices.add(newMin);
			
			// Run through the node's adjacency list to find the next node in path to destination.
			for(Entry<Integer,Integer> adjList : Nodes.get(newMin).adjacencyList.entrySet()){
				
				//Store the values of the retrieved node from adjacency list.
				int adjNode = adjList.getKey();
				int weight = adjList.getValue();
				
				// Add the weight of the retrieved node to find the dist from source to this node.
				int dist = Nodes.get(newMin).minDist + weight;
				
				// If the new distance is lower than the existing minimum distance of this node from the source, 
				// Set this distance as new minDist.
				if(dist < Nodes.get(adjNode).minDist && !visitedVertices.contains(adjNode)){
					
					Nodes.get(adjNode).minDist = dist;
					
					// Reset path from source to this node, according to the new minimum distance.
					Nodes.get(adjNode).path.clear();
					Nodes.get(adjNode).path.addAll(Nodes.get(newMin).path);
					Nodes.get(adjNode).path.add(adjNode);
					
					// Perform Decrease key on the corresponding fibonacci node to update the distance value..
					fibHeap.decreaseKey(fibNodes.get(adjNode) , dist );
				}
			}
		}
		
	}
	
	// Function to print the shortest path from source vertex to destination vertex.
	void printShortestPath(int source, int dest){
		for(Integer i : Nodes.keySet()){
			if(i == dest){
				System.out.println(Nodes.get(i).minDist);
				for(Integer nodes: Nodes.get(i).path){
					System.out.print(nodes + "  ");
				}
				
			}
		}
	}
	
}

