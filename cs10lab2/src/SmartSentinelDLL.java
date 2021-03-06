/**
 * SLL.java
 * 
 * Implementation of singly linked list with a sentinel implemented as the 
 * predecessor to the current element. WARNING: This implementation is guaranteed 
 * to work only if always given immutable objects (or at least ones that do not 
 * change).
 * 
 * @author THC
 * @author Scot Drysdale converted to Java
 * @author Scot Drysdale, THC have made a number of modifications.
 * @author Scot Drysdale most recently modified on 1/12/2011
 * @author Alan Lu changed implementation to include a sentinel 10/15/2015
 */
public class SmartSentinelDLL<T> implements CS10LinkedList<T> {
	// Instance variables.
	private Element<T> currentPred; // element before current in list
	private Element<T> head; // head of list

	/**
	 * A private class inner representing the elements in the list.
	 */
	private static class Element<T> {
		// Because this is a private inner class, these can't be seen from
		// outside SLL.
		private T data; // reference to data stored in this element
		private Element<T> next; // reference to next item in list

		/**
		 * Constructor for a linked list element, given an object.
		 * 
		 * @param obj
		 *            the data stored in the element.
		 */
		public Element(T obj) {
			next = null; // no element after this one, yet
			data = obj; // OK to copy reference, since obj references an
						// immutable object
		}

		/**
		 * @return the String representation of a linked list element.
		 */
		public String toString() {
			return data.toString();
		}
	}

	/**
	 * Constructor to create an empty singly linked list.
	 */
	public SmartSentinelDLL() {
		head = new Element<T>(null);
		currentPred = head;
	}

	/**
	 * @see CS10ListADT#clear()
	 */
	public void clear() {
		head = new Element<T>(null);
		currentPred = head;
	}

	/**
	 * @see CS10ListADT#add()
	 */
	public void add(T obj) {
		Element<T> x = new Element<T>(obj); // allocate a new element

		// There are two distinct cases, depending on whether the new element
		// is to be the new head.
		if (hasCurrent()) {
			currentPred = currentPred.next;
			// The new element is not the new head.
			x.next = currentPred.next; // fix the next reference for the new
										// element
			currentPred.next = x; // set current to new element
		} else { // no current
			head.next = x; // after head should be this element
			currentPred = head; // before previous should be dummy head
			currentPred.next = x; // after previous is current
		}
		
	}

	/**
	 * * @see CS10ListADT#remove()
	 */
	public void remove() {
		if (!hasCurrent()) { // check whether current element exists
			System.err.println("No current item");
			return;
		}
		
		currentPred.next = currentPred.next.next; // splice current out of list;
	}

	/**
	 * @return the String representation of this list.
	 */
	public String toString() {
		String result = "";

		for (Element<T> x = head.next; x != null; x = x.next)
			result += x.toString() + "\n";

		return result;
	}

	/**
	 * @see CS10ListADT#contains()
	 */
	public boolean contains(T obj) {
		Element<T> x;

		for (x = head; x.next != null && !x.next.data.equals(obj); x = x.next)
			;

		// We dropped out of the loop either because we ran off the end of the
		// list
		// (in which case x == null) or because we found s (and so x != null).
		if (x != null) {
			currentPred = x;
			currentPred.next = x.next;
		}

		return x != null;
	}

	/**
	 * @see CS10ListADT#isEmpty()
	 */
	public boolean isEmpty() {
		return head.next == null;
	}

	/**
	 * @see CS10ListADT#hasCurrent()
	 */
	public boolean hasCurrent() {
		return currentPred.next != null;
	}

	/**
	 * @see CS10ListADT#hasNext()
	 */
	public boolean hasNext() {
		return hasCurrent() && currentPred.next.next != null;
	}

	/**
	 * @see CS10ListADT#getFirst()
	 */
	public T getFirst() {
		if (isEmpty()) {
			System.err.println("The list is empty");
			return null;
		}
		currentPred = head; // simply set previous to head
		currentPred.next = head.next; // and after previous (Current) to first element
		return get();
	}

	/**
	 * @see CS10ListADT#getLast()
	 */
	public T getLast() {
		if (isEmpty()) {
			System.err.println("The list is empty");
			return null;
		} else {
			// iterate through list starting from currentPred
			// O(n) time (linear)
			while (currentPred.next.next != null) { // check if the next is last
				next(); // if not last then continue
			}
			return get(); // return the last
		}
	}

	/**
	 * @see CS10ListADT#addFirst()
	 */
	public void addFirst(T obj) {
		Element<T> x = new Element<T>(obj); // allocate a new element

		x.next = head.next; // after this element should be the original first
		currentPred = head; // set previous to dummy head
		currentPred.next = x; // set current to new first element
		head.next = x; // set new element as first after dummy head
	}

	/**
	 * @see CS10ListADT#addLast()
	 */
	public void addLast(T obj) {
		if (isEmpty())
			addFirst(obj);
		else {
			getLast();
			add(obj);
		}
	}

	/**
	 * @see CS10ListADT#get()
	 */
	public T get() {
		if (hasCurrent()) {
			return currentPred.next.data;
		} else {
			System.err.println("No current item");
			return null;
		}

	}

	/**
	 * @see CS10ListADT#set()
	 */
	public void set(T obj) {
		if (hasCurrent())
			currentPred.next.data = obj;
		else
			System.err.println("No current item");
	}

	/**
	 * @see CS10ListADT#next()
	 */
	public T next() {
		if (hasNext()) {
			currentPred = currentPred.next;
			return currentPred.data;
		} else {
			System.err.println("No next item");
			return null;
		}
	}
}
