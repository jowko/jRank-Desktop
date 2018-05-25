package pl.jowko.jrank.desktop.feature.learningtable;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.ComboBoxTableCell;
import pl.jowko.jrank.desktop.feature.internationalization.Labels;
import pl.jowko.jrank.desktop.feature.internationalization.LanguageService;
import pl.jowko.jrank.desktop.feature.learningtable.dialogs.AttributeDialogHelper;
import pl.jowko.jrank.desktop.feature.learningtable.wrappers.CardinalFieldWrapper;
import pl.jowko.jrank.desktop.feature.learningtable.wrappers.EnumFieldWrapper;
import pl.jowko.jrank.desktop.feature.learningtable.wrappers.FloatFieldWrapper;
import pl.jowko.jrank.desktop.feature.learningtable.wrappers.IntegerFieldWrapper;
import pl.jowko.jrank.logger.JRankLogger;
import pl.poznan.put.cs.idss.jrs.core.InvalidValueException;
import pl.poznan.put.cs.idss.jrs.types.Attribute;
import pl.poznan.put.cs.idss.jrs.types.EnumDomain;
import pl.poznan.put.cs.idss.jrs.types.Field;

import java.util.ArrayList;
import java.util.List;

import static pl.jowko.jrank.desktop.feature.learningtable.wrappers.EnumFieldWrapper.UNKNOWN_FIELD_FLAG;

/**
 * Created by Piotr on 2018-05-13.
 * Class contains useful methods for managing learning table.
 */
public class LearningTableHelper {
	
	private LanguageService labels;
	
	public LearningTableHelper() {
		labels = LanguageService.getInstance();
	}
	
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
	
	/**
	 * Sets cell factories with are needed to switch column cell to edit mode.
	 * It creates appropriate fields for each field type.
	 * @param column to with add cellFactory, attribute is extracted from here
	 * @param columnIndex needed to extract cell data from correct position from row
	 */
	void setCellFactories(AttributeTableColumn column, int columnIndex) {
		column.setCellValueFactory(param ->
				new ReadOnlyObjectWrapper<>(param.getValue().get(columnIndex))
		);
		Attribute attribute = column.getAttribute();
		
		if(attribute.getInitialValue() instanceof CardinalFieldWrapper) {
			column.setCellFactory(col -> new CardinalFieldTableCell<>());
		} else if(attribute.getInitialValue() instanceof IntegerFieldWrapper) {
			column.setCellFactory(col -> new IntegerFieldTableCell<>());
		} else if(attribute.getInitialValue() instanceof FloatFieldWrapper) {
			column.setCellFactory(col -> new DecimalFieldTableCell<>());
		} else if(attribute.getInitialValue() instanceof EnumFieldWrapper){
			handleEnumFieldFactory(column, attribute);
		} else {
			column.setCellFactory(col -> new StringFieldTableCell<>());
		}
	}
	
	/**
	 * This methods creates row of fields(example) for learning table.
	 * @param attributes from with fields are created
	 * @return list of fields with default values
	 */
	ObservableList<Field> getEmptyExample(List<Attribute> attributes) {
		ObservableList<Field> fields = FXCollections.observableArrayList();
		
		for(Attribute attribute: attributes) {
			Field field = RuleRankFieldHelper.createNewFieldOfProvidedType(attribute.getInitialValue());
			field.setUnknown();
			fields.add(field);
		}
		
		return fields;
	}
	
	/**
	 * In case of editing attribute with enum field type, ComboBoxes need to be recreated with new enum values.
	 * This method create new cell factory for provided column.
	 * Also replaces old enum fields with new ones for each row.
	 * @param column from with enum field is extracted
	 * @param attributeIndex with is used to extract correct cell from row.
	 * @param learningTable with is used to extract all row from table
	 */
	void handleEnumEdition(AttributeTableColumn column, int attributeIndex, TableView<ObservableList<Field>> learningTable) {
		EnumFieldWrapper enumField = (EnumFieldWrapper) column.getAttribute().getInitialValue();
		EnumDomain domain = enumField.getDomain();
		
		ObservableList<ObservableList<Field>> list = learningTable.getItems();
		list.forEach(row -> {
			EnumFieldWrapper field = (EnumFieldWrapper) row.get(attributeIndex);
			
			if(field.isUnknown() == 0) {
				EnumFieldWrapper wrapper = new EnumFieldWrapper(0, domain);
				wrapper.setUnknown();
				row.set(attributeIndex, wrapper);
				return;
			}
			
			String previousValue = field.getName();
			try {
				int previousIndex = domain.getIndex(previousValue);
				row.set(attributeIndex, new EnumFieldWrapper(domain.getName(previousIndex), domain));
			} catch (InvalidValueException e) {
				JRankLogger.warn("Value: " + previousValue + " is not available now for field [" + column.getAttribute().getName() + "]. Setting field as unknown");
				domain.addElement(UNKNOWN_FIELD_FLAG);
				row.set(attributeIndex, new EnumFieldWrapper(UNKNOWN_FIELD_FLAG, domain));
			}
			
		});
		setCellFactories(column, attributeIndex);
	}
	
	/**
	 * Created label for column with is displayed in column header.
	 * Also contains tooltip with more details about attribute
	 * @param attribute from with label is created
	 * @return Label with tooltip
	 */
	public Label getColumnLabel(Attribute attribute) {
		Label label = new Label(getColumnText(attribute));
		label.setTooltip(createColumnTooltip(attribute));
		return label;
	}
	
	/**
	 * Calculates preferred column width basing on field type and attribute name length.
	 * @param attribute from with prefWidth is calculated
	 * @return preferred width of column
	 */
	public int getColumnPrefWidth(Attribute attribute) {
		int prefWidth = 70;
		int nameLength = attribute.getName().length();
		
		if(nameLength > 10 && nameLength < 20)
			prefWidth += nameLength * 2;
		else if(nameLength >= 20)
			prefWidth += nameLength * 5;
		
		if(attribute.getInitialValue() instanceof EnumFieldWrapper)
			prefWidth += 30;
		
		return prefWidth;
	}
	
	private String getColumnText(Attribute attribute) {
		return attribute.getName() +
				'\n' +
				RuleRankFieldHelper.getColumnFieldType(attribute.getInitialValue()) +
				getColumnPreference(attribute.getPreferenceType());
	}
	
	private String getColumnPreference(int preference) {
		if(preference == Attribute.GAIN)
			return " (+)";
		if(preference == Attribute.COST)
			return " (-)";
		return "";
	}
	
	private Tooltip createColumnTooltip(Attribute attribute) {
		StringBuilder builder = new StringBuilder();
		int preference = attribute.getPreferenceType();
		int kind = attribute.getKind();
		
		if(preference == Attribute.NONE)
			builder.append(labels.get(Labels.LEARN_TABLE_NO_PREFERENCE));
		else if(preference == Attribute.GAIN)
			builder.append(labels.get(Labels.LEARN_TABLE_GAIN));
		else if(preference == Attribute.COST)
			builder.append(labels.get(Labels.LEARN_TABLE_COST));
		
		if(kind == Attribute.DECISION)
			builder.append(labels.get(Labels.LEARN_TABLE_DECISION));
		else if(kind == Attribute.DESCRIPTION)
			builder.append(labels.get(Labels.LEARN_TABLE_DESCRIPTION));
		
		if(attribute.getActive())
			builder.append(labels.get(Labels.LEARN_TABLE_ACTIVE));
		else
			builder.append(labels.get(Labels.LEARN_TABLE_INACTIVE));
		
		String fieldType = AttributeDialogHelper.getFieldTypeFromField(attribute.getInitialValue()).toString().toLowerCase();
		builder.append(fieldType);
		
		return new Tooltip(builder.toString());
	}
	
	/**
	 * This method add values to ComboBox for enum field in editable table.
	 * It also handles unknown enum value.
	 * It adds UNKNOWN_FIELD_FLAG to enum domain and to ComboBox as selectable option.
	 * @see EnumFieldWrapper
	 * @param column to with add enum field
	 * @param attribute from with enum values are extracted
	 */
	private void handleEnumFieldFactory(TableColumn<ObservableList<Field>, Field> column, Attribute attribute) {
		EnumFieldWrapper enumField = (EnumFieldWrapper) attribute.getInitialValue();
		List<String> comboValues = new EnumListProvider(enumField).getValues();
		List<EnumFieldWrapper> fields = new ArrayList<>();
		
		try { // check if unknown value in domain already exists
			enumField.getDomain().getIndex(UNKNOWN_FIELD_FLAG);
		} catch (InvalidValueException e) { // value doesn't exist
			enumField.getDomain().addElement(UNKNOWN_FIELD_FLAG);
		}
		
		for(String value: comboValues) {
			fields.add(new EnumFieldWrapper(value, enumField.getDomain()));
		}
		
		// add empty option for unknown field value
		EnumFieldWrapper field = new EnumFieldWrapper(UNKNOWN_FIELD_FLAG, enumField.getDomain());
		fields.add(field);
		
		column.setCellFactory(ComboBoxTableCell.forTableColumn(
				new EnumFieldConverter(enumField.getDomain()),
				FXCollections.observableArrayList(fields)
		));
	}
	
}
