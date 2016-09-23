/** 
 * DrawRects.java
 * 
 * Allows the user to enter a number of rectangles using mouse input.
 * Keeps previous rectangles around.
 * Inspired by a C++ class demo by THC
 * 
 * @author Scot Drysdale on 4/19/00.  Modified to a JApplet 1/16/2012
 * Modified to add a "clear" button and use an ArrayList on 1/18/2012
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class DrawRects extends JApplet implements MouseListener,
		MouseMotionListener {
	private static final long serialVersionUID = 1L;
	private Point pressedPoint = null;	// where the mouse was pressed
	private Rect currentRect = null; 		// the rectangle being dragged.
	private ArrayList<Rect> boxes = new ArrayList<Rect>();	// a list of rectangles

	private static final Color[] colors = { Color.red, Color.cyan, Color.magenta,
			Color.yellow };
	private int colorIndex = 0;		// index into colors of the current color
	private JButton clearButton;	// button to clear the screen

	private static final int APPLET_WIDTH = 520;	// width of the applet
	private static final int APPLET_HEIGHT = 550; // height of the applet
	private static final int CANVAS_WIDTH = 500;	// width of the canvas
	private static final int CANVAS_HEIGHT = 500; // height of the applet

	/**
	 * Initializes the applet
	 */
	public void init() {
		addMouseListener(this);
		addMouseMotionListener(this);
		setSize(APPLET_WIDTH, APPLET_HEIGHT);

		Container cp = getContentPane();	// content pane holds components
		cp.setLayout(new FlowLayout());		// fill left to right, top to bottom

		Canvas canvas = new Canvas();
		canvas.setBackground(Color.white);
		cp.add(canvas);	// the canvas is the only component

		// Make a button to clear the canvas, set the button's background
		// to cyan, and add it to the content pane.
		clearButton = new JButton("Clear");
		clearButton.setBackground(Color.cyan);
		clearButton.addActionListener(canvas);
		cp.add(clearButton);

		setVisible(true); // makes the applet (and its components) visible
	}

	/**
	 * Captures the position at which the mouse is initially pressed.
	 * It creates a new currentRect object, because the previous one
	 * will have been added to the ListOfRects.
	 * 
	 * @param event the event that caused this callback
	 */
	public void mousePressed(MouseEvent event) {
		pressedPoint = event.getPoint();
		currentRect = new Rect(pressedPoint.x, pressedPoint.y, 0, 0, Color.black);
	}

	/**
	 * Gets the current position of the mouse as it is dragged and draws a
	 * rectangle with this point and pressedPoint as corners. This creates a
	 * rubberbanding rectangle effect.
	 * 
	 * @param event the event that caused this callback
	 */
	public void mouseDragged(MouseEvent event) {
		if (currentRect != null) { // make sure that currentRect exists
			Point pt = event.getPoint();
			currentRect.setX(Math.min(pt.x, pressedPoint.x));
			currentRect.setY(Math.min(pt.y, pressedPoint.y));
			currentRect.setWidth(Math.abs(pt.x - pressedPoint.x));
			currentRect.setHeight(Math.abs(pt.y - pressedPoint.y));
			repaint();
		}
	}

	/**
	 * Done dragging mouse, so add current Rect to ListOfRects.
	 * 
	 * @param event the event that caused this callback
	 */
	public void mouseReleased(MouseEvent event) {
		if (currentRect != null) { // make sure that currentRect exists
			currentRect.setColor(colors[colorIndex]);
			colorIndex = (colorIndex + 1) % colors.length;
			boxes.add(currentRect);
			currentRect = null; // currentRect is now in the list, so can't reuse it
		}
		repaint();
	}

	public void mouseClicked(MouseEvent event) {
		// the current most efficient way to remove elements from a list as of Java 8
		boxes.removeIf(e -> e.containsPoint(event.getPoint()));
		// alternatively a reverse-iterating for loop can be used and accomplish the same thing
	}
	
	// Provide empty definitions for unused event methods.
	public void mouseEntered(MouseEvent event) {}
	public void mouseExited(MouseEvent event) {}
	public void mouseMoved(MouseEvent event) {}

	/**
	 * The canvas to draw upon
	 */
	private class Canvas extends JPanel implements ActionListener {
		private static final long serialVersionUID = 1L;

		/**
		 * Constructor to choose preferred size
		 */
		public Canvas() {
			// Canvas is a subclass of JPanel. The way we set the size of
			// a JPanel is by the setPreferredSize method. It takes a reference to
			// a Dimension object, which just packages together a width and height.
			setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
		}

		/**
		 * Draw the rectangles
		 * 
		 * @param page the graphics object to draw on
		 */
		public void paintComponent(Graphics page) {
			super.paintComponent(page);

			page.setColor(Color.black);
			page.drawRect(0, 0, CANVAS_WIDTH - 1, CANVAS_HEIGHT - 1);	// draw border

			for (Rect rectangle : boxes)		// draw the saved rectangles
				rectangle.fill(page);

			if (currentRect != null)	// draw the rectangle being dragged out (if exists)
				currentRect.draw(page);
		}

		/**
		 * Handle the button - provide an actionListener
		 * @param event the event that caused this callback
		 */
		public void actionPerformed(ActionEvent event) {
			boxes.clear();
			repaint();
		}
	}
}
