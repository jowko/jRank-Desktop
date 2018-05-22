package pl.jowko.jrank.desktop.feature.tabs.upper;

import pl.jowko.jrank.desktop.feature.rules.RulesController;
import pl.jowko.jrank.desktop.feature.tabs.JRankTab;
import pl.jowko.jrank.desktop.feature.tabs.TabInitializationException;
import pl.jowko.jrank.desktop.feature.workspace.WorkspaceItem;
import pl.jowko.jrank.desktop.service.JRSFileMediator;
import pl.poznan.put.cs.idss.jrs.rules.RulesContainer;

import java.io.IOException;

/**
 * Created by Piotr on 2018-05-08.
 * This class loads RulesContainer and creates tab for rules.
 */
class RulesTab extends JRankTab {
	
	/**
	 * Creates rules tab for .rules files.
	 * @param workspaceItem with file name and path
	 * @param tabText to display on tab
	 * @throws TabInitializationException when something goes wrong
	 */
	RulesTab(WorkspaceItem workspaceItem, String tabText) throws TabInitializationException {
		try {
			RulesContainer container = JRSFileMediator.loadRules(workspaceItem);
			RulesController controller = initializeTabAndGetController(workspaceItem, tabText);
			controller.initializeRules(container, workspaceItem, this);
		} catch (RuntimeException | IOException e) {
			throwInitializationException("rules", workspaceItem.getFileName(), e);
		}
	}
	
	@Override
	protected String getResourceName() {
		return "/fxml/upperTabs/rulesTab.fxml";
	}
	
}
