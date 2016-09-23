package MyLab5;

import java.util.ArrayList;

/**
 * An implementation of a map using an unsorted table.
 * modified from Data Structures and Algorithms, Sixth 
 * Edition
 *
 * @author Michael T. Goodrich
 * @author Roberto Tamassia
 * @author Michael H. Goldwasser
 * @author Alan Lu
 */
public class UnsortedTableMap {
  /** Underlying storage for the map of entries. */
  private ArrayList<Entry> table = new ArrayList<>();

  /** Constructs an initially empty map. */
  public UnsortedTableMap() { }

  // private utility
  /** Returns the index of an entry with equal key, or -1 if none found. */
  private int findIndex(int key) {
    int n = table.size();
    for (int j=0; j < n; j++)
      if (table.get(j).getKey().equals(key))
        return j;
    return -1;                                   // special value denotes that key was not found
  }

  // public methods
  /**
   * Returns the number of entries in the map.
   * @return number of entries in the map
   */
  public int size() { return table.size(); }

  /**
   * Returns the value associated with the specified key, or null if no such entry exists.
   * @param key  the key whose associated value is to be returned
   * @return the associated value, or null if no such entry exists
   */
  public Integer get(int key) {
    int j = findIndex(key);
    if (j == -1) return null;                         // not found
    return table.get(j).getValue();
  }

  /**
   * Associates the given value with the given key. If an entry with
   * the key was already in the map, this replaced the previous value
   * with the new one and returns the old value. Otherwise, a new
   * entry is added and null is returned.
   * @param key    key with which the specified value is to be associated
   * @param value  value to be associated with the specified key
   * @return the previous value associated with the key (or null, if no such entry)
   */
  public Integer put(int key, int value) {
    int j = findIndex(key);
    if (j == -1) {
      table.add(new Entry(key, value));          // add new entry
      return null;
    } else                                            // key already exists
      return table.get(j).setValue(value);            // replaced value is returned
  }

  /**
   * Removes the entry with the specified key, if present, and returns its value.
   * Otherwise does nothing and returns null.
   * @param key  the key whose entry is to be removed from the map
   * @return the previous value associated with the removed key, or null if no such entry exists
   */
  public Integer remove(int key) {
    int j = findIndex(key);
    int n = size();
    if (j == -1) return null;                         // not found
    int answer = table.get(j).getValue();
    if (j != n - 1)
      table.set(j, table.get(n-1));                   // relocate last entry to 'hole' created by removal
    table.remove(n-1);                                // remove last entry of table
    return answer;
  }

  public ArrayList<Entry> getTable() {
	  return table;
  }
  
}
