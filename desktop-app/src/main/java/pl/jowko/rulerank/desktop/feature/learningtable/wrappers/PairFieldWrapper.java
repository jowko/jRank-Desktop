package pl.jowko.rulerank.desktop.feature.learningtable.wrappers;

import pl.poznan.put.cs.idss.jrs.types.PairField;

/**
 * This class wraps pair field to override default toString method. <br>
 * This change will show pair elements indices from 1 instead of 0. <br>
 *  <br>
 * Created by Piotr on 2018-06-04
 */
public class PairFieldWrapper extends PairField {
	
	public PairFieldWrapper(PairField field) {
		super(field.getFirstElement(), field.getSecondElement());
	}
	
	@Override
	public String toString() {
		return super.toStringWithIncrementation();
	}
	
}
