package pl.jowko.jrank.desktop.feature.tabs.upper;

import pl.jowko.jrank.desktop.exception.JRankRuntimeException;
import pl.jowko.jrank.desktop.feature.rules.RulesController;
import pl.jowko.jrank.desktop.feature.tabs.JRankTab;
import pl.jowko.jrank.desktop.feature.tabs.TabInitializationException;
import pl.jowko.jrank.desktop.feature.workspace.WorkspaceItem;
import pl.jowko.jrank.desktop.service.JRSFileMediator;
import pl.poznan.put.cs.idss.jrs.rules.RulesContainer;

import java.io.IOException;

/**
 * Created by Piotr on 2018-05-08.
 * This class loads RulesContainer and creates tab for rules from .rules file.
 * @see RulesController
 * @see RulesContainer
 */
class RulesTab extends JRankTab {
	
	/**
	 * Creates rules tab for .rules files.
	 * It will load fxml file and initialize tab with .rules file content.
	 * @param workspaceItem from workspace tree
	 * @param tabName to display on tab header(tab text)
	 * @throws TabInitializationException when error occur on tab initialization
	 * @throws IOException when somethings goes wrong with file reading
	 */
	RulesTab(WorkspaceItem workspaceItem, String tabName) throws TabInitializationException, IOException {
		super(tabName);
		try {
			RulesContainer container = JRSFileMediator.loadRules(workspaceItem);
			RulesController controller = initializeTabAndGetController(workspaceItem);
			controller.initializeRules(container, workspaceItem, this);
		} catch (JRankRuntimeException e) {
			throwInitializationException("rules", workspaceItem.getFileName(), e);
		}
	}
	
	@Override
	protected String getResourceName() {
		return "/fxml/upperTabs/rulesTab.fxml";
	}
	
}
