package pl.jowko.jrank.desktop.feature.rules;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import pl.jowko.jrank.desktop.feature.tabs.upper.JRankTab;
import pl.jowko.jrank.desktop.feature.workspace.WorkspaceItem;
import pl.jowko.jrank.logger.JRankLogger;
import pl.poznan.put.cs.idss.jrs.rules.Rule;
import pl.poznan.put.cs.idss.jrs.rules.RulesContainer;

import java.util.List;

/**
 * Created by Piotr on 2018-05-21.
 * This is controller for rules tab and table.
 * It displays rules table and enables viewing rule statistics.
 */
public class RulesController {
	
	@FXML
	TableView<RuleRow> rulesTable;
	
	private RulesContainer container;
	private List<Rule> rules;
	private WorkspaceItem workspaceItem;
	private JRankTab tab;
	
	/**
	 * Initializes rule tab.
	 * @param rulesContainer from with rules are extracted
	 * @param workspaceItem with corresponds to file in workspace
	 * @param rulesTab with contains rules table
	 */
	public void initializeRules(RulesContainer rulesContainer, WorkspaceItem workspaceItem, JRankTab rulesTab) {
		this.container = rulesContainer;
		this.workspaceItem = workspaceItem;
		this.tab = rulesTab;
		this.rules = new RulesExtractor(rulesContainer).extract();
		
		if(rules.size() == 0) {
			JRankLogger.warn("No rules to display. Check if rules file is correct.");
			rulesTable.setVisible(false);
			rulesTable.setMaxSize(0, 0);
			return;
		}
		
		initializeTable();
	}
	
	/**
	 * Initialize table content. It creates appropriate number of columns and items.
	 */
	private void initializeTable() {
		RuleTableCreator tableCreator = new RuleTableCreator(rules);
		rulesTable.getColumns().addAll(tableCreator.getColumns());
		rulesTable.getItems().addAll(tableCreator.getItems());
	}
	
}
