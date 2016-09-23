package MyLab5;

public class MyAVLMap extends MyBSTMap {

	/** Constructs an empty map using the natural ordering of keys. */
	public MyAVLMap() {
		super();
	}

	public boolean insert(int k, int v) {
		Node n = super.findNode(k);
		if (n == null) return super.insert(k, v);
		if (n.key != k) { // key not in map
			Node newNode = addNode(n, k, v); // create node
			// check if new node is root
			if (newNode.parent == null || newNode.isLeaf()) return true;
			// rotate if necessary
			if (newNode.parent.left.equals(newNode)) {
				// check if we need to rotate (difference greater than 1)
				if (newNode.left.height() - newNode.right.height() == 2) {
					if (k < newNode.key) { // rotate left
						newNode = rotateWithLeftChild(newNode);
					} else {
						newNode = doubleWithLeftChild(newNode);
					}
				}
			} else if (newNode.parent.right.equals(newNode)) {
				// check if we need to rotate (difference greater than 1)
				if (newNode.right.height() - newNode.left.height() == 2) {
					if (k > newNode.key) { // rotate right
						newNode = rotateWithRightChild(newNode);
					} else {
						newNode = doubleWithRightChild(newNode);
					}
				}
			}
			return true;
		} else { // key is already in map
			n.value = v;
			return false;
		}
	}

	/* Rotate binary tree node with left child */
	private Node rotateWithLeftChild(Node k2) {
		Node k1 = k2.left;
		k2.left = k1.right;
		k1.right = k2;
		return k1;
	}

	/* Rotate binary tree node with right child */
	private Node rotateWithRightChild(Node k1) {
		Node k2 = k1.right;
		k1.right = k2.left;
		k2.left = k1;
		return k2;
	}

	/**
	 * Double rotate binary tree node: first left child with its right child;
	 * then node k3 with new left child
	 */
	private Node doubleWithLeftChild(Node k3) {
		k3.left = rotateWithRightChild(k3.left);
		return rotateWithLeftChild(k3);
	}

	/**
	 * Double rotate binary tree node: first right child with its left child;
	 * then node k1 with new right child
	 */
	private Node doubleWithRightChild(Node k1) {
		k1.right = rotateWithLeftChild(k1.right);
		return rotateWithRightChild(k1);
	}

}
