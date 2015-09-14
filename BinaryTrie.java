
// BinaryTrie class contains the implementation of Binary Trie.
public class BinaryTrie {
	TrieNode root;		// Indicates the root node of the binary trie.
	
	// Constructor to initialize the root of the binary trie.
	BinaryTrie(){
		root = new TrieNode();
	}
	
	// Function to insert an IP address into the binary trie.
	
	void binaryTrieInsert(String IP, int vertex){
		TrieNode Node = root;
		// Loop through each of the characters in IP address string.
		for(char c:IP.toCharArray()){
			
			// If the character from the IP address is already present in the trie move down to that child.
			TrieNode child = Node.hasChar(c);
			if(child != null){
				Node = child;
			}
			else{
				// If the character is not present in the trie and it is a '0' branch to the left.
				if(c == '0'){ 
					Node.Left = new TrieNode();
					Node = Node.Left;
				}
				
				// If the character is not present in the trie and it is a '1' branch to the right.
				else{
					Node.Right = new TrieNode();
					Node = Node.Right;
				}
			}
		}
		Node.hasNextHop = true;
		Node.vertex = vertex;
	}
	
	// Function to search an IP address in the binary trie.
	
	SearchResult searchTrie(String IP){
		TrieNode Node = root;
		StringBuilder longestMatch = new StringBuilder();
		SearchResult Result = null;
		// Traverse down the binary trie till the characters match.
		for(char c : IP.toCharArray()){
			if(Node.hasChar(c) == null){
				break;
			}
			else{
				Node = Node.hasChar(c);
			}
			longestMatch.append(c);
		}
		
		// When there is no match and the node is a leaf, store the result into the SearchResult object.
		if(Node.hasNextHop == true){
			Result = new SearchResult();
			Result.vertex = Node.vertex;
			Result.longestMatch = new String(longestMatch);
			
		}
		return Result;
	}
	
	// Function to perform post order traversal of the binary trie and merge sub tries with the same next hop.
	void mergeSubTries(TrieNode Node){
		if(Node == null){
			return;
		}
		mergeSubTries(Node.Left);
		mergeSubTries(Node.Right);
		
		// Merge left and right branches, if they have same next hop nodes.
		if(Node.Left != null && Node.Right != null && Node.Left.hasNextHop && Node.Right.hasNextHop){
			if(Node.Left.vertex == Node.Right.vertex){
				Node.hasNextHop = true;
				Node.vertex = Node.Right.vertex;
				Node.Left = null;
				Node.Right = null;
			}
		}
		
		//If only Right node is present and it is a leaf node, then merge it with the present node.
		else if(Node.Left == null && Node.Right != null && Node.Right.hasNextHop){
			Node.hasNextHop = true;
			Node.vertex = Node.Right.vertex;
			Node.Right = null;
		}
		
		//If only Left node is present and it is a leaf node, then merge it with the present node.
		else if(Node.Right == null && Node.Left != null && Node.Left.hasNextHop){
			Node.hasNextHop = true;
			Node.vertex = Node.Left.vertex;
			Node.Left = null;
		}
	}
	
}



