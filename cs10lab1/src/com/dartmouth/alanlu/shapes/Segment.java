package com.dartmouth.alanlu.shapes;

import java.awt.*;

/**
 * Segment.java Class for a line segment.
 * 
 * Written by THC for CS 10 Lab Assignment 1.
 *
 * @author Tom Cormen
 * @author Alan Lu
 * @see Shape
 */
public class Segment extends Shape {
	int x1, y1, x2, y2; // endpoints

	/**
	 * creates a segment
	 * 
	 * @param c
	 *            color of the segment
	 * @param x1
	 *            first x endpoint coordinate
	 * @param y1
	 *            first y endpoint coordinate
	 * @param x2
	 *            second x endpoint coordinate
	 * @param y2
	 *            second y endpoint coordinate
	 */
	public Segment(Color c, int x1, int y1, int x2, int y2) {
		super(c);
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	/**
	 * draws the line
	 */
	public void drawShape(Graphics page) {
		page.drawLine(x1, y1, x2, y2);
	}

	// Helper method that returns true if Point p is within a tolerance of a
	// given bounding box. Here, the bounding box is given by the coordinates of
	// its left, top, right, and bottom.
	private static boolean almostContainsPoint(Point p, int left, int top, int right, int bottom, double tolerance) {
		return p.x >= left - tolerance && p.y >= top - tolerance && p.x <= right + tolerance
				&& p.y <= bottom + tolerance;
	}

	// Helper method that returns the distance from Point p to the line
	// containing a line segment whose endpoints are given.
	private static double distanceToPoint(Point p, int x1, int y1, int x2, int y2) {
		if (x1 == x2) // vertical segment?
			return (double) (Math.abs(p.x - x1)); // yes, use horizontal
													// distance
		else if (y1 == y2) // horizontal segment?
			return (double) (Math.abs(p.y - y1)); // yes, use vertical distance
		else {
			// Here, we know that the segment is neither vertical nor
			// horizontal.
			// Compute m, the slope of the line containing the segment.
			double m = ((double) (y1 - y2)) / ((double) (x1 - x2));

			// Compute mperp, the slope of the line perpendicular to the
			// segment.
			double mperp = -1.0 / m;

			// Compute the (x, y) intersection of the line containing the
			// segment and the line that is perpendicular to the segment and
			// that
			// contains Point p.
			double x = (((double) y1) - ((double) p.y) - (m * x1) + (mperp * p.x)) / (mperp - m);
			double y = m * (x - x1) + y1;

			// Return the distance between Point p and (x, y).
			return Math.sqrt(Math.pow(p.x - x, 2) + Math.pow(p.y - y, 2));
		}
	}

	/**
	 * @return if the point lies on the line
	 */
	public boolean containsPoint(Point p) {
		// small tolerances allowed so the user doesn't have to be on the line
		// to the pixel
		return almostContainsPoint(p, x1, y1, x2, y2, 5) && distanceToPoint(p, x1, y1, x2, y2) <= 4;
	}

	/**
	 * moves the line by specified amount
	 * 
	 * @param deltaX
	 *            change in x
	 * @param deltaY
	 *            change in y
	 */
	public void move(int deltaX, int deltaY) {
		x1 += deltaX;
		y1 += deltaY;
		x2 += deltaX;
		y2 += deltaY;
	}

	/**
	 * @return the midpoint of the line, truncating if necessary
	 */
	public Point getCenter() {
		return new Point((x1 + x2) / 2, (y1 + y2) / 2);
	}

	/**
	 * Sets the endpoints of this segment
	 * 
	 * @param x1
	 *            x coordinate of the first endpoint
	 * @param y1
	 *            y coordinate of the first endpoint
	 * @param x2
	 *            x coordinate of the second endpoint
	 * @param y2
	 *            y coordinate of the second endpoint
	 */
	public void setEndpoints(int x1, int y1, int x2, int y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	/**
	 * @return the endpoints in two point objects
	 */
	public Point[] getEndpoints() {
		return new Point[] { new Point(x1, y1), new Point(x2, y2) };
	}
}