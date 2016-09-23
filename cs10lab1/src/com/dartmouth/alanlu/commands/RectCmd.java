package com.dartmouth.alanlu.commands;

import java.awt.Point;

import com.dartmouth.alanlu.Drawing;
import com.dartmouth.alanlu.shapes.Rect;

public class RectCmd extends Command {
	private Point initialPoint;
	private Rect rect;

	public void executeClick(Point p, Drawing dwg) {
		if (rect != null) { // make sure rect exists
			rect = null; // rect is now in the list, so can't reuse it
		}
	}
	
	// Stores the first point at which the user clicks
	public void executePress(Point p, Drawing dwg) {
		initialPoint = p;
		rect = new Rect(dwg.getColor(), p.x, p.y, 0, 0);
		dwg.add(rect);
	}

	public void executeDrag(Point p, Drawing dwg) {
		rect.setX(Math.min(p.x, initialPoint.x));
		rect.setY(Math.min(p.y, initialPoint.y));
		rect.setWidth(Math.abs(p.x - initialPoint.x));
		rect.setHeight(Math.abs(p.y - initialPoint.y));
	}

}
