package pl.jowko.rulerank.desktop.feature.tabs.upper;

import pl.jowko.rulerank.desktop.exception.RuleRankRuntimeException;
import pl.jowko.rulerank.desktop.feature.rules.RulesController;
import pl.jowko.rulerank.desktop.feature.tabs.RuleRankTab;
import pl.jowko.rulerank.desktop.feature.tabs.TabInitializationException;
import pl.jowko.rulerank.desktop.feature.workspace.WorkspaceItem;
import pl.jowko.rulerank.desktop.service.JRSFileMediator;
import pl.poznan.put.cs.idss.jrs.rules.RulesContainer;

import java.io.IOException;

/**
 * This class loads RulesContainer and creates tab for rules from .rules file. <br>
 * <br>
 * Created by Piotr on 2018-05-08.
 * @see RulesController
 * @see RulesContainer
 */
class RulesTab extends RuleRankTab {
	
	/**
	 * Creates rules tab for .rules files. <br>
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
		} catch (RuleRankRuntimeException e) {
			throwInitializationException("rules", workspaceItem.getFileName(), e);
		}
	}
	
	@Override
	protected String getResourceName() {
		return "/fxml/upperTabs/rulesTab.fxml";
	}
	
}
