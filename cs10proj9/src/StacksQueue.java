/**
 * Implements a queue using two stacks. For CS 10 Short Assigment 8
 * 
 * @author YOU
 */
public class StacksQueue<T> implements CS10Queue<T> {
	CS10Stack<T> inStack; // place where enqueued
	CS10Stack<T> outStack; // place where dequeued

	/**
	 * Constructor, makes an empty queue.
	 */
	public StacksQueue() {
		inStack = new ArrayListStack<T>();
		outStack = new ArrayListStack<T>();
	}

	/**
	 * Add an element to the rear of the queue.
	 * 
	 * @param obj
	 *            element to be enqueued
	 */
	public void enqueue(T obj) {
		inStack.push(obj);
	}

	/**
	 * Remove an element from the front of the queue.
	 * 
	 * @return the element removed from the front of the queue
	 */
	public T dequeue() {
		if (outStack.isEmpty()) refillOutStack(); // refill the outstack and empty the instack
		return outStack.pop(); // removes the frontmost of the reversed order elements
	}

	/**
	 * Return the element at the front of queue, but do not remove it
	 * 
	 * @return the element at the front of the queue
	 */
	public T front() {
		if (outStack.isEmpty()) refillOutStack(); // refill the outstack and empty the instack
		return outStack.peek();
	}
	
	/**
	 * Is the queue empty?
	 * 
	 * @return true if queue is empty, false otherwise
	 */
	public boolean isEmpty() {
		return inStack.isEmpty() && outStack.isEmpty();
	}
	
	/**
	 * Refills the outStack with the elements in inStack in reverse order
	 */
	private void refillOutStack() {
		while (!inStack.isEmpty()) {
			outStack.push(inStack.pop()); // fills the outStack in reverse order
											// of the inStack
		}
	}

	/**
	 * A testing program
	 */
	public static void main(String[] args) {
		CS10Queue<String> q = new StacksQueue<String>();
		System.out.println("Is it empty? : " + q.isEmpty());
		q.enqueue("cat");
		q.enqueue("dog");
		q.enqueue("bee");
		System.out.println("Is it empty? : " + q.isEmpty());
		System.out.println("Front is: " + q.dequeue());
		System.out.println("Next front is: " + q.dequeue());
		System.out.println("Is it empty? : " + q.isEmpty());
		q.enqueue("eagle");
		System.out.println("Next front is: " + q.dequeue());
		System.out.println("Next front is: " + q.dequeue());
		System.out.println("Is it empty? : " + q.isEmpty());
		System.out.println("dequeue of empty stack: " + q.dequeue());

		q.enqueue("bear");
		q.enqueue("beaver");
		System.out.println("front is: " + q.dequeue());
		q.enqueue("cat");
		q.enqueue("dog");
		q.enqueue("sheep");
		q.enqueue("cow");
		q.enqueue("eagle");
		q.enqueue("bee");
		q.enqueue("lion");
		q.enqueue("tiger");
		q.enqueue("zebra");
		q.enqueue("ant");

		System.out.println("Bigger example:");
		System.out.println("front is: " + q.dequeue());
		System.out.println("Next front is: " + q.dequeue());
		System.out.println("Next front is: " + q.dequeue());
		System.out.println("Next front is: " + q.dequeue());
		System.out.println("Next front is: " + q.dequeue());
		System.out.println("Next front is: " + q.dequeue());
		System.out.println("Next front is: " + q.dequeue());
		System.out.println("Next front is: " + q.dequeue());
		System.out.println("Next front is: " + q.dequeue());
		System.out.println("Next front is: " + q.dequeue());
		System.out.println("Next front is: " + q.dequeue());
		System.out.println("Is it empty? : " + q.isEmpty());
		System.out.println("Next front is: " + q.dequeue());
	}
}
