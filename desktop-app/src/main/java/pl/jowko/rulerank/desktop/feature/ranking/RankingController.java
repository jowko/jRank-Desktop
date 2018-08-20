package pl.jowko.rulerank.desktop.feature.ranking;

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
import pl.jowko.rulerank.desktop.feature.learningtable.UnknownFieldValidator;
import pl.jowko.rulerank.desktop.feature.tabs.TabInitializationException;
import pl.jowko.rulerank.desktop.service.DialogsService;
import pl.jowko.rulerank.logger.RuleRankLogger;
import pl.poznan.put.cs.idss.jrs.core.mem.MemoryContainer;

import static javafx.collections.FXCollections.observableArrayList;
import static pl.jowko.rulerank.desktop.feature.clipboard.CsvTableCreator.createTable;
import static pl.jowko.rulerank.desktop.utils.BooleanUtils.not;

/**
 * Controller for ranking tab and table. <br>
 * It creates table from provided ranking and MemoryContainer. <br>
 *  <br>
 * Created by Piotr on 2018-05-22.
 */
public class RankingController {
	
	@FXML
	TableView<RankingRow> rankingTable;
	
	private LanguageService labels;
	
	/**
	 * Initializes ranking from provided data.
	 * @param rankingFileContent from .ranking file
	 * @param isfTable containing learning data table
	 * @throws TabInitializationException when something goes wrong with initializing tab
	 */
	public void initializeRanking(String rankingFileContent, MemoryContainer isfTable) throws TabInitializationException {
		LearningTable table = new LearningTable(isfTable);
		labels = LanguageService.getInstance();
		checkIfTableHasUnknownFields(table);
		RankingTableCreator creator = new RankingTableCreator(rankingFileContent, table);
		rankingTable.getColumns().addAll(creator.getColumns());
		rankingTable.getItems().addAll(creator.getItems());
		rankingTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		initializeContextMenu();
	}
	
	/**
	 * If table contains unknown fields, it means that ranking is not up to date with learning table. <br>
	 * To generate ranking, all non decision fields in ranking should be known.
	 * @param table with is loaded from isf file and represents data from experiment
	 * @throws TabInitializationException when table contains unknown fields
	 */
	private void checkIfTableHasUnknownFields(LearningTable table) throws TabInitializationException {
		UnknownFieldValidator validator = new UnknownFieldValidator(table);
		
		if(not(validator.isValid())) {
			String msg = labels.get(Labels.RANKING_UNKNOWN_FIELDS);
			DialogsService.showActionFailedDialog(msg);
			
			throw new TabInitializationException(msg);
		}
	}
	
	/**
	 * Initialize ContextMenu with all actions.
	 */
	private void initializeContextMenu() {
		ContextMenu menu = new ContextMenu();
		MenuItem item = new MenuItem(labels.get(Labels.RANKING_COPY_ROWS));
		item.setOnAction(event -> copySelectedRowsAction());
		menu.getItems().add(item);
		rankingTable.setContextMenu(menu);
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
		ClipBoardManager.putCsvTable(createTable(rankingTable, items));
	}
	
	/**
	 * Gets list of selected items(RankingRow) and converts each RankingRow to list of string containing cell values. <br>
	 * It will return list of list, where each list represents row and each object in nested list represents cell.
	 * @return selected RankingRow objects flattened to string list
	 */
	private ObservableList<ObservableList<String>> getSelectedItems() {
		ObservableList<RankingRow> rows = rankingTable.getSelectionModel().getSelectedItems();
		ObservableList<ObservableList<String>> items = observableArrayList();
		
		rows.forEach(row -> {
			ObservableList<String> cells = observableArrayList();
			cells.add(Integer.toString(row.getPosition()));
			cells.add(Double.toString(row.getEvaluation()));
			cells.addAll(row.getCells());
			items.add(cells);
		});
		
		return items;
	}
	
}
