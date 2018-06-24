package pl.jowko.jrank.desktop.feature.graph;

import com.fxgraph.cells.CircleCell;
import com.fxgraph.graph.Graph;
import com.fxgraph.graph.Model;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import pl.jowko.jrank.desktop.exception.JRankRuntimeException;
import pl.jowko.jrank.desktop.feature.workspace.IsfFinder;
import pl.jowko.jrank.desktop.feature.workspace.WorkspaceItem;
import pl.poznan.put.cs.idss.jrs.core.mem.MemoryContainer;
import pl.poznan.put.cs.idss.jrs.types.Attribute;
import pl.poznan.put.cs.idss.jrs.types.Field;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;
import static pl.jowko.jrank.desktop.utils.BooleanUtils.not;

/**
 * Created by Piotr on 2018-06-06
 * This class reads graph from file and initializes data for UI.
 */
class GraphReader {
	
	private String content;
	private MemoryContainer container;
	private Graph graph;
	private Pattern splitPattern;
	private Pattern quotesPattern;
	
	/**
	 * Creates instance of this class and creates graph from provided text content.
	 * @param content from .graph file
	 * @param workspaceItem with is used to load MemoryContainer
	 */
	GraphReader(String content, WorkspaceItem workspaceItem) {
		this.content = content;
		GraphSelectAction selectAction = new GraphSelectAction();
		graph = new Graph(selectAction);
		splitPattern = Pattern.compile(" ");
		quotesPattern = Pattern.compile("\"(.*?)\"");
		loadMemoryContainer(workspaceItem);
		extractGraph();
	}
	
	/**
	 * Gets graph extracted from .graph file
	 * @return graph
	 */
	Graph getGraph() {
		return graph;
	}
	
	private void loadMemoryContainer(WorkspaceItem workspaceItem) {
		try {
			container = new IsfFinder(workspaceItem, true).getMemoryContainer();
			if(isNull(container))
				throw new JRankRuntimeException("Isf data file was not found on provided path");
		} catch (IOException e) {
			throw new JRankRuntimeException("Error when reading isf file for graph: " + e.getMessage());
		}
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
		List<Integer> descIndices = getDescriptionFieldsIds();
		
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
				Tooltip.install(cell, createTooltip(values[0], descIndices));
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
		if(matcher.find())
			return matcher.group(1);
		throw new JRankRuntimeException("Graph file doesn't contain label or color values in double quotes");
	}
	
	private Color getColor(String value) {
		if("green".equalsIgnoreCase(value))
			return Color.GREEN;
		if("red".equalsIgnoreCase(value))
			return Color.RED;
		return Color.GRAY;
	}
	
	/**
	 * This methods finds all description fields indexes in container.
	 * They are later used to create tooltips for nodes
	 * @return list of indices of description fields/attributes
	 */
	private List<Integer> getDescriptionFieldsIds() {
		List<Integer> indices = new ArrayList<>();
		
		int i=0;
		for(Attribute attribute : container.getAttributes()) {
			if(attribute.getKind() == Attribute.DESCRIPTION) {
				indices.add(i);
			}
			i++;
		}
		
		return indices;
	}
	
	/**
	 * Creates tooltip for node.
	 * @param cellId is used to find corresponding to node example in container.
	 * @param descIndices is used to extract description fields
	 * @return tooltip or null
	 */
	private Tooltip createTooltip(String cellId, List<Integer> descIndices) {
		if(descIndices.isEmpty())
			return null;
		
		int id = Integer.valueOf(cellId)-1;
		StringBuilder text = new StringBuilder();
		Field[] fields = container.getExample(id).getFields();
		
		for(int index : descIndices) {
			text.append(fields[index].toString()).append(", ");
		}
		// removes comma with space for last element
		text.delete(text.length() - 2, text.length());
		
		Tooltip tooltip = new Tooltip(text.toString());
		tooltip.setFont(Font.font("Verdana", 18));
		
		return tooltip;
	}
	
}
