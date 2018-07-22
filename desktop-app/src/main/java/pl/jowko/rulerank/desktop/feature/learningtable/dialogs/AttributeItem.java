package pl.jowko.rulerank.desktop.feature.learningtable.dialogs;

import pl.poznan.put.cs.idss.jrs.types.Attribute;

import java.util.Objects;

/**
 * Created by Piotr on 2018-05-25
 * This class serves as wrapper for Attribute.
 * It is used in some places in UI.
 * Main function of this class is to provide elegant toString implementation for UI.
 */
public class AttributeItem {
	
	private Attribute attribute;
	
	public AttributeItem(Attribute attribute) {
		this.attribute = attribute;
	}
	
	public Attribute getAttribute() {
		return attribute;
	}
	
	@Override
	public String toString() {
		return attribute.getName();
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AttributeItem that = (AttributeItem) o;
		return Objects.equals(attribute, that.attribute);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(attribute);
	}
	
}
