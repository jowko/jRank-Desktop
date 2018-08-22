package pl.jowko.rulerank.desktop.feature.graph;

import com.fxgraph.cells.CircleCell;
import com.fxgraph.graph.Graph;
import com.fxgraph.graph.Model;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Font;
import pl.jowko.rulerank.desktop.exception.RuleRankRuntimeException;
import pl.jowko.rulerank.desktop.feature.workspace.IsfFinder;
import pl.jowko.rulerank.desktop.feature.workspace.WorkspaceItem;
import pl.poznan.put.cs.idss.jrs.core.mem.MemoryContainer;
import pl.poznan.put.cs.idss.jrs.types.Attribute;
import pl.poznan.put.cs.idss.jrs.types.Field;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

/**
 * Creates Graph object with is used by JavaFX. <br>
 * <br>
 * Created by Piotr on 2018-08-22
 * @see GraphController
 * @see GraphReader
 */
class GraphAssembler {
	
	private GraphReader graphReader;
	private MemoryContainer container;
	private List<Integer> descIndices;
	
	/**
	 * @param graphFileContent from .graph file
	 * @param workspaceItem representing .graph file
	 */
	GraphAssembler(String graphFileContent, WorkspaceItem workspaceItem) {
		loadMemoryContainer(workspaceItem);
		descIndices = getDescriptionFieldsIds();
		graphReader = new GraphReader(graphFileContent);
	}
	
	/**
	 * Creates graph from provided graph file. <br>
	 * It will assemble cells with tooltips and edges from dto returned from GraphReader
	 * @see GraphReader
	 * @param selectAction with handles select cell action
	 * @return Graph filled with cells and edges extracted from graph file
	 */
	Graph createGraph(GraphSelectAction selectAction) {
		GraphDto graphDto = graphReader.extractGraph();
		Graph graph = new Graph(selectAction);
		
		Model model = graph.getModel();
		
		graphDto.getCells().forEach(dto -> {
			CircleCell circleCell = new CircleCell(dto.getCellId(), dto.getLabel(), dto.getColor());
			Tooltip.install(circleCell, createTooltip(dto.getCellId(), descIndices));
			model.addCell(circleCell);
		});
		
		graphDto.getEdges().forEach(dto ->
			model.addEdge(dto.getSourceId(), dto.getTargetId(), dto.getLabel(), dto.getColor())
		);
		
		graph.endUpdate();
		
		return graph;
	}
	
	private void loadMemoryContainer(WorkspaceItem workspaceItem) {
		try {
			container = new IsfFinder(workspaceItem, true).getMemoryContainer();
			if(isNull(container))
				throw new RuleRankRuntimeException("Isf data file was not found on provided path");
		} catch (IOException e) {
			throw new RuleRankRuntimeException("Error when reading isf file for graph: " + e.getMessage());
		}
	}
	
	/**
	 * This methods finds all description fields indexes in container. <br>
	 * They are later used to create tooltips for nodes <br>
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
