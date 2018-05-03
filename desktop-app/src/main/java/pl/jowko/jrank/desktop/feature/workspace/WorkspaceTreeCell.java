package pl.jowko.jrank.desktop.feature.workspace;

import javafx.scene.control.TreeCell;

/**
 * Created by Piotr on 2018-04-21.
 */
class WorkspaceTreeCell extends TreeCell<WorkspaceItem> {
	
	@Override
	public void updateItem(WorkspaceItem item, boolean empty) {
		super.updateItem(item, empty) ;
		if (empty) {
			setText(null);
		} else {
			setText(item.getFileName());
		}
	}
	
}