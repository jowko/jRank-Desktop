package pl.jowko.jrank.desktop.feature.tabs.upper;

import javafx.scene.Parent;
import javafx.scene.control.Tab;
import pl.jowko.jrank.desktop.ResourceLoader;
import pl.jowko.jrank.desktop.feature.workspace.WorkspaceItem;

import java.io.IOException;

/**
 * Created by Piotr on 2018-04-29.
 * This class contains functionality common for tabs in this application.
 */
public abstract class JRankTab extends Tab {
	
	WorkspaceItem workspaceItem;
	
	private boolean tabEdited;
	
	/**
	 * This method returns path to fxml resource.
	 * @return path to fxml resource
	 */
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
	
	public boolean isTabEdited() {
		return tabEdited;
	}
	
	/**
	 * Inform tab about its edition state.
	 * When tab is edited, * character is added on beginning of name.
	 * If tab edition is canceled, * character is removed from beginning of tab name when this character was added before.
	 * @param tabEdited with inform tab about edition state
	 */
	public void setTabEdited(boolean tabEdited) {
		renameTabWhenEdited(tabEdited);
		this.tabEdited = tabEdited;
	}
	
	private void renameTabWhenEdited(boolean tabEdited) {
		if(tabEdited && this.tabEdited)
			return;
		
		if(tabEdited) {
			setText('*' + getText());
		} else if(this.tabEdited) {
			String newText = getText().replaceFirst("\\*", "");
			setText(newText);
		}
	}
	
}