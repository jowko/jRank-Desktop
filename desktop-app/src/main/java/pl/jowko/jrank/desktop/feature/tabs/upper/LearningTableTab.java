package pl.jowko.jrank.desktop.feature.tabs.upper;

import pl.jowko.jrank.desktop.feature.workspace.WorkspaceItem;

import java.io.IOException;

/**
 * Created by Piotr on 2018-05-08.
 */
class LearningTableTab extends JRankTab {
	
	LearningTableTab(WorkspaceItem workspaceItem, String tabText) throws IOException {
		initializeTabAndGetController(workspaceItem, tabText);
	}
	
	String getResourceName() {
		return "/fxml/upperTabs/learningTableTab.fxml";
	}

}
