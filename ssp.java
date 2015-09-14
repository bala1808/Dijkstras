// ssp class has the main function which calls the generateGraph and shortestPathMethod. 
// This performs the Dijkstra's Shortest Path algorithm using a Fibonacci Heap.

import java.io.File;

public class ssp {
	public static void main(String args[]){
		String inputFile = args[0];
		int source = Integer.parseInt(args[1]);
		int destination = Integer.parseInt(args[2]);
		Graph g = new Graph();
		try
		{
			File iFile = new File(inputFile);
			if(iFile.exists()){
				// Generate directed graph from the input file.
				g.generateGraph(iFile);
						
				// Find Shortest path from source to destination.
				g.shortestPath(source , destination);
				g.printShortestPath(source,destination);

			}
			
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
