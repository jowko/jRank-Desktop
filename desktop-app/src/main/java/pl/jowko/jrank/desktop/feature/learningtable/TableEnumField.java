package pl.jowko.jrank.desktop.feature.learningtable;

import pl.poznan.put.cs.idss.jrs.types.EnumDomain;
import pl.poznan.put.cs.idss.jrs.types.EnumField;

/**
 * Created by Piotr on 2018-05-12.
 * This class replaces equals method due to JavaFX passing null to equals method when ComboBox is created.
 * Using original EnumField results in NullPointerException
 */
public class TableEnumField extends EnumField {
	
	public TableEnumField(EnumField field) {
		super(field.getIndex(), field.getDomain());
	}
	
	public TableEnumField(String name, EnumDomain domain) {
		super(name, domain);
	}
	
	/**
	 * Check if argument is equal to this object
	 * @param object to check equality
	 * @return true if object argument is equal to this object, false otherwise
	 */
	@Override
	public boolean equals(Object object) {
		if (object == null) {
			return false;
		}
		return super.equals(object);
	}
	
}
