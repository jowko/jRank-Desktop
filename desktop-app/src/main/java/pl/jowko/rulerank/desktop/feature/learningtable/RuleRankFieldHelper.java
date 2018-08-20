package pl.jowko.rulerank.desktop.feature.learningtable;

import pl.jowko.rulerank.desktop.feature.learningtable.dialogs.AttributeDialogHelper;
import pl.jowko.rulerank.desktop.feature.learningtable.wrappers.*;
import pl.poznan.put.cs.idss.jrs.types.Field;

/**
 * This class contains helper methods related with jRS fields. <br>
 *  <br>
 * Created by Piotr on 2018-05-14.
 */
class RuleRankFieldHelper {
	
	private RuleRankFieldHelper() {}
	
	/**
	 * Creates new instance of provided field with same type.
	 * @param field with indicates type of Field to be created
	 * @return new field wrapper with same type as provided field
	 */
	static Field createNewFieldOfProvidedType(Field field) {
		if(field instanceof StringFieldWrapper)
			return new StringFieldWrapper();
		if(field instanceof CardinalFieldWrapper)
			return new CardinalFieldWrapper();
		if(field instanceof IntegerFieldWrapper)
			return new IntegerFieldWrapper();
		if(field instanceof FloatFieldWrapper)
			return new FloatFieldWrapper();
		if(field instanceof EnumFieldWrapper)
			return new EnumFieldWrapper(0, ((EnumFieldWrapper) field).getDomain());
		
		return null;
	}
	
	/**
	 * Gets String name of field type provided in param.
	 * @param initialValue from with field type is extracted
	 * @return String value of field type of provided field
	 */
	static String getColumnFieldType(Field initialValue) {
		return AttributeDialogHelper.getFieldTypeFromField(initialValue).toString();
	}
	
}
