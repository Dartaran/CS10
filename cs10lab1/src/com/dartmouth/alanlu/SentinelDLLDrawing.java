package com.dartmouth.alanlu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import com.dartmouth.alanlu.shapes.Shape;

/**
 * SentinelDLLDrawing.java
 * 
 * Subclass of the abstract Drawing class. Stores a drawing as an ordered list
 * of Shape objects.
 * 
 * Written by THC for CS 10 Lab Assignment 1.
 * 
 * @author Tom Cormen
 * @author YOU
 * @see Drawing
 */
public class SentinelDLLDrawing extends Drawing {
	private SentinelDLL<Shape> shapes; // the ordered list of Shape objects

	/**
	 * Constructor creates an empty list of Shape objects and saves the default
	 * color.
	 *
	 * @param initialColor the initial drawing color
	 */
	public SentinelDLLDrawing(Color initialColor) {
		super(initialColor);
		shapes = new SentinelDLL<Shape>();
	}

	/**
	 * Add a Shape to the front of the list.
	 * 
	 * @param c a shape you wish to add to the drawing
	 */
	public void add(Shape s) {
		shapes.addFirst(s);
	}
	
	/**
	 * Add a Shape in front of another Shape.
	 * 
	 * @param shape shape you wish to add
	 * @param shapeToGoInFrontOf shape you wish to go in front of
	 */
	public void addInFrontOf(Shape shape, Shape shapeToGoInFrontOf) {
		if (!shapes.contains(shapeToGoInFrontOf)) return;
		
		for (Shape s = shapes.getFirst(); s != null; s = shapes.next()) {
			if (s.equals(shapeToGoInFrontOf)) break;
		}
		shapes.previous();
		shapes.add(shape);
	}
	
	/**
	 * Have each Shape in the list draw itself. Draws from back to front, so
	 * that Shapes in the front overlay Shapes in the back.
	 * 
	 * @param page the page you wish to draw the shapes on
	 */
	public void draw(Graphics page) {
		// the shapes closer to the front should be in front so iterate backwards
		for (Shape s = shapes.getLast(); s != null; s = shapes.previous())
			s.draw(page);
	}

	/**
	 * Return a reference to the first Shape in the drawing (from front to back)
	 * that contains Point p. If no Shape contains p, return null.
	 * 
	 * @param p the point under which you wish to find the frontmost shape
	 * @return the front most shape or null if no shape is found
	 */
	public Shape getFrontmostContainer(Point p) {
		for (Shape s = shapes.getFirst(); s != null; s = shapes.next())
			if (s.containsPoint(p))
				return s;
		return null;
	}

	/**
	 * Given a reference to a Shape, remove it from the drawing if it's there.
	 * 
	 * @param s the which you wish to remove from the drawing
	 */
	public void remove(Shape s) {
		for (Shape shape = shapes.getFirst(); shape != null; shape = shapes.next())
			if (shape.equals(s)) shapes.remove();
		// contains can also be used here but the prof's code for that method is broken (haha)
	}

	/**
	 * Given a reference to a Shape, move it to the front of the drawing if it's
	 * actually in the drawing.
	 * 
	 * @param s
	 *            the shape which you wish to move to the front
	 */
	public void moveToFront(Shape s) {
		if (shapes.contains(s)) {
			for (Shape shape = shapes.getFirst(); shape != null; shape = shapes.next())
				if (shape.equals(s)) shapes.remove();
			
			shapes.addFirst(s);
		}
	}

	/**
	 * Given a reference to a Shape, move it to the back of the drawing if it's
	 * actually in the drawing.
	 * 
	 * @param s
	 *            the shape which you wish to move to the back
	 */
	public void moveToBack(Shape s) {
		if (shapes.contains(s)) {
			for (Shape shape = shapes.getFirst(); shape != null; shape = shapes.next())
				if (shape.equals(s)) shapes.remove();
			
			shapes.addLast(s);
		}
	}

	/**
	 * Make a Shape replace the Shape currently at the front of the drawing.
	 * 
	 * @param s
	 *            the shape you wish to replace the frontmost shape with
	 */
	public void replaceFront(Shape s) {
		shapes.getFirst();
		shapes.set(s);
	}
}
