package pl.jowko.rulerank.desktop.feature.learningtable;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import pl.poznan.put.cs.idss.jrs.types.Attribute;
import pl.poznan.put.cs.idss.jrs.types.Example;
import pl.poznan.put.cs.idss.jrs.types.Field;

import java.util.ArrayList;
import java.util.List;

import static pl.jowko.rulerank.desktop.feature.learningtable.LearningTableActions.ATTRIBUTE_ID_SECRET_NAME;

/**
 * Created by Piotr on 2018-05-15.
 * This class helps to assemble LearningTable from UI table.
 * It creates new LearningTable containing newest data from UI table.
 */
class LearningTableAssembler {
	
	private TableView<ObservableList<Field>> learningTable;
	private LearningTable table;
	private List<Attribute> attributes;
	/**
	 * This list holds indexes of original table items
	 */
	private List<Integer> columnsReorderedIndexes;
	
	/**
	 * Creates instance of this class and initialize data.
	 * @param learningTable used in UI
	 * @param table with is used to extract file info, description and id
	 * @param attributes with are needed to calculate correct indexes for attributes in case of reorder of columns
	 */
	LearningTableAssembler(TableView<ObservableList<Field>> learningTable, LearningTable table, List<Attribute> attributes) {
		this.learningTable = learningTable;
		this.attributes = attributes;
		columnsReorderedIndexes = new ArrayList<>();
		this.table = new LearningTable(table.getFileInfo(), table.getMemoryContainerDescription(), table.getId());
	}
	
	/**
	 * Extract all examples and attributes from UI table and return them in new Learning table.
	 * @return new LearningTable containing newest data from UI table
	 */
	LearningTable getLearningTableFromUITable() {
		addAttributes();
		addExamples();
		
		return table;
	}
	
	/**
	 * Extract all attributes from UI table using columns.
	 */
	private void addAttributes() {
		ObservableList<TableColumn<ObservableList<Field>, ?>> columns = learningTable.getColumns();
		
		for(TableColumn column: columns) {
			Attribute attribute = ((AttributeTableColumn) column).getAttribute();
			if(ATTRIBUTE_ID_SECRET_NAME.equals(attribute.getName())) {
				continue;
			}
			
			table.getAttributes().add(attribute);
			columnsReorderedIndexes.add(attributes.indexOf(attribute));
		}
	}
	
	/**
	 * Extract all fields from UI table using items and create list of examples from them.
	 * This method will replace wrapped fields with raw jRS fields.
	 * This is because of not overriding duplicate method with is used in Example class.
	 * @see pl.jowko.rulerank.desktop.feature.learningtable.wrappers;
	 */
	private void addExamples() {
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
		Field[] fields = new Field[columnsReorderedIndexes.size()];
		
		for(int i=0; i<columnsReorderedIndexes.size(); i++) {
			fields[i] = row.get(columnsReorderedIndexes.get(i));
		}
		
		return fields;
	}
	
}
