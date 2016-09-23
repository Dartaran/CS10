package com.dartmouth.alanlu.commands;

import java.awt.Point;

import com.dartmouth.alanlu.Drawing;

public class DeleteCmd extends Command {
	/**
	 * Removes the frontmost shape at the click point
	 */
	public void executeClick(Point p, Drawing dwg) {
		if (dwg.getFrontmostContainer(p) != null)
			dwg.remove(dwg.getFrontmostContainer(p));
	}
}
