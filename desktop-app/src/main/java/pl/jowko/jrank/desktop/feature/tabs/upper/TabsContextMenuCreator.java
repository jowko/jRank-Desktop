package pl.jowko.jrank.desktop.feature.tabs.upper;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import pl.jowko.jrank.desktop.feature.internationalization.Labels;
import pl.jowko.jrank.desktop.feature.internationalization.LanguageService;
import pl.jowko.jrank.desktop.feature.tabs.JRankTab;
import pl.jowko.jrank.desktop.service.DialogsService;

import java.util.ArrayList;
import java.util.List;

import static pl.jowko.jrank.desktop.utils.BooleanUtils.not;

/**
 * Created by Piotr on 2018-05-18.
 * This class creates context menus for tab headers.
 * This actions must be created for each tab with are added to TabPane.
 */
class TabsContextMenuCreator {
	
	private TabPane tabPane;
	private LanguageService labels;
	
	/**
	 * Create instance of this class
	 * @param tabPane on with context menus will be added
	 */
	TabsContextMenuCreator(TabPane tabPane) {
		this.tabPane = tabPane;
		labels = LanguageService.getInstance();
	}
	
	/**
	 * Create ContextMenu for provided tab. Three actions are added:
	 * - Close this tab
	 * - Close all tabs
	 * - Close all except this
	 * All close actions checks, if tabs to close were edited and ask for confirmation in such case.
	 * @param tab to with ContextMenu will be added
	 */
	void create(JRankTab tab) {
		ContextMenu menu = new ContextMenu();
		menu.getItems().add(createCloseTabMenuItem(tab));
		menu.getItems().add(createCloseAllTabMenuItem());
		menu.getItems().add(createCloseOtherTabMenuItem(tab));
		tab.setContextMenu(menu);
	}
	
	/**
	 * Creates close this tab MenuItem for ContextMenu.
	 * It will check if clicked tab if edited.
	 * If it was edited, application display confirmation window.
	 * If it was not edited, application closes tab without confirmation.
	 * @see JRankTab
	 * @param tab to with close event will be added.
	 * @return MenuItem with close this action for provided tab
	 */
	private MenuItem createCloseTabMenuItem(JRankTab tab) {
		MenuItem closeTab = new MenuItem(labels.get(Labels.TABS_CLOSE_THIS));
		closeTab.setOnAction(event -> {
			
			if(shouldPerformAction(tab.isTabEdited(), Labels.TABS_CLOSE_THIS_CONFIRM)) {
				tabPane.getTabs().remove(tab);
			}
			
		});
		return closeTab;
	}
	
	/**
	 * Creates close all tabs MenuItem for ContextMenu.
	 * It will check, if any opened tab is in edit mode.
	 * If it is, application ask form confirmation.
	 * If no tab is in edit mode, all tabs are closed without confirmation.
	 * @see JRankTab
	 * @return MenuItem with close all tabs action
	 */
	private MenuItem createCloseAllTabMenuItem() {
		MenuItem closeTab = new MenuItem(labels.get(Labels.TABS_CLOSE_ALL));
		closeTab.setOnAction(event -> {
			long editedTabsCount = getEditedTabsCount(tabPane.getTabs());
			
			if(shouldPerformAction(editedTabsCount > 0, Labels.TABS_CLOSE_ALL_CONFIRM)) {
				tabPane.getTabs().clear();
			}
			
		});
		return closeTab;
	}
	
	/**
	 * Creates close other tabs MenuItem for ContextMenu.
	 * If any of other tabs are in edit mode, application ask for confirmation.
	 * If none of other tabs are in edit mode, application closes all other tabs.
	 * @see JRankTab
	 * @param tab with will not be closed on this action
	 * @return MenuItem with close other tabs action for provided tab
	 */
	private MenuItem createCloseOtherTabMenuItem(Tab tab) {
		MenuItem closeTab = new MenuItem(labels.get(Labels.TABS_CLOSE_OTHERS));
		closeTab.setOnAction(event -> {
			List<Tab> tabsToRemove = new ArrayList<>();
			
			for(Tab tabToRemove : tabPane.getTabs()) {
				if(not(tab.equals(tabToRemove))) {
					tabsToRemove.add(tabToRemove);
				}
			}
			
			long editedTabsCount = getEditedTabsCount(tabsToRemove);
			
			if(shouldPerformAction(editedTabsCount > 0, Labels.TABS_CLOSE_OTHERS_CONFIRM)) {
				tabPane.getTabs().removeAll(tabsToRemove);
			}
			
		});
		return closeTab;
	}
	
	/**
	 * Display confirmation dialog if tab edition occurred.
	 * @param isEditModeOn boolean condition indicating that confirmation dialog should be displayed
	 * @param labelCode with will be displayed on confirmation dialog
	 * @return true is user confirm action or no edit action occurred, false otherwise
	 */
	private boolean shouldPerformAction(boolean isEditModeOn, String labelCode) {
		if(isEditModeOn)
			return DialogsService.showConfirmationDialog(labels.get(labelCode));
		return true;
	}
	
	private long getEditedTabsCount(List<Tab> tabs) {
		return tabs.stream()
				.filter(tab -> {
					JRankTab jRankTab = (JRankTab) tab;
					return jRankTab.isTabEdited();
				})
				.count();
	}
	
}
