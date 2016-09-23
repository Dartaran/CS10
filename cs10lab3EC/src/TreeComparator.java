import java.util.Comparator;

public class TreeComparator<T> implements Comparator<BinaryTree<T>> {

	/**
	 * @param tree1
	 *            the first tree to compare
	 * @param tree2
	 *            the second tree to compare
	 * @return -1 if character frequency of tree1 is less than tree2, 0 if same,
	 *         and 1 if greater
	 */
	public int compare(BinaryTree<T> tree1, BinaryTree<T> tree2) {
		HuffmanElement element1 = (HuffmanElement) tree1.getValue();
		HuffmanElement element2 = (HuffmanElement) tree2.getValue();

		if (element1.getFrequency() < element2.getFrequency())
			return -1;
		else if (element1.getFrequency() == element2.getFrequency())
			return 0;
		
		return 1;
	}

}
