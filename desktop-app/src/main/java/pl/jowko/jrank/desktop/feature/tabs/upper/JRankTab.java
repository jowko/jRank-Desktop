package pl.jowko.jrank.desktop.feature.tabs.upper;

import javafx.scene.Parent;
import javafx.scene.control.Tab;
import pl.jowko.jrank.desktop.ResourceLoader;
import pl.jowko.jrank.desktop.feature.workspace.WorkspaceItem;

import java.io.IOException;

/**
 * Created by Piotr on 2018-04-29.
 */
abstract class JRankTab extends Tab {
	
	WorkspaceItem workspaceItem;
	
	abstract String getResourceName();
	
	<T> T initializeTabAndGetController(WorkspaceItem workspaceItem, String tabText) throws IOException {
		this.workspaceItem = workspaceItem;
		setText(tabText);
		
		ResourceLoader loader = new ResourceLoader(getResourceName());
		Parent tabContent = loader.load();
		tabContent.getStylesheets().add("common.css");
		super.setContent(tabContent);
		
		return loader.getController();
	}
	
}
