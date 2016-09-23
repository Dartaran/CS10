
/**

 * ScrollableMap.java

 * Class for a scrollable roadmap that responds to user actions.

 * For CS 10 Lab Assignment 4.

 * 

 * @author Yu-Han Lyu, Tom Cormen, and Alan Lu

 */

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;

import net.datastructures.AdaptablePriorityQueue;
import net.datastructures.Edge;
import net.datastructures.Entry;
import net.datastructures.Graph;
import net.datastructures.HeapAdaptablePriorityQueue;
import net.datastructures.Map;
import net.datastructures.ProbeHashMap;
import net.datastructures.Vertex;

public class ScrollableMap extends JLabel implements Scrollable,

MouseMotionListener, MouseListener {

	private static final long serialVersionUID = 1L;

	// The first two instance variables are independent of our roadmap
	// application.

	private int maxUnitIncrement = 1; // increment for scrolling by dragging

	private boolean missingPicture = false; // do we have an image to display?

	private JLabel infoLabel; // where to display the result, in words

	private JButton destButton; // the destination button, so that it can be
								// enabled

	private RoadMap roadmap; // the roadmap
	private Vertex<City> source, destination; // the source and destination
	private boolean sourceClicked, destClicked; // store whether these buttons
												// were clicked
	private List<Edge<Highway>> paths; // paths to draw
	private Map<Vertex<City>, Vertex<City>> previousCity;
	private int mode; // 0 for distance, 1 for time

	// ADD OTHER INSTANCE VARIABLES AS NEEDED.

	/**
	 * 
	 * Constructor.
	 * 
	 * @param i
	 *            the highway roadmap image
	 * @param m
	 *            increment for scrolling by dragging
	 * @param infoLabel
	 *            where to display the result
	 * @param destButton
	 *            the destination button
	 * @param roadmap
	 *            the RoadMap object, a graph
	 */
	public ScrollableMap(ImageIcon i, int m, JLabel infoLabel, JButton destButton, RoadMap roadmap) {
		super(i);
		if (i == null) {
			missingPicture = true;
			setText("No picture found.");
			setHorizontalAlignment(CENTER);
			setOpaque(true);
			setBackground(Color.white);
		}
		maxUnitIncrement = m;
		this.infoLabel = infoLabel;
		this.destButton = destButton;
		this.roadmap = roadmap;
		// Let the user scroll by dragging to outside the window.
		setAutoscrolls(true); // enable synthetic drag events
		addMouseMotionListener(this); // handle mouse drags
		addMouseListener(this);
		this.requestFocus();
		findSource(); // start off by having the user click a source city
		mode = 0;
		source = null;
		destination = null;
		previousCity = new ProbeHashMap<>();
	}

	// Methods required by the MouseMotionListener interface:
	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// The user is dragging us, so scroll!
		Rectangle r = new Rectangle(e.getX(), e.getY(), 1, 1);
		scrollRectToVisible(r);
	}

	// Draws the map and shortest paths, as appropriate.
	// If shortest paths have been computed, draws either the entire
	// shortest-path tree
	// or just a shortest path from the source vertex to the destination vertex.
	@Override
	public void paintComponent(Graphics page) {
		Graphics2D page2D = (Graphics2D) page;
		setRenderingHints(page2D);
		super.paintComponent(page2D);
		Stroke oldStroke = page2D.getStroke(); // save the current stroke
		page2D.setStroke(new BasicStroke(5.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));

		if (paths != null && paths.size() > 0) {
			if (destination == null) {
				drawPaths(paths, page);
			} else {
				Stack<Vertex<City>> tempPath = new Stack<>();
				List<Vertex<City>> tempPath2 = new ArrayList<>();
				List<Edge<Highway>> edgePath = new ArrayList<>();

				Vertex<City> v = destination;
				tempPath.push(v); // work backwards, destination is first

				// continue going through the previousCity map
				// until we reach source, which has no previous city
				while (previousCity.get(v) != null) {
					v = previousCity.remove(v);
					tempPath.push(v);
				}

				while (!tempPath.isEmpty()) {
					tempPath2.add(tempPath.pop()); // reverse the order by
													// adding to list
				}

				// we need another step because edges reuse vertices
				for (int i = 0; i < tempPath2.size() - 1; i++) {
					edgePath.add(roadmap.getGraph().getEdge(tempPath2.get(i), tempPath2.get(i + 1)));
				}

				drawPaths(edgePath, page);
			}
		}

		page2D.setStroke(oldStroke); // restore the saved stroke
	}

	private void drawPaths(List<Edge<Highway>> pathList, Graphics page) {
		for (Edge<Highway> e : pathList) {
			// get coordinates of edge vertices
			Vertex<City>[] endVertices = roadmap.getGraph().endVertices(e);
			page.drawLine(endVertices[0].getElement().getCoordinates().x,
					endVertices[0].getElement().getCoordinates().y, endVertices[1].getElement().getCoordinates().x,
					endVertices[1].getElement().getCoordinates().y); // draw the
																		// line
		}
	}

	// Enable all rendering hints to enhance the quality.
	public static void setRenderingHints(Graphics2D page) {
		page.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		page.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		page.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		page.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
	}

	// Methods required by the MouseListener interface.
	// When the mouse is clicked, find which vertex it's over.
	// If it's over a vertex and we're finding the source,
	// record the source, clear the destination, enable the destination
	// button, and find and draw the shortest paths from the source.
	// If it's over a vertex and we're finding the destination, record
	// the destination, and find and draw a shortest path from the source
	// to the destination.
	public void mouseClicked(MouseEvent e) {
		if (sourceClicked) {
			source = roadmap.cityAt(e.getPoint());
			if (source == null)
				return; // no city at this point

			destination = null;
			paths = shortestPaths(roadmap.getGraph(), source, mode);
			destButton.setEnabled(true);
			updateText();
			repaint();
		} else if (destClicked) {
			destination = roadmap.cityAt(e.getPoint());
			if (destination == null)
				return; // no city at this point

			paths = shortestPaths(roadmap.getGraph(), source, mode);
			updateText();
			repaint();
		}
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	// Return the preferred size of this component.
	@Override
	public Dimension getPreferredSize() {
		if (missingPicture)
			return new Dimension(320, 480);
		else
			return super.getPreferredSize();
	}

	// Needs to be here.
	@Override
	public Dimension getPreferredScrollableViewportSize() {
		return getPreferredSize();
	}

	// Needs to be here.
	@Override
	public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
		// Get the current position.
		int currentPosition = 0;
		if (orientation == SwingConstants.HORIZONTAL)
			currentPosition = visibleRect.x;
		else
			currentPosition = visibleRect.y;
		// Return the number of pixels between currentPosition
		// and the nearest tick mark in the indicated direction.
		if (direction < 0) {
			int newPosition = currentPosition - (currentPosition / maxUnitIncrement) * maxUnitIncrement;
			return (newPosition == 0) ? maxUnitIncrement : newPosition;
		} else
			return ((currentPosition / maxUnitIncrement) + 1) * maxUnitIncrement - currentPosition;
	}

	// Needs to be here.
	@Override
	public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
		if (orientation == SwingConstants.HORIZONTAL)
			return visibleRect.width - maxUnitIncrement;
		else
			return visibleRect.height - maxUnitIncrement;
	}

	// Needs to be here.
	@Override
	public boolean getScrollableTracksViewportWidth() {
		return false;
	}

	// Needs to be here.

	@Override
	public boolean getScrollableTracksViewportHeight() {
		return false;
	}

	// Needs to be here.
	public void setMaxUnitIncrement(int pixels) {
		maxUnitIncrement = pixels;
	}

	// Called when the source button is pressed.
	public void findSource() {
		sourceClicked = true;
		destClicked = false;
	}

	// Called when the destination button is pressed.
	public void findDest() {
		destClicked = true;
		sourceClicked = false;
	}

	// Called when the time button is pressed. Tells the roadmap to use time
	// for edge weights, and finds and draws shortest paths.
	public void useTime() {
		if (paths != null)
			paths.clear();
		mode = 1;
		paths = shortestPaths(roadmap.getGraph(), source, 1);
		updateText();
		repaint();
	}

	// Called when the distance button is pressed. Tells the roadmap to use
	// distance
	// for edge weights, and finds and draws shortest paths.
	public void useDistance() {
		if (paths != null)
			paths.clear();
		mode = 0;
		paths = shortestPaths(roadmap.getGraph(), source, 0);
		updateText();
		repaint();
	}

	private void updateText() {
		infoLabel.setText("Shortest path" + ((destination == null) ? "s" : "") + " from " + source.getElement().getName()
				+ ((destination == null) ? "" : " to " + destination.getElement().getName()) + " using " + ((mode == 0) ? "distance." : "time."));
	}

	/**
	 * Computes shortest-path distance from src vertex to all reachable vertices
	 * of g Modified from page 660 of Data Structures and Algorithms in Java,
	 * 6th Edition
	 * 
	 * @param g
	 *            The graph of the roads
	 * @param src
	 *            The source vertex
	 * @param mode
	 *            The mode used for weighting, either 0 for distance or 1 for
	 *            time
	 * @return A list of all the cities in the path of the shortest path
	 */
	private List<Edge<Highway>> shortestPaths(Graph<City, Highway> g, Vertex<City> src, int mode) {
		// Written without generics so that I would have to cast less
		// Map to store the shortest paths to cities
		Map<Vertex<City>, Edge<Highway>> shortestPaths = new ProbeHashMap<>();
		List<Edge<Highway>> paths = new ArrayList<>();

		// Create a priority heap to store the distances of vertices
		AdaptablePriorityQueue<Double, Vertex<City>> pq = new HeapAdaptablePriorityQueue<>();

		// Map that associates each vertex with corresponding entry
		Map<Vertex<City>, Entry<Double, Vertex<City>>> entries = new ProbeHashMap<>();

		// Map that stores distances to nodes.
		Map<Vertex<City>, Double> result = new ProbeHashMap<>();

		// Store all the vertices into the maps/heaps
		for (Vertex<City> v : g.vertices()) {
			entries.put(v, pq.insert(Double.POSITIVE_INFINITY, v));
		}

		pq.replaceKey(entries.get(src), 0.); // replace the src vertex with 0

		// continue until the heap is empty
		while (!pq.isEmpty()) {
			// grab the current vertex
			Entry<Double, Vertex<City>> curr = pq.removeMin();
			Vertex<City> u = curr.getValue();
			Double weight = curr.getKey();

			result.put(u, weight); // store this in the results map

			// check every outgoing edge
			for (Edge<Highway> e : g.outgoingEdges(u)) {
				Vertex<City> v = g.opposite(u, e);

				// if we already know the shortest path from source to vertex
				// no need to check
				if (result.get(v) != null)
					continue;

				// mode 0 for distance weight, mode 1 for time weight
				double pathCost = weight + ((mode == 0) ? e.getElement().getDistance() : e.getElement().getTime());

				// if the length of the best-known path to this vertex from the
				// source is longer than this potential path, update
				if (pathCost < entries.get(v).getKey()) {
					pq.replaceKey(entries.get(v), pathCost);
					previousCity.put(v, u); // update previous vertex for city
											// to city pathfinding
					shortestPaths.put(v, e);
				}
			}
		}

		for (Edge<Highway> e : shortestPaths.values()) {
			paths.add(e);
		}

		return paths;
	}

}