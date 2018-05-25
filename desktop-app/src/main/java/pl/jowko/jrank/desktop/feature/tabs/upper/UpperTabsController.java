package pl.jowko.jrank.desktop.feature.tabs.upper;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import pl.jowko.jrank.desktop.exception.JRankException;
import pl.jowko.jrank.desktop.feature.tabs.JRankTab;
import pl.jowko.jrank.desktop.feature.workspace.FileType;
import pl.jowko.jrank.desktop.feature.workspace.WorkspaceItem;
import pl.jowko.jrank.logger.JRankLogger;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.util.Objects.nonNull;

/**
 * Created by Piotr on 2018-04-29.
 * This controller manages upper TabPane.
 * In this pane main tabs are displayed.
 * It allows to add and remove(close) tabs from TabPane.
 * All added tabs should inherit JRankTab class.
 * @see JRankTab
 * @see pl.jowko.jrank.desktop.feature.tabs.upper package for all supported tabs
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
	
	/**
	 * Creates tab from provided workspace tree item.
	 * It will check, if tab for provided item exists.
	 * If such tab exists, nothing happens.
	 * If such tab doesn't exits, new tab will be created for provided item.
	 * Only one tab for each workspace item can be created.
	 * Created tab will load necessary data and focus will be set on new tab.
	 * If some error occurs while creating tab, tab will not be created and error will be logged.
	 * @param workspaceItem from workspace tree
	 */
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
		} catch (JRankException e) {
			JRankLogger.error(e.getMessage());
		} catch (IOException | RuntimeException e) {
			JRankLogger.error("Unexpected error occurred while creating tab: ", e);
		}
	}
	
	/**
	 * Close provided tab in correct way.
	 * @param tab to close
	 */
	public void closeTab(Tab tab) {
		EventHandler<Event> handler = tab.getOnClosed();
		if (null != handler) {
			handler.handle(null);
		} else {
			tab.getTabPane().getTabs().remove(tab);
		}
	}
	
	/**
	 * Gets tab from TabPane if tab with provided name already exists.
	 * @param newTabText by with search will be performed
	 * @return found tab or null
	 */
	private Tab getTabIfExists(String newTabText) {
		return upperTabs.getTabs().stream()
				.filter(tab -> {
					JRankTab jRankTab = (JRankTab) tab;
					return nonNull(jRankTab.getTabName()) && jRankTab.getTabName().equals(newTabText);
				})
				.findAny().orElse(null);
	}
	
	/**
	 * Creates tab text(header) from workspace item.
	 * Experiment name and file name will be concatenated to form tab name.
	 * @param workspaceItem for with tab text will be created
	 * @return String containing experiment and file name
	 */
	private String createTabText(WorkspaceItem workspaceItem) {
		Path path = Paths.get(workspaceItem.getFilePath());
		return path.getParent().getFileName().toString() + "\\" + workspaceItem.getFileName();
	}
	
	/**
	 * Creates tab of correct type for provided workspace item and tab text.
	 * Each file type has own tab type and fxml file.
	 * If application tries to create tab for directory or for root node, WrongFileTypeException will be thrown
	 * @param workspaceItem from with tab will be created
	 * @param tabText do display on tab
	 * @see pl.jowko.jrank.desktop.feature.tabs.upper package for all supported tabs
	 * @return JRankTab for provided item and will initialized data
	 * @throws JRankException when something goes wrong with initializing tab
	 * @throws IOException when something goes wrong with reading files
	 * @throws WrongFileTypeException when directory or root node will be passed to this methd
	 */
	private JRankTab createTabForFile(WorkspaceItem workspaceItem, String tabText) throws JRankException, IOException {
		FileType itemType = workspaceItem.getFileType();
		switch (itemType) {
			case ROOT:
				throw new WrongFileTypeException("Cannot open root node as file.");
			case DIRECTORY:
				throw new WrongFileTypeException("Cannot open experiment directory: [" + workspaceItem.getFileName() + "] as file");
			case JRANK_SETTINGS:
				return new PropertiesTab(workspaceItem, tabText);
			case LEARNING_TABLE:
				return new LearningTableTab(workspaceItem, tabText);
			case COMPARISION_TABLE:
				return new ComparisionTableTab(workspaceItem, tabText);
			case APPROXIMATION:
				return new ApproximationsTab(workspaceItem, tabText);
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
