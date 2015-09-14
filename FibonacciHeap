import java.util.ArrayList;
import java.util.HashSet;

// Fibonacci Heap class contains the implementation of the Fibonacci Heap
public class FibonacciHeap {
	FibNode Min;			// refers to the smallest node in the Fibonacci Heap.
	int minIndex;			// refers to the index of the smallest node in the Fibonacci Heap.
	int size;				// indicates the size of the Fibonacci heap.
	
	// Function to insert a node into Fibonacci Heap. It changes Min pointer as necessary.
	
	FibNode fibHeapInsert(int index, double dist){
		FibNode Node = new FibNode(index,dist);
		// If the Fibonacci heap is empty then the inserted node becomes Min node.
		if(Min == null){
			Min = Node;
		}
		else{
			Node.Left = Min;
			Node.Right = Min.Right;
			Min.Right.Left = Node;
			Min.Right = Node;
			
			// If the inserted node has dist less than min, then the Min node is updated.
			if(Node.dist < Min.dist){
				Min = Node;
				minIndex = Min.index;
			}
		}
		size++;		// Increments size of Fibonacci heap by 1.
		return Node;
	}
	
	// Function to meld two Fibonacci Heaps. takes two nodes,uses a temporary node.
	
	FibNode meld(FibNode Node1, FibNode Node2){
		if(Node1 == null && Node2 == null){
			return null;
		}
		else if(Node1 != null && Node2 == null){
			return Node1;
		}
		else if(Node1 == null && Node2 != null){
			return Node2;
		}
		else{
				FibNode temp;
				temp = Node1.Right;
				Node1.Right = Node2.Right;
				Node1.Right.Left = Node1;
				Node2.Right = temp;
				Node2.Right.Left = Node2;
				if(Node1.dist < Node2.dist){
					return Node1;
				}
				else{
					return Node2;
				}
		}
	}
	
	
	// Function to remove the min node in the Fibonacci heap
	int removeMin(){
		int childNum = 0;
		FibNode temp = Min;
		if(temp != null){
			
			
			FibNode child = null;
			FibNode nextChild = null;
			
			child = temp.Child;
			childNum = temp.degree;
			
			while(childNum>0){

				nextChild = child.Right;
				
				// Remove Child from Sibling List.
				
				child.Left.Right = child.Right;
				child.Right.Left = child.Left;
				
				// Add removed child to top circular list
				
				child.Left = Min;
				child.Right = Min.Right;
				Min.Right = child;
				child.Right.Left = child;
				
				// Change parent pointer of removed child to null.
				
				child.Parent = null;
				
				// Change child pointer of min to the right sibling of removed child.
				
				child = nextChild;
				
				//Decrease degree(Number of Children) of min;
				childNum--;
				// Min.degree--;  Not necessary, this node is being removed anyways.
			}
			
			// Remove the min node.
			temp.Left.Right=temp.Right;
			temp.Right.Left=temp.Left;
			
			// Set Min to null if it is the last node.
			if(temp == temp.Right){
				Min=null;
			}
			// Else set the Min.Right Node as min and perform pairwise combine.
			else{
				Min = temp.Right;
				pairWiseCombine();
			}
			size--;
		}
		return temp.index;
	}
	
	// Function to perform pairwise combine. It melds trees of equal degrees till there are only trees with unique degrees in the Fibonacci heap.
	void pairWiseCombine(){
		
		int degTableSize = 30;
		int topListCount = 0;
		int deg = 0;
		// Table to track nodes of same degree.
		
		ArrayList<FibNode> degTable = new ArrayList<FibNode>();
		
		FibNode degNode;
		FibNode tempMin = Min;
		FibNode combiningNode = null;
		
		for(int i=0; i<degTableSize; i++){
			degTable.add(null);
		}
		
		// Find number of nodes is top list.
		if(tempMin!=null){
			topListCount++;
			tempMin = tempMin.Right;
			while(tempMin != Min ){
				topListCount++;
				tempMin = tempMin.Right;
			}
		}
		
		// Examine top list for same degree nodes so that they can be combined.
		while(topListCount > 0){
			deg = tempMin.degree;
			FibNode next = tempMin.Right;
			while(true){
				combiningNode = degTable.get(deg);
				if(combiningNode == null){
					break;
				}
				
				// Check which node will become the parent node.
				if(tempMin.dist > combiningNode.dist){
					FibNode temp = combiningNode;
					combiningNode = tempMin;
					tempMin = temp;
				}
				// Create link between parent and child nodes.
				linkNodes(combiningNode,tempMin);
				
				// Remove node from degree table. As it has been combined.
				degTable.set(deg, null);
				deg++;
			}
			topListCount--;
			degTable.set(deg,tempMin);
			tempMin = next;
		}
		
		// Removing nodes from degree table and adding it to the top list.
		Min = null;
		for(int i=0; i< degTableSize ; i++){
			degNode = degTable.get(i);
			if(degNode == null){
				continue;
			}
			if(Min == null){
				Min = degNode;
			}
			else{
				
				// Remove Node from sibling list.
				degNode.Left.Right = degNode.Right;
				degNode.Right.Left = degNode.Left;
				
				// Add to top list.
				degNode.Left = Min;
				degNode.Right = Min.Right;
				Min.Right = degNode;
				degNode.Right.Left = degNode;
				
				if(degNode.dist < Min.dist){
					Min = degNode;
				}
			}
		}
	}
	
	// Function to create link between a parent node and child node. Used in pairwise combine function.
	
	void linkNodes(FibNode child, FibNode parent){
		// Remove child node from sibling list.
		child.Right.Left = child.Left;
		child.Left.Right = child.Right;
		
		// If the parent node has children add the new child to the sibling list.
		if(parent.Child != null){
			child.Left = parent.Child;
			child.Right = parent.Child.Right;
			parent.Child.Right = child;
			child.Right.Left = child;
		}
		
		// If the parent node has no children then start a new sibling list.
		else{
			parent.Child = child;
			child.Right = child;
			child.Left = child;
		}
		
		// Make the parent node the parent of the child node.
		child.Parent = parent;
		
		// Increment parent's degree.
		parent.degree++;
		
		// Set Child's childcut value as "False";
		child.ChildCut = false;
		
	}
	
	// Function to meld a removed element to the top circular list.
	void meldTopList(FibNode Node, FibNode parent){
		
		// Remove Node from Sibling List.
		Node.Left.Right = Node.Right;
		Node.Right.Left = Node.Left;
		
		// Decrease Parent Node's degree.
		parent.degree--;
		
		// If parent has no remaining children, set Child to null.
		if(parent.degree==0){
			parent.Child = null;
		}
		else{
			parent.Child = Node.Right;
		}
		
		// Add removed child to top list.
		Node.Left = Min;
		Node.Right = Min.Right;
		Node.Right.Left = Node;
		Min.Right = Node;
		
		// Remove parent link to removed node.
		Node.Parent = null;
		
		// Set ChildCut to false.
		Node.ChildCut = false;
		
	}
	
	// Function to arbitrarily decrease the weight/dist of a Node.
	void decreaseKey(FibNode Node, int key){
		Node.dist = key;
		FibNode parent = Node.Parent;
		
		if(parent != null && Node.dist < parent.dist){
			
			// Add Node to toplist.
			meldTopList(Node,parent);
			
			// Perform Cascading cut.
			cascadeCut(parent);
		}
		// Change min node if necessary.
		if(Node.dist < Min.dist){
			Min = Node;
		}
	}
	
	// Function to perform cascading cut. 
	// When a child is lost from a subtree, all parent nodes with ChildCut field "True" are removed and added to 
	// top list. This is stopped when the first parent with ChildCut field "false" is reached.
	
	void cascadeCut(FibNode Node){
		if(Node.Parent != null){
			//If there is no parent set ChildCut to "true"
			if(Node.ChildCut == false)
				Node.ChildCut = true;
			else {
				//Store Node.parent to recurse.
				FibNode parent = Node.Parent;
				
				//Remove Node and add to top list.
				meldTopList(Node,parent);
				//Recurse.
				cascadeCut(parent);
			}	
		}
	}
	
	// Function to check if the fibonacci heap is empty.
	boolean isEmpty(){
		if(Min == null){
			return true;
		}
		return false;
	}
	
	// Function to return smallest node in the fibonacci heap.
	FibNode returnMin(){
		return Min;
	}

	// Function to return the size of the fibonacci heap.
	int returnSize(){
		return size;
	}
	
	
	// Print Fibonacci Heap.
	
	void printHeap(FibNode Node,HashSet<FibNode> check){
		if(Node==null)
			return;
			
    	if(!check.contains(Node))
		{
			System.out.println(Node.index + " --> "+Node.Right.index+" ");
			check.add(Node);
			printHeap(Node.Child,check);
			printHeap(Node.Right,check);
		}	
	}
}
