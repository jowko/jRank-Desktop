package pl.jowko.jrank.desktop.feature.learningtable;

import pl.poznan.put.cs.idss.jrs.types.Attribute;
import pl.poznan.put.cs.idss.jrs.types.EnumField;
import pl.poznan.put.cs.idss.jrs.types.Example;
import pl.poznan.put.cs.idss.jrs.types.Field;

/**
 * Created by Piotr on 2018-05-13.
 * This class replaces EnumField classes with custom implementation to override equals method.
 * Without it, using EnumField in JavaFX causes NullPointerException.
 */
class EnumReplacer {
	
	/**
	 * This method replace EnumField in table to TableEnumField.
	 * All EnumField from attributes and examples are replaced.
	 * @param table containing examples and attributes
	 */
	void replaceJRSEnumsWithTableEnumFields(LearningTable table) {
		replaceJRSEnumsInAttributes(table);
		replaceJRSEnumsInExamples(table);
	}
	
	void replaceTableEnumsWithJRSEnums(LearningTable table) {
		replaceTableEnumsInAttributes(table);
		replaceTableEnumsInExamples(table);
	}
	
	private void replaceJRSEnumsInAttributes(LearningTable table) {
		for(Attribute attribute : table.getAttributes()) {
			if(attribute.getInitialValue() instanceof EnumField) {
				EnumField field = (EnumField) attribute.getInitialValue();
				attribute.setInitialValue(new TableEnumField(field));
			}
		}
	}
	
	private void replaceJRSEnumsInExamples(LearningTable table) {
		for(Example example : table.getExamples()) {
			Field[] fields = example.getFields();
			
			for(int i=0; i<fields.length; i++) {
				if(fields[i] instanceof EnumField) {
					fields[i] = new TableEnumField((EnumField) fields[i]);
				}
			}
		}
	}
	
	private void replaceTableEnumsInAttributes(LearningTable table) {
		for(Attribute attribute : table.getAttributes()) {
			if(attribute.getInitialValue() instanceof TableEnumField) {
				TableEnumField field = (TableEnumField) attribute.getInitialValue();
				attribute.setInitialValue(new EnumField(field.getIndex(), field.getDomain()));
			}
		}
	}
	
	private void replaceTableEnumsInExamples(LearningTable table) {
		for(Example example : table.getExamples()) {
			Field[] fields = example.getFields();
			
			for(int i=0; i<fields.length; i++) {
				if(fields[i] instanceof TableEnumField) {
					TableEnumField field = ((TableEnumField) fields[i]);
					fields[i] = new EnumField(field.getIndex(), field.getDomain());
				}
			}
		}
	}
	
}
