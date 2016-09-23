package com.dartmouth.alanlu.shapes;

import java.awt.*;

/**
 * Rect.java Class for a rectangle.
 * 
 * Written by THC for CS 10 Lab Assignment 1.
 *
 * @author Tom Cormen
 * @author Alan Lu
 * @see Shape
 */
public class Rect extends Shape {
	private int myX, myY; // x and y coordinates of upper left corner
	private int myWidth, myHeight; // width and height

	/**
	 * Creates a rectangle
	 * 
	 * @param c
	 *            color
	 * @param x
	 *            x of top left corner
	 * @param y
	 *            y of top left corner
	 * @param width
	 *            width
	 * @param height
	 *            height
	 */
	public Rect(Color c, int x, int y, int width, int height) {
		super(c);
		myX = x;
		myY = y;
		myWidth = width;
		myHeight = height;
	}

	/**
	 * Draws the rectangle
	 */
	public void drawShape(Graphics page) {
		page.fillRect(myX, myY, myWidth, myHeight);
	}

	/**
	 * @return whether or not the rectangle contains the point, including
	 *         borders.
	 */
	public boolean containsPoint(Point p) {
		return myX <= p.x && p.x <= (myX + myWidth) && myY <= p.y && p.y <= (myY + myHeight);
	}

	/**
	 * moves the rectangle by the specified x and y
	 * 
	 * @param deltaX
	 *            amount to move on the x axis
	 * @param deltaY
	 *            amount to move on the y axis
	 */
	public void move(int deltaX, int deltaY) {
		myX += deltaX;
		myY += deltaY;
	}

	/**
	 * @return the center of the rectangle, truncating if necessary
	 */
	public Point getCenter() {
		return new Point((myX + myWidth + myX) / 2, (myY + myHeight + myY) / 2);
	}

	/**
	 * Set the x value of the upper left corner of Rect to x
	 * 
	 * @param x
	 *            new x value
	 */
	public void setX(int x) {
		myX = x;
	}

	/**
	 * Set the y value of the upper left corner of Rect to y
	 * 
	 * @param y
	 *            new y value
	 */
	public void setY(int y) {
		myY = y;
	}

	/**
	 * @return x value of the upper left corner of Rect
	 */
	public int getX() {
		return myX;
	}

	/**
	 * @return the y value of the upper left corner of Rect
	 */
	public int getY() {
		return myY;
	}

	/**
	 * Set the width of the Rect to width
	 * 
	 * @param width
	 *            the new width
	 */
	public void setWidth(int width) {
		myWidth = width;
	}
	
	/**
	 * Set the height of the Rect to height
	 * 
	 * @param height
	 *            the new height
	 */
	public void setHeight(int height) {
		myHeight = height;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return myWidth;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return myHeight;
	}

}
