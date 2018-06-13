package pl.jowko.jrank.desktop.feature.pct;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import pl.jowko.jrank.desktop.feature.internationalization.Labels;
import pl.jowko.jrank.desktop.feature.internationalization.LanguageService;
import pl.jowko.jrank.desktop.feature.learningtable.LearningTable;
import pl.jowko.jrank.desktop.feature.learningtable.LearningTableHelper;
import pl.jowko.jrank.desktop.feature.learningtable.wrappers.PairFieldWrapper;
import pl.jowko.jrank.feature.customfx.IndexedTableColumn;
import pl.poznan.put.cs.idss.jrs.types.*;

import java.util.List;

import static javafx.collections.FXCollections.observableArrayList;
import static pl.jowko.jrank.desktop.feature.internationalization.Labels.PCT_ID;

/**
 * Created by Piotr on 2018-05-28
 * This class extract data from Learning table and creates columns and items(rows) for PCT table.
 * @see PCTController
 */
class PCTCreator {
	
	private LanguageService labels;
	private LearningTableHelper tableHelper;
	
	private LearningTable isfTable;
	private List<TableColumn<ObservableList<Field>, ?>> columns;
	private List<ObservableList<Field>> items;
	
	/**
	 * Create instance of this class.
	 * It will generate columns and items(rows) containing data extracted from isf table.
	 * @param isfTable with will be used to create columns and items(rows)
	 */
	PCTCreator(LearningTable isfTable) {
		this.isfTable = isfTable;
		labels = LanguageService.getInstance();
		tableHelper = new LearningTableHelper();
		initializeData();
	}
	
	/**
	 * Gets columns for PCT table.
	 * Columns have format:
	 * ID | attributes from MemoryContainer...
	 * @return list of columns
	 */
	List<TableColumn<ObservableList<Field>, ?>> getColumns() {
		return columns;
	}
	
	/**
	 * Gets items for PCT table.
	 * Items have format:
	 * ID | fields from MemoryContainer...
	 * @return list of items(rows)
	 */
	List<ObservableList<Field>> getItems() {
		return items;
	}
	
	/**
	 * Create columns and items
	 */
	private void initializeData() {
		createColumns();
		createItems();
	}
	
	/**
	 * Creates columns from attributes extracted from MemoryContainer.
	 * First column is ID column, rest represents attributes.
	 */
	private void createColumns() {
		columns = observableArrayList();
		columns.add(createIdColumn());
		int columnIndex = 1;
		
		for(Attribute attribute : isfTable.getAttributes()) {
			columns.add(createColumn(attribute, columnIndex++));
		}
	}
	
	/**
	 * Creates ID column.
	 * @return column for ID field
	 */
	private IndexedTableColumn<ObservableList<Field>, ?> createIdColumn() {
		IndexedTableColumn<ObservableList<Field>, Field> column = new IndexedTableColumn<>(labels.get(PCT_ID), 0);
		column.setText(labels.get(PCT_ID));
		column.setMinWidth(50d);
		column.setCellValueFactory(param ->
				new ReadOnlyObjectWrapper<>(param.getValue().get(0))
		);
		
		return column;
	}
	
	/**
	 * Creates column for provided attribute.
	 * @param attribute from with column is created
	 * @param columnIndex with indicates cell position in example
	 * @return column created from provided attribute
	 */
	private IndexedTableColumn<ObservableList<Field>, ?> createColumn(Attribute attribute, int columnIndex) {
		IndexedTableColumn<ObservableList<Field>, Field> column = new IndexedTableColumn<>(attribute.getName(), columnIndex);
		column.setMinWidth(50d);
		column.setPrefWidth(tableHelper.getColumnPrefWidth(attribute));
		column.setGraphic(tableHelper.getColumnLabel(attribute));
		
		setCellValueFactory(attribute, columnIndex, column);
		
		return column;
	}
	
	/**
	 * Sets cell value factory for column cell.
	 * This code is responsible for way in with data is displayed in table.
	 * It will display S or Sc label for grade column.
	 * In other cases it will display value from field.
	 * @param attribute from with column is created
	 * @param columnIndex with indicates cell position in example
	 * @param column with is currently created for provided attribute
	 */
	private void setCellValueFactory(Attribute attribute, int columnIndex, IndexedTableColumn<ObservableList<Field>, Field> column) {
		if(attribute.getInitialValue() instanceof FloatField && "Comprehensive_preference_grade".equalsIgnoreCase(attribute.getName())) {
			column.setCellValueFactory(param -> {
				FloatField field = (FloatField) param.getValue().get(columnIndex);
				String value;
				if(field.get() >= 0.0) {
					value = labels.get(Labels.PCT_S);
				} else {
					value = labels.get(Labels.PCT_SC);
				}
				
				return new ReadOnlyObjectWrapper<>(new StringField(value));
			});
		} else {
			column.setCellValueFactory(param ->
					new ReadOnlyObjectWrapper<>(param.getValue().get(columnIndex))
			);
		}
	}
	
	/**
	 * Create items(rows) from examples extracted from MemoryContainer.
	 */
	private void createItems() {
		items = observableArrayList();
		int rowIndex = 1;
		for(Example example : isfTable.getExamples()) {
			items.add(createItem(example, rowIndex++));
		}
	}
	
	/**
	 * Create item(row) from provided example.
	 * Also creates ID field for first position using rowIndex.
	 * @param example from with fields are extracted
	 * @param rowIndex with is used to create ID cell
	 * @return list of fields representing one item(row of data)
	 */
	private ObservableList<Field> createItem(Example example, int rowIndex) {
		ObservableList<Field> fields = observableArrayList();
		// create id field
		fields.add(new CardinalField(rowIndex));
		
		for(Field field : example.getFields()) {
			if(field instanceof PairField)
				fields.add(new PairFieldWrapper((PairField) field));
			else
				fields.add(field);
		}
		
		return fields;
	}
	
}
