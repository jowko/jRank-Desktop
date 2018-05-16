package pl.jowko.jrank.desktop.feature.learningtable;

import pl.poznan.put.cs.idss.jrs.types.*;

/**
 * Created by Piotr on 2018-05-14.
 */
class RuleRankFieldHelper {
	
	Field createNewFieldOfProvidedType(Field field) {
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
	
	void setFieldValue(Field field, Field newValue) {
		if(field instanceof CardinalField) {
			CardinalField newField = (CardinalField) newValue;
			((CardinalField) field).set(newField.get());
		}
		
		if(field instanceof IntegerField) {
			IntegerField newField = (IntegerField) newValue;
			((IntegerField) field).set(newField.get());
		}
		
		if(field instanceof StringField) {
			StringField newField = (StringField) newValue;
			((StringField) field).set(newField.get());
		}
		
		if(field instanceof TableEnumField) {
			TableEnumField newField = (TableEnumField) newValue;
			((TableEnumField) field).set(newField.getName());
		}
		
		if(field instanceof FloatField) {
			FloatField newField = (FloatField) newValue;
			((FloatField) field).set(newField.get());
		}
	}
	
	String getColumnFieldType(Field initialValue) {
		if(initialValue instanceof StringField)
			return "String";
		if(initialValue instanceof CardinalField)
			return "Cardinal";
		if(initialValue instanceof IntegerField)
			return "Integer";
		if(initialValue instanceof FloatField)
			return "Float";
		if(initialValue instanceof TableEnumField)
			return "Enum";
		
		return "";
	}
	
}
