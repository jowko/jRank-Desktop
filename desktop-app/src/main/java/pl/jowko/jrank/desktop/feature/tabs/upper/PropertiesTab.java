package pl.jowko.jrank.desktop.feature.tabs.upper;

import pl.jowko.jrank.desktop.feature.properties.JRankProperties;
import pl.jowko.jrank.desktop.feature.properties.PropertiesAssembler;
import pl.jowko.jrank.desktop.feature.properties.PropertiesController;
import pl.jowko.jrank.desktop.feature.workspace.WorkspaceItem;
import pl.jowko.jrank.logger.JRankLogger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Piotr on 2018-04-29.
 */
class PropertiesTab extends JRankTab {
	
	private JRankProperties jRankProperties;
	
	PropertiesTab(WorkspaceItem workspaceItem, String tabText) throws IOException {
		PropertiesController controller = initializeTabAndGetController(workspaceItem, tabText);
		loadProperties();
		controller.initializeProperties(getJRankProperties(), workspaceItem, this);
	}
	
	String getResourceName() {
		return "/fxml/upperTabs/jRankSettingsTab.fxml";
	}
	
	public JRankProperties getJRankProperties() {
		return jRankProperties;
	}
	
	private void loadProperties() {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(workspaceItem.getFilePath()));
			PropertiesAssembler propertiesAssembler = new PropertiesAssembler(properties);
			jRankProperties = propertiesAssembler.toJrankProperties();
		} catch (IOException e) {
			JRankLogger.error("Error when reading file: " + workspaceItem.getFileName(), e);
		}
	}
	
}
