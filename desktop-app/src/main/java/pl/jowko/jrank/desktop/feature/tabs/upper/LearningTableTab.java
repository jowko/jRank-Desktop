package pl.jowko.jrank.desktop.feature.tabs.upper;

import pl.jowko.jrank.desktop.feature.learningtable.LearningTableController;
import pl.jowko.jrank.desktop.feature.workspace.WorkspaceItem;
import pl.poznan.put.cs.idss.jrs.core.mem.MemoryContainer;
import pl.poznan.put.cs.idss.jrs.utilities.ISFLoader;

import java.io.IOException;

/**
 * Created by Piotr on 2018-05-08.
 */
class LearningTableTab extends JRankTab {
	
	LearningTableTab(WorkspaceItem workspaceItem, String tabText) throws IOException {
		LearningTableController controller = initializeTabAndGetController(workspaceItem, tabText);
		MemoryContainer container = ISFLoader.loadISFIntoMemoryContainer(workspaceItem.getFilePath());
		System.out.println(container);
	}
	
	String getResourceName() {
		return "/fxml/upperTabs/learningTableTab.fxml";
	}

}
