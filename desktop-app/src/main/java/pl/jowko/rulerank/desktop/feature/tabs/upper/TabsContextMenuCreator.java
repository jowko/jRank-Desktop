package pl.jowko.rulerank.desktop.feature.tabs.upper;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import pl.jowko.rulerank.desktop.feature.internationalization.Labels;
import pl.jowko.rulerank.desktop.feature.internationalization.LanguageService;
import pl.jowko.rulerank.desktop.feature.tabs.RuleRankTab;
import pl.jowko.rulerank.desktop.feature.tabs.TabEditionChecker;

import java.util.ArrayList;
import java.util.List;

import static pl.jowko.rulerank.desktop.utils.BooleanUtils.not;

/**
 * This class creates context menus for tab headers. <br>
 * This actions must be created for each tab with are added to TabPane. <br>
 * <br>
 * Created by Piotr on 2018-05-18.
 * @see TabEditionChecker
 */
class TabsContextMenuCreator {
	
	private LanguageService labels;
	private UpperTabsController controller;
	private TabEditionChecker editionChecker;
	
	/**
	 * Create instance of this class
	 * @param controller with is used to close tabs
	 */
	TabsContextMenuCreator(UpperTabsController controller) {
		this.controller = controller;
		editionChecker = new TabEditionChecker();
		labels = LanguageService.getInstance();
	}
	
	/**
	 * Create ContextMenu for provided tab. Three actions are added: <br>
	 * - Close this tab <br>
	 * - Close all tabs <br>
	 * - Close all except this <br>
	 * All close actions checks, if tabs to close were edited and ask for confirmation in such case.
	 * @param tab to with ContextMenu will be added
	 */
	void create(RuleRankTab tab) {
		ContextMenu menu = new ContextMenu();
		menu.getItems().add(createCloseTabMenuItem(tab));
		menu.getItems().add(createCloseAllTabMenuItem());
		menu.getItems().add(createCloseOtherTabMenuItem(tab));
		tab.setContextMenu(menu);
	}
	
	/**
	 * Creates close this tab MenuItem for ContextMenu. <br>
	 * It will check if clicked tab if edited. <br>
	 * If it was edited, application display confirmation window. <br>
	 * If it was not edited, application closes tab without confirmation.
	 * @see RuleRankTab
	 * @param tab to with close event will be added.
	 * @return MenuItem with close this action for provided tab
	 */
	private MenuItem createCloseTabMenuItem(RuleRankTab tab) {
		MenuItem closeTab = new MenuItem(labels.get(Labels.TABS_CLOSE_THIS));
		closeTab.setOnAction(event -> {
			
			if(editionChecker.shouldPerformAction(tab.isTabEdited(), Labels.TABS_CLOSE_THIS_CONFIRM)) {
				controller.closeTab(tab);
			}
			
		});
		return closeTab;
	}
	
	/**
	 * Creates close all tabs MenuItem for ContextMenu. <br>
	 * It will check, if any opened tab is in edit mode. <br>
	 * If it is, application ask form confirmation. <br>
	 * If no tab is in edit mode, all tabs are closed without confirmation.
	 * @see RuleRankTab
	 * @return MenuItem with close all tabs action
	 */
	private MenuItem createCloseAllTabMenuItem() {
		MenuItem closeTab = new MenuItem(labels.get(Labels.TABS_CLOSE_ALL));
		closeTab.setOnAction(event -> {
			boolean areTabsEdited = editionChecker.areTabsInEditMode(controller.getUpperTabs().getTabs());
			
			if(editionChecker.shouldPerformAction(areTabsEdited, Labels.TABS_CLOSE_ALL_CONFIRM)) {
				controller.closeTab(new ArrayList<>(controller.getUpperTabs().getTabs()));
			}
			
		});
		return closeTab;
	}
	
	/**
	 * Creates close other tabs MenuItem for ContextMenu. <br>
	 * If any of other tabs are in edit mode, application ask for confirmation. <br>
	 * If none of other tabs are in edit mode, application closes all other tabs.
	 * @see RuleRankTab
	 * @param tab with will not be closed on this action
	 * @return MenuItem with close other tabs action for provided tab
	 */
	private MenuItem createCloseOtherTabMenuItem(Tab tab) {
		MenuItem closeTab = new MenuItem(labels.get(Labels.TABS_CLOSE_OTHERS));
		closeTab.setOnAction(event -> {
			List<Tab> tabsToRemove = new ArrayList<>();
			
			for(Tab tabToRemove : controller.getUpperTabs().getTabs()) {
				if(not(tab.equals(tabToRemove))) {
					tabsToRemove.add(tabToRemove);
				}
			}
			boolean areTabsEdited = editionChecker.areTabsInEditMode(tabsToRemove);
			
			if(editionChecker.shouldPerformAction(areTabsEdited, Labels.TABS_CLOSE_OTHERS_CONFIRM)) {
				controller.closeTab(tabsToRemove);
			}
			
		});
		return closeTab;
	}
	
	
}
