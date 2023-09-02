/**
 * 
 */
package project5;

/**
 * @author Dimitri Chaber Implementation based on BST_Recursive and Data
 *         Structures and Algorithms textbook
 */
public class CollisionsData {

	// root of the tree
	private Node root;
	// current number of nodes in the tree
	private int numOfElements;
	private boolean found;
	private int totpedInjured, totmotoInjured, totcycInjured,totperInjured,totperKilled;
	private int totpedKilled, totmotoKilled, totcycKilled, totCol;
	int recCount;
	
	
	/**
	 * CollisionData constructor
	 */
	public CollisionsData() {
		root = null;
		numOfElements = 0;
	}

	/**
	 * Add the given data item to the tree. If item is null, the tree does not
	 * change. If item already exists, the tree does not change.
	 * 
	 * @param item
	 *            the new element to be added to the tree
	 */
	public void add(Collision item) {
		if (item == null)
			return;
		root = add(root, item);

	}

	/*
	 * Actual recursive implementation of add. calls rebalance if tree becomes
	 * unbalanced due to add
	 * 
	 * @param item the new element to be added to the tree
	 */
	private Node add(Node node, Collision item) {
		recCount++;
		if (node == null) {
			numOfElements++;
			return (new Node(item));
		}
		if (item.compareTo(node.data) < 0)
			node.left = add(node.left, item);
		
		else if (item.compareTo(node.data) > 0)
			node.right = add(node.right, item);

		recomputeHeight(node);
		if (!isBalanced(node)) {
			
			
			return rebalance(node);
		}

		return node;

	}

	/**
	 * Rotates the the nodes to restore AVL balance to the tree
	 * @param Node that is out of balance
	 */
	private Node rebalance(Node a) {
		
		if (balanceFactor(a) > 1) {
			// RR rotation
			
			if (balanceFactor(a.right) >= 0) {
				Node b = a.right;
				a.right = b.left;
				b.left = a;
				recomputeHeight(a);
				recomputeHeight(b);
				return b;
			}
			// RL rotation
			else {
				Node b = a.right;
				Node c = b.left;
				a.right = c.left;
				b.left = c.right;
				c.left = a;
				c.right = b;
				recomputeHeight(a);
				recomputeHeight(b);
				recomputeHeight(c);
				return c;
			}
		}
		else if (balanceFactor(a) < -1) {
			// LL rotation
			if (balanceFactor(a.left) <= 0) {
				Node b = a.left;
				a.left = b.right;
				b.right = a;
				recomputeHeight(a);
				recomputeHeight(b);
				return b;
			}
			// LR rotation
			else {
				Node b = a.left;
				Node c = b.right;
				
				a.left = c.right;
				b.right = c.left;
				c.left = b;
				c.right = a;
				recomputeHeight(a);
				recomputeHeight(b);
				recomputeHeight(c);
				return c;

			}
		} 
			return a;

	}

	/**
	 * Remove the item from the tree. If item is null the tree remains unchanged. If
	 * item is not found in the tree, the tree remains unchanged.
	 * 
	 * @param target
	 *            the item to be removed from this tree
	 */
	public boolean remove(Collision target) {
		root = recRemove(root, target);
		return found;

	}

	/*
	 * Actual recursive implementation of remove method: find the node to remove.
	 * Rebalances the tree if removal unbalanced it.
	 * @param target the item to be removed from this tree
	 */
	private Node recRemove(Node n, Collision c) {
		if (n == null)
			found = false;
		else if (c.compareTo(n.data) < 0)
			n.left = recRemove(n.left, c);
		else if (c.compareTo(n.data) > 0)
			n.right = recRemove(n.right, c);
		else {
			n = removeNode(n);
			found = true;
		}
		recomputeHeight(n);
		if (!isBalanced(n))
			return rebalance(n);
		return n;

	}

	/*
	 * Actual recursive implementation of remove method: perform the removal.  
	 * 
	 * @param target the item to be removed from this tree 
	 * @return a reference to the node itself, or to the modified subtree 
	 */
	private Node removeNode(Node n) {
		Collision temp;
		if (n.left == null)
			return n.right;
		else if (n.right == null)
			return n.left;
		else {
			temp = getPredecessor(n.left);
			n.data = temp;
			n.left = recRemove(n.left, temp);
			return n;
		}

	}

	/*
	 * Returns the information held in the rightmost node of subtree  
	 * 
	 * @param subtree root of the subtree within which to search for the rightmost node 
	 * @return returns data stored in the rightmost node of subtree  
	 */
	private Collision getPredecessor(Node subtree) {
		if (subtree == null)
			throw new NullPointerException("getPredecessor called with an empty subtree");
		Node temp = subtree;
		while (temp.right != null)
			temp = temp.right;
		return temp.data;

	}

	/**
	 * Determines the number of elements stored in this BST.
	 * 
	 * @return number of elements in this BST
	 */
	public int size() {
		return numOfElements;
	}

	/**
	 * Generates a report of injured and killed based on given
	 * zip and dates.
	 * @param zip zip where accidents took place
	 * @param dateBegin beginging of date interval
	 * @param dateEnd end of date interval
	 * @return report String with report
	 */
	public String getReport(String zip, Date dateBegin, Date dateEnd) {
		String report;
		//reset values to 0
		totperInjured = 0;
		totperKilled = 0;
		totpedInjured = 0;
		totmotoInjured = 0;
		totCol = 0;
		totcycInjured = 0;
		totpedKilled = 0;
		totmotoKilled = 0;
		totcycKilled = 0;
		
		//call recursive search method
		recReport(zip,dateBegin,dateEnd,root);

		
		report = "Motor Vehicle collisions for zipcode: " + zip + "(" + dateBegin + "-" + dateEnd + ")\n"
				+ "Total number of collisions: " + totCol + "\n" + "Number of fatalities: " + totperKilled + "\n"
				+ "pedestrians: " + totpedKilled + "\n" + "cyclists: " + totcycKilled + "\n" + "motorists: "
				+ totmotoKilled + "\n" + "Number of injuries: " + totperInjured + "\n" + "pedestrians: " + totpedInjured
				+ "\n" + "cyclists: " + totcycInjured + "\n" + "motorists: " + totmotoInjured + "\n";
		return report;

	}

	

	/**
	 * Recalculates the height of the node after an add remove or rotation
	 * has been completed
	 * @param n node to recalculate height of
	 */
	private void recomputeHeight(Node n) {
		
		if(n.right == null && n.left == null)
			n.height = 0;
		else if(n.right == null)
			n.height = n.left.height + 1;
		else if(n.left == null)
			n.height = n.right.height + 1;
		else if(n.right != null && n.left != null)
		n.height = Math.max(n.left.height, n.right.height) + 1;

	}

	/**
	 * Checks whether a node is balanced
	 * @param n node to check
	 * @return true if node is balanced
	 */
	private boolean isBalanced(Node n) {
		if(balanceFactor(n) >1 || balanceFactor(n) < -1)
			return false;
		else
			return true;

	}

	/**
	 * Produces tree like string representation of this BST.
	 * 
	 * @return string containing tree-like representation of this BST.
	 */
	public String toStringTreeFormat() {

		StringBuilder s = new StringBuilder();

		preOrderPrint(root, 0, s);
		return s.toString();
	}

	/*
	 * Actual recursive implementation of preorder traversal to produce tree-like
	 * string representation of this tree.
	 * 
	 * @param tree the root of the current subtree
	 * 
	 * @param level level (depth) of the current recursive call in the tree to
	 * determine the indentation of each item
	 * 
	 * @param output the string that accumulated the string representation of this
	 * BST
	 */
	private void preOrderPrint(Node tree, int level, StringBuilder output) {
		if (tree != null) {
			String spaces = "\n";
			if (level > 0) {
				for (int i = 0; i < level - 1; i++)
					spaces += "   ";
				spaces += "|--";
			}
			output.append(spaces);
			output.append(tree.data);
			preOrderPrint(tree.left, level + 1, output);
			preOrderPrint(tree.right, level + 1, output);
		}
		// uncomment the part below to show "null children" in the output
		else {
			String spaces = "\n";
			if (level > 0) {
				for (int i = 0; i < level - 1; i++)
					spaces += "   ";
				spaces += "|--";
			}
			output.append(spaces);
			output.append("null");
		}
	}

	/**
	 * Returns balance factor of the node
	 * @param n node to check balance factor
	 * @return balance factor of node
	 */
	public int balanceFactor(Node n) {
		if (n.right == null)
			return -n.height;
		if (n.left == null)
			return n.height;
		return (n.right.height - n.left.height);
	}

	/**
	 * Recursive method to look for objects matching given zip code 
	 * and date range
	 * @param zip zip code to find
	 * @param start beginning of date range
	 * @param end end of date range
	 * @param node 
	 * 
	 */
	public void recReport(String zip, Date start, Date end, Node node) {
		
		if (node == null)
			return;
		if (node.data.getZip().compareTo(zip) > 0) {
			recReport(zip, start, end, node.left);
		} else if (node.data.getZip().compareTo(zip) < 0) {
			recReport(zip, start, end, node.right);
		} else if (node.data.getZip().compareTo(zip) == 0) {
			recReport(zip, start, end, node.right);
			recReport(zip, start, end, node.left);
			if (node.data.getDate().compareTo(start) >= 0 && node.data.getDate().compareTo(end) <= 0) {
				totpedInjured += node.data.getPedestriansInjured();
				totmotoInjured += node.data.getMotoristsInjured();
				totcycInjured += node.data.getCyclistsInjured();
				totpedKilled += node.data.getPedestriansKilled();
				totmotoKilled += node.data.getMotoristsKilled();
				totcycKilled += node.data.getCyclistsKilled();
				totperInjured += node.data.getPersonsInjured();
				totperKilled += node.data.getPersonsKilled();
				totCol++;

			}

		}
		

	}

	/**
	 * Node class is used to represent nodes in a binary search tree.
	 * It contains a data item that has to implement Comparable interface
	 * and references to left and right subtrees and height. 
	 * 
	 * @author Joanna Klukowska
	 *
	 * 
	 */
	protected static class Node implements Comparable<Node> {

		protected Node left;
		protected Node right;
		protected Collision data;

		protected int height = 0; // height of the node in the tree

		/**
		 * Constructs a BSTNode initializing the data part according to the parameter
		 * and setting both references to subtrees to null.
		 * 
		 * @param data
		 *            data to be stored in the node
		 */
		protected Node(Collision data) {
			this.data = data;
			left = null;
			right = null;
			height = 0;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Comparable#compareTo(java.lang.Object)
		 */
		@Override
		public int compareTo(Node other) {
			return this.data.compareTo(other.data);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return data.toString();
		}

	}

}
