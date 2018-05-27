package pl.jowko.jrank.desktop.feature.rules;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import pl.jowko.jrank.desktop.feature.clipboard.ClipBoardManager;
import pl.jowko.jrank.desktop.feature.clipboard.CsvTableCreator;
import pl.jowko.jrank.desktop.feature.internationalization.Labels;
import pl.jowko.jrank.desktop.feature.internationalization.LanguageService;
import pl.jowko.jrank.desktop.feature.tabs.JRankTab;
import pl.jowko.jrank.desktop.feature.tabs.TabInitializationException;
import pl.jowko.jrank.desktop.feature.tabs.lower.LowerTabsController;
import pl.jowko.jrank.desktop.feature.tabs.upper.UpperTabsController;
import pl.jowko.jrank.desktop.feature.workspace.WorkspaceItem;
import pl.jowko.jrank.desktop.service.DialogsService;
import pl.jowko.jrank.logger.JRankLogger;
import pl.poznan.put.cs.idss.jrs.rules.Rule;
import pl.poznan.put.cs.idss.jrs.rules.RulesContainer;

import java.io.IOException;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static javafx.collections.FXCollections.observableArrayList;
import static pl.jowko.jrank.desktop.feature.clipboard.CsvTableCreator.createTable;

/**
 * Created by Piotr on 2018-05-21.
 * This is controller for rules tab and table.
 * It displays rules table and enables viewing rule statistics.
 */
public class RulesController {
	
	@FXML
	TableView<RuleRow> rulesTable;
	
	private List<Rule> rules;
	private WorkspaceItem workspaceItem;
	private JRankTab rulesTab;
	private StatisticsTab statisticsTab;
	
	private LanguageService labels;
	
	/**
	 * Initializes rule tab.
	 * @param rulesContainer from with rules are extracted
	 * @param workspaceItem with corresponds to file in workspace
	 * @param rulesTab with contains rules table
	 * @throws TabInitializationException when no rules were provided
	 */
	public void initializeRules(RulesContainer rulesContainer, WorkspaceItem workspaceItem, JRankTab rulesTab) throws TabInitializationException {
		this.workspaceItem = workspaceItem;
		this.rulesTab = rulesTab;
		this.rules = new RulesExtractor(rulesContainer).extract();
		labels = LanguageService.getInstance();
		
		if(rules.size() == 0) {
			String msg = labels.get(Labels.RULES_NO_DATA);
			DialogsService.showActionFailedDialog(msg);
			throw new TabInitializationException(msg);
		}
		
		initializeTable();
		initializeCloseEventForRulesTab();
	}
	
	/**
	 * Initialize table content. It creates appropriate number of columns and items.
	 * Also sets listeners to show statistics when user click on or select row
	 */
	private void initializeTable() {
		RuleTableCreator tableCreator = new RuleTableCreator(rules);
		rulesTable.getColumns().addAll(tableCreator.getColumns());
		rulesTable.getItems().addAll(tableCreator.getItems());
		rulesTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		initializeShowStatisticsEvent();
		initializeContextMenu();
	}
	
	/**
	 * Initialize listeners to show statistics tab.
	 * Listener is bind to click and select event.
	 * This means, that statistics tab will be shown when user click on row or select row by click or arrows.
	 */
	private void initializeShowStatisticsEvent() {
		rulesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (nonNull(newSelection)) {
				showRuleStatistics(newSelection.getRule());
			}
		});
		rulesTable.setRowFactory( tv -> {
			TableRow<RuleRow> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if(nonNull(row.getItem()))
					showRuleStatistics(row.getItem().getRule());
			});
			return row ;
		});
	}
	
	/**
	 * Initialize ContextMenu with all actions.
	 */
	private void initializeContextMenu() {
		ContextMenu menu = new ContextMenu();
		MenuItem item = new MenuItem(labels.get(Labels.RULES_COPY_ROWS));
		item.setOnAction(event -> copySelectedRowsAction());
		menu.getItems().add(item);
		rulesTable.setContextMenu(menu);
	}
	
	/**
	 * Copy selected rows action.
	 * This action will copy to user clipboard all selected rows in csv format.
	 * @see CsvTableCreator
	 */
	private void copySelectedRowsAction() {
		ObservableList<ObservableList<String>> items = getSelectedItems();
		if(items.size() == 0) {
			JRankLogger.warn("No rows selected. No rows were copied.");
			return;
		}
		ClipBoardManager.putCsvTable(createTable(rulesTable, items));
	}
	
	/**
	 * Gets list of selected items(RuleRow) and converts each RuleRow to list of string containing cell values.
	 * It will return list of list, where each list represents row and each object in nested list represents cell.
	 * @return selected RuleRow objects flattened to string list
	 */
	private ObservableList<ObservableList<String>> getSelectedItems() {
		ObservableList<RuleRow> rows = rulesTable.getSelectionModel().getSelectedItems();
		ObservableList<ObservableList<String>> items = observableArrayList();
		
		rows.forEach(row -> {
			ObservableList<String> cells = observableArrayList();
			cells.add(Integer.toString(row.getId()));
			cells.addAll(row.getCells());
			items.add(cells);
		});
		
		return items;
	}
	
	/**
	 * Show statistics tab in lower tabs.
	 * If Statistics tab wasn't created, this method will create new tab.
	 * If Statistics tab was created, this method will display new data on existing statistics tab.
	 * It also adds close listener to statistics tab to remove statisticsTab reference. This enables recreating statistics tab if user closed it manually.
	 * @param rule to display
	 */
	private void showRuleStatistics(Rule rule) {
		if(isNull(statisticsTab)) {
			try {
				statisticsTab = new StatisticsTab(rule, labels.get(Labels.STAT_TAB_HEADER) + workspaceItem.getFileName());
				LowerTabsController.getInstance().addTab(statisticsTab);
				statisticsTab.setOnCloseRequest(event -> statisticsTab = null);
			} catch (IOException e) {
				JRankLogger.error("Error when opening rule statistics: " + e);
			}
		} else {
			statisticsTab.getController().showRule(rule);
		}
	}
	
	/**
	 * Initializes close event for rules tab.
	 * If rules tab are closed, statistics tab are also automatically closed.
	 * Force close is called for rulesTab, because firing onClosed event will cancel close of rulesTab.
	 * @see UpperTabsController
	 */
	private void initializeCloseEventForRulesTab() {
		rulesTab.setOnClosed(event -> {
			LowerTabsController.getInstance().closeTab(statisticsTab);
			UpperTabsController.getInstance().forceCloseTab(rulesTab);
		});
	}
	
}
