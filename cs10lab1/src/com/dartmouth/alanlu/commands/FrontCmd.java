package com.dartmouth.alanlu.commands;

import java.awt.Point;

import com.dartmouth.alanlu.Drawing;
import com.dartmouth.alanlu.shapes.Shape;

public class FrontCmd extends Command {
	
	/**
	 * Sends the shape on the pointer to the front
	 */
	public void executeClick(Point p, Drawing dwg) {
		Shape shapeToMove = dwg.getFrontmostContainer(p);
		
		if (shapeToMove != null) dwg.moveToFront(shapeToMove);
	}
	
}
