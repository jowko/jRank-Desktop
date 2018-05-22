package pl.jowko.jrank.desktop.feature.tabs.upper;

import pl.jowko.jrank.desktop.exception.JRankRuntimeException;
import pl.jowko.jrank.desktop.feature.tabs.JRankTab;
import pl.jowko.jrank.desktop.feature.tabs.TabInitializationException;
import pl.jowko.jrank.desktop.feature.workspace.WorkspaceItem;

import java.io.IOException;

/**
 * Created by Piotr on 2018-05-08.
 */
class GraphTab extends JRankTab {
	
	GraphTab(WorkspaceItem workspaceItem, String tabText) throws TabInitializationException, IOException {
		try {
			initializeTabAndGetController(workspaceItem, tabText);
		} catch (JRankRuntimeException e) {
			throwInitializationException("graph", workspaceItem.getFileName(), e);
		}
	}
	
	@Override
	protected String getResourceName() {
		return "/fxml/upperTabs/graphTab.fxml";
	}
	
}
