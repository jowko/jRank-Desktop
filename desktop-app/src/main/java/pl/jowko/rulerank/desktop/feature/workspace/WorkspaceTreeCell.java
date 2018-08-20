package pl.jowko.rulerank.desktop.feature.workspace;

import javafx.scene.control.TreeCell;

/**
 * This class extends base TreeCell to set correct text for item in tree. <br>
 *  <br>
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