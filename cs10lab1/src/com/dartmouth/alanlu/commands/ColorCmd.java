package com.dartmouth.alanlu.commands;

import java.awt.Color;
import java.awt.Point;

import com.dartmouth.alanlu.Drawing;

public class ColorCmd extends Command {
	private Color color;
	
	/**
	 * Creates a new ColorCmd
	 * @param color desired new color
	 */
	public ColorCmd(Color color) {
		this.color = color;
	}
	
	/**
	 * Changes the color of a shape
	 */
	public void executeClick(Point p, Drawing dwg) {
		if (dwg.getFrontmostContainer(p) != null)
			dwg.getFrontmostContainer(p).setColor(color);
	}
}
