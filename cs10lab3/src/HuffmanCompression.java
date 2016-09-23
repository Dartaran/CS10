import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;

import javax.swing.JFileChooser;

public class HuffmanCompression {
	private static String filePath; // the path of the file to compress/decompress
	private static Map<Character, Integer> cFreq = new HashMap<>(); // character frequency map
	private static Map<Character, String> codeWords = new HashMap<>(); // code word map
	private static PriorityQueue<BinaryTree<HuffmanElement>> queue; // min heap queue

	public static void main(String[] args) {
		filePath = getFilePath();
		getCharacterFrequency();
		generateSingletonTrees();
		generateTree();
		if (cFreq.size() > 0) getCodeWords(queue.peek(), "");
		compressFile();
		decompressFile();
	}

	// Generates a tree with one node for each character and adds to the priority queue
	private static void generateSingletonTrees() {
		if (cFreq.size() == 0) return; // empty tree
		queue = new PriorityQueue<>(cFreq.size(), new TreeComparator<HuffmanElement>());
		Iterator<Entry<Character, Integer>> it = cFreq.entrySet().iterator();
		while (it.hasNext()) {
			Entry<Character, Integer> entry = (Entry<Character, Integer>) it.next();
			queue.add(new BinaryTree<HuffmanElement>(new HuffmanElement(entry.getKey(), entry.getValue())));
		}
	}

	private static void generateTree() {
		BinaryTree<HuffmanElement> lowest, secondLowest, root;
		if (cFreq.size() == 0) return;
		while (queue.size() > 1) { // while the queue is not one tree
			lowest = queue.poll(); // remove the lowest frequently occuring
									// character
			secondLowest = queue.poll(); // remove the second lowest frequently
											// occuring character
			// create a new tree with the lowest and second lowest frequently
			// occuring characters as children
			root = new BinaryTree<>(
					new HuffmanElement(null, lowest.getValue().getFrequency() + secondLowest.getValue().getFrequency()),
					lowest, secondLowest);
			queue.add(root); // add this tree back to the queue
		}
	}

	private static void getCodeWords(BinaryTree<HuffmanElement> node, String pathSoFar) {
		if (node.getLeft() == null || node.getRight() == null) { // only one character
			codeWords.put(cFreq.keySet().iterator().next(), "0");
			return;
		}
		
		if (node.getLeft().isLeaf()) { // if leaf then put character code word in map
			codeWords.put(node.getLeft().getValue().getCharacter(), pathSoFar + "0");
		} else { // if not go down one more level
			getCodeWords(node.getLeft(), pathSoFar + "0");
		}

		if (node.getRight().isLeaf()) { // same as above but use 1 instead of 0
			codeWords.put(node.getRight().getValue().getCharacter(), pathSoFar + "1");
		} else {
			getCodeWords(node.getRight(), pathSoFar + "1");
		}
	}

	private static void compressFile() {
		BufferedReader inputFile = null;
		BufferedBitWriter bitOutputFile = null;

		try {
			inputFile = new BufferedReader(new FileReader(filePath));
			bitOutputFile = new BufferedBitWriter(filePath.substring(0, filePath.lastIndexOf(".")) + "_compressed");

			if (cFreq.size() == 0) return; // empty tree
			
			int cInt;
			String codeWord;
			while ((cInt = inputFile.read()) != -1) {
				codeWord = codeWords.get((char) cInt); // get the code word for this character
				for (int i = 0; i < codeWord.length(); i++) {
					bitOutputFile.writeBit(codeWord.charAt(i) - '0'); // write the bit of the code word
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inputFile.close();
				bitOutputFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void decompressFile() {
		BufferedWriter outputFile = null;
		BufferedBitReader bitInputFile = null;

		try {
			outputFile = new BufferedWriter(
					new FileWriter(filePath.substring(0, filePath.lastIndexOf(".")) + "_decompressed.txt"));
			bitInputFile = new BufferedBitReader(filePath.substring(0, filePath.lastIndexOf(".")) + "_compressed");

			if (cFreq.size() == 0) return; // empty tree
			
			int bit = 0;
			BinaryTree<HuffmanElement> tree = queue.peek(); // grab the tree with all the characters

			while ((bit = bitInputFile.readBit()) != -1) { // read each bit until the end
				if (codeWords.size() == 1) { // only one unique character special case
					outputFile.write(codeWords.keySet().iterator().next());
					break;
				}
				if (bit == 0) { // if bit is 0 we should go left
					if (tree.getLeft().isLeaf()) { // if it's a leaf grab the character
						outputFile.write(tree.getLeft().getValue().getCharacter());
						tree = queue.peek(); // reset the tree for the next character
					} else
						tree = tree.getLeft(); // not a leaf so go down one more level
				} else if (bit == 1) { // if bit is 1 we should go right
					if (tree.getRight().isLeaf()) { // see comments above
						outputFile.write(tree.getRight().getValue().getCharacter());
						tree = queue.peek();
					} else
						tree = tree.getRight();
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				outputFile.close();
				bitInputFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void getCharacterFrequency() {
		BufferedReader inputFile = null;

		try {
			inputFile = new BufferedReader(new FileReader(filePath));
			int cInt;
			char c;
			while ((cInt = inputFile.read()) != -1) { // read until no more characters
				c = (char) cInt;
				cFreq.put(c, cFreq.getOrDefault(c, 0) + 1); // update frequency
				// if character not present set frequency to 1
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inputFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Puts up a fileChooser and gets the path name for the file to be opened.
	 * Returns an empty string if the user clicks Cancel.
	 * 
	 * @return path name of the file chosen
	 */
	public static String getFilePath() {
		// Create a file chooser.
		JFileChooser fc = new JFileChooser();

		int returnVal = fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			String pathName = file.getAbsolutePath();
			return pathName;
		} else
			return "";
	}

}
