/**
 * SLL.java
 * 
 * Implementation of singly linked list without a sentinel. WARNING: This
 * implementation is guaranteed to work only if always given immutable objects
 * (or at least ones that do not change).
 * 
 * @author THC
 * @author Scot Drysdale converted to Java
 * @author Scot Drysdale, THC have made a number of modifications.
 * @author Scot Drysdale most recently modified on 1/12/2011
 */
public class SLL<T> implements CS10LinkedList<T> {
	// Instance variables.
	private Element<T> current; // current position in the list
	private Element<T> head; // head of list
	private Element<T> tail; // tail of list

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
	public SLL() {
		clear();
	}

	/**
	 * @see CS10ListADT#clear()
	 */
	public void clear() {
		// No elements are in the list, so everything is null.
		current = null;
		head = null;
		tail = null;
	}

	/**
	 * @see CS10ListADT#add()
	 */
	public void add(T obj) {
		Element<T> x = new Element<T>(obj); // allocate a new element

		// There are two distinct cases, depending on whether the new element
		// is to be the new head.
		if (hasCurrent()) {
			// The new element is not the new head.
			x.next = current.next; // fix the next reference for the new element
			current.next = x; // fix the next reference for current element
		} else {
			// The new element becomes the new head.
			x.next = head; // new element's next pointer is old head
			head = x; // and the new element becomes the head
		}

		// And check whether we need to update the tail.
		if (tail == current)
			tail = x;

		current = x; // new element is current position
	}

	/**
	 * * @see CS10ListADT#remove()
	 */
	public void remove() {
		Element<T> pred; // current element's predecessor

		if (!hasCurrent()) { // check whether current element exists
			System.err.println("No current item");
			return;
		}

		if (current == head) {
			head = current.next; // no predecessor, so update head
			pred = null;
		} else {
			// NOTE: Finding the predecessor makes remove a linear-time
			// operation,
			// rather than a constant-time operation.
			for (pred = head; pred != null && pred.next != current; pred = pred.next)
				;

			// At this point, either pred == null, in which case we never found
			// the
			// current element on the list (an error), or pred.next == current.
			if (pred == null) {
				System.err.println("Current item is not part of list.");
				return;
			}

			pred.next = current.next; // splice current out of list
		}

		// If we're removing the tail of the list, update that information.
		if (tail == current)
			tail = pred;

		current = current.next; // make the successor the current position
	}

	/**
	 * @return the String representation of this list.
	 */
	public String toString() {
		String result = "";

		for (Element<T> x = head; x != null; x = x.next)
			result += x.toString() + "\n";

		return result;
	}

	/**
	 * @see CS10ListADT#contains()
	 */
	public boolean contains(T obj) {
		Element<T> x;

		for (x = head; x != null && !x.data.equals(obj); x = x.next)
			;

		// We dropped out of the loop either because we ran off the end of the
		// list
		// (in which case x == null) or because we found s (and so x != null).
		if (x != null)
			current = x;

		return x != null;
	}

	/**
	 * @see CS10ListADT#isEmpty()
	 */
	public boolean isEmpty() {
		return head == null;
	}

	/**
	 * @see CS10ListADT#hasCurrent()
	 */
	public boolean hasCurrent() {
		return current != null;
	}

	/**
	 * @see CS10ListADT#hasNext()
	 */
	public boolean hasNext() {
		return hasCurrent() && current.next != null;
	}

	/**
	 * @see CS10ListADT#getFirst()
	 */
	public T getFirst() {
		if (isEmpty()) {
			System.err.println("The list is empty");
			return null;
		}
		current = head;
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
			current = tail;
			return get();
		}
	}

	/**
	 * @see CS10ListADT#addFirst()
	 */
	public void addFirst(T obj) {
		current = null;
		add(obj);
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
			return current.data;
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
			current.data = obj;
		else
			System.err.println("No current item");
	}

	/**
	 * @see CS10ListADT#next()
	 */
	public T next() {
		if (hasNext()) {
			current = current.next;
			return current.data;
		} else {
			System.err.println("No next item");
			return null;
		}
	}
}
