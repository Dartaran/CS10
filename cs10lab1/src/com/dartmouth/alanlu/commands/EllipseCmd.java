package com.dartmouth.alanlu.commands;

import java.awt.Point;

import com.dartmouth.alanlu.Drawing;
import com.dartmouth.alanlu.shapes.Ellipse;

public class EllipseCmd extends Command {
	private Point initialPoint; // initial click point
	private Ellipse ellipse; // the ellipse being drawn out

	public void executeClick(Point p, Drawing dwg) {
		if (ellipse != null) { // make sure ellipse exists
			ellipse = null; // ellipse is now drawn
		}
	}

	// Stores the first point at which the user clicks
	public void executePress(Point p, Drawing dwg) {
		initialPoint = p;
		ellipse = new Ellipse(dwg.getColor(), p.x, p.y, 0, 0);
		dwg.add(ellipse);
	}

	public void executeDrag(Point p, Drawing dwg) {
		ellipse.setXRadius(Math.abs(p.x - initialPoint.x) / 2);
		ellipse.setYRadius(Math.abs(p.y - initialPoint.y) / 2);
		ellipse.setCenter(new Point(Math.min(p.x, initialPoint.x) + ellipse.getXRadius(),
				Math.min(p.y, initialPoint.y) + ellipse.getYRadius()));
	}

}
