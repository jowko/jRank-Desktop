package pl.jowko.rulerank.desktop.feature.tabs.upper;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import pl.jowko.rulerank.desktop.exception.RuleRankException;
import pl.jowko.rulerank.desktop.feature.tabs.RuleRankTab;
import pl.jowko.rulerank.desktop.feature.tabs.TabInitializationException;
import pl.jowko.rulerank.desktop.feature.workspace.FileType;
import pl.jowko.rulerank.desktop.feature.workspace.WorkspaceItem;
import pl.jowko.rulerank.desktop.service.JRSFileMediator;
import pl.jowko.rulerank.logger.RuleRankLogger;
import pl.poznan.put.cs.idss.jrs.core.mem.MemoryContainer;
import pl.poznan.put.cs.idss.jrs.pct.PCTDetector;

import java.io.IOException;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static pl.jowko.rulerank.desktop.utils.PathUtils.getSubDirectoryPath;

/**
 * This controller manages upper TabPane. <br>
 * In this pane main tabs are displayed. <br>
 * It allows to add and remove(close) tabs from TabPane. <br>
 * All added tabs should inherit RuleRankTab class. <br>
 *  <br>
 * Created by Piotr on 2018-04-29.
 * @see RuleRankTab
 * @see pl.jowko.rulerank.desktop.feature.tabs.upper package for all supported tabs
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
		menuCreator = new TabsContextMenuCreator(instance);
	}
	
	/**
	 * Creates tab from provided workspace tree item. <br>
	 * It will check, if tab for provided item exists. <br>
	 * If such tab exists, nothing happens. <br>
	 * If such tab doesn't exits, new tab will be created for provided item. <br>
	 * Only one tab for each workspace item can be created. <br>
	 * Created tab will load necessary data and focus will be set on new tab. <br>
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
			
			RuleRankTab tab = createTabForFile(workspaceItem, tabText);
			menuCreator.create(tab);
			upperTabs.getTabs().add(tab);
			upperTabs.getSelectionModel().select(tab);
		} catch (RuleRankException e) {
			RuleRankLogger.error(e.getMessage());
		} catch (IOException | RuntimeException e) {
			RuleRankLogger.error("Unexpected error occurred while creating tab: ", e);
		}
	}
	
	/**
	 * Close provided tab in correct way. <br>
	 * It will fire onClosed event for closed tab if is available. <br>
	 * If this event isn't available, tab will be closed.
	 * @param tab to close
	 */
	public void closeTab(Tab tab) {
		EventHandler<Event> handler = tab.getOnClosed();
		if (null != handler) {
			handler.handle(null);
		} else {
			upperTabs.getTabs().remove(tab);
		}
	}
	
	/**
	 * Force close of tab. <br>
	 * No close handler will be fired with this close action.
	 * @param tab to close
	 */
	public void forceCloseTab(Tab tab) {
		upperTabs.getTabs().remove(tab);
	}
	
	/**
	 * Close all provided tab from list. <br>
	 * It will fire onClosed event for each closed tab if is available. <br>
	 * If this event isn't available, tab will be closed.
	 * @param tabs to close
	 */
	public void closeTab(List<Tab> tabs) {
		for(Tab tab : tabs) {
			EventHandler<Event> handler = tab.getOnClosed();
			if (null != handler) {
				handler.handle(null);
			} else {
				upperTabs.getTabs().remove(tab);
			}
		}
	}
	
	public TabPane getUpperTabs() {
		return upperTabs;
	}
	
	/**
	 * Gets tab from TabPane if tab with provided name already exists.
	 * @param newTabText by with search will be performed
	 * @return found tab or null
	 */
	private Tab getTabIfExists(String newTabText) {
		return upperTabs.getTabs().stream()
				.filter(tab -> {
					RuleRankTab ruleRankTab = (RuleRankTab) tab;
					return nonNull(ruleRankTab.getTabName()) && ruleRankTab.getTabName().equals(newTabText);
				})
				.findAny().orElse(null);
	}
	
	/**
	 * Creates tab text(header) from workspace item. <br>
	 * Experiment name and file name will be concatenated to form tab name.
	 * @param workspaceItem for with tab text will be created
	 * @return String containing experiment and file name
	 */
	private String createTabText(WorkspaceItem workspaceItem) {
		return getSubDirectoryPath(workspaceItem.getExperimentName()) + workspaceItem.getFileName();
	}
	
	/**
	 * Creates tab of correct type for provided workspace item and tab text. <br>
	 * Each file type has own tab type and fxml file. <br>
	 * If application tries to create tab for directory or for root node, WrongFileTypeException will be thrown
	 * @param workspaceItem from with tab will be created
	 * @param tabText do display on tab
	 * @see pl.jowko.rulerank.desktop.feature.tabs.upper package for all supported tabs
	 * @return RuleRankTab for provided item and will initialized data
	 * @throws RuleRankException when something goes wrong with initializing tab
	 * @throws IOException when something goes wrong with reading files
	 * @throws WrongFileTypeException when directory or root node will be passed to this methd
	 */
	private RuleRankTab createTabForFile(WorkspaceItem workspaceItem, String tabText) throws RuleRankException, IOException {
		FileType itemType = workspaceItem.getFileType();
		switch (itemType) {
			case ROOT:
				throw new WrongFileTypeException("Cannot open root node as file.");
			case DIRECTORY:
				throw new WrongFileTypeException("Cannot open experiment directory: [" + workspaceItem.getFileName() + "] as file");
			case PROPERTIES:
				return new PropertiesTab(workspaceItem, tabText);
			case ISF_TABLE:
				return handleIsfTable(workspaceItem, tabText);
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
	
	/**
	 * This methods handle isf table case. <br>
	 * When isf file is loaded, it can represent editable isf for learning or test data. <br>
	 * But it can also represent non editable Partial Comparision Table(PCT) in isf format. <br>
	 * This method loads isf table, checks it type and returns RuleRankTab for provided type.
	 * @param workspaceItem from workspace tree
	 * @param tabText to display on tab header
	 * @return RuleRankTab for isf file
	 * @throws IOException when something goes wrong with reading files
	 * @throws TabInitializationException when something goes wrong with initialization of tabs
	 */
	private RuleRankTab handleIsfTable(WorkspaceItem workspaceItem, String tabText) throws IOException, TabInitializationException {
		MemoryContainer container = JRSFileMediator.loadMemoryContainer(workspaceItem.getFilePath());
		
		if(isNull(container))
			throw new TabInitializationException("Loaded memory container is empty.");
		
		boolean isPCT = PCTDetector.isPCT(container);
		if(isPCT) {
			return new ComparisionTableTab(container, workspaceItem, tabText);
		}
		return new LearningTableTab(container, workspaceItem, tabText);
	}
	
}
