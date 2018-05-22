package pl.jowko.jrank.desktop.feature.tabs.upper;

import pl.jowko.jrank.desktop.feature.tabs.JRankTab;
import pl.jowko.jrank.desktop.feature.tabs.TabInitializationException;
import pl.jowko.jrank.desktop.feature.textfile.TextFileController;
import pl.jowko.jrank.desktop.feature.workspace.WorkspaceItem;
import pl.jowko.jrank.desktop.service.JRSFileMediator;

import java.io.IOException;

/**
 * Created by Piotr on 2018-05-11.
 */
class TextFileTab extends JRankTab {
	
	TextFileTab(WorkspaceItem workspaceItem, String tabText) throws TabInitializationException {
		try {
			TextFileController controller = initializeTabAndGetController(workspaceItem, tabText);
			controller.initializeTab(JRSFileMediator.loadTextFile(workspaceItem));
		} catch (RuntimeException | IOException e) {
			throwInitializationException("text", workspaceItem.getFileName(), e);
		}
	}
	
	@Override
	protected String getResourceName() {
		return "/fxml/upperTabs/textFileTab.fxml";
	}
	
}
