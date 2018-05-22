package pl.jowko.jrank.desktop.feature.tabs.upper;

import pl.jowko.jrank.desktop.exception.JRankRuntimeException;
import pl.jowko.jrank.desktop.feature.learningtable.LearningTableController;
import pl.jowko.jrank.desktop.feature.tabs.JRankTab;
import pl.jowko.jrank.desktop.feature.tabs.TabInitializationException;
import pl.jowko.jrank.desktop.feature.workspace.WorkspaceItem;
import pl.jowko.jrank.desktop.service.JRSFileMediator;
import pl.poznan.put.cs.idss.jrs.core.mem.MemoryContainer;

import java.io.IOException;

/**
 * Created by Piotr on 2018-05-08.
 */
class LearningTableTab extends JRankTab {
	
	LearningTableTab(WorkspaceItem workspaceItem, String tabText) throws TabInitializationException, IOException {
		try {
			LearningTableController controller = initializeTabAndGetController(workspaceItem, tabText);
			MemoryContainer container = JRSFileMediator.loadMemoryContainer(workspaceItem.getFilePath());
			controller.initializeTable(container, this, workspaceItem);
		} catch (JRankRuntimeException e) {
			throwInitializationException("isf", workspaceItem.getFileName(), e);
		}
	}
	
	@Override
	protected String getResourceName() {
		return "/fxml/upperTabs/learningTableTab.fxml";
	}

}
