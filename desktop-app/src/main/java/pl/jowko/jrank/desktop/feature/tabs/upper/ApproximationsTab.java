package pl.jowko.jrank.desktop.feature.tabs.upper;

import pl.jowko.jrank.desktop.feature.approximations.ApproximationsController;
import pl.jowko.jrank.desktop.feature.workspace.WorkspaceItem;
import pl.jowko.jrank.desktop.service.JRSFileMediator;

import java.io.IOException;

/**
 * Created by Piotr on 2018-05-08.
 * This tab displays approximations file(.apx).
 * For now, this file is displayed as text file.
 */
class ApproximationsTab extends JRankTab {
	
	ApproximationsTab(WorkspaceItem workspaceItem, String tabText) throws IOException {
		ApproximationsController controller = initializeTabAndGetController(workspaceItem, tabText);
		controller.initializeTab(JRSFileMediator.loadTextFile(workspaceItem));
	}
	
	@Override
	String getResourceName() {
		return "/fxml/upperTabs/approximationsTab.fxml";
	}
	
}
