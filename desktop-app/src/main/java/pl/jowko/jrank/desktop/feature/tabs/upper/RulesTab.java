package pl.jowko.jrank.desktop.feature.tabs.upper;

import pl.jowko.jrank.desktop.feature.workspace.WorkspaceItem;

import java.io.IOException;

/**
 * Created by Piotr on 2018-05-08.
 */
class RulesTab extends JRankTab {
	
	RulesTab(WorkspaceItem workspaceItem, String tabText) throws IOException {
		initializeTabAndGetController(workspaceItem, tabText);
	}
	
	@Override
	String getResourceName() {
		return "/fxml/upperTabs/rulesTab.fxml";
	}
	
}
