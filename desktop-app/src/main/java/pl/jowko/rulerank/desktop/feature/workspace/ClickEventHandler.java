package pl.jowko.rulerank.desktop.feature.workspace;

import javafx.scene.control.TreeCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import pl.jowko.rulerank.desktop.feature.tabs.upper.UpperTabsController;

import static pl.jowko.rulerank.desktop.utils.BooleanUtils.not;

/**
 * Created by Piotr on 2018-04-29.
 * This handler handles click event on workspace item in workspace tree.
 * It opens file if its item was clicked twice.
 * @see TreeBuilder
 * @see UpperTabsController
 */
class ClickEventHandler {
	
	/**
	 * Handle event fired when user double clicks on workspace tree item.
	 * It opens tab with file content or do nothing if user clicked on root node(workspace main directory) or catalog.
	 * @param event with is fired by user action
	 * @param cell on with this event was performed
	 */
	void onItemClickEvent(MouseEvent event, TreeCell<WorkspaceItem> cell) {
		if(!event.getButton().equals(MouseButton.PRIMARY) || event.getClickCount() != 2 || cell.isEmpty()) {
			return;
		}
		
		WorkspaceItem workspaceItem = cell.getTreeItem().getValue();
		if(not(isFileTypeValid(workspaceItem)))
			return;
		
		UpperTabsController.getInstance().createTab(workspaceItem);
	}
	
	/**
	 * Check if file type is valid for file opening.
	 * If user clicked on catalog or root node, this method will return false.
	 * @param workspaceItem on with user clicked
	 * @return true if item can be opened in tab
	 */
	private boolean isFileTypeValid(WorkspaceItem workspaceItem) {
		FileType itemType = workspaceItem.getFileType();
		
		return !FileType.ROOT.equals(itemType) && !FileType.DIRECTORY.equals(itemType);
	}


}
