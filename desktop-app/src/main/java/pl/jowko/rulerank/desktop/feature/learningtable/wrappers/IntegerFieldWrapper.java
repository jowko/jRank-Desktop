package pl.jowko.rulerank.desktop.feature.learningtable.wrappers;

import pl.poznan.put.cs.idss.jrs.types.IntegerField;

/**
 * Wrapper for integer type field. <br>
 * It replaces toString method to display empty String when field is unknown. <br>
 *  <br>
 * Created by Piotr on 2018-05-24
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
