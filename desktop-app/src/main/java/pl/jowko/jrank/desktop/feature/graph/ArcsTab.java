package pl.jowko.jrank.desktop.feature.graph;

import com.fxgraph.graph.Cell;
import pl.jowko.jrank.desktop.feature.tabs.JRankTab;

import java.io.IOException;

/**
 * Created by Piotr on 2018-06-24
 * This tab contains information about node arcs in graph.
 * It is used in lower tabs by graph tab.
 */
class ArcsTab extends JRankTab {
	
	private ArcsController controller;
	
	/**
	 * Creates arcs tab with text representing node id.
	 * It also initializes controller for arcs tab.
	 *
	 * @param node with represents graph node
	 * @throws IOException when something goes wrong with loading fxml file
	 */
	ArcsTab(Cell node) throws IOException {
		super(node.getCellId());
		controller = initializeTabAndGetController(null);
		controller.initializeArcs(node, this);
	}
	
	@Override
	protected String getResourceName() {
		return "/fxml/lowerTabs/arcsTab.fxml";
	}
	
	public ArcsController getController() {
		return controller;
	}
	
}
