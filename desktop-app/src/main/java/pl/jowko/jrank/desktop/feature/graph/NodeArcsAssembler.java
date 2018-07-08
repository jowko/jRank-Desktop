package pl.jowko.jrank.desktop.feature.graph;

import com.fxgraph.graph.Cell;
import com.fxgraph.graph.Edge;
import com.fxgraph.graph.Graph;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Piotr on 2018-07-07
 * This class prepares data for arcs tab.
 * @see ArcsTab
 * @see NodeArcs
 */
class NodeArcsAssembler {
	
	private Cell node;
	private Graph graph;
	
	private static Color S_RELATION_COLOR = Color.GREEN;
	private static Color SC_RELATION_COLOR = Color.RED;
	
	/**
	 * Initializes instance of this class
	 * @param node from graph with is currently selected
	 * @param graph with is displayed in graph tab
	 */
	NodeArcsAssembler(Cell node, Graph graph) {
		this.node = node;
		this.graph = graph;
	}
	
	/**
	 * Creates instance of NodeArcs class using provided data.
	 * @return NodeArcs containing data to display in arcs tab
	 */
	NodeArcs assemble() {
		String nodeId = node.getCellId();
		
		List<Edge> outEdges = graph.getModel().getAllEdges().stream()
				.filter(edge -> edge.getSource().getCellId().equals(nodeId))
				.collect(Collectors.toList());
		
		List<Edge> inEdges = graph.getModel().getAllEdges().stream()
				.filter(edge -> edge.getTarget().getCellId().equals(nodeId))
				.collect(Collectors.toList());
		
		String outS = getNodesIds(outEdges, S_RELATION_COLOR, true);
		String outSc = getNodesIds(outEdges, SC_RELATION_COLOR, true);
		String inS = getNodesIds(inEdges, S_RELATION_COLOR, false);
		String inSc = getNodesIds(inEdges, SC_RELATION_COLOR, false);
		
		return new NodeArcs(nodeId, outS, outSc, inS, inSc);
	}
	
	/**
	 * Gets nodes ids in String format with provided relation type(relation color).
	 * @param edges from with node ids will be extracted
	 * @param relationColor with represents type of relation S or Sc
	 * @param isOutRelation determines if source or target value from edge is used
	 * @return list of nodes id with are from relation S xor Sc
	 */
	private String getNodesIds(List<Edge> edges, Color relationColor, boolean isOutRelation) {
		StringBuilder builder = new StringBuilder();
		
		edges.stream()
				.filter(edge -> relationColor.equals(edge.getLine().getStroke()))
				.forEach(edge -> {
					if(isOutRelation) {
						builder.append(edge.getTarget().getCellId());
					} else {
						builder.append(edge.getSource().getCellId());
					}
					builder.append(", ");
				});
		
		if(builder.length() > 1)
			return builder.substring(0, builder.length() - 2);
		
		return builder.toString();
	}
	
}
