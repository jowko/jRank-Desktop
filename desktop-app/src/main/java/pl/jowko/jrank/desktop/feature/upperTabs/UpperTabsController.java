package pl.jowko.jrank.desktop.feature.upperTabs;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import pl.jowko.jrank.desktop.feature.workspace.FileType;
import pl.jowko.jrank.desktop.feature.workspace.WorkspaceItem;
import pl.jowko.jrank.logger.JRankLogger;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.util.Objects.nonNull;

/**
 * Created by Piotr on 2018-04-29.
 */
public class UpperTabsController {
	
	private static UpperTabsController instance;
	
	@FXML
	private TabPane upperTabs;
	
	public static UpperTabsController getInstance() {
		return instance;
	}
	
	@FXML
	void initialize() {
		instance = this;
	}
	
	public void createTab(WorkspaceItem workspaceItem) {
		try {
			String tabText = createTabText(workspaceItem);
			Tab oldTab = getTabIfExists(tabText);
			if(nonNull(oldTab)) {
				upperTabs.getSelectionModel().select(oldTab);
				return;
			}
			
			JRankTab tab = createTabForFile(workspaceItem, tabText);
			upperTabs.getTabs().add(tab);
			upperTabs.getSelectionModel().select(tab);
		} catch (IOException e) {
			JRankLogger.error("Error when creating properties tab: ", e);
		}
	}
	
	private Tab getTabIfExists(String newTabText) {
		return upperTabs.getTabs().stream()
				.filter(tab -> tab.getText().equals(newTabText))
				.findAny().orElse(null);
	}
	
	private String createTabText(WorkspaceItem workspaceItem) {
		Path path = Paths.get(workspaceItem.getFilePath());
		return path.getParent().getFileName().toString() + "\\" + workspaceItem.getFileName();
	}
	
	private JRankTab createTabForFile(WorkspaceItem workspaceItem, String tabText) throws IOException {
		FileType itemType = workspaceItem.getFileType();
		switch (itemType) {
			case ROOT:
				return null;
			case FOLDER:
				return null;
			case JRANK_SETTINGS:
				return new PropertiesTab(workspaceItem, tabText);
			case DATA_TABLE:
				return null;
			case COMPARISION_TABLE:
				return null;
			case DOMINANCE:
				return null;
			case RULES:
				return null;
			case RANKING:
				return null;
			case GRAPH:
				return null;
			case UNKNOWN:
				return null;
		}
		return null;
	}
	
}
