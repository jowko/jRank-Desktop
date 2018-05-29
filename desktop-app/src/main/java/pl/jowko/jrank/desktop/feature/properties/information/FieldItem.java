package pl.jowko.jrank.desktop.feature.properties.information;

import pl.poznan.put.cs.idss.jrs.types.Field;

import java.util.List;

/**
 * Created by Piotr on 2018-05-29
 * This class holds list of fields, with is displayed in ListView or TreeView.
 * List of fields is used to display proper label.
 * Labels are created from description fields and ID field.
 * User can configure dynamically, what he want to see.
 * List of fields and displayedFieldIndex is used to display proper label.
 *
 * Example:
 * Fields(indices):
 * ID(0) | Model(1) | Brand(2)
 *
 * If user wants to see Model field value as label, he will choose Model from ComboBox and application sets displayedFieldIndex to 1.
 */
class FieldItem {
	
	private List<Field> fields;
	private int displayedFieldIndex;
	
	/**
	 * List of fields containing ID field on first position and description fields extracted from example.
	 * @param fields with ID field and description fields
	 */
	FieldItem(List<Field> fields) {
		this.fields = fields;
		displayedFieldIndex = 0;
	}
	
	List<Field> getFields() {
		return fields;
	}
	
	void setDisplayedFieldIndex(int displayedFieldIndex) {
		this.displayedFieldIndex = displayedFieldIndex;
	}
	
	int getDisplayedFieldIndex() {
		return displayedFieldIndex;
	}
	
	@Override
	public String toString() {
		return fields.get(displayedFieldIndex).toString();
	}
	
}
