package pl.jowko.jrank.desktop.feature.workspace;

import javafx.scene.control.TreeCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import pl.jowko.jrank.desktop.feature.upperTabs.UpperTabsController;

/**
 * Created by Piotr on 2018-04-29.
 */
class ClickEventHandler {
	
	void onItemClickEvent(MouseEvent event, TreeCell<WorkspaceItem> cell) {
		if(!event.getButton().equals(MouseButton.PRIMARY) || event.getClickCount() != 2 || cell.isEmpty()) {
			return;
		}
		
		WorkspaceItem workspaceItem = cell.getTreeItem().getValue();
		FileType itemType = workspaceItem.getFileType();
		
		if(FileType.ROOT.equals(itemType) || FileType.FOLDER.equals(itemType)) {
			// do nothing
			return;
		}
		
		if(FileType.JRANK_SETTINGS.equals(itemType)) {
			// others types not supported yet
			UpperTabsController.getInstance().createTab(workspaceItem);
		}
		
		System.out.println(workspaceItem); //TODO remove this later
		
	}


}
