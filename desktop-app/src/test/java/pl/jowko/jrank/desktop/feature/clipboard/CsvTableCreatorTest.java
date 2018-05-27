package pl.jowko.jrank.desktop.feature.clipboard;

import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static javafx.collections.FXCollections.observableArrayList;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Piotr on 2018-05-27
 */
class CsvTableCreatorTest {
	
	@Test
	void shouldCreateCsvTable() {
		List<String> columns = Arrays.asList("test", "test2");
		ObservableList<ObservableList<String>> items = observableArrayList();
		items.add(observableArrayList("b", "a"));
		items.add(observableArrayList("d", "c"));
		List<Integer> indexes = Arrays.asList(0,1);
		CsvTable table = CsvTableCreator.createTable(columns, items, indexes);
		
		assertEquals(getExpectedString(), table.toString());
	}
	
	@Test
	void shouldCreateCsvTableOnReversedIndexes() {
		List<String> columns = Arrays.asList("test", "test2");
		ObservableList<ObservableList<String>> items = observableArrayList();
		items.add(observableArrayList("a", "b"));
		items.add(observableArrayList("c", "d"));
		List<Integer> indexes = Arrays.asList(1,0);
		CsvTable table = CsvTableCreator.createTable(columns, items, indexes);
		
		assertEquals(getExpectedString(), table.toString());
	}
	
	private String getExpectedString() {
		return "test|test2\nb|a\nd|c\n";
	}
	
	
}