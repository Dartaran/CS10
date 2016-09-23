
/**
 * ContainsPointDriver.java
 * 
 * For CS 10 Short Assignment 3.
 * 
 * @author YOU!!
 */

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ContainsPointDriver {
	private static final int MAX_X = 200, MAX_Y = 150; // max coordinate values
	private static final double MAX_RADIUS = 100.0; // size of largest circle

	public static void main(String[] args) {
		ArrayList<GeomShape> shapes = new ArrayList<GeomShape>(); // a
																	// polymorphic
																	// ArrayList
		Random generator = new Random(); // random number generator
		Scanner reader = new Scanner(System.in);
		int x, y;
		boolean found = false;
		
		final int INIT_LIST_SIZE = 20;
		// Randomly add some shapes to the ArrayList.
		for (int i = 0; i < INIT_LIST_SIZE; i++) {
			shapes.add(getRandomShape(generator));
		}

		// See what we've got.
		System.out.println("After adding " + INIT_LIST_SIZE + " shapes:");
		for (int i = 0; i < shapes.size(); i++)
			System.out.println(shapes.get(i));

		// the assignment says to use an infinite while loop, but I don't really see a reason to here...
		// an infinite while loop would read while (true) instead
		while (!shapes.isEmpty()) {
			// prompt user for coordinates and store them in variables x and y
			System.out.println("Input the x coordinate: ");
			x = reader.nextInt();
			System.out.println("Input the y coordinate: ");
			y = reader.nextInt();

			// iterate through shapes and remove the first object containing the
			// specified point
			for (GeomShape shape : shapes) {
				if (shape.containsPoint(x, y)) {
					System.out.println("Removed shape " + shape.toString() + " at index " + shapes.indexOf(shape));
					shapes.remove(shape);
					found = true;
					break;
				}
			}
			if (found == false) {
				System.out.println("No shape found with specified coordinates.");
			}
			else {
				found = false;
			}
		}
		
		reader.close();
	}

	/**
	 * Create a randomly generated shape object.
	 * 
	 * @param generator
	 * @return an object that implements the GeomShape interface
	 */
	public static GeomShape getRandomShape(Random generator) {
		// Randomly pick a Circle or Rectangle to create.
		if (generator.nextInt(2) == 0)
			// Chose Circle. Randomly choose a center and radius.
			return new Circle(generator.nextInt(MAX_X), generator.nextInt(MAX_Y), generator.nextDouble() * MAX_RADIUS);
		else {
			// Chose Rectangle. Randomly choose its corners.
			int x1 = generator.nextInt(MAX_X);
			int x2 = generator.nextInt(MAX_X);
			int y1 = generator.nextInt(MAX_Y);
			int y2 = generator.nextInt(MAX_Y);
			return new Rectangle(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
		}
	}
}