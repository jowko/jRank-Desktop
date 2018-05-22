package pl.jowko.jrank.desktop.feature.tabs.upper;

import pl.jowko.jrank.desktop.feature.tabs.JRankTab;
import pl.jowko.jrank.desktop.feature.tabs.TabInitializationException;
import pl.jowko.jrank.desktop.feature.workspace.WorkspaceItem;

import java.io.IOException;

/**
 * Created by Piotr on 2018-05-08.
 */
class ComparisionTableTab extends JRankTab {
	
	ComparisionTableTab(WorkspaceItem workspaceItem, String tabText) throws TabInitializationException {
		try {
			initializeTabAndGetController(workspaceItem, tabText);
		} catch (RuntimeException | IOException e) {
			throwInitializationException("pct isf", workspaceItem.getFileName(), e);
		}
		
	}
	
	@Override
	protected String getResourceName() {
		return "/fxml/upperTabs/comparisionTableTab.fxml";
	}
	
}
