package pl.jowko.jrank.desktop.feature.workspace;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeView;

import pl.jowko.jrank.desktop.feature.internationalization.Labels;
import pl.jowko.jrank.desktop.feature.internationalization.LanguageService;

/**
 * Created by Piotr on 2018-06-10
 * This class creates ContextMenu for workspace tree.
 * Are actions are performed on selected workspace item.
 * @see ContextMenuActions
 */
class ContextMenuCreator {
	
	private ContextMenuActions actions;
	private LanguageService labels;
	
	ContextMenuCreator() {
		labels = LanguageService.getInstance();
	}
	
	/**
	 * This method creates class to handle actions and creates context menu.
	 * Current implemented actions:
	 *  - Delete - It deletes selected item and its children. It ask for confirmation first.
	 *  - Copy   - It copies selected item to user ClipBoard
	 *  - Paste  - It pastes item from clipboard to selected directory
	 *  - Cut    - It cuts selected item to user ClipBoard
	 *  - Rename - It renames item with new name provided by user
	 *  - Add properties - It adds new properties file to selected directory
	 *  - Add isf        - It adds new isf file to selected directory
	 * @param treeView on with actions will be performed
	 * @see ContextMenuActions
	 */
	void createContextMenu(TreeView<WorkspaceItem> treeView) {
		actions = new ContextMenuActions(treeView);
		ContextMenu menu = new ContextMenu();
		
		menu.getItems().add(createDeleteAction());
		menu.getItems().add(createCopyAction());
		menu.getItems().add(createPasteAction());
		menu.getItems().add(createCutAction());
		menu.getItems().add(createRenameAction());
		menu.getItems().add(createAddPropertiesAction());
		menu.getItems().add(createAddIsfFileAction());
		
		treeView.setContextMenu(menu);
	}
	
	private MenuItem createDeleteAction() {
		MenuItem item = new MenuItem(labels.get(Labels.WORK_MENU_DELETE));
		item.setOnAction(event ->
			actions.deleteItemAction()
		);
		return item;
	}
	
	private MenuItem createCopyAction() {
		MenuItem item = new MenuItem(labels.get(Labels.WORK_MENU_COPY));
		item.setOnAction(event ->
				actions.copyItemAction()
		);
		return item;
	}
	
	private MenuItem createPasteAction() {
		MenuItem item = new MenuItem(labels.get(Labels.WORK_MENU_PASTE));
		item.setOnAction(event ->
				actions.pasteItemAction()
		);
		return item;
	}
	
	private MenuItem createCutAction() {
		MenuItem item = new MenuItem(labels.get(Labels.WORK_MENU_CUT));
		item.setOnAction(event ->
				actions.cutItemAction()
		);
		return item;
	}
	
	private MenuItem createAddPropertiesAction() {
		MenuItem item = new MenuItem(labels.get(Labels.WORK_MENU_ADD_PROPERTIES));
		item.setOnAction(event ->
				actions.addPropertiesFileAction()
		);
		return item;
	}
	
	private MenuItem createAddIsfFileAction() {
		MenuItem item = new MenuItem(labels.get(Labels.WORK_MENU_ADD_ISF));
		item.setOnAction(event ->
				actions.addIsfFileAction()
		);
		return item;
	}
	
	private MenuItem createRenameAction() {
		MenuItem item = new MenuItem(labels.get(Labels.WORK_MENU_RENAME));
		item.setOnAction(event ->
				actions.renameItemAction()
		);
		return item;
	}
	
}
