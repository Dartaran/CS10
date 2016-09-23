package MyLab5;

public class MyAVLMap extends MyBSTMap {

	/** Constructs an empty map using the natural ordering of keys. */
	public MyAVLMap() {
		super();
	}

	public boolean insert(int k, int v) {
		Node n = super.findNode(k);
		if (n == null) return super.insert(k, v);
		if (n.key != k) {
			Node newNode = addNode(n, k, v);
			if (newNode.parent == null || newNode.isLeaf()) return true;
			if (newNode.parent.left.equals(newNode)) {
				if (newNode.left.height() - newNode.right.height() == 2) {
					if (k < newNode.key) {
						newNode = rotateWithLeftChild(newNode);
					} else {
						newNode = doubleWithLeftChild(newNode);
					}
				}
			} else if (newNode.parent.right.equals(newNode)) {
				if (newNode.right.height() - newNode.left.height() == 2) {
					if (k < newNode.key) {
						newNode = rotateWithRightChild(newNode);
					} else {
						newNode = doubleWithRightChild(newNode);
					}
				}
			}
			return true;
		} else {
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
