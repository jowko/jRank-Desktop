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
 * This tab displays properties form from .properties file.
 * Properties form(ruleRank settings) are fully editable.
 * @see PropertiesController
 * @see JRankProperties
 */
class PropertiesTab extends JRankTab {
	
	private JRankProperties jRankProperties;
	
	/**
	 * Creates properties tab for .properties files.
	 * It will load fxml file and initialize tab with .properties file content.
	 * @param workspaceItem from workspace tree
	 * @param tabName to display on tab header(tab text)
	 * @throws TabInitializationException when error occur on tab initialization
	 * @throws IOException when somethings goes wrong with file reading
	 */
	PropertiesTab(WorkspaceItem workspaceItem, String tabName) throws TabInitializationException, IOException {
		super(tabName);
		try {
			PropertiesController controller = initializeTabAndGetController(workspaceItem);
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
