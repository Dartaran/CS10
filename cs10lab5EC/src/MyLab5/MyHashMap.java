package MyLab5;

import java.util.ArrayList;
import java.util.Random;

public class MyHashMap implements MyMapADT {
	private UnsortedTableMap[] table;
	private int capacity; // the capacity of the table
	private int prime; // the prime factor
	private long scale, shift; // the shift and scaling factors
	private int size; // size of the map

	public MyHashMap() {
		this(11); // default capacity of 11 as specified in the assignment
	}

	public MyHashMap(int capacity) {
		Random rand = new Random();
		this.capacity = capacity;
		prime = 109345121; // prime number as specified in assignment
		scale = rand.nextInt(prime - 1) + 1;
		shift = rand.nextInt(prime);
		createTable();
	}

	private void createTable() {
		table = new UnsortedTableMap[capacity]; // create the table with the
												// capacity
	}

	// based on page 427 of Data Structures and Algorithms, Sixth Edition
	public boolean insert(int k, int v) {
		boolean newKey = bucketPut(hashValue(k), k, v);
		// check if we need to resize
		if (size > capacity / 2) // keep load factor <= 0.5
			resize(2 * capacity - 1); // (or find a nearby prime)
		return newKey;
	}

	// based on page 427 of Data Structures and Algorithms, Sixth Edition
	private void resize(int newCap) {
		// store data in temporary list
		ArrayList<UnsortedTableMap> buffer = new ArrayList<>(size);

		for (int h = 0; h < capacity; h++)
			buffer.add(table[h]);

		capacity = newCap; // change the capacity
		createTable(); // based on updated capacity
		size = 0; // will be recomputed while reinserting entries
		// pick new random values for scale and shift
		Random rand = new Random();
		scale = rand.nextInt(prime - 1) + 1;
		shift = rand.nextInt(prime);

		for (UnsortedTableMap m : buffer) {
			if (m == null)
				continue;
			for (Entry e : m.getTable())
				insert(e.getKey(), e.getValue()); // repopulate table
		}
	}

	/**
	 * Associates key k with value v in bucket with hash value h, returning the
	 * previously associated value, if any.
	 * 
	 * From Data Structures and Algorithms, Sixth Edition
	 * 
	 * @param h
	 *            the hash value of the relevant bucket
	 * @param k
	 *            the key of interest
	 * @param v
	 *            the value to be associated
	 * @return previous value associated with k (or null, if no such entry)
	 */
	private boolean bucketPut(int h, int k, int v) {
		UnsortedTableMap bucket = table[h];
		if (bucket == null)
			bucket = table[h] = new UnsortedTableMap();
		int oldSize = bucket.size();
		bucket.put(k, v);
		size += (bucket.size() - oldSize); // size may have increased
		return size != oldSize;
	}

	// from page 423 of Data Structures and Algorithms, 6th Edition
	/** Hash function applying MAD method to default hash code. */
	private int hashValue(Integer key) {
		return (int) ((Math.abs(key.hashCode() * scale + shift) % prime) % capacity);
	}

	// modified from the ProbeHashMap method findSlot on page
	// 427 of Data Structures and Algorithms, 6th Edition
	public RetVal find(int k) {
		UnsortedTableMap bucket = table[hashValue(k)];
		if (bucket == null)
			return new RetVal(false, 0);
		if (bucket.get(k) == null)
			return new RetVal(false, 0);
		return new RetVal(true, bucket.get(k));
	}

	// based on code from page 427 of Data Structures and Algorithms, Sixth
	// Edition
	public RetVal delete(int k) {
		UnsortedTableMap bucket = table[hashValue(k)];
		if (bucket == null)
			return new RetVal(false, 0);
		int oldSize = bucket.size();
		size -= (oldSize - bucket.size()); // size may have decreased
		return new RetVal(true, bucket.remove(k));
	}

	@Override
	public int size() {
		return size;
	}

	public String toString() {
		String debug = "h(k) = ((" + scale + "k + " + shift + ") mod " + prime + ") mod " + capacity
				+ System.getProperty("line.separator");
		for (int i = 0; i < capacity; i++) {
			if (table[i] == null)
				continue;
			for (Entry e : table[i].getTable()) {
				if (e.getKey() == null)
					continue;
				debug += i + ": " + e.getKey() + " ";
			}
		}
		return debug.substring(0, debug.length() - 2); // splice out last comma
														// and space
	}

}
