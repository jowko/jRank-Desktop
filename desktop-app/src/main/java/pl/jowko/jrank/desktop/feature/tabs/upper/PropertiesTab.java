package pl.jowko.jrank.desktop.feature.tabs.upper;

import pl.jowko.jrank.desktop.feature.properties.JRankProperties;
import pl.jowko.jrank.desktop.feature.properties.PropertiesController;
import pl.jowko.jrank.desktop.feature.workspace.WorkspaceItem;
import pl.jowko.jrank.desktop.service.JRSFileMediator;

import java.io.IOException;

/**
 * Created by Piotr on 2018-04-29.
 */
class PropertiesTab extends JRankTab {
	
	private JRankProperties jRankProperties;
	
	PropertiesTab(WorkspaceItem workspaceItem, String tabText) throws IOException {
		PropertiesController controller = initializeTabAndGetController(workspaceItem, tabText);
		jRankProperties = JRSFileMediator.loadProperties(workspaceItem);
		controller.initializeProperties(getJRankProperties(), workspaceItem, this);
	}
	
	@Override
	String getResourceName() {
		return "/fxml/upperTabs/jRankSettingsTab.fxml";
	}
	
	public JRankProperties getJRankProperties() {
		return jRankProperties;
	}
	
}
