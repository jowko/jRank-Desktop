package pl.jowko.rulerank.desktop.feature.learningtable;

import javafx.collections.ObservableList;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import pl.jowko.rulerank.desktop.feature.internationalization.Labels;
import pl.jowko.rulerank.desktop.feature.internationalization.LanguageService;
import pl.poznan.put.cs.idss.jrs.types.Field;

/**
 * This class creates ContextMenu for Learning table. <br>
 * Actions are performed by LearningTableActions class <br>
 *  <br>
 * Created by Piotr on 2018-05-19.
 * @see LearningTableActions
 * @see LearningTableController
 */
class TableContextMenuCreator {
	
	private TableView<ObservableList<Field>> table;
	private LearningTableActions actions;
	private LanguageService labels;
	
	/**
	 * Creates instance of this class
	 * @param table on with ContextMenu will be added
	 * @param tableActions with will be used to execute actions
	 */
	TableContextMenuCreator(TableView<ObservableList<Field>> table, LearningTableActions tableActions) {
		this.table = table;
		this.actions = tableActions;
		labels = LanguageService.getInstance();
	}
	
	/**
	 * Create ContextMenu for learning table. <br>
	 * For now, following actions are implemented: <br>
	 * - Create example - adds new empty row to table <br>
	 * - Customize attributes - opens edit attribute dialog for columns in table <br>
	 * - Add attribute - opens add attribute dialog to add new column to table <br>
	 * - Remove selected examples - removes selected rows from table <br>
	 * - Copy selected rows - Copies selected rows to user clipboard in CSV format
	 */
	void create() {
		ContextMenu menu = new ContextMenu();
		
		menu.getItems().add(createAddExampleItem());
		menu.getItems().add(createCustomizeAttributesItem());
		menu.getItems().add(createAddAttributeItem());
		menu.getItems().add(createRemoveExamplesItem());
		menu.getItems().add(createCopyRowAction());
		
		table.setContextMenu(menu);
	}
	
	private MenuItem createAddExampleItem() {
		MenuItem item = new MenuItem(labels.get(Labels.LEARN_TABLE_ADD_EXAMPLE));
		item.setOnAction(event -> actions.addExampleAction());
		return item;
	}
	
	private MenuItem createRemoveExamplesItem() {
		MenuItem item = new MenuItem(labels.get(Labels.LEARN_TABLE_REMOVE_EXAMPLES));
		item.setOnAction(event -> actions.removeSelectedExamplesAction());
		return item;
	}
	
	private MenuItem createCustomizeAttributesItem() {
		MenuItem item = new MenuItem(labels.get(Labels.LEARN_TABLE_CUSTOMIZE_ATTRIBUTES));
		item.setOnAction(event -> actions.customizeAttributes());
		return item;
	}
	
	private MenuItem createAddAttributeItem() {
		MenuItem item = new MenuItem(labels.get(Labels.LEARN_TABLE_ADD_ATTRIBUTE));
		item.setOnAction(event -> actions.addNewAttribute());
		return item;
	}
	
	private MenuItem createCopyRowAction() {
		MenuItem item = new MenuItem(labels.get(Labels.LEARN_TABLE_COPY_ROWS));
		item.setOnAction(event -> actions.copySelectedRowsAction());
		return item;
	}
	
}
