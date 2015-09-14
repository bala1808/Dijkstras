// routing class implements the Routing algorithm by making using of Binary Tries.
// This uses Dijkstras Shortest Path algorithm using Fibonacci Heaps to implement the Routing algorithm.

public class routing {
	public static void main(String args[]){
		String GraphFile = args[0];
		String IPAddressFile = args[1];
		int source = Integer.parseInt(args[2]);
		int destination = Integer.parseInt(args[3]);
		Router r= new Router();
		r.genGraph(GraphFile);
		r.generateIPTable(IPAddressFile);
		r.generateRoutingTable();
		r.generateTrie();
		r.shortestIPPath(source, destination);
	}
}
