package pl.jowko.jrank.desktop.feature.tabs.upper;

import pl.jowko.jrank.desktop.exception.JRankRuntimeException;
import pl.jowko.jrank.desktop.feature.learningtable.LearningTableController;
import pl.jowko.jrank.desktop.feature.tabs.JRankTab;
import pl.jowko.jrank.desktop.feature.tabs.TabInitializationException;
import pl.jowko.jrank.desktop.feature.workspace.WorkspaceItem;
import pl.poznan.put.cs.idss.jrs.core.mem.MemoryContainer;

import java.io.IOException;

/**
 * Created by Piotr on 2018-05-08.
 * This tab displays learning data table from .isf files.
 * Table is fully editable and customizable.
 * @see LearningTableController
 */
class LearningTableTab extends JRankTab {
	
	/**
	 * Creates learning data table tab for .isf files.
	 * It will load fxml file and initialize tab with .isf file content.
	 *
	 * @param container with isf table
	 * @param workspaceItem from workspace tree
	 * @param tabName to display on tab header(tab text)
	 * @throws TabInitializationException when error occur on tab initialization
	 * @throws IOException when somethings goes wrong with file reading
	 */
	LearningTableTab(MemoryContainer container, WorkspaceItem workspaceItem, String tabName) throws TabInitializationException, IOException {
		super(tabName);
		try {
			LearningTableController controller = initializeTabAndGetController(workspaceItem);
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
