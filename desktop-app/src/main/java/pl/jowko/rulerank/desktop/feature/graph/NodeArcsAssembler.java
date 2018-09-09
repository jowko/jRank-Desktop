package pl.jowko.rulerank.desktop.feature.graph;

import com.fxgraph.graph.Cell;
import com.fxgraph.graph.Graph;
import com.fxgraph.graph.LabeledEdge;
import javafx.scene.paint.Color;
import pl.jowko.rulerank.desktop.feature.internationalization.Labels;
import pl.jowko.rulerank.desktop.feature.internationalization.LanguageService;

import java.util.List;
import java.util.stream.Collectors;

import static pl.jowko.rulerank.desktop.feature.graph.GraphColor.*;
import static pl.jowko.rulerank.desktop.utils.StringUtils.isNotNullOrEmpty;

/**
 * This class prepares data for arcs tab. <br>
 *  <br>
 * Created by Piotr on 2018-07-07
 * @see ArcsTab
 * @see NodeArcs
 */
class NodeArcsAssembler {
	
	private Cell node;
	private Graph graph;
	private LanguageService labels;
	
	/**
	 * Initializes instance of this class
	 * @param node from graph with is currently selected
	 * @param graph with is displayed in graph tab
	 */
	NodeArcsAssembler(Cell node, Graph graph) {
		this.node = node;
		this.graph = graph;
		labels = LanguageService.getInstance();
	}
	
	/**
	 * Creates instance of NodeArcs class using provided data.
	 * @return NodeArcs containing data to display in arcs tab
	 */
	NodeArcs assemble() {
		String nodeId = node.getCellId();
		
		List<LabeledEdge> outEdges = graph.getModel().getAllEdges().stream()
				.filter(edge -> edge.getSource().getCellId().equals(nodeId))
				.map(edge -> (LabeledEdge) edge)
				.collect(Collectors.toList());
		
		List<LabeledEdge> inEdges = graph.getModel().getAllEdges().stream()
				.filter(edge -> edge.getTarget().getCellId().equals(nodeId))
				.map(edge -> (LabeledEdge) edge)
				.collect(Collectors.toList());
		
		String outS = getNodesIds(outEdges, S_COLOR, true);
		String outSc = getNodesIds(outEdges, SC_COLOR, true);
		String inS = getNodesIds(inEdges, S_COLOR, false);
		String inSc = getNodesIds(inEdges, SC_COLOR, false);
		
		return new NodeArcs(nodeId, outS, outSc, inS, inSc);
	}
	
	/**
	 * Gets nodes ids in String format with provided relation type(relation color).
	 * @param edges from with node ids will be extracted
	 * @param relationColor with represents type of relation S or Sc
	 * @param isOutRelation determines if source or target value from edge is used
	 * @return list of nodes id with are from relation S xor Sc
	 */
	private String getNodesIds(List<LabeledEdge> edges, Color relationColor, boolean isOutRelation) {
		StringBuilder builder = new StringBuilder();
		
		edges.stream()
				.filter(edge -> relationColor.equals(edge.getLine().getStroke()) || S_SC_COLOR.equals(edge.getLine().getStroke()) || edge.hasSecondLabel())
				.forEach(edge -> {
					if(isOutRelation) {
						builder.append(edge.getTarget().getCellId());
					} else {
						builder.append(edge.getSource().getCellId());
					}
					
					appendLabel(builder, relationColor, edge);
				});
		
		if(builder.length() > 1)
			return builder.substring(0, builder.length() - 2);
		
		return labels.get(Labels.ARCS_NONE);
	}
	
	/**
	 * Appends label from edge to builder. <br>
	 * If considered edge was reduced from two edges to one, it is assumed that label for SC relation is in second label.
	 * @param builder to with label will be appended
	 * @param relationColor of current considered relation
	 * @param edge from with label will be extracted
	 */
	private static void appendLabel(StringBuilder builder, Color relationColor, LabeledEdge edge) {
		String label;
		
		if(SC_COLOR.equals(relationColor) && edge.hasSecondLabel()) {
			label = edge.getSecondLabel();
		} else {
			label = edge.getLabel();
		}
		
		if(isNotNullOrEmpty(label)) {
			builder.append(' ');
			builder.append(label);
		}
		
		builder.append(", ");
	}
	
}
