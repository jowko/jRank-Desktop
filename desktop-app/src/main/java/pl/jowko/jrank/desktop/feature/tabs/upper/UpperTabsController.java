package pl.jowko.jrank.desktop.feature.tabs.upper;

import javafx.event.Event;
import javafx.event.EventHandler;
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
	
	private TabsContextMenuCreator menuCreator;
	
	public static UpperTabsController getInstance() {
		return instance;
	}
	
	@FXML
	void initialize() {
		instance = this;
		menuCreator = new TabsContextMenuCreator(upperTabs);
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
			menuCreator.create(tab);
			upperTabs.getTabs().add(tab);
			upperTabs.getSelectionModel().select(tab);
		} catch (IOException e) {
			JRankLogger.error("Error when creating tab for file: [" + workspaceItem.getFileName() + "]. Cause: ", e);
		}
	}
	
	public void closeTab(Tab tab) {
		EventHandler<Event> handler = tab.getOnClosed();
		if (null != handler) {
			handler.handle(null);
		} else {
			tab.getTabPane().getTabs().remove(tab);
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
			case LEARNING_TABLE:
				return new LearningTableTab(workspaceItem, tabText);
			case COMPARISION_TABLE:
				return new ComparisionTableTab(workspaceItem, tabText);
			case DOMINANCE:
				return new DominanceTab(workspaceItem, tabText);
			case RULES:
				return new RulesTab(workspaceItem, tabText);
			case RANKING:
				return new RankingTab(workspaceItem, tabText);
			case GRAPH:
				return new GraphTab(workspaceItem, tabText);
			case TEXT:
				return new TextFileTab(workspaceItem, tabText);
			case UNKNOWN:
				return new UnknownFileTab(workspaceItem, tabText);
		}
		return null;
	}
	
}
