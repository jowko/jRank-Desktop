package pl.jowko.jrank.desktop.feature.graph;

import com.fxgraph.cells.CircleCell;
import com.fxgraph.graph.Graph;
import com.fxgraph.graph.Model;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static pl.jowko.jrank.desktop.utils.BooleanUtils.not;

/**
 * Created by Piotr on 2018-06-06
 * This class reads graph from file and initializes data for UI.
 */
class GraphReader {
	
	private String content;
	private Graph graph;
	private Pattern splitPattern;
	private Pattern quotesPattern;
	
	/**
	 * Creates instance of this class and creates graph from provided text content.
	 * @param content from .graph file
	 */
	GraphReader(String content) {
		this.content = content;
		graph = new Graph();
		splitPattern = Pattern.compile(" ");
		quotesPattern = Pattern.compile("\"(.*?)\"");
		extractGraph();
	}
	
	/**
	 * Gets graph extracted from .graph file
	 * @return graph
	 */
	Graph getGraph() {
		return graph;
	}
	
	/**
	 * Extracts graph from .graph file.
	 * It will create edges and cells using extracted data.
	 * For each line, it is checked, if line contains edge or node.
	 * Lines with doesn't contain edge or nodes are skipped.
	 * It will also extract labels for nodes and color for edges.
	 *
	 * Valid format for node line:
	 * 1 [label="1"];
	 * Valid format for edge line:
	 * 1 -> 7 [color="green"];
	 */
	private void extractGraph() {
		Scanner scanner = new Scanner(content);
		Model model = graph.getModel();
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if(not(Character.isDigit(line.charAt(0))))
				continue;
			
			String[] values = splitPattern.split(line);
			// edges
			if(line.contains("->")) {
				String color = findValueInQuotes(values[3]);
				model.addEdge(values[0], values[2], getColor(color));
			
			} else { // vertices
				String label = findValueInQuotes(values[1]);
				
				CircleCell cell = new CircleCell(values[0], label, Color.LIGHTGREY);
				Tooltip.install(cell, new Tooltip("circle")); //TODO
				model.addCell(cell);
			}
		}
		
		graph.endUpdate();
	}
	
	/**
	 * Extract value from text with is in double quotes.
	 * Such text: [label="1"];
	 * Will return 1
	 * @param text from with value will be extracted
	 * @return extracted value from double quotes
	 */
	private String findValueInQuotes(String text) {
		Matcher matcher = quotesPattern.matcher(text);
		matcher.find();
		return matcher.group(1);
	}
	
	private Color getColor(String value) {
		if("green".equalsIgnoreCase(value))
			return Color.GREEN;
		if("red".equalsIgnoreCase(value))
			return Color.RED;
		return Color.GRAY;
	}
	
}
