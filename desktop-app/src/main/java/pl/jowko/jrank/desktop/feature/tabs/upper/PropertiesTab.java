package pl.jowko.jrank.desktop.feature.tabs.upper;

import pl.jowko.jrank.desktop.exception.JRankRuntimeException;
import pl.jowko.jrank.desktop.feature.properties.JRankProperties;
import pl.jowko.jrank.desktop.feature.properties.PropertiesController;
import pl.jowko.jrank.desktop.feature.tabs.JRankTab;
import pl.jowko.jrank.desktop.feature.tabs.TabInitializationException;
import pl.jowko.jrank.desktop.feature.workspace.WorkspaceItem;
import pl.jowko.jrank.desktop.service.JRSFileMediator;

import java.io.IOException;

/**
 * Created by Piotr on 2018-04-29.
 */
class PropertiesTab extends JRankTab {
	
	private JRankProperties jRankProperties;
	
	PropertiesTab(WorkspaceItem workspaceItem, String tabText) throws TabInitializationException, IOException {
		try {
			PropertiesController controller = initializeTabAndGetController(workspaceItem, tabText);
			jRankProperties = JRSFileMediator.loadProperties(workspaceItem);
			controller.initializeProperties(getJRankProperties(), workspaceItem, this);
		} catch (JRankRuntimeException e) {
			throwInitializationException("properties", workspaceItem.getFileName(), e);
		}
	}
	
	@Override
	protected String getResourceName() {
		return "/fxml/upperTabs/jRankSettingsTab.fxml";
	}
	
	public JRankProperties getJRankProperties() {
		return jRankProperties;
	}
	
}
