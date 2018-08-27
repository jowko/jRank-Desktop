package pl.jowko.rulerank.desktop.feature.pct;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import pl.jowko.rulerank.desktop.feature.clipboard.ClipBoardManager;
import pl.jowko.rulerank.desktop.feature.clipboard.CsvTableCreator;
import pl.jowko.rulerank.desktop.feature.internationalization.Labels;
import pl.jowko.rulerank.desktop.feature.internationalization.LanguageService;
import pl.jowko.rulerank.desktop.feature.learningtable.LearningTable;
import pl.jowko.rulerank.logger.RuleRankLogger;
import pl.poznan.put.cs.idss.jrs.core.mem.MemoryContainer;
import pl.poznan.put.cs.idss.jrs.types.Field;

import static javafx.collections.FXCollections.observableArrayList;
import static pl.jowko.rulerank.desktop.feature.clipboard.CsvTableCreator.createTable;

/**
 * Controller for Partial Pairwise Comparison Table(PCT) <br>
 *  <br>
 * Created by Piotr on 2018-05-28
 */
public class PCTController {
	
	@FXML
	TableView<ObservableList<Field>> pctTable;
	
	private LanguageService labels;
	
	/**
	 * Initialize Partial Pairwise Comparison Table(PCT). <br>
	 * If will convert provided MemoryContainer to Learning table and initialize UI table.
	 * @param isfTable with will be displayed on UI table
	 */
	public void initializeRanking(MemoryContainer isfTable) {
		LearningTable table = new LearningTable(isfTable);
		labels = LanguageService.getInstance();
		initializeTable(table);
	}
	
	/**
	 * Create UI table with data extracted from learning table.
	 * @param table with will be displayed on UI table
	 */
	private void initializeTable(LearningTable table) {
		PCTCreator creator = new PCTCreator(table);
		pctTable.getColumns().addAll(creator.getColumns());
		pctTable.getItems().addAll(creator.getItems());
		pctTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		initializeContextMenu();
	}
	
	/**
	 * Initialize ContextMenu with all actions.
	 */
	private void initializeContextMenu() {
		ContextMenu menu = new ContextMenu();
		MenuItem item = new MenuItem(labels.get(Labels.PCT_COPY_ROWS));
		item.setOnAction(event -> copySelectedRowsAction());
		menu.getItems().add(item);
		pctTable.setContextMenu(menu);
	}
	
	/**
	 * Copy selected rows action. <br>
	 * This action will copy to user clipboard all selected rows in csv format.
	 * @see CsvTableCreator
	 */
	private void copySelectedRowsAction() {
		ObservableList<ObservableList<String>> items = getSelectedItems();
		if(items.size() == 0) {
			RuleRankLogger.warn("No rows selected. No rows were copied.");
			return;
		}
		ClipBoardManager.putCsvTable(createTable(pctTable, items));
	}
	
	/**
	 * Gets list of selected items(list of fields) and converts each item to list of string containing cell values. <br>
	 * It will return list of list, where each list represents row and each object in nested list represents cell. <br>
	 * @return selected items in String list format
	 */
	private ObservableList<ObservableList<String>> getSelectedItems() {
		ObservableList<ObservableList<Field>> rows = pctTable.getSelectionModel().getSelectedItems();
		ObservableList<ObservableList<String>> items = observableArrayList();
		
		rows.forEach(row -> {
			ObservableList<String> cells = observableArrayList();
			row.forEach(field -> cells.add(field.toString()));
			items.add(cells);
		});
		
		return items;
	}
	
}
