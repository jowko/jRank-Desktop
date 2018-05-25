package pl.jowko.jrank.desktop.feature.learningtable.wrappers;

import pl.poznan.put.cs.idss.jrs.types.StringField;

/**
 * Created by Piotr on 2018-05-24
 * Wrapper for String type field.
 * It replaces toString method to display empty String when field is unknown.
 */
public class StringFieldWrapper extends StringField {
	
	public StringFieldWrapper() {}
	
	public StringFieldWrapper(String value) {
		super(value);
	}
	
	@Override
	public String toString() {
		return unknown ? "" : value;
	}
	
}
