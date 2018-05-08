package pl.jowko.jrank.desktop.feature.workspace;

import javafx.scene.control.TreeCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import pl.jowko.jrank.desktop.feature.tabs.upper.UpperTabsController;
import pl.jowko.jrank.logger.JRankLogger;

import static pl.jowko.jrank.desktop.utils.BooleanUtils.not;

/**
 * Created by Piotr on 2018-04-29.
 */
class ClickEventHandler {
	
	void onItemClickEvent(MouseEvent event, TreeCell<WorkspaceItem> cell) {
		if(!event.getButton().equals(MouseButton.PRIMARY) || event.getClickCount() != 2 || cell.isEmpty()) {
			return;
		}
		
		WorkspaceItem workspaceItem = cell.getTreeItem().getValue();
		if(not(isFileTypeValid(workspaceItem)))
			return;
		
		UpperTabsController.getInstance().createTab(workspaceItem);
		
	}
	
	private boolean isFileTypeValid(WorkspaceItem workspaceItem) {
		FileType itemType = workspaceItem.getFileType();
		
		if(FileType.ROOT.equals(itemType) || FileType.FOLDER.equals(itemType)) {
			// do nothing
			return false;
		}
		
		if(FileType.UNKNOWN.equals(itemType)) {
			JRankLogger.warn("File: [" + workspaceItem.getFilePath() + "] was not recognized");
			return false;
		}
		return true;
	}


}
