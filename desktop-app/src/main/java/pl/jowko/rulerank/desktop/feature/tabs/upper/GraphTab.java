package pl.jowko.rulerank.desktop.feature.tabs.upper;

import pl.jowko.rulerank.desktop.exception.RuleRankRuntimeException;
import pl.jowko.rulerank.desktop.feature.graph.GraphController;
import pl.jowko.rulerank.desktop.feature.tabs.RuleRankTab;
import pl.jowko.rulerank.desktop.feature.tabs.TabInitializationException;
import pl.jowko.rulerank.desktop.feature.workspace.WorkspaceItem;
import pl.jowko.rulerank.desktop.service.JRSFileMediator;

import java.io.IOException;

/**
 * Created by Piotr on 2018-05-08.
 * This tab displays graph visualisation using data from .graph file.
 */
class GraphTab extends RuleRankTab {
	
	/**
	 * Creates graph visualisation tab for .graph files.
	 * It will load fxml file and initialize tab with .graph file content.
	 * @param workspaceItem from workspace tree
	 * @param tabName to display on tab header(tab text)
	 * @throws TabInitializationException when error occur on tab initialization
	 * @throws IOException when somethings goes wrong with file reading
	 */
	GraphTab(WorkspaceItem workspaceItem, String tabName) throws TabInitializationException, IOException {
		super(tabName);
		try {
			GraphController controller = initializeTabAndGetController(workspaceItem);
			String graphContent = JRSFileMediator.loadTextFile(workspaceItem);
			controller.initializeGraph(graphContent, workspaceItem, this);
		} catch (RuleRankRuntimeException e) {
			throwInitializationException("graph", workspaceItem.getFileName(), e);
		}
	}
	
	@Override
	protected String getResourceName() {
		return "/fxml/upperTabs/graphTab.fxml";
	}
	
}