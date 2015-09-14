
// TrieNode class contains the basic structure each of the Trie nodes.

public class TrieNode {
	int vertex; 		// Indicates the next hop node in the trie.
	TrieNode Left;		// Refers to the left branch node of the current trie node.
	TrieNode Right;		// Refers to the right branch node of the current trie node.
	boolean hasNextHop;	// is set to true if the current node holds the next hop node i.e it is a leaf node. 

	// Constructor to initialize members of trie node.
	TrieNode(){
		vertex = -1;
		Left = null;
		Right = null;
		hasNextHop = false;
	}
	
	// Function to check if the current node has the given character in it's branches.
	TrieNode hasChar(char c){
		if( c=='0' && Left != null){
			return Left;
		}
		if( c== '1' && Right != null){
			return Right;
		}
		return null;
	}
}
