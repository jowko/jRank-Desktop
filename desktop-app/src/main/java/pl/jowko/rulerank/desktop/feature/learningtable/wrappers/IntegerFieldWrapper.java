package pl.jowko.rulerank.desktop.feature.learningtable.wrappers;

import pl.poznan.put.cs.idss.jrs.types.IntegerField;

/**
 * Created by Piotr on 2018-05-24
 * Wrapper for integer type field.
 * It replaces toString method to display empty String when field is unknown.
 */
public class IntegerFieldWrapper extends IntegerField {
	
	public IntegerFieldWrapper() {}
	
	public IntegerFieldWrapper(int value) {
		super(value);
	}
	
	@Override
	public String toString() {
		return unknown ? "" : Integer.toString(value);
	}
	
}
