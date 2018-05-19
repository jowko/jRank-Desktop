package pl.jowko.jrank.desktop.feature.learningtable;

import javafx.collections.ObservableList;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import pl.poznan.put.cs.idss.jrs.types.Field;

/**
 * Created by Piotr on 2018-05-19.
 */
class TableContextMenuCreator {
	
	private TableView<ObservableList<Field>> table;
	private LearningTableActions actions;
	
	TableContextMenuCreator(TableView<ObservableList<Field>> table, LearningTableActions tableActions) {
		this.table = table;
		this.actions = tableActions;
	}
	
	void create() {
		ContextMenu menu = new ContextMenu();
		
		menu.getItems().add(createAddExampleItem());
		menu.getItems().add(createCustomizeAttributesItem());
		menu.getItems().add(createAddAttributeItem());
		menu.getItems().add(createRemoveExamplesItem());
		
		table.setContextMenu(menu);
	}
	
	private MenuItem createAddExampleItem() {
		MenuItem item = new MenuItem("Add example");
		item.setOnAction(event -> actions.addExampleAction());
		return item;
	}
	
	private MenuItem createRemoveExamplesItem() {
		MenuItem item = new MenuItem("Remove selected examples");
		item.setOnAction(event -> actions.removeSelectedExamplesAction());
		return item;
	}
	
	private MenuItem createCustomizeAttributesItem() {
		MenuItem item = new MenuItem("Customize attributes");
		item.setOnAction(event -> actions.customizeAttributes());
		return item;
	}
	
	private MenuItem createAddAttributeItem() {
		MenuItem item = new MenuItem("Add attribute");
		item.setOnAction(event -> actions.addNewAttribute());
		return item;
	}
	
}
