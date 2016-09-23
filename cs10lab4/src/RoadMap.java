import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.datastructures.AdjacencyMapGraph;
import net.datastructures.Vertex;

public class RoadMap {
	// the graph storing all the edges and vertices of the map
	private AdjacencyMapGraph<City, Highway> graph;

	public RoadMap(String cityPath, String linkPath) {
		Map<String, Vertex<City>> cityNames = new HashMap<>();
		graph = new AdjacencyMapGraph<>(false);

		try {
			readCities(new File(ClassLoader.getSystemResource(cityPath).toURI()), cityNames);
			readLinks(new File(ClassLoader.getSystemResource(linkPath).toURI()), cityNames);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Reads the city coordinates and store them as vertices
	 * 
	 * @param path
	 *            The path of the file
	 * @param cities
	 *            The map containing the names and vertices of the cities
	 */
	private void readCities(File path, Map<String, Vertex<City>> cities) {
		BufferedReader inputFile = null;

		try {
			inputFile = new BufferedReader(new FileReader(path));
			String line, cityName;
			Point p;
			// read line by line since coordinates are split up by line
			while ((line = inputFile.readLine()) != null) {
				// store the city
				cityName = line.split(",")[0];
				// store the city's point
				p = new Point(Integer.valueOf(line.split(",")[1]), Integer.valueOf(line.split(",")[2]));
				cities.put(cityName, graph.insertVertex(new City(cityName, p)));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inputFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @return the graph
	 */
	public AdjacencyMapGraph<City, Highway> getGraph() {
		return graph;
	}

	/**
	 * Reads the city coordinates and store them as vertices
	 * 
	 * @param path
	 *            The path of the file
	 * @param cities
	 *            The map containing the names and vertices of the cities
	 */
	private void readLinks(File path, Map<String, Vertex<City>> cities) {
		BufferedReader inputFile = null;

		try {
			inputFile = new BufferedReader(new FileReader(path));
			String line, cityName1, cityName2;
			double distance;
			int time;
			// read line by line since coordinates are split up by line
			while ((line = inputFile.readLine()) != null) {
				// store the city names
				cityName1 = line.split(",")[0];
				cityName2 = line.split(",")[1];
				// store the distance
				distance = Double.parseDouble(line.split(",")[2]);
				// time in hours, minutes so multiply hours by 60 to convert
				time = Integer.parseInt(line.split(",")[3]) * 60 + Integer.parseInt(line.split(",")[4]);
				graph.insertEdge(cities.get(cityName1), cities.get(cityName2), new Highway(distance, time));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inputFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Returns if the city at the specified point is there is one. Otherwise,
	 * returns null.
	 * 
	 * @param p
	 *            The point to check if there is a city at
	 * @return The vertex at the point or null if there is none.
	 */
	public Vertex<City> cityAt(Point p) {
		Iterator<Vertex<City>> itr = graph.vertices().iterator();
		while (itr.hasNext()) {
			Vertex<City> vertex = itr.next();

			if (vertex.getElement().isNear(p))
				return vertex;
		}
		return null;
	}
}
