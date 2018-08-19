package pl.jowko.rulerank.desktop.feature.learningtable.wrappers;

import pl.poznan.put.cs.idss.jrs.types.StringField;

/**
 * Wrapper for String type field. <br>
 * It replaces toString method to display empty String when field is unknown. <br>
 *  <br>
 * Created by Piotr on 2018-05-24
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
