package pl.jowko.jrank.desktop.feature.learningtable.wrappers;

import pl.poznan.put.cs.idss.jrs.types.FloatField;

/**
 * Created by Piotr on 2018-05-24
 * Wrapper for float type field.
 * It replaces toString method to display empty String when field is unknown.
 */
public class FloatFieldWrapper extends FloatField {
	
	public FloatFieldWrapper() {}
	
	public FloatFieldWrapper(double value) {
		super(value);
	}
	
	@Override
	public String toString() {
		return unknown ? "" : Double.toString(value);
	}
	
}
