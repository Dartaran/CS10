package com.dartmouth.alanlu.commands;

import java.awt.Point;

import com.dartmouth.alanlu.Drawing;
import com.dartmouth.alanlu.shapes.Segment;

public class LineCmd extends Command {
	private Point initialPoint;
	private Segment line;

	public void executeClick(Point p, Drawing dwg) {
		if (line != null) { // make sure line exists
			line = null; // line is now in the list, so can't reuse it
		}
	}

	// Stores the first point at which the user clicks
	public void executePress(Point p, Drawing dwg) {
		initialPoint = p;
		line = new Segment(dwg.getColor(), p.x, p.y, p.x, p.y);
		dwg.add(line);
	}

	public void executeDrag(Point p, Drawing dwg) {
		line.setEndpoints(initialPoint.x, initialPoint.y, p.x, p.y);
	}
}
