package pl.jowko.jrank.desktop.feature.learningtable.dialogs;

import pl.jowko.jrank.desktop.feature.learningtable.wrappers.*;
import pl.jowko.jrank.logger.JRankLogger;
import pl.poznan.put.cs.idss.jrs.types.CardinalField;
import pl.poznan.put.cs.idss.jrs.types.EnumDomain;
import pl.poznan.put.cs.idss.jrs.types.Field;

import static pl.jowko.jrank.desktop.utils.BooleanUtils.not;

/**
 * Created by Piotr on 2018-05-19.
 * This class contains some methods used in attribute dialog form.
 */
public class AttributeDialogHelper {
	
	/**
	 * This method checks what type was selected in form and return new Field object with corresponding type.
	 * @see FieldType
	 * @param value of field from UI form
	 * @param enumText from TextArea containing enum categories
	 * @return new Field of type corresponding to value param
	 */
	static Field getFieldFromForm(FieldType value, String enumText) {
		switch (value) {
			case STRING_FIELD:
				return new StringFieldWrapper();
			case INTEGER_FIELD:
				return new IntegerFieldWrapper();
			case CARDINAL_FIELD:
				return new CardinalFieldWrapper();
			case DECIMAL_FIELD:
				return new FloatFieldWrapper();
			case ENUM_FIELD:
				return createEnumFromForm(enumText);
		}
		JRankLogger.warn("Field type was not recognized. Setting default StringField.");
		
		return new StringFieldWrapper();
	}
	
	/**
	 * This methods checks what field type was used in attribute and return corresponding enum FieldType.
	 * @see FieldType
	 * @param field from attribute
	 * @return enum corresponding to field type of attribute
	 */
	public static FieldType getFieldTypeFromField(Field field) {
		if(field instanceof StringFieldWrapper)
			return FieldType.STRING_FIELD;
		if(field instanceof CardinalField)
			return FieldType.CARDINAL_FIELD;
		if(field instanceof IntegerFieldWrapper)
			return FieldType.INTEGER_FIELD;
		if(field instanceof FloatFieldWrapper)
			return FieldType.DECIMAL_FIELD;
		if(field instanceof EnumFieldWrapper)
			return FieldType.ENUM_FIELD;
		
		return FieldType.STRING_FIELD;
	}
	
	/**
	 * Extract domain elements from enum TextArea.
	 * Element are extracted by splitting text by commas.
	 * If multiple commas in a row were provided, they will return empty Strings from split method.
	 * This empty Strings are not added to domain.
	 * @param enumText from enum TextArea
	 * @return EnumField containing elements extracted from EnumTextArea
	 */
	private static Field createEnumFromForm(String enumText) {
		String[] enums = enumText.split(",");
		EnumDomain domain = new EnumDomain();
		
		for(String element : enums) {
			if(not(element.isEmpty()))
				domain.addElement(element);
		}
		
		return new EnumFieldWrapper(0, domain);
	}
	
}
