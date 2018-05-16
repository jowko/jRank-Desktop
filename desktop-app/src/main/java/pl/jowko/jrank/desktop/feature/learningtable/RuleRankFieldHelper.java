package pl.jowko.jrank.desktop.feature.learningtable;

import pl.jowko.jrank.desktop.feature.learningtable.dialogs.FieldType;
import pl.poznan.put.cs.idss.jrs.types.*;

/**
 * Created by Piotr on 2018-05-14.
 */
class RuleRankFieldHelper {
	
	private RuleRankFieldHelper() {}
	
	static Field createNewFieldOfProvidedType(Field field) {
		if(field instanceof StringField)
			return new StringField();
		if(field instanceof CardinalField)
			return new CardinalField();
		if(field instanceof IntegerField)
			return new IntegerField();
		if(field instanceof FloatField)
			return new FloatField();
		if(field instanceof TableEnumField)
			return new TableEnumField(0, ((TableEnumField) field).getDomain());
		
		return null;
	}
	
	static String getColumnFieldType(Field initialValue) {
		if(initialValue instanceof StringField)
			return FieldType.STRING_FIELD.toString();
		if(initialValue instanceof CardinalField)
			return FieldType.CARDINAL_FIELD.toString();
		if(initialValue instanceof IntegerField)
			return FieldType.INTEGER_FIELD.toString();
		if(initialValue instanceof FloatField)
			return FieldType.DECIMAL_FIELD.toString();
		if(initialValue instanceof TableEnumField)
			return FieldType.ENUM_FIELD.toString();
		
		return "";
	}
	
}
