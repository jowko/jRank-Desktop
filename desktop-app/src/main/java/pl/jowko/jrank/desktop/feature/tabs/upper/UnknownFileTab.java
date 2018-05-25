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
 * @see UnknownFileController
 */
class UnknownFileTab extends JRankTab {
	
	/**
	 * Creates unknown file tab for .apx files.
	 * It will load fxml file and initialize tab with unknown file content.
	 * @param workspaceItem from workspace tree
	 * @param tabName to display on tab header(tab text)
	 * @throws TabInitializationException when error occur on tab initialization
	 * @throws IOException when somethings goes wrong with file reading
	 */
	UnknownFileTab(WorkspaceItem workspaceItem, String tabName) throws TabInitializationException, IOException {
		super(tabName);
		JRankLogger.warn("File [" + workspaceItem.getFileName() + "] is not recognized by application. Trying to open it as text file.");
		try {
			UnknownFileController controller = initializeTabAndGetController(workspaceItem);
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
