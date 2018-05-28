package pl.jowko.jrank.desktop.feature.tabs.upper;

import pl.jowko.jrank.desktop.exception.JRankRuntimeException;
import pl.jowko.jrank.desktop.feature.tabs.JRankTab;
import pl.jowko.jrank.desktop.feature.tabs.TabInitializationException;
import pl.jowko.jrank.desktop.feature.textfile.TextFileController;
import pl.jowko.jrank.desktop.feature.workspace.WorkspaceItem;
import pl.jowko.jrank.desktop.service.JRSFileMediator;

import java.io.IOException;

/**
 * Created by Piotr on 2018-05-11.
 * Creates tab for .txt file.
 * This tab contains TextArea containing .txt file content.
 * @see TextFileController
 */
class TextFileTab extends JRankTab {
	
	/**
	 * Creates text file tab for .txt files.
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
		} catch (JRankRuntimeException e) {
			throwInitializationException("text", workspaceItem.getFileName(), e);
		}
	}
	
	@Override
	protected String getResourceName() {
		return "/fxml/upperTabs/textFileTab.fxml";
	}
	
}
