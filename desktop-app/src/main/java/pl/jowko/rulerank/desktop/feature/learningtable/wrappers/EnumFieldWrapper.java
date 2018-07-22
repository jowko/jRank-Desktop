package pl.jowko.rulerank.desktop.feature.learningtable.wrappers;

import pl.poznan.put.cs.idss.jrs.types.EnumDomain;
import pl.poznan.put.cs.idss.jrs.types.EnumField;
import pl.poznan.put.cs.idss.jrs.types.Field;

/**
 * Created by Piotr on 2018-05-12.
 * This class replaces equals method due to JavaFX passing null to equals method when ComboBox is created.
 * Using original EnumField results in NullPointerException.
 * This class replaces toString method to display empty String when field is unknown.
 */
public class EnumFieldWrapper extends EnumField {
	
	/**
	 * This flag is used to indicate, that enum field value is unknown.
	 * This variable value should be set in enum field in such case.
	 * Setting jRS unknown boolean in enum field leads to weird errors in JavaFX and using this flag bypasses this problem.
	 * If this flag is set as enum value, it will be displayed as empty string in UI table.
	 * @see pl.jowko.rulerank.desktop.feature.learningtable.EnumFieldConverter
	 */
	public static String UNKNOWN_FIELD_FLAG = "SomeVeryLongAndWeirdTextWithWillNotBeDisplayedForUserToIndicateThatThisPositionRepresentsUnknownField";
	
	public EnumFieldWrapper(EnumField field) {
		super(field.getIndex(), field.getDomain());
	}
	
	public EnumFieldWrapper(String name, EnumDomain domain) {
		super(name, domain);
	}
	
	public EnumFieldWrapper(int index, EnumDomain domain) {
		super(index, domain);
	}
	
	/**
	 * Used to fix problem with NullPointerException in some cases in JavaFX table UI.
	 */
	@Override
	public void copy(Field field) {
		if (field == null) {
			setUnknown();
			return;
		}
		
		super.copy(field);
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
	
	@Override
	public String toString() {
		return unknown ? "" : getName();
	}
	
}
