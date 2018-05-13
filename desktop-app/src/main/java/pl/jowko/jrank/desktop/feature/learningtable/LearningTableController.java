package pl.jowko.jrank.desktop.feature.learningtable;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import pl.poznan.put.cs.idss.jrs.core.mem.MemoryContainer;
import pl.poznan.put.cs.idss.jrs.types.*;

import java.util.ArrayList;
import java.util.List;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * Created by Piotr on 2018-05-08.
 */
public class LearningTableController {
	
	@FXML
	TableView<ObservableList<Field>> learningTable;
	
	private LearningTable table;
	private List<Attribute> columns;
	
	public void initializeTable(MemoryContainer container) {
		table = new LearningTable(container);
		new EnumReplacer().replaceJRSEnumsWithTableEnumFields(table);
		initializeTable();
	}
	
	private void initializeTable() {
		columns = table.getAttributes();
		
		for(Attribute attribute : columns) {
			createNewColumn(attribute);
		}
		
		for(Example example : table.getExamples()) {
			learningTable.getItems().add(observableArrayList(example.getFields()));
		}
	}
	
	public void addNewColumnAction() {
		Attribute attribute = new Attribute("someName", new StringField());
		columns.add(attribute);
		createNewColumn(attribute);
		
		ObservableList<ObservableList<Field>> list = learningTable.getItems();
		list.forEach(row -> row.add(new StringField()));
	}
	
	private void createNewColumn(Attribute attribute) {
		TableColumn<ObservableList<Field>, Field> column = new TableColumn<>(attribute.getName());
		int attributeIndex = learningTable.getColumns().size();
		setCellFactories(column, attribute, attributeIndex);
		column.setOnEditCommit(this::handleEditCellAction);
		column.setMinWidth(50d);
		
		learningTable.getColumns().add(column);
	}
	
	private void setCellFactories(TableColumn<ObservableList<Field>, Field> column, Attribute attribute, int finalIdx) {
		column.setCellValueFactory(param ->
				new ReadOnlyObjectWrapper<>(param.getValue().get(finalIdx))
		);
		
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
