package pl.jowko.jrank.desktop.feature.tabs.upper;

import pl.jowko.jrank.desktop.exception.JRankRuntimeException;
import pl.jowko.jrank.desktop.feature.tabs.JRankTab;
import pl.jowko.jrank.desktop.feature.tabs.TabInitializationException;
import pl.jowko.jrank.desktop.feature.unknown.UnknownFileController;
import pl.jowko.jrank.desktop.feature.workspace.WorkspaceItem;
import pl.jowko.jrank.desktop.service.JRSFileMediator;
import pl.jowko.jrank.logger.JRankLogger;

import java.io.IOException;

/**
 * Created by Piotr on 2018-05-21.
 * This tab loads unknown file type as text file.
 */
class UnknownFileTab extends JRankTab {
	
	UnknownFileTab(WorkspaceItem workspaceItem, String tabText) throws TabInitializationException, IOException {
		JRankLogger.warn("File [" + workspaceItem.getFileName() + "] is not recognized by application. Trying to open it as text file.");
		try {
			UnknownFileController controller = initializeTabAndGetController(workspaceItem, tabText);
			controller.initializeTab(JRSFileMediator.loadTextFile(workspaceItem));
		} catch (JRankRuntimeException e) {
			throwInitializationException("unknown", workspaceItem.getFileName(), e);
		}
	}
	
	@Override
	protected String getResourceName() {
		return "/fxml/upperTabs/unknownFileTab.fxml";
	}
	
}
