package com.dartmouth.alanlu;

/**
 * Editor.java
 * An applet for an object-oriented graphical editor.
 * This class implements the GUI for the editor.
 *
 * Written by THC for CS 10 Lab Assignment 1.
 *
 * @author Tom Cormen
 * @author YOU
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.dartmouth.alanlu.commands.BackCmd;
import com.dartmouth.alanlu.commands.ColorCmd;
import com.dartmouth.alanlu.commands.Command;
import com.dartmouth.alanlu.commands.CopyCmd;
import com.dartmouth.alanlu.commands.DeleteCmd;
import com.dartmouth.alanlu.commands.EllipseCmd;
import com.dartmouth.alanlu.commands.FrontCmd;
import com.dartmouth.alanlu.commands.LineCmd;
import com.dartmouth.alanlu.commands.MoveCmd;
import com.dartmouth.alanlu.commands.RectCmd;

public class Editor extends JApplet {
	private static final long serialVersionUID = 1L;

	private final int APPLET_WIDTH = 700, APPLET_HEIGHT = 500;
	private final Color initialColor = Color.red; // default color starts as red

	private Command cmd; // the command being executed
	private Drawing dwg; // the drawing: shapes in order
	private ColorIndicator colorBox; // a GUI component to show the current
										// default color

	private boolean showGrid = false;
	private int gridSize = 20;
	
	/**
	 * Set up the buttons and canvas and register the listeners.
	 */
	public void init() {
		cmd = new Command(); // all methods in Command are empty

		// The instance variable dwg should reference a subclass of the
		// abstract class Drawing. In the provided implementation, it
		// references an ArrayListDrawing. You should change it to reference
		// a subclass of Drawing that uses a circular, doubly linked list
		// with a sentinel.
		dwg = new SentinelDLLDrawing(initialColor); // make an empty drawing

		// The drawing will appear in a white CanvasPanel.
		CanvasPanel canvasPanel = new CanvasPanel();
		canvasPanel.setBackground(Color.white);

		// Make JButton objects for all the command buttons.
		JButton rectButton = new JButton("Rectangle");
		JButton ellipseButton = new JButton("Ellipse");
		JButton lineButton = new JButton("Line");
		JButton moveButton = new JButton("Move");
		JButton copyButton = new JButton("Copy");
		JButton deleteButton = new JButton("Delete");
		JButton frontButton = new JButton("Front");
		JButton backButton = new JButton("Back");
		JButton exchangeButton = new JButton("Exchange");
		JButton gridButton = new JButton("Grid");
		JTextField gridTextBox = new JTextField(String.valueOf(gridSize));
		JButton redButton = new JButton("Red");
		JButton greenButton = new JButton("Green");
		JButton blueButton = new JButton("Blue");

		// Add listeners for all the command buttons.
		rectButton.addActionListener(new RectButtonListener());
		ellipseButton.addActionListener(new EllipseButtonListener());
		lineButton.addActionListener(new LineButtonListener());
		moveButton.addActionListener(new MoveButtonListener());
		copyButton.addActionListener(new CopyButtonListener());
		deleteButton.addActionListener(new DeleteButtonListener());
		frontButton.addActionListener(new FrontButtonListener());
		backButton.addActionListener(new BackButtonListener());
		exchangeButton.addActionListener(new ExchangeButtonListener());
		gridButton.addActionListener(new GridButtonListener());
		redButton.addActionListener(new RedButtonListener());
		greenButton.addActionListener(new GreenButtonListener());
		blueButton.addActionListener(new BlueButtonListener());
		
		// Listen for changes in the grid text box
		gridTextBox.getDocument().addDocumentListener(new DocumentListener() {
			// Update the grid size appropriately while checking for valid input
			public void changedUpdate(DocumentEvent e) {
				if (gridTextBox.getText().equals("") || warn())
					return;
				gridSize = Integer.parseInt(gridTextBox.getText());
				repaint();
			}

			public void removeUpdate(DocumentEvent e) {
				if (gridTextBox.getText().equals("") || warn())
					return;
				gridSize = Integer.parseInt(gridTextBox.getText());
				repaint();
			}
			
			public void insertUpdate(DocumentEvent e) {
				if (gridTextBox.getText().equals("") || warn())
					return;
				gridSize = Integer.parseInt(gridTextBox.getText());
				repaint();
			}

			public boolean warn() {
				// The grid size has to be numeric and greater than 0
				if (!isNumeric(gridTextBox.getText()) || Integer.parseInt(gridTextBox.getText()) < 0) {
					JOptionPane.showMessageDialog(null, "Error: Please enter a numerical value larger than 0.",
							"Error Message", JOptionPane.ERROR_MESSAGE);
					return true;
				}
				return false;
			}
		});

		// The command buttons will be arranged in 3 rows. Each row will
		// appear in its own JPanel, and the 3 JPanels will be stacked
		// vertically.
		JPanel shapePanel = new JPanel(); // holds buttons for adding shapes
		JLabel shapeLabel = new JLabel("Add shape:");
		shapePanel.setLayout(new FlowLayout());
		shapePanel.add(shapeLabel);
		rectButton.setBackground(Color.yellow);
		ellipseButton.setBackground(Color.yellow);
		lineButton.setBackground(Color.yellow);
		shapePanel.add(rectButton);
		shapePanel.add(ellipseButton);
		shapePanel.add(lineButton);

		JPanel editPanel = new JPanel(); // holds buttons for editing operations
		JLabel editLabel = new JLabel("Edit:");
		editPanel.setLayout(new FlowLayout());
		editPanel.add(editLabel);
		moveButton.setBackground(Color.yellow);
		copyButton.setBackground(Color.yellow);
		deleteButton.setBackground(Color.yellow);
		frontButton.setBackground(Color.yellow);
		backButton.setBackground(Color.yellow);
		exchangeButton.setBackground(Color.yellow);
		gridButton.setBackground(Color.yellow);
		editPanel.add(moveButton);
		editPanel.add(copyButton);
		editPanel.add(deleteButton);
		editPanel.add(frontButton);
		editPanel.add(backButton);
		editPanel.add(exchangeButton);
		editPanel.add(gridButton);
		editPanel.add(gridTextBox);

		// The color panel is slightly different from the other two. In
		// addition to a label and buttons for the color commands, this
		// panel holds a ColorIndicator that gives the current default
		// color.
		JPanel colorPanel = new JPanel();
		JLabel colorLabel = new JLabel("Colors:");
		colorPanel.setLayout(new FlowLayout());
		colorPanel.add(colorLabel);
		colorBox = new ColorIndicator();
		colorBox.show(initialColor);
		redButton.setBackground(Color.yellow);
		greenButton.setBackground(Color.yellow);
		blueButton.setBackground(Color.yellow);
		colorPanel.add(colorBox);
		colorPanel.add(redButton);
		colorPanel.add(greenButton);
		colorPanel.add(blueButton);

		// Use a grid layout to stack the button panels vertically. Also,
		// give them a cyan background.
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(3, 1));
		shapePanel.setBackground(Color.cyan);
		editPanel.setBackground(Color.cyan);
		colorPanel.setBackground(Color.cyan);
		buttonPanel.add(shapePanel);
		buttonPanel.add(editPanel);
		buttonPanel.add(colorPanel);

		// Now we have two panels: buttonPanel and canvasPanel. We want
		// buttonPanel to appear above canvasPanel, and canvasPanel should
		// grow with the applet.
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		cp.add(buttonPanel, BorderLayout.NORTH);
		cp.add(canvasPanel, BorderLayout.CENTER);

		setSize(APPLET_WIDTH, APPLET_HEIGHT);
	}

	public static boolean isNumeric(String str) {
		try {
			Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	private class RectButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			cmd = new RectCmd();
			repaint();
		}
	}

	/**
	 * What to do when ellipseButton is pressed.
	 */
	private class EllipseButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			cmd = new EllipseCmd();
			repaint();
		}
	}

	/**
	 * What to do when lineButton is pressed.
	 */
	private class LineButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			cmd = new LineCmd();
			repaint();
		}
	}

	/**
	 * What to do when moveButton is pressed.
	 */
	private class MoveButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			cmd = new MoveCmd();
			repaint();
		}
	}
	
	/**
	 * What to do when moveButton is pressed.
	 */
	private class CopyButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			cmd = new CopyCmd();
			repaint();
		}
	}

	/**
	 * What to do when deleteButton is pressed.
	 */
	private class DeleteButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			cmd = new DeleteCmd();
			repaint();
		}
	}

	/**
	 * What to do when frontButton is pressed.
	 */
	private class FrontButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			cmd = new FrontCmd();
			repaint();
		}
	}

	/**
	 * What to do when backButton is pressed.
	 */
	private class BackButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			cmd = new BackCmd();
			repaint();
		}
	}

	/**
	 * What to do when exchangeButton is pressed.
	 */
	private class ExchangeButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			cmd = new ExchangeCmd();
			repaint();
		}
	}
	
	/**
	 * What to do when moveButton is pressed.
	 */
	private class GridButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			showGrid = !showGrid;
			repaint();
		}
	}

	/**
	 * What to do when redButton is pressed.
	 */
	private class RedButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			colorBox.show(Color.red); // show that the new default color is red
			cmd = new ColorCmd(Color.red);
			dwg.setColor(Color.red);
			repaint();
		}
	}

	/**
	 * What to do when greenButton is pressed.
	 */
	private class GreenButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			colorBox.show(Color.green); // show that the new default color is
										// green
			cmd = new ColorCmd(Color.green);
			dwg.setColor(Color.green);
			repaint();
		}
	}

	/**
	 * What to do when blueButton is pressed.
	 */
	private class BlueButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			colorBox.show(Color.blue); // show that the new default color is
										// blue
			cmd = new ColorCmd(Color.blue);
			dwg.setColor(Color.blue);
			repaint();
		}
	}

	/**
	 * A ColorIndicator shows what the current color is.
	 */
	private class ColorIndicator extends JPanel {
		private static final long serialVersionUID = 0;

		private final int COLORBOX_WIDTH = 20, COLORBOX_HEIGHT = 20;

		/**
		 * Constructor sets the size and border.
		 */
		public ColorIndicator() {
			setBorder(BorderFactory.createEtchedBorder());
			setPreferredSize(new Dimension(COLORBOX_WIDTH, COLORBOX_HEIGHT));
		}

		/**
		 * Show a new color.
		 * 
		 * @param color
		 *            the color to show
		 */
		public void show(Color color) {
			setBackground(color);
		}
	}

	/**
	 * CanvasPanel is the class upon which we actually draw. It listens for
	 * mouse events and calls the appropriate method of the current command.
	 */
	private class CanvasPanel extends JPanel implements MouseListener, MouseMotionListener {
		private static final long serialVersionUID = 0;

		/**
		 * Constructor just needs to set up the CanvasPanel as a listener.
		 */
		public CanvasPanel() {
			addMouseListener(this);
			addMouseMotionListener(this);
		}

		/**
		 * Paint the whole drawing
		 * 
		 * @page the Graphics object to draw on
		 */
		public void paintComponent(Graphics page) {
			super.paintComponent(page); // execute the paintComponent method of
										// JPanel
			page.setColor(Color.DARK_GRAY);
			
			if (showGrid) {
				// grid vertical lines
				for (int i = gridSize; i < APPLET_WIDTH; i += gridSize) {
					page.drawLine(i, 0, i, APPLET_HEIGHT);
				}
				// grid horizontal lines
				for (int j = gridSize; j < APPLET_HEIGHT; j += gridSize) {
					page.drawLine(0, j, APPLET_WIDTH, j);
				}
			}
			
			dwg.draw(page); // have the drawing draw itself
		}

		/**
		 * When the mouse is clicked, call the executeClick method of the
		 * current command.
		 */
		public void mouseClicked(MouseEvent event) {
			cmd.executeClick(event.getPoint(), dwg);
			repaint();
		}

		/**
		 * When the mouse is pressed, call the executePress method of the
		 * current command.
		 */
		public void mousePressed(MouseEvent event) {
			cmd.executePress(event.getPoint(), dwg);
			repaint();
		}

		/**
		 * When the mouse is dragged, call the executeDrag method of the current
		 * command.
		 */
		public void mouseDragged(MouseEvent event) {
			cmd.executeDrag(event.getPoint(), dwg);
			repaint();
		}

		// We don't care about the other mouse events.
		public void mouseReleased(MouseEvent event) {
		}

		public void mouseEntered(MouseEvent event) {
		}

		public void mouseExited(MouseEvent event) {
		}

		public void mouseMoved(MouseEvent event) {
		}
	}

}
