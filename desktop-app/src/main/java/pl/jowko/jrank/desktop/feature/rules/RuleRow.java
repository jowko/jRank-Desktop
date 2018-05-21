package pl.jowko.jrank.desktop.feature.rules;

import pl.poznan.put.cs.idss.jrs.rules.Rule;

import java.util.List;

/**
 * Created by Piotr on 2018-05-21.
 * This class contains data for row in rule table.
 * Cells list is used to display text in columns.
 */
class RuleRow {
	
	private List<String> cells;
	private Rule rule;
	
	/**
	 * Creates instance of this class.
	 * @param cells with will be used to display data in table. Cells count should be equal to columns count in rules table.
	 * @param rule related with this row. Used to display rule statistics.
	 */
	RuleRow(List<String> cells, Rule rule) {
		this.cells = cells;
		this.rule = rule;
	}
	
	List<String> getCells() {
		return cells;
	}
	
	Rule getRule() {
		return rule;
	}
	
}
