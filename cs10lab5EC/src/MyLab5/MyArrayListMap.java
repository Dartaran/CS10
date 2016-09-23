package MyLab5;

import java.util.ArrayList;

public class MyArrayListMap implements MyMapADT {
	ArrayList<Entry> data; // lists for keys and values
	
	public MyArrayListMap() {
		data = new ArrayList<>();
	}

	@Override
	public boolean insert(int k, int v) {
		// iterate through the arraylist
		for (int i = 0; i < data.size(); i++) {
			if (data.get(i).getKey() == k) { // key found update value
				data.get(i).setValue(v);
				return false;
			}
		}
		data.add(new Entry(k, v)); // not found add entry
		return true;
	}

	@Override
	public RetVal find(int k) {
		// iterate through the arraylist
		for (int i = 0; i < data.size(); i++)
			if (data.get(i).getKey() == k) // found
				return new RetVal(true, data.get(i).getValue());
		
		return new RetVal(false, 0); // not found
	}

	@Override
	public RetVal delete(int k) {
		// iterate through the arraylist
		for (int i = 0; i < data.size(); i++)
			if (data.get(i).getKey() == k) { // found
				// remove entry
				return new RetVal(true, data.remove(i).getValue());
			}
		return new RetVal(false, 0); // not found
	}

	@Override
	public int size() {
		return data.size();
	}
	
	public String toString() {
		String keys = "";
		// get each element in arraylist
		for (Entry e : data) {
			keys += e.getKey() + ", ";
		}
		return keys.substring(0, keys.length() - 2); // take out last comma and space
	}

}
