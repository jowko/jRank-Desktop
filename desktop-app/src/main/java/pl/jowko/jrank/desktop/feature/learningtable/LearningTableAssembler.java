package pl.jowko.jrank.desktop.feature.learningtable;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import pl.poznan.put.cs.idss.jrs.types.Attribute;
import pl.poznan.put.cs.idss.jrs.types.Example;
import pl.poznan.put.cs.idss.jrs.types.Field;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piotr on 2018-05-15.
 */
class LearningTableAssembler {
	
	private TableView<ObservableList<Field>> learningTable;
	private LearningTable table;
	private List<Attribute> attributes;
	/**
	 * This list holds indexes of original table items
	 */
	private List<Integer> columnsReorderedIndexes;
	
	LearningTableAssembler(TableView<ObservableList<Field>> learningTable, LearningTable newTable, List<Attribute> attributes) {
		this.learningTable = learningTable;
		this.attributes = attributes;
		columnsReorderedIndexes = new ArrayList<>();
		table = new LearningTable(newTable.getFileInfo(), newTable.getMemoryContainerDescription(), newTable.getId());
	}
	
	LearningTable getLearningTableFromUITable() {
		addAttributes();
		addExamples();
		
		return table;
	}
	
	private void addAttributes() throws RuntimeException {
		ObservableList<TableColumn<ObservableList<Field>, ?>> columns = learningTable.getColumns();
		
		for(TableColumn column: columns) {
			Attribute attribute = ((AttributeTableColumn) column).getAttribute();
			table.getAttributes().add(attribute);
			columnsReorderedIndexes.add(attributes.indexOf(attribute));
		}
	}
	
	private void addExamples() throws RuntimeException {
		ObservableList<ObservableList<Field>> rows = learningTable.getItems();
		
		for(ObservableList<Field> row: rows) {
			Example example = new Example(getReorderedFields(row));
			table.getExamples().add(example);
		}
	}
	
	/**
	 * When reordering columns, indexes of columns are changing, but items indexes are not.
	 * After column reorder columns and items have different field order.
	 * This method gets row as a parameter and return array of fields with valid order(same order as in a column)
	 * @param row of UI learning table
	 * @return Field array with valid order of fields
	 */
	private Field[] getReorderedFields(ObservableList<Field> row) {
		Field[] fields = new Field[row.size()];
		
		for(int i=0; i<row.size(); i++) {
			fields[i] = row.get(columnsReorderedIndexes.get(i));
		}
		
		return fields;
	}
	
}
