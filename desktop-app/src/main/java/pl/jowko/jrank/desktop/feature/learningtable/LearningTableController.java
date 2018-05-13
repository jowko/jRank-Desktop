package pl.jowko.jrank.desktop.feature.learningtable;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import pl.jowko.jrank.desktop.ResourceLoader;
import pl.jowko.jrank.desktop.feature.learningtable.dialogs.ColumnDialogController;
import pl.poznan.put.cs.idss.jrs.core.mem.MemoryContainer;
import pl.poznan.put.cs.idss.jrs.types.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static javafx.collections.FXCollections.observableArrayList;

/**
 * Created by Piotr on 2018-05-08.
 */
public class LearningTableController {
	
	@FXML
	Button addAttributeButton;
	@FXML
	Label selectAttributeLabel;
	@FXML
	ComboBox<String> selectAttribute;
	@FXML
	Button removeAttributeButton;
	@FXML
	Button editAttributeButton;
	
	@FXML
	TableView<ObservableList<Field>> learningTable;
	
	private LearningTable table;
	
	public void initializeTable(MemoryContainer container) {
		table = new LearningTable(container);
		new EnumReplacer().replaceJRSEnumsWithTableEnumFields(table);
		initializeTable();
		setItemsToAttributeComboBox();
	}
	
	public void addNewAttributeAction() throws IOException  {
		ResourceLoader resourceLoader = new ResourceLoader("/fxml/upperTabs/columnActionDialog.fxml");
		Parent parent = resourceLoader.load();
		ColumnDialogController controller = resourceLoader.getController();
		controller.initializeDialog(this, parent);
	}
	
	public void createNewColumn(Attribute attribute) {
		AttributeTableColumn column = new AttributeTableColumn(attribute.getName(), attribute);
		int attributeIndex = learningTable.getColumns().size();
		setCellFactories(column, attributeIndex);
		column.setOnEditCommit(this::handleEditCellAction);
		column.setMinWidth(50d);
		
		learningTable.getColumns().add(column);
		setItemsToAttributeComboBox();
	}
	
	public void removeAttributeAction() {
		TableColumn tableColumn = getSelectedColumn();
		if(isNull(tableColumn))
			return;
		
		int attributeIndex = learningTable.getColumns().indexOf(tableColumn);
		learningTable.getColumns().remove(tableColumn);
		
		ObservableList<ObservableList<Field>> list = getLearningTable().getItems();
		list.forEach(row -> row.remove(attributeIndex));
		recreateCellValuesFactories();
		setItemsToAttributeComboBox();
	}
	
	public void editAttributeAction() {
	
	}
	
	public TableView<ObservableList<Field>> getLearningTable() {
		return learningTable;
	}
	
	private void initializeTable() {
		for(Attribute attribute : table.getAttributes()) {
			createNewColumn(attribute);
		}
		
		for(Example example : table.getExamples()) {
			learningTable.getItems().add(observableArrayList(example.getFields()));
		}
	}
	
	private TableColumn getSelectedColumn() {
		String attributeName = selectAttribute.getValue();
		
		return learningTable.getColumns().stream()
				.filter(column -> column.getText().equalsIgnoreCase(attributeName))
				.findAny()
				.orElse(null);
	}
	
	private void setItemsToAttributeComboBox() {
		selectAttribute.setItems(observableArrayList(
				learningTable.getColumns().stream()
						.map(TableColumn::getText)
						.collect(Collectors.toList()))
		);
	}
	
	/**
	 * When removing column from table, indexes are not correctly related to columns.
	 * After column removal cellValueFactory must be recreated with new indexes.
	 */
	private void recreateCellValuesFactories() {
		List<TableColumn<ObservableList<Field>, ?>> columns = learningTable.getColumns();
		for(int i=0; i<columns.size(); i++) {
			AttributeTableColumn column = (AttributeTableColumn) columns.get(i);
			final int finalIdx = i;
			column.setCellValueFactory(param ->
					new ReadOnlyObjectWrapper<>(param.getValue().get(finalIdx))
			);
		}
	}
	
	private void setCellFactories(AttributeTableColumn column, int finalIdx) {
		column.setCellValueFactory(param ->
				new ReadOnlyObjectWrapper<>(param.getValue().get(finalIdx))
		);
		Attribute attribute = column.getAttribute();
		
		if(attribute.getInitialValue() instanceof IntegerField) {
			column.setCellFactory(col -> new IntegerFieldTableCell<>());
		} else if(attribute.getInitialValue() instanceof FloatField) {
			column.setCellFactory(col -> new DecimalFieldTableCell<>());
		} else if(attribute.getInitialValue() instanceof TableEnumField){
			handleEnumFieldFactory(column, attribute);
		} else {
			column.setCellFactory(col -> new StringFieldTableCell<>());
		}
	}
	
	private void handleEnumFieldFactory(TableColumn<ObservableList<Field>, Field> column, Attribute attribute) {
		TableEnumField enumField = (TableEnumField) attribute.getInitialValue();
		List<String> comboValues = new ArrayList<>(enumField.getDomain().getElementsNames());
		List<TableEnumField> fields = new ArrayList<>();
		
		for(String value: comboValues) {
			fields.add(new TableEnumField(value, enumField.getDomain()));
		}
		
		column.setCellFactory(ComboBoxTableCell.forTableColumn(
				new EnumFieldConverter(enumField.getDomain()),
				FXCollections.observableArrayList(fields)
		));
		column.setMinWidth(100d);
	}
	
	private void handleEditCellAction(TableColumn.CellEditEvent<ObservableList<Field>, Field> t) {
		Field field = t.getOldValue();
		String fieldValue = t.getNewValue().toString();
		
		if(field instanceof IntegerField) {
			((IntegerField) field).set(Integer.valueOf(fieldValue));
		}
		
		if(field instanceof StringField) {
			((StringField) field).set(fieldValue);
		}
		
		if(field instanceof TableEnumField) {
			((TableEnumField) field).set(fieldValue);
		}
		
		if(field instanceof FloatField) {
			((FloatField) field).set(Double.valueOf(fieldValue));
		}
	}
	
}
