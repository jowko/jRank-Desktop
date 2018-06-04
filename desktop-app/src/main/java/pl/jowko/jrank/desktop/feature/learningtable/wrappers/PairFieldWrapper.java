package pl.jowko.jrank.desktop.feature.learningtable.wrappers;

import pl.poznan.put.cs.idss.jrs.types.PairField;

/**
 * Created by Piotr on 2018-06-04
 * This class wraps pair field to override default toString method.
 * This change will show pair elements indices from 1 instead of 0.
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
