package com.dartmouth.alanlu.commands;

import java.awt.Point;

import com.dartmouth.alanlu.Drawing;
import com.dartmouth.alanlu.SentinelDLLDrawing;
import com.dartmouth.alanlu.shapes.Ellipse;
import com.dartmouth.alanlu.shapes.Rect;
import com.dartmouth.alanlu.shapes.Segment;
import com.dartmouth.alanlu.shapes.Shape;

/**
 * Command.java Superclass for commands. Provides empty definitions for the
 * methods executeClick, executePress, and executeDrag. Each subclass should
 * override some of the methods.
 * 
 * Written by THC for CS 10 Lab Assigment 1.
 * 
 * @author Tom Cormen
 */
public class CopyCmd extends Command {
	private Shape shapeToCopy; // the shape to copy
	private Shape copiedShape; // the new shape created from the shape to copy
	private Point previousPoint; // the previous point of the new shape

	public void executeClick(Point p, Drawing dwg) {
		if (shapeToCopy != null && copiedShape != null) {
			shapeToCopy = null;
			copiedShape = null;
		}
	}

	public void executePress(Point p, Drawing dwg) {
		shapeToCopy = dwg.getFrontmostContainer(p);

		if (shapeToCopy != null) {
			// instantiate new shape depending on type
			if (shapeToCopy instanceof Rect)
				copiedShape = new Rect(shapeToCopy.getColor(), ((Rect) shapeToCopy).getX(), ((Rect) shapeToCopy).getY(),
						((Rect) shapeToCopy).getWidth(), ((Rect) shapeToCopy).getHeight());
			else if (shapeToCopy instanceof Ellipse)
				copiedShape = new Ellipse(shapeToCopy.getColor(), ((Ellipse) shapeToCopy).getCenter().x,
						((Ellipse) shapeToCopy).getCenter().y, ((Ellipse) shapeToCopy).getXRadius(),
						((Ellipse) shapeToCopy).getYRadius());
			else if (shapeToCopy instanceof Segment)
				copiedShape = new Segment(shapeToCopy.getColor(), ((Segment) shapeToCopy).getEndpoints()[0].x,
						((Segment) shapeToCopy).getEndpoints()[0].y, ((Segment) shapeToCopy).getEndpoints()[1].x,
						((Segment) shapeToCopy).getEndpoints()[1].y);
			
			if (copiedShape != null) // make sure the shape has been instantiated
				((SentinelDLLDrawing) dwg).addInFrontOf(copiedShape, shapeToCopy);
			
			previousPoint = p;
		}
	}

	public void executeDrag(Point p, Drawing dwg) {
		if (copiedShape != null) {
			copiedShape.move(p.x - previousPoint.x, p.y - previousPoint.y);
			previousPoint = p;
		}
	}
}
