package pl.jowko.jrank.desktop.feature.tabs.upper;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.util.ArrayList;
import java.util.List;

import static pl.jowko.jrank.desktop.utils.BooleanUtils.not;

/**
 * Created by Piotr on 2018-05-18.
 */
class TabsContextMenuCreator {
	
	private TabPane tabPane;
	
	TabsContextMenuCreator(TabPane tabPane) {
		this.tabPane = tabPane;
	}
	
	void create(Tab tab) {
		ContextMenu menu = new ContextMenu();
		menu.getItems().add(createCloseTabMenuItem(tab));
		menu.getItems().add(createCloseAllTabMenuItem());
		menu.getItems().add(createCloseOtherTabMenuItem(tab));
		tab.setContextMenu(menu);
	}
	
	private MenuItem createCloseTabMenuItem(Tab tab) {
		MenuItem closeTab = new MenuItem("Close this tab");
		closeTab.setOnAction(event -> tabPane.getTabs().remove(tab));
		return closeTab;
	}
	
	private MenuItem createCloseAllTabMenuItem() {
		MenuItem closeTab = new MenuItem("Close all tabs");
		closeTab.setOnAction(event -> tabPane.getTabs().clear());
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
			
			tabPane.getTabs().removeAll(tabsToRemove);
		});
		return closeTab;
	}
	
}
