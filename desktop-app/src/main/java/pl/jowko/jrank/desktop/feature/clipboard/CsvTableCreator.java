package pl.jowko.jrank.desktop.feature.clipboard;

import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Piotr on 2018-05-27
 * Creates CsvTable from JavaFX TableView items and columns.
 */
public class CsvTableCreator {
	
	private CsvTableCreator() {}
	
	/**
	 * Create CsvTable from provided elements.
	 * Column names should be passed as first parameter, table items(rows) as second.
	 * Last parameter is list of indexes with helps to resolve order of fields.
	 * When reordering columns, JavaFX updates indexes of columns, but not items.
	 * Items have same order as on table creation, so indexes list is required to properly match items to columns.
	 *
	 * Example:
	 * Assume such table with indexes(in brackets) and one item:
	 * C1(0) C2(1) C3(2)
	 * c1    c2    c3
	 *
	 * After column reorder we will have such table(in brackets are original indexes):
	 * C3(2) C2(1) C1(0)
	 *
	 * But in items data are stored in original order:
	 * C3(2) C2(1) C1(0)
	 * c1    c2    c3
	 *
	 * To get correct order of items, we have to remember original order of columns.
	 * With such information, we can write c3 to position 0, c2 to 1 and c1 to 2.
	 * In such case indexes list should have values: 2, 1, 0
	 *
	 * @param headers with indicates columns names
	 * @param tableItems with are rows
	 * @param indexes with order of displayed fields
	 */
	public static <T> CsvTable createTable(List<String> headers, ObservableList<ObservableList<T>> tableItems, List<Integer> indexes) {
		List<CsvRow> rows = new ArrayList<>();
		
		rows.add(new CsvRow(headers));
		
		for(ObservableList<T> row : tableItems) {
			String[] cells = new String[headers.size()];
			
			for(int i=0; i<row.size(); i++) {
				cells[i] = row.get(indexes.get(i)).toString();
			}
			rows.add(new CsvRow(Arrays.asList(cells)));
		}
		
		return new CsvTable(rows);
	}
	
}
