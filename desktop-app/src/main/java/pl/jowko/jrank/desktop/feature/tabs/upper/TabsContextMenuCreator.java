package pl.jowko.jrank.desktop.feature.tabs.upper;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import pl.jowko.jrank.desktop.service.DialogsService;

import java.util.ArrayList;
import java.util.List;

import static pl.jowko.jrank.desktop.utils.BooleanUtils.not;

/**
 * Created by Piotr on 2018-05-18.
 * This class creates context menus for tab headers.
 */
class TabsContextMenuCreator {
	
	private TabPane tabPane;
	
	/**
	 * Create instance of this class
	 * @param tabPane on with context menus will be added
	 */
	TabsContextMenuCreator(TabPane tabPane) {
		this.tabPane = tabPane;
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
	
	private MenuItem createCloseTabMenuItem(JRankTab tab) {
		MenuItem closeTab = new MenuItem("Close this tab");
		closeTab.setOnAction(event -> {
			
			if(tab.isTabEdited()) {
				boolean isActionConfirmed = DialogsService.showConfirmationDialog("Do you want to close this tab? You loose unsaved changes.");
				if(not(isActionConfirmed))
					return;
			}
			
			tabPane.getTabs().remove(tab);
		});
		return closeTab;
	}
	
	private MenuItem createCloseAllTabMenuItem() {
		MenuItem closeTab = new MenuItem("Close all tabs");
		closeTab.setOnAction(event -> {
			long editedTabsCount = getEditedTabsCount(tabPane.getTabs());
			
			if(editedTabsCount > 0) {
				boolean isActionConfirmed = DialogsService.showConfirmationDialog("At least one tab have unsaved changes. Do you want to close all tabs and loose unsaved changes?");
				if(not(isActionConfirmed))
					return;
			}
			
			tabPane.getTabs().clear();
		});
		return closeTab;
	}
	
	private MenuItem createCloseOtherTabMenuItem(Tab tab) {
		MenuItem closeTab = new MenuItem("Close all except this");
		closeTab.setOnAction(event -> {
			List<Tab> tabsToRemove = new ArrayList<>();
			
			for(Tab tabToRemove : tabPane.getTabs()) {
				if(not(tab.equals(tabToRemove))) {
					tabsToRemove.add(tabToRemove);
				}
			}
			
			long editedTabsCount = getEditedTabsCount(tabsToRemove);
			
			if(editedTabsCount > 0) {
				boolean isActionConfirmed = DialogsService.showConfirmationDialog("At least one tab have unsaved changes. Do you want to close this tabs and loose unsaved changes?");
				if(not(isActionConfirmed))
					return;
			}
			
			tabPane.getTabs().removeAll(tabsToRemove);
		});
		return closeTab;
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
