// FibNode Class contains the basic structure of each node in the fibonacci heap.
public class FibNode {
	
	int index;				// indicates the index of the node.
	int degree;				// indicates the number of children for the node. 
	double dist;			// indicates the minimum distance.
	FibNode Child;			// refers to the child of the node.
	FibNode Parent;			// refers to the parent of the node.
	FibNode Left;			// refers to the left sibling of the node.
	FibNode Right;			// refers to the right sibling of the node.
	boolean ChildCut;		// indicates if the node has lost a child since it became a child of current parent
	
	// checks if the node has any children
	
	boolean isChildEmpty(){ 
		if(Child == null){
			return true;
		}
		return false;
	}
	
	
	//Constructor with inputs as index and weight
	
	FibNode(int index, double weight){
		this.index = index;
		this.dist = weight;
		this.ChildCut = false;
		this.Right = this;
		this.Left = this;
	}
}
