package pl.jowko.rulerank.desktop.feature.properties.information;

import pl.poznan.put.cs.idss.jrs.types.Field;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * This class holds list of fields, with is displayed in ListView or TreeView. <br>
 * List of fields is used to display proper label. <br>
 * Labels are created from description fields and ID field. <br>
 * User can configure dynamically, what he want to see. <br>
 * List of fields and displayedFieldIndex is used to display proper label. <br>
 * <br>
 * Example: <br>
 * Fields(indices): <br>
 * ID(0) | Model(1) | Brand(2) <br>
 * <br>
 * If user wants to see Model field value as label, he will choose Model from ComboBox and application sets displayedFieldIndex to 1. <br>
 *  <br>
 * Created by Piotr on 2018-05-29
 */
class FieldItem implements Serializable, Comparable<FieldItem> {
	
	private static final long serialVersionUID = -8442666730012290047L;
	
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
	
	String getId() {
		return fields.get(0).toString();
	}
	
	void setDisplayedFieldIndex(int displayedFieldIndex) {
		this.displayedFieldIndex = displayedFieldIndex;
	}
	
	int getDisplayedFieldIndex() {
		return displayedFieldIndex;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		FieldItem item = (FieldItem) o;
		return Objects.equals(fields, item.fields);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(fields);
	}
	
	@Override
	public String toString() {
		return fields.get(displayedFieldIndex).toString();
	}
	
	/**
	 * Compares two elements. <br>
	 * Objects are compared only by id field(first element in list).
	 * @param o to be compared
	 * @return 0 if elements are equal, -1 if o is bigger than than this, 1 otherwise
	 */
	@Override
	public int compareTo(FieldItem o) {
		if(this.equals(o)) {
			return 0;
		}
		return fields.get(0).compareTo(o.fields.get(0));
	}
	
}
