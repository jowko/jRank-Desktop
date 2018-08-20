package pl.jowko.rulerank.desktop.feature.clipboard;

import java.util.List;

/**
 * This class represents single row of csv table. <br>
 * Each element in cells list represents String value of CSV cell(without separator) <br>
 *  <br>
 * Created by Piotr on 2018-05-27
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
