package pl.jowko.jrank.desktop.feature.learningtable.dialogs;

import pl.jowko.jrank.desktop.feature.learningtable.TableEnumField;
import pl.jowko.jrank.logger.JRankLogger;
import pl.poznan.put.cs.idss.jrs.types.*;

/**
 * Created by Piotr on 2018-05-19.
 * This class contains some methods used in attribute dialog form.
 */
class AttributeDialogHelper {
	
	/**
	 * This method checks what type was selected in form and return new Field object with corresponding type.
	 * @param value of field from UI form
	 * @param enumText from TextArea containing enum categories
	 * @return new Field of type corresponding to value param
	 */
	Field getFieldFromForm(FieldType value, String enumText) {
		switch (value) {
			case STRING_FIELD:
				return new StringField();
			case INTEGER_FIELD:
				return new IntegerField();
			case CARDINAL_FIELD:
				return new CardinalField();
			case DECIMAL_FIELD:
				return new FloatField();
			case ENUM_FIELD:
				return createEnumFromForm(enumText);
		}
		JRankLogger.warn("Field type was not recognized. Setting default StringField.");
		
		return new StringField();
	}
	
	/**
	 * This methods checks what field type was used in attribute and return corresponding enum FieldType.
	 * @param field from attribute
	 * @return enum corresponding to field type of attribute
	 */
	FieldType getFieldTypeFromField(Field field) {
		if(field instanceof StringField)
			return FieldType.STRING_FIELD;
		if(field instanceof CardinalField)
			return FieldType.CARDINAL_FIELD;
		if(field instanceof IntegerField)
			return FieldType.INTEGER_FIELD;
		if(field instanceof FloatField)
			return FieldType.DECIMAL_FIELD;
		if(field instanceof TableEnumField)
			return FieldType.ENUM_FIELD;
		
		return FieldType.STRING_FIELD;
	}
	
	private TableEnumField createEnumFromForm(String enumText) {
		String[] enums = enumText.split(",");
		EnumDomain domain = new EnumDomain(enums);
		return new TableEnumField(enums[0], domain);
	}
	
}
