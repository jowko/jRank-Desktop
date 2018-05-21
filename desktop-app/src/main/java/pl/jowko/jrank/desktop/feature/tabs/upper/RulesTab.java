package pl.jowko.jrank.desktop.feature.tabs.upper;

import pl.jowko.jrank.desktop.feature.rules.RulesController;
import pl.jowko.jrank.desktop.feature.workspace.WorkspaceItem;
import pl.jowko.jrank.desktop.service.JRSFileMediator;
import pl.jowko.jrank.logger.JRankLogger;
import pl.poznan.put.cs.idss.jrs.rules.RulesContainer;

import java.io.IOException;

/**
 * Created by Piotr on 2018-05-08.
 * This class loads RulesContainer and creates tab for rules.
 */
class RulesTab extends JRankTab {
	
	RulesTab(WorkspaceItem workspaceItem, String tabText) throws TabInitializationException {
		try {
			RulesContainer container = JRSFileMediator.loadRules(workspaceItem);
			RulesController controller = initializeTabAndGetController(workspaceItem, tabText);
			controller.initializeRules(container, workspaceItem, this);
		} catch (RuntimeException | IOException e) {
			throw new TabInitializationException("Error while reading rules file: [" + workspaceItem.getFileName() + "]. Cause: " + e.getMessage());
		}
	}
	
	@Override
	String getResourceName() {
		return "/fxml/upperTabs/rulesTab.fxml";
	}
	
}
