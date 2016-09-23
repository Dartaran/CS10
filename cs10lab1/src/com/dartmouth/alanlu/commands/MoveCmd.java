package com.dartmouth.alanlu.commands;

import java.awt.Point;

import com.dartmouth.alanlu.Drawing;
import com.dartmouth.alanlu.shapes.Shape;

public class MoveCmd extends Command {
	private Shape shapeToMove; // the shape to move
	private Point previousPoint; // the original click point
	
	/**
	 * Detects and stores the shape to move
	 */
	public void executePress(Point p, Drawing dwg) {
		if (dwg.getFrontmostContainer(p) == null) return;
		shapeToMove = dwg.getFrontmostContainer(p);
		previousPoint = p;
	}
	/**
	 * Moves the shape if there is any
	 */
	public void executeDrag(Point p, Drawing dwg) {
		if (shapeToMove == null) return;
		shapeToMove.move(p.x - previousPoint.x, p.y - previousPoint.y);
		previousPoint = p;
	}
}
