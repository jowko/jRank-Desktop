package pl.jowko.rulerank.desktop.feature.learningtable;

import javafx.util.StringConverter;
import pl.jowko.rulerank.desktop.feature.learningtable.wrappers.EnumFieldWrapper;
import pl.poznan.put.cs.idss.jrs.types.EnumDomain;
import pl.poznan.put.cs.idss.jrs.types.Field;

import static java.util.Objects.isNull;
import static pl.jowko.rulerank.desktop.feature.learningtable.wrappers.EnumFieldWrapper.UNKNOWN_FIELD_FLAG;

/**
 * Created by Piotr on 2018-05-12.
 * This class helps to convert enum field values to string values for display in ComboBox.
 */
class EnumFieldConverter extends StringConverter<Field> {
	
	private EnumDomain domain;
	
	EnumFieldConverter(EnumDomain domain) {
		this.domain = domain;
	}
	
	/**
	 * Gets text from provided field to display on ComboBox.
	 * If field is unknown, empty String will be returned.
	 */
	@Override
	public String toString(Field object) {
		if(isNull(object))
			return "";
		
		if(UNKNOWN_FIELD_FLAG.equals(object.toString()))
			return "";
		
		return object.toString();
	}
	
	/**
	 * Create enum field from provided string.
	 * If string is empty or contains '?' character, it will be returned as unknown field.
	 */
	@Override
	public Field fromString(String string) {
		if(string.isEmpty() || "?".equals(string)) {
			EnumFieldWrapper wrapper = new EnumFieldWrapper(0, domain);
			wrapper.setUnknown();
			return wrapper;
		}
		
		return new EnumFieldWrapper(string, domain);
	}
	
}
