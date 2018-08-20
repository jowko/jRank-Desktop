package pl.jowko.rulerank.desktop.feature.tabs.upper;

import pl.jowko.rulerank.desktop.exception.RuleRankRuntimeException;
import pl.jowko.rulerank.desktop.feature.tabs.RuleRankTab;
import pl.jowko.rulerank.desktop.feature.tabs.TabInitializationException;
import pl.jowko.rulerank.desktop.feature.textfile.TextFileController;
import pl.jowko.rulerank.desktop.feature.workspace.WorkspaceItem;
import pl.jowko.rulerank.desktop.service.JRSFileMediator;

import java.io.IOException;

/**
 * Creates tab for .txt file. <br>
 * This tab contains TextArea containing .txt file content. <br>
 * <br>
 * Created by Piotr on 2018-05-11.
 * @see TextFileController
 */
class TextFileTab extends RuleRankTab {
	
	/**
	 * Creates text file tab for .txt files. <br>
	 * It will load fxml file and initialize tab with .txt file content.
	 * @param workspaceItem from workspace tree
	 * @param tabName to display on tab header(tab text)
	 * @throws TabInitializationException when error occur on tab initialization
	 * @throws IOException when somethings goes wrong with file reading
	 */
	TextFileTab(WorkspaceItem workspaceItem, String tabName) throws TabInitializationException, IOException {
		super(tabName);
		try {
			TextFileController controller = initializeTabAndGetController(workspaceItem);
			controller.initializeTab(JRSFileMediator.loadTextFile(workspaceItem), workspaceItem, this);
		} catch (RuleRankRuntimeException e) {
			throwInitializationException("text", workspaceItem.getFileName(), e);
		}
	}
	
	@Override
	protected String getResourceName() {
		return "/fxml/upperTabs/textFileTab.fxml";
	}
	
}
