package pl.jowko.rulerank.desktop.feature.tabs.upper;

import pl.jowko.rulerank.desktop.exception.RuleRankRuntimeException;
import pl.jowko.rulerank.desktop.feature.learningtable.LearningTableController;
import pl.jowko.rulerank.desktop.feature.tabs.RuleRankTab;
import pl.jowko.rulerank.desktop.feature.tabs.TabInitializationException;
import pl.jowko.rulerank.desktop.feature.workspace.WorkspaceItem;
import pl.poznan.put.cs.idss.jrs.core.mem.MemoryContainer;

import java.io.IOException;

/**
 * This tab displays learning data table from .isf files. <br>
 * Table is fully editable and customizable. <br>
 * <br>
 * Created by Piotr on 2018-05-08.
 * @see LearningTableController
 */
class LearningTableTab extends RuleRankTab {
	
	/**
	 * Creates learning data table tab for .isf files. <br>
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
		} catch (RuleRankRuntimeException e) {
			throwInitializationException("isf", workspaceItem.getFileName(), e);
		}
	}
	
	@Override
	protected String getResourceName() {
		return "/fxml/upperTabs/learningTableTab.fxml";
	}

}
