package pl.jowko.rulerank.desktop.feature.tabs.upper;

import pl.jowko.rulerank.desktop.exception.JRankRuntimeException;
import pl.jowko.rulerank.desktop.feature.approximations.ApproximationsController;
import pl.jowko.rulerank.desktop.feature.tabs.JRankTab;
import pl.jowko.rulerank.desktop.feature.tabs.TabInitializationException;
import pl.jowko.rulerank.desktop.feature.workspace.WorkspaceItem;
import pl.jowko.rulerank.desktop.service.JRSFileMediator;

import java.io.IOException;

/**
 * Created by Piotr on 2018-05-08.
 * This tab displays approximations file(.apx).
 * For now, this file is displayed as text file.
 * @see ApproximationsController
 */
class ApproximationsTab extends JRankTab {
	
	/**
	 * Creates approximations tab for .apx files.
	 * It will load fxml file and initialize tab with .apx file content.
	 * @param workspaceItem from workspace tree
	 * @param tabName to display on tab header(tab text)
	 * @throws TabInitializationException when error occur on tab initialization
	 * @throws IOException when somethings goes wrong with file reading
	 */
	ApproximationsTab(WorkspaceItem workspaceItem, String tabName) throws TabInitializationException, IOException {
		super(tabName);
		try {
			ApproximationsController controller = initializeTabAndGetController(workspaceItem);
			controller.initializeTab(JRSFileMediator.loadTextFile(workspaceItem));
		} catch (JRankRuntimeException e) {
			throwInitializationException("approximations", workspaceItem.getFileName(), e);
		}
	}
	
	@Override
	protected String getResourceName() {
		return "/fxml/upperTabs/approximationsTab.fxml";
	}
	
}
