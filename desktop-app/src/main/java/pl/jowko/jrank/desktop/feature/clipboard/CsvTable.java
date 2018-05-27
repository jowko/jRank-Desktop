package pl.jowko.jrank.desktop.feature.clipboard;

import java.util.List;

/**
 * Created by Piotr on 2018-05-27
 * This class represents CSV table.
 * It contains list of rows, with each has list of cells.
 */
public class CsvTable {
	
	private List<CsvRow> rows;
	
	public CsvTable(List<CsvRow> rows) {
		this.rows = rows;
	}
	
	public List<CsvRow> getRows() {
		return rows;
	}
	
	/**
	 * Convert CSV table to string value.
	 * It will return table containing all rows, in with each cell is separated by separator.
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		char separator = '|';
		
		for(CsvRow row : rows) {
			List<String> cells = row.getCells();
			for(int i=0; i<cells.size()-1; i++) {
				builder.append(cells.get(i))
						.append(separator);
			}
			// append last cell without separator
			builder.append(cells.get(cells.size()-1))
					.append('\n');
		}
		
		return builder.toString();
	}
	
}
