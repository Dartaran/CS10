public class Highway {
	private double distance; // distance between
	private int time; // time to travel in minutes
	
	public Highway(double d, int t) {
		distance = d;
		time = t;
	}

	/**
	 * @return the distance
	 */
	public double getDistance() {
		return distance;
	}

	/**
	 * @return the time
	 */
	public int getTime() {
		return time;
	}
}
