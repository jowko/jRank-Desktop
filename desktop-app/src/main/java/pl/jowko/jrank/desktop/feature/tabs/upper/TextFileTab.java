package pl.jowko.jrank.desktop.feature.tabs.upper;

import pl.jowko.jrank.desktop.feature.textfile.TextFileController;
import pl.jowko.jrank.desktop.feature.workspace.WorkspaceItem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Piotr on 2018-05-11.
 */
class TextFileTab extends JRankTab {
	
	TextFileTab(WorkspaceItem workspaceItem, String tabText) throws IOException {
		TextFileController controller = initializeTabAndGetController(workspaceItem, tabText);
		controller.initializeTab(getFileContent(workspaceItem));
	}
	
	@Override
	String getResourceName() {
		return "/fxml/upperTabs/textFileTab.fxml";
	}
	
	private String getFileContent(WorkspaceItem workspaceItem) throws IOException {
		return new String(Files.readAllBytes(Paths.get(workspaceItem.getFilePath())));
	}
	
}
