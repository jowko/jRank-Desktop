package pl.jowko.jrank.desktop.feature.learningtable;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.ComboBoxTableCell;
import pl.poznan.put.cs.idss.jrs.types.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piotr on 2018-05-13.
 */
class LearningTableHelper {
	
	/**
	 * When removing column from table, indexes are not correctly related to columns.
	 * After column removal cellValueFactory must be recreated with new indexes.
	 */
	void recreateCellValuesFactories(List<TableColumn<ObservableList<Field>, ?>> columns) {
		for(int i=0; i<columns.size(); i++) {
			AttributeTableColumn column = (AttributeTableColumn) columns.get(i);
			final int finalIdx = i;
			column.setCellValueFactory(param ->
					new ReadOnlyObjectWrapper<>(param.getValue().get(finalIdx))
			);
		}
	}
	
	void setCellFactories(AttributeTableColumn column, int finalIdx) {
		column.setCellValueFactory(param ->
				new ReadOnlyObjectWrapper<>(param.getValue().get(finalIdx))
		);
		Attribute attribute = column.getAttribute();
		
		if(attribute.getInitialValue() instanceof CardinalField) {
			column.setCellFactory(col -> new CardinalFieldTableCell<>());
		} else if(attribute.getInitialValue() instanceof IntegerField) {
			column.setCellFactory(col -> new IntegerFieldTableCell<>());
		} else if(attribute.getInitialValue() instanceof FloatField) {
			column.setCellFactory(col -> new DecimalFieldTableCell<>());
		} else if(attribute.getInitialValue() instanceof TableEnumField){
			handleEnumFieldFactory(column, attribute);
		} else {
			column.setCellFactory(col -> new StringFieldTableCell<>());
		}
	}
	
	ObservableList<Field> getEmptyExample(List<Attribute> attributes) {
		ObservableList<Field> fields = FXCollections.observableArrayList();
		
		for(Attribute attribute: attributes) {
			fields.add(RuleRankFieldHelper.createNewFieldOfProvidedType(attribute.getInitialValue()));
		}
		
		return fields;
	}
	
	String getColumnText(Attribute attribute) {
		return attribute.getName() +
				'\n' +
				RuleRankFieldHelper.getColumnFieldType(attribute.getInitialValue()) +
				'\n' +
				getColumnPreference(attribute.getPreferenceType(), attribute.getKind());
	}
	
	private String getColumnPreference(int preference, int kind) {
		if(preference == 1)
			return "Gain";
		if(preference == 2)
			return "Cost";
		if(kind == 1)
			return "Decision";
		if(kind == 2)
			return "Description";
		return "";
	}
	
	private void handleEnumFieldFactory(TableColumn<ObservableList<Field>, Field> column, Attribute attribute) {
		TableEnumField enumField = (TableEnumField) attribute.getInitialValue();
		List<String> comboValues = new EnumListProvider(enumField).getValues();
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
	
}
