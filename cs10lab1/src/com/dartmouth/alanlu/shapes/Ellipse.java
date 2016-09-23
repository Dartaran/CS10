package com.dartmouth.alanlu.shapes;
import java.awt.*;

/**
 * Ellipse.java Class for an ellipse.
 * 
 * @author Tom Cormen
 * @author Alan Lu
 * @see Shape
 */
public class Ellipse extends Shape {
	int myX, myY; // center x and y coordinates
	int rX, rY; // radius for the x and y axes

	/**
	 * Creates an ellipse object
	 * @param c color of the ellipse
	 * @param x x coordinate of center
	 * @param y y coordinate of center
	 * @param radX x radius length (half of width)
	 * @param radY y radius length (half of height)
	 */
	public Ellipse(Color c, int x, int y, int radX, int radY) {
		super(c);
		myX = x;
		myY = y;
		rX = radX;
		rY = radY;
	}

	/**
	 * draw the ellipse
	 */
	public void drawShape(Graphics page) {
		page.fillOval(myX - rX, myY - rY, rX * 2, rY * 2);
	}

	/**
	 * @return if the ellipse contains the specified point
	 */
	public boolean containsPoint(Point p) {
		return (((Math.pow((p.x - myX), 2)) / (Math.pow(rX, 2)))
				+ ((Math.pow((p.y - myY), 2)) / (Math.pow(rY, 2))) <= 1);
	}

	/**
	 * move the center of the ellipse
	 */
	public void move(int deltaX, int deltaY) {
		myX += deltaX;
		myY += deltaY;
	}

	/**
	 * @return the center point
	 */
	public Point getCenter() {
		return new Point(myX, myY);
	}
	
	/**
	 * @return the radius in the x axis
	 */
	public int getXRadius() {
		return rX;
	}
	
	/**
	 * @return the radius in the y axis
	 */
	public int getYRadius() {
		return rY;
	}
	
	public void setXRadius(int x) {
		rX = x;
	}
	
	public void setYRadius(int y) {
		rY = y;
	}
	
	public void setEllipseCenter(int x, int y) {
		myX = x;
		myY = y;
	}
}
