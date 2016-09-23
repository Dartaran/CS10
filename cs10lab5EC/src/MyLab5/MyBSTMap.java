package MyLab5;

public class MyBSTMap implements MyMapADT {
	private Node root;
	private int size; // at high amounts of keys, calling the node.size() method
						// causes a stack overflow on certain computers so keep
						// track with a variable

	protected class Node {
		int key, value; // key and value to store
		Node left, right, parent; // children and parent

		public Node(int k, int v) {
			key = k;
			value = v;
			left = null;
			right = null;
			parent = null;
		}

		public Node(int k, int v, Node l, Node r, Node p) {
			key = k;
			value = v;
			left = l;
			right = r;
			parent = p;
		}

		/**
		 * @return the height of the tree subrooted at this node
		 */
		public int height() {
			if (isLeaf())
				return 0;
			else
				return 1 + Math.max(hasLeft() ? left.height() : 0, hasRight() ? right.height() : 0);
			// the height is simply 1 + the max of the recursive left or right
			// child height
		}

		/**
		 * @return the number of nodes (internal and leaf) in the subtree rooted
		 *         at this node.
		 */
		public int size() {
			return 1 + (hasLeft() ? left.size() : 0) + (hasRight() ? right.size() : 0);
		}

		public boolean hasLeft() {
			return left != null;
		}

		public boolean hasRight() {
			return right != null;
		}

		/**
		 * @return if the node is a leaf (no children)
		 */
		public boolean isLeaf() {
			return left == null && right == null;
		}
	}

	public Node getRoot() {
		return root;
	}

	@Override
	public boolean insert(int k, int v) {
		if (root == null) { // if map is empty make a root
			root = new Node(k, v);
			size++;
			return true;
		}
		Node n = findNode(k); // see if k already exists in map
		if (n.key != k) { // k isn't in map yet
			addNode(n, k, v);
			return true;
		} else {
			n.value = v;
			return false;
		}
	}

	/**
	 * Adds a new node to this tree.
	 * 
	 * @param parent
	 *            The parent of this node
	 * @param k
	 *            The key
	 * @param v
	 *            The value
	 * @return The new node
	 */
	protected Node addNode(Node parent, int k, int v) {
		Node kNode = new Node(k, v); // instantiate the new node
		kNode.parent = parent; // set the parent
		// compare keys and set to left or right child depending
		if (k < parent.key)
			parent.left = kNode;
		else
			parent.right = kNode;
		size++; // add to size count

		return kNode;
	}

	@Override
	public RetVal find(int k) {
		if (root == null)
			return new RetVal(false, 0);

		Node n = root;

		while (n != null) {
			if (k == n.key) {
				return new RetVal(true, n.value);
			} else if (k < n.key) {
				n = n.left;
			} else if (k > n.key) {
				n = n.right;
			}
		}

		return new RetVal(false, 0); // not found
	}

	public Node findNode(int k) {
		if (root == null)
			return null;

		Node n = root;

		while (n != null) {
			if (k == n.key) {
				return n;
			} else if (k < n.key) {
				if (n.left == null)
					return n;
				n = n.left;
			} else if (k > n.key) {
				if (n.right == null)
					return n;
				n = n.right;
			}
		}

		return null;
	}

	@Override
	public RetVal delete(int k) {
		
		if (delete(root, k) == null) return new RetVal(false, 0);
		else {
			return new RetVal
		}
		return null;
	}

	public Node delete(Node n, int k) {
		if (n == null)
			return null;
		if (k < n.key)
			n.left = delete(n.left, k); // go left
		else if (k > n.key)
			n.right = delete(n.right, k); // go right
		else { // found node, delete
			if (n.right == null)
				return n.left;
			if (n.left == null)
				return n.right;
			// make a copy of node n
			Node t = new Node(n.key, n.value, n.left, n.right, n.parent);
			n = min(t.right);
			n.right = deleteMin(t.right);
			n.left = t.left;
		}
		size--;
		return n;
	}

	private Node min(Node n) {
		if (n.left == null)
			return n;
		else
			return min(n.left);
	}

	public int size() {
		return size;
	}

	public String toString() {
		String debug = "Keys: " + size + System.getProperty("line.separator");
		if (root == null)
			return "No nodes currently.";
		// can't use Queue per the assignment so the following solution takes
		// O(n^2) time
		// instead of O(n). Add 1 to height to account for 0 height
		for (int i = 1; i <= root.height() + 1; i++) {
			debug += givenLevelString(root, i);
		}
		return debug;
	}

	public String givenLevelString(Node r, int level) {
		String levelString = "";
		if (r == null)
			return "";
		if (level == 1) {
			return "(" + r.key + ", " + r.height() + ") ";
		} else if (level > 1) {
			levelString += givenLevelString(r.left, level - 1);
			levelString += givenLevelString(r.right, level - 1);
		}
		return levelString;
	}

}
