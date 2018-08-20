package pl.jowko.rulerank.desktop.feature.tabs.upper;

import pl.jowko.rulerank.desktop.exception.RuleRankRuntimeException;
import pl.jowko.rulerank.desktop.feature.tabs.RuleRankTab;
import pl.jowko.rulerank.desktop.feature.tabs.TabInitializationException;
import pl.jowko.rulerank.desktop.feature.unknown.UnknownFileController;
import pl.jowko.rulerank.desktop.feature.workspace.WorkspaceItem;
import pl.jowko.rulerank.desktop.service.JRSFileMediator;
import pl.jowko.rulerank.logger.RuleRankLogger;

import java.io.IOException;

/**
 * This tab loads unknown file type as text file. <br>
 *  <br>
 * Created by Piotr on 2018-05-21.
 * @see UnknownFileController
 */
class UnknownFileTab extends RuleRankTab {
	
	/**
	 * Creates unknown file tab for .apx files. <br>
	 * It will load fxml file and initialize tab with unknown file content.
	 * @param workspaceItem from workspace tree
	 * @param tabName to display on tab header(tab text)
	 * @throws TabInitializationException when error occur on tab initialization
	 * @throws IOException when somethings goes wrong with file reading
	 */
	UnknownFileTab(WorkspaceItem workspaceItem, String tabName) throws TabInitializationException, IOException {
		super(tabName);
		RuleRankLogger.warn("File [" + workspaceItem.getFileName() + "] is not recognized by application. Trying to open it as text file.");
		try {
			UnknownFileController controller = initializeTabAndGetController(workspaceItem);
			controller.initializeTab(JRSFileMediator.loadTextFile(workspaceItem));
		} catch (RuleRankRuntimeException e) {
			throwInitializationException("unknown", workspaceItem.getFileName(), e);
		}
	}
	
	@Override
	protected String getResourceName() {
		return "/fxml/upperTabs/unknownFileTab.fxml";
	}
	
}
