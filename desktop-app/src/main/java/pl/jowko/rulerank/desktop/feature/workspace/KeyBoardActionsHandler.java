package pl.jowko.rulerank.desktop.feature.workspace;

import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

/**
 * This class initializes keyboard shortcuts actions for workspace actions. <br>
 *  <br>
 * Created by Piotr on 2018-06-10
 * @see ContextMenuActions
 */
class KeyBoardActionsHandler {
	
	private final KeyCombination copyCombination = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN);
	private final KeyCombination pasteCombination = new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN);
	private final KeyCombination cutCombination = new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN);
	private final KeyCombination renameCombination = new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN);
	
	private ContextMenuActions actions;
	
	KeyBoardActionsHandler(ContextMenuActions actions) {
		this.actions = actions;
	}
	
	/**
	 * Initializes handler for processing keyboard events. <br>
	 * Current supported keys: <br>
	 * - Delete - deletes item from workspace tree <br>
	 * - Copy   - copies item from workspace tree <br>
	 * - Paste  - paste item into workspace tree <br>
	 * - Cut    - cuts item from workspace tree <br>
	 * - Rename - renames item from workspace tree
	 * @param treeView on with handler will be configured
	 */
	void initKeyBoardActions(TreeView<WorkspaceItem> treeView) {
		treeView.setOnKeyPressed(event -> {
			if(KeyCode.DELETE == event.getCode()) {
				actions.deleteItemAction();
				return;
			}
			if(copyCombination.match(event)) {
				actions.copyItemAction();
				return;
			}
			if(pasteCombination.match(event)) {
				actions.pasteItemAction();
				return;
			}
			if(cutCombination.match(event)) {
				actions.cutItemAction();
				return;
			}
			if(renameCombination.match(event)) {
				actions.renameItemAction();
				return;
			}
		});
	}
	
}
