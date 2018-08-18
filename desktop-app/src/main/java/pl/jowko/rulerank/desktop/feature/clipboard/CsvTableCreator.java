package pl.jowko.rulerank.desktop.feature.clipboard;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import pl.jowko.rulerank.feature.customfx.IndexedTableColumn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Creates CsvTable from JavaFX TableView items and columns. <br>
 *  <br>
 * Created by Piotr on 2018-05-27
 */
public class CsvTableCreator {
	
	private CsvTableCreator() {}
	
	/**
	 * Create CsvTable from provided elements. <br>
	 * Column names should be passed as first parameter, table items(rows) as second. <br>
	 * Last parameter is list of indexes with helps to resolve order of fields. <br>
	 * When reordering columns, JavaFX updates indexes of columns, but not items. <br>
	 * Items have same order as on table creation, so indexes list is required to properly match items to columns. <br>
	 * <br>
	 * Example: <br>
	 * Assume such table with indexes(in brackets) and one item: <br>
	 * C1(0) C2(1) C3(2) <br>
	 * c1    c2    c3 <br>
	 * <br>
	 * After column reorder we will have such table(in brackets are original indexes): <br>
	 * C3(2) C2(1) C1(0) <br>
	 * <br>
	 * But in items data are stored in original order: <br>
	 * C3(2) C2(1) C1(0) <br>
	 * c1    c2    c3 <br>
	 * <br>
	 * To get correct order of items, we have to remember original order of columns. <br>
	 * With such information, we can write c3 to position 0, c2 to 1 and c1 to 2. <br>
	 * In such case indexes list should have values: 2, 1, 0 <br>
	 * <br>
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
	
	/**
	 * Create Csv table from provided UI JavaFX table and selected items. <br>
	 * Table should use IndexedTableColumn class when creating columns.
	 * @see IndexedTableColumn
	 */
	public static <T> CsvTable createTable(TableView<?> table, ObservableList<ObservableList<T>> selectedItems) {
		List<String> columns = CsvTableCreator.extractColumnNames(table);
		List<Integer> indexes = CsvTableCreator.extractIndexes(table);
		return CsvTableCreator.createTable(columns, selectedItems, indexes);
	}
	
	/**
	 * Extract column names from table. <br>
	 * Table should use IndexedTableColumn class when creating columns.
	 * @see IndexedTableColumn
	 */
	public static List<String> extractColumnNames(TableView<?> table) {
		return table.getColumns().stream()
				.map(col -> ((IndexedTableColumn)col).getName())
				.collect(Collectors.toList());
	}
	
	/**
	 * Extract indexes from table. <br>
	 * Table should use IndexedTableColumn class when creating columns.
	 * @see IndexedTableColumn
	 */
	public static List<Integer> extractIndexes(TableView<?> table) {
		return table.getColumns().stream()
				.map(col -> ((IndexedTableColumn)col).getIndex())
				.collect(Collectors.toList());
	}
	
}
