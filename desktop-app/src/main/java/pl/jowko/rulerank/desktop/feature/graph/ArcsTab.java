package pl.jowko.rulerank.desktop.feature.graph;

import pl.jowko.rulerank.desktop.feature.tabs.RuleRankTab;

import java.io.IOException;

/**
 * Created by Piotr on 2018-06-24
 * This tab contains information about node arcs in graph.
 * It is used in lower tabs by graph tab.
 */
class ArcsTab extends RuleRankTab {
	
	private ArcsController controller;
	
	/**
	 * Creates arcs tab with text representing node id.
	 * It also initializes controller for arcs tab.
	 *
	 * @param arcs with contains arcs for selected graph node
	 * @throws IOException when something goes wrong with loading fxml file
	 */
	ArcsTab(NodeArcs arcs) throws IOException {
		super(arcs.getNodeId());
		controller = initializeTabAndGetController(null);
		controller.initializeArcs(arcs, this);
	}
	
	@Override
	protected String getResourceName() {
		return "/fxml/lowerTabs/arcsTab.fxml";
	}
	
	public ArcsController getController() {
		return controller;
	}
	
}
