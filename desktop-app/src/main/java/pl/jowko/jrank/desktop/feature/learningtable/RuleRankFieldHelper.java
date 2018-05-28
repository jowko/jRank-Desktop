package pl.jowko.jrank.desktop.feature.learningtable;

import pl.jowko.jrank.desktop.feature.learningtable.dialogs.FieldType;
import pl.jowko.jrank.desktop.feature.learningtable.wrappers.*;
import pl.poznan.put.cs.idss.jrs.types.Field;

/**
 * Created by Piotr on 2018-05-14.
 * This class contains helper methods related with jRS fields.
 */
class RuleRankFieldHelper {
	
	private RuleRankFieldHelper() {}
	
	/**
	 * Creates new instance of provided field with same type.
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
	 */
	static String getColumnFieldType(Field initialValue) {
		if(initialValue instanceof StringFieldWrapper)
			return FieldType.STRING_FIELD.toString();
		if(initialValue instanceof CardinalFieldWrapper)
			return FieldType.CARDINAL_FIELD.toString();
		if(initialValue instanceof IntegerFieldWrapper)
			return FieldType.INTEGER_FIELD.toString();
		if(initialValue instanceof FloatFieldWrapper)
			return FieldType.DECIMAL_FIELD.toString();
		if(initialValue instanceof EnumFieldWrapper)
			return FieldType.ENUM_FIELD.toString();
		
		return "";
	}
	
}
