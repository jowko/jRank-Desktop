package pl.jowko.jrank.desktop.feature.rules;

import javafx.fxml.FXML;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import pl.jowko.jrank.desktop.feature.internationalization.Labels;
import pl.jowko.jrank.desktop.feature.internationalization.LanguageService;
import pl.jowko.jrank.desktop.feature.tabs.JRankTab;
import pl.jowko.jrank.desktop.feature.tabs.lower.LowerTabsController;
import pl.jowko.jrank.desktop.feature.workspace.WorkspaceItem;
import pl.jowko.jrank.logger.JRankLogger;
import pl.poznan.put.cs.idss.jrs.rules.Rule;
import pl.poznan.put.cs.idss.jrs.rules.RulesContainer;

import java.io.IOException;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

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
	 */
	public void initializeRules(RulesContainer rulesContainer, WorkspaceItem workspaceItem, JRankTab rulesTab) {
		this.workspaceItem = workspaceItem;
		this.rulesTab = rulesTab;
		this.rules = new RulesExtractor(rulesContainer).extract();
		labels = LanguageService.getInstance();
		
		if(rules.size() == 0) {
			JRankLogger.warn("No rules to display. Check if rules file is correct.");
			rulesTable.setVisible(false);
			rulesTable.setMaxSize(0, 0);
			return;
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
		initializeShowStatisticsEvent();
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
			row.setOnMouseClicked(event ->
					showRuleStatistics(row.getItem().getRule())
			);
			return row ;
		});
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
	 */
	private void initializeCloseEventForRulesTab() {
		rulesTab.setOnCloseRequest(event ->
			LowerTabsController.getInstance().removeTab(statisticsTab)
		);
	}
	
}
