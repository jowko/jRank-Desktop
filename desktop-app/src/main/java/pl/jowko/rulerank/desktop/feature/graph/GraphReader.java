package pl.jowko.rulerank.desktop.feature.graph;

import javafx.scene.paint.Color;
import pl.jowko.rulerank.desktop.exception.RuleRankRuntimeException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static pl.jowko.rulerank.desktop.utils.BooleanUtils.not;

/**
 * This class reads graph from file and initializes data for UI. <br>
 *  <br>
 * Created by Piotr on 2018-06-06
 */
class GraphReader {
	
	private String content;
	private static final Pattern splitPattern;
	private static final Pattern labelPattern;
	private static final Pattern colorPattern;
	
	static {
		splitPattern = Pattern.compile(" ");
		labelPattern = Pattern.compile("label=\"(.*?)\"");
		colorPattern = Pattern.compile("color=\"(.*?)\"");
	}
	
	/**
	 * Creates instance of this class and creates graph from provided text content.
	 * @param content from .graph file
	 */
	GraphReader(String content) {
		this.content = content;
		
	}
	
	/**
	 * Extracts graph from .graph file. <br>
	 * It will create edges and cells using extracted data. <br>
	 * For each line, it is checked, if line contains edge or node. <br>
	 * Lines with doesn't contain edge or nodes are skipped. <br>
	 * It will also extract labels for nodes and color for edges. <br>
	 * <br>
	 * Valid format for node line: <br>
	 * 1 [label="1"]; <br>
	 * Valid format for edge line: <br>
	 * {@literal 1 -> 7} [color="green"];
	 * @return Graph dto containing edges and cells
	 */
	GraphDto extractGraph() {
		Scanner scanner = new Scanner(content);
		List<EdgeDto> edges = new ArrayList<>();
		List<CellDto> cells = new ArrayList<>();
		
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if(not(Character.isDigit(line.charAt(0))))
				continue;
			
			String[] values = splitPattern.split(line);
			// edges
			if(line.contains("->")) {
				String color = findValueInQuotes(line, colorPattern);
				edges.add(new EdgeDto(values[0], values[2], getColor(color)));
			
			} else { // vertices
				String label = findValueInQuotes(line, labelPattern);
				cells.add(new CellDto(values[0], label, Color.LIGHTGREY));
			}
		}
		
		return new GraphDto(cells, edges);
	}
	
	/**
	 * Extract value from text with is in double quotes. <br>
	 * Such text: [label="1"]; <br>
	 * Will return 1
	 * @param text from with value will be extracted
	 * @param pattern to be searched
	 * @return extracted value from double quotes
	 */
	private String findValueInQuotes(String text, Pattern pattern) {
		Matcher matcher = pattern.matcher(text);
		if(matcher.find())
			return matcher.group(1);
		throw new RuleRankRuntimeException("Graph file doesn't contain label or color values in double quotes");
	}
	
	private Color getColor(String value) {
		if("green".equalsIgnoreCase(value))
			return Color.GREEN;
		if("red".equalsIgnoreCase(value))
			return Color.RED;
		return Color.GRAY;
	}
	
}
