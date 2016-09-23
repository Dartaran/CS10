import java.awt.Point;

public class City {
	private String name;
	private Point coordinates;
	
	public City(String n, Point p) {
		name = n;
		coordinates = p;
	}
	
	public boolean isNear(Point p) {
		return p.distance(coordinates) <= 5; // 5 pixels of allowance
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the coordinates
	 */
	public Point getCoordinates() {
		return coordinates;
	}
}
