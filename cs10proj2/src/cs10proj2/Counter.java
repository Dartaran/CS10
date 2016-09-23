package cs10proj2;

public class Counter {
	private int val;
	private int limit;
	private final static int DEFAULT_LIMIT = 15;

	/**
	 * Initializes a Counter with a value of 0 and limit of 15
	 */
	public Counter() {
		val = 0;
		limit = DEFAULT_LIMIT;
	}

	/**
	 * Initializes a Counter with a value of 0 and a specified limit
	 * 
	 * @param limit
	 *            - limit of Counter
	 */
	public Counter(int limit) {
		val = 0;
		this.limit = limit;
	}

	/**
	 * Initializes a Counter with a specified value and limit
	 * 
	 * @param val
	 *            - value of Counter
	 * @param limit
	 *            - limit of Counter
	 */
	public Counter(int val, int limit) {
		this.val = val;
		this.limit = limit;
	}

	/**
	 * Increases the value of the Counter by one and wraps around if value is
	 * greater than limit
	 * 
	 * @return - true if the current tick has caused value to wrap around
	 */
	public boolean tick() {
		val++; // increase value by one
		if (val >= limit) { // if greater than limit wrap around
			val = 0; // resets value to 0
			return true; // return that this tick has caused value to wrap
							// around
		}
		return false;
	}

	/**
	 * Set the value to the specified integer
	 * 
	 * @param val
	 *            - desired value
	 */
	public void set(int val) {
		if ((val < limit) && (val > 0)) { // make sure the desired value is less
											// than limit but greater than 0
			this.val = val;
		}
	}

	/**
	 * Get the current value of the Counter
	 * 
	 * @return - returns the current Value of Counter
	 */
	public int getVal() {
		return val;
	}

}
