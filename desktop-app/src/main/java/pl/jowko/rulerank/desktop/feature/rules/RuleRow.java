package pl.jowko.rulerank.desktop.feature.rules;

import pl.poznan.put.cs.idss.jrs.rules.Rule;

import java.util.List;

/**
 * This class contains data for row in rule table. <br>
 * Cells list and id is used to display text in columns. <br>
 *  <br>
 * Created by Piotr on 2018-05-21.
 */
class RuleRow {
	
	private int id;
	private List<String> cells;
	private Rule rule;
	
	/**
	 * Creates instance of this class.
	 * @param id with is displayed in ID column
	 * @param cells with will be used to display data in table. Cells count should be equal to columns count in rules table.
	 * @param rule related with this row. Used to display rule statistics.
	 */
	RuleRow(int id, List<String> cells, Rule rule) {
		this.id = id;
		this.cells = cells;
		this.rule = rule;
	}
	
	public int getId() {
		return id;
	}
	
	List<String> getCells() {
		return cells;
	}
	
	Rule getRule() {
		return rule;
	}
	
}
