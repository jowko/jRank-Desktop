package pl.jowko.rulerank.desktop.feature.clipboard;

import java.util.List;

/**
 * Created by Piotr on 2018-05-27
 * This class represents single row of csv table.
 * Each element in cells list represents String value of CSV cell(without separator)
 */
public class CsvRow {
	
	private List<String> cells;
	
	public CsvRow(List<String> cells) {
		this.cells = cells;
	}
	
	public List<String> getCells() {
		return cells;
	}
	
}
