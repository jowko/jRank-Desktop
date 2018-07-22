package pl.jowko.rulerank.desktop.feature.graph;

import com.fxgraph.cells.CircleCell;
import com.fxgraph.graph.Cell;
import com.fxgraph.graph.MouseClickAction;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import pl.jowko.rulerank.desktop.feature.tabs.lower.LowerTabsController;
import pl.jowko.rulerank.logger.JRankLogger;

import java.io.IOException;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static pl.jowko.rulerank.desktop.utils.BooleanUtils.not;

/**
 * Created by Piotr on 2018-06-24
 * This class perform select action on graph node when node is clicked.
 */
class GraphSelectAction implements MouseClickAction {
	
	private CircleCell lastSelected;
	private ArcsTab arcsTab;
	private GraphController graphController;
	
	GraphSelectAction(GraphController graphController) {
		this.graphController = graphController;
	}
	
	/**
	 * Performs select action on graph node.
	 * It will add select circle to selected node and remove selection from previous node.
	 * Also it will initialize/update arcs tab with displays additional information related with node.
	 * @param node with is clicked
	 */
	@Override
	public void performAction(Cell node) {
		if(not(node instanceof CircleCell))
			return;
		
		removeSelectionFromPreviousNode();
		selectNode(node);
		showArcsTab(node);
	}
	
	private void removeSelectionFromPreviousNode() {
		if(nonNull(lastSelected)) {
			Paint circleColor = lastSelected.getCircle().getFill();
			lastSelected.getCircle().setStroke(circleColor);
		}
	}
	
	private void selectNode(Cell node) {
		lastSelected = (CircleCell) node;
		lastSelected.getCircle().setStroke(Color.BLUE);
	}
	
	/**
	 * Show arcs tab and fill it with information from graph node.
	 * It will create tab if it not exists and initialize all needed events.
	 * If tab already exists, it will update tab with new data.
	 * @param node with will be used in arcs tab
	 */
	private void showArcsTab(Cell node) {
		NodeArcs arcs = new NodeArcsAssembler(node, graphController.getGraph()).assemble();
		
		if(isNull(arcsTab)) {
			try {
				arcsTab = new ArcsTab(arcs);
				LowerTabsController.getInstance().addTab(arcsTab);
				arcsTab.setOnCloseRequest(event -> arcsTab = null);
				graphController.initializeCloseEventForGraphTab(arcsTab);
			} catch (IOException e) {
				JRankLogger.error("Error when opening rule statistics: " + e);
			}
		} else { // tab already exists
			arcsTab.getController().initializeArcs(arcs);
		}
		LowerTabsController.getInstance().focusOnTab(arcsTab);
	}
	
}
