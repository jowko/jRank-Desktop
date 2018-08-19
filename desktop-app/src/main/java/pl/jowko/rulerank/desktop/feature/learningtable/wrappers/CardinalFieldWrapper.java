package pl.jowko.rulerank.desktop.feature.learningtable.wrappers;

import pl.poznan.put.cs.idss.jrs.types.CardinalField;

/**
 * Wrapper for cardinal type field. <br>
 * It replaces toString method to display empty String when field is unknown. <br>
 *  <br>
 * Created by Piotr on 2018-05-24
 */
public class CardinalFieldWrapper extends CardinalField {
	
	public CardinalFieldWrapper() {}
	
	public CardinalFieldWrapper(int value) {
		super(value);
	}
	
	@Override
	public String toString() {
		return unknown ? "" : Integer.toString(value);
	}
	
}
