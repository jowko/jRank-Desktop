package pl.jowko.jrank.desktop.feature.learningtable;

import pl.poznan.put.cs.idss.jrs.types.Field;
import pl.poznan.put.cs.idss.jrs.types.FloatField;
import pl.poznan.put.cs.idss.jrs.types.IntegerField;
import pl.poznan.put.cs.idss.jrs.types.StringField;

/**
 * Created by Piotr on 2018-05-14.
 */
class RuleRankFieldHelper {
	
	Field copyField(Field field) {
		if(field instanceof StringField)
			return new StringField(((StringField) field).get());
		if(field instanceof IntegerField)
			return new IntegerField(((IntegerField) field).get());
		if(field instanceof FloatField)
			return new FloatField(((FloatField) field).get());
		if(field instanceof TableEnumField)
			return new TableEnumField(((TableEnumField)field));
		
		return null;
	}
	
	void setFieldValue(Field field, String fieldValue) {
		if(field instanceof IntegerField) {
			((IntegerField) field).set(Integer.valueOf(fieldValue));
		}
		
		if(field instanceof StringField) {
			((StringField) field).set(fieldValue);
		}
		
		if(field instanceof TableEnumField) {
			((TableEnumField) field).set(fieldValue);
		}
		
		if(field instanceof FloatField) {
			((FloatField) field).set(Double.valueOf(fieldValue));
		}
	}
	
	String getColumnFieldType(Field initialValue) {
		if(initialValue instanceof StringField)
			return "String";
		if(initialValue instanceof IntegerField)
			return "Integer";
		if(initialValue instanceof FloatField)
			return "Float";
		if(initialValue instanceof TableEnumField)
			return "Enum";
		
		return "";
	}
	
}
