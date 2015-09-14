import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

// Router class contains the implementation of the Router.
public class Router {
	
	// Hashmap of vertex ID and corresponding IP addresses.
	HashMap<Integer,String> IPAddresses = new HashMap<Integer,String>();
	
	// Hashmap of VertexID and the  HashMap of each destination IP addresses and corresponding next hops.
	HashMap<Integer, HashMap<String,Integer>> routerTable = new HashMap<Integer, HashMap<String,Integer>>(); 
	
	// HashMap of vertexID and the corresponding binary tries.
	HashMap<Integer, BinaryTrie> Tries = new HashMap<Integer, BinaryTrie>();
	
	int minIPDist; // Indicates the minimum distance between the source and destination addresses.
	ArrayList<String> IPPath = new ArrayList<String>();
	Graph g= new Graph();
	Graph temp= new Graph();
	String graphName = null;
	
	
	// Function to convert a given IP address string in decimal form into a IP address string in binary form. 
	String convertToBinary(String IP){
		StringBuilder binaryIP = new StringBuilder();
		//	Splits the given IP address around "," and converts each part into a binary string of length eight.
		String[] splitIP = IP.split("\\.");
		for(String i : splitIP){
			int x = Integer.parseInt(i);
			String temp = Integer.toBinaryString(x);
			int zeroesReq = 8-temp.length();
			for(int j=0 ; j<zeroesReq; j++){
				binaryIP.append("0");
			}
			binaryIP.append(temp);
		}
		return new String(binaryIP);
	}
	

	// Function to generate the graph from the Graph file (first input file). 
	
	void genGraph(String GraphFile){
		try
		{
			graphName = GraphFile;
			File iFile = new File(GraphFile);
			
			if(iFile.exists()){
				g.generateGraph(iFile);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	
	// Function to build a table of IP addresses in binary form from the IP Addresses File (second input file).
	
	void generateIPTable(String IPAddressFile){
		try
		{
			File iFile = new File(IPAddressFile);
			if(iFile.exists()){
				Scanner scan = new Scanner(iFile);
				int i=0;
				while(scan.hasNext()){
					String IPAddrDec = scan.next();
					String IPAddrBin = convertToBinary(IPAddrDec);
					IPAddresses.put(i,IPAddrBin);
					i++;
				}
				scan.close();
			}
			else{
				System.out.println("File does not exist");
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	// Function to generate the Routing Table by calling the shortest path function for all IP Addresses.
	
	 void generateRoutingTable(){
	
		for(int source=0 ; source < g.vertices; source++){
			File iFileOld = new File(graphName);
			temp.generateGraph(iFileOld);
			
			// HashMap of destination IPAddresses and corresponding next hop.
			HashMap<String , Integer> nextHop = new HashMap<String , Integer>();
			
			for(int dest=0 ; dest < temp.vertices ; dest++){
				if(source == dest){
					continue;
				}
				else{
					temp.shortestPath(source, dest);

					nextHop.put(IPAddresses.get(dest) ,temp.Nodes.get(dest).path.get(1));
				}
			}

			
			routerTable.put(source,nextHop);
		}

		
	}
	 
	 // Function to insert IP addresses into a binary trie and merge the subtrees of with same leaves.
	 
	 void generateTrie(){
		 for(int i=0 ; i<g.vertices ; i++){
			 BinaryTrie Trie = new BinaryTrie();
			 HashMap<String,Integer> temp = new HashMap<String,Integer>();
			 temp = routerTable.get(i);
			 for(String str:temp.keySet()){
				 Trie.binaryTrieInsert(str, temp.get(str));
			 }
			 Tries.put(i,Trie);
			 Trie.mergeSubTries(Trie.root);
		 }
	 }
	 
	 // Function to find the shortest path between the source IP address and destination IP address using Binary Tries.
	 
	 void shortestIPPath(int source , int dest){
		 minIPDist = 0;
		 int hop = source;	// Variable to track hops along the path.
		 String IP = IPAddresses.get(dest);
		 while(hop != dest){
			 SearchResult Result = Tries.get(hop).searchTrie(IP);
			 int nextHop = Result.vertex;
			 IPPath.add(Result.longestMatch);
			 minIPDist += g.Nodes.get(hop).adjacencyList.get(nextHop);
			 hop = nextHop;
			 		 
		 }
		printShortestIPPath(source,dest);
	 }
	 
	 
	 // Function to print the shortest path between the source IP Address and the destination IP address.
	 
	 void printShortestIPPath(int source, int dest){
		 System.out.println(minIPDist);
		 String IPPathResult = new String();
		 IPPathResult += IPPath.get(0);
		 for(int i=1 ; i<IPPath.size() ; i++){
			 IPPathResult = IPPathResult + " " + IPPath.get(i);
		 }
		 System.out.println(IPPathResult);
	 }
}
