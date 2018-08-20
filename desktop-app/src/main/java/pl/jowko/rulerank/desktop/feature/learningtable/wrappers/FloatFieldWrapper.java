package pl.jowko.rulerank.desktop.feature.learningtable.wrappers;

import pl.poznan.put.cs.idss.jrs.types.FloatField;

/**
 * Wrapper for float type field. <br>
 * It replaces toString method to display empty String when field is unknown. <br>
 *  <br>
 * Created by Piotr on 2018-05-24
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
