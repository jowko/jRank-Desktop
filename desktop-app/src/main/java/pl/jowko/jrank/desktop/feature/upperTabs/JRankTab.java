package pl.jowko.jrank.desktop.feature.upperTabs;

import javafx.scene.Parent;
import javafx.scene.control.Tab;
import pl.jowko.jrank.desktop.feature.workspace.WorkspaceItem;

/**
 * Created by Piotr on 2018-04-29.
 */
class JRankTab extends Tab {
	
	WorkspaceItem workspaceItem;
	
	void initializeTab(WorkspaceItem workspaceItem, Parent tabContent, String tabText) {
		this.workspaceItem = workspaceItem;
		setText(tabText);
		super.setContent(tabContent);
	}
	
	
}
