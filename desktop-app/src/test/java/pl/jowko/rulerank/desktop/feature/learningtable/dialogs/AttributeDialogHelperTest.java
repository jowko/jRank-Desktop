package pl.jowko.rulerank.desktop.feature.learningtable.dialogs;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pl.jowko.rulerank.desktop.feature.learningtable.LearningTableDataProvider;
import pl.jowko.rulerank.desktop.feature.learningtable.wrappers.*;
import pl.poznan.put.cs.idss.jrs.types.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.jowko.rulerank.desktop.feature.learningtable.dialogs.AttributeDialogHelper.getFieldFromForm;
import static pl.jowko.rulerank.desktop.feature.learningtable.dialogs.AttributeDialogHelper.getFieldTypeFromField;

/**
 * Created by Piotr on 2018-05-19.
 */
class AttributeDialogHelperTest {
	
	
	@Test
	void shouldGetStringField() {
		Field field = getFieldFromForm(FieldType.STRING_FIELD, null);
		assertTrue(field instanceof StringFieldWrapper);
	}
	
	@Test
	void shouldGetIntegerField() {
		Field field = getFieldFromForm(FieldType.INTEGER_FIELD, null);
		assertTrue(field instanceof IntegerFieldWrapper);
	}
	
	@Test
	void shouldGetCardinalField() {
		Field field = getFieldFromForm(FieldType.CARDINAL_FIELD, null);
		assertTrue(field instanceof CardinalFieldWrapper);
	}
	
	@Test
	void shouldGetDecimalField() {
		Field field = getFieldFromForm(FieldType.DECIMAL_FIELD, null);
		assertTrue(field instanceof FloatFieldWrapper);
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"test", "1,2,3", "1,2,3,"})
	void shouldGetEnumField(String enumText) {
		Field field = getFieldFromForm(FieldType.ENUM_FIELD, enumText);
		assertTrue(field instanceof EnumFieldWrapper);
	}
	
	@Test
	void shouldGetStringFieldType() {
		FieldType type = getFieldTypeFromField(new StringFieldWrapper());
		assertEquals(FieldType.STRING_FIELD, type);
	}
	
	@Test
	void shouldGetIntegerFieldType() {
		FieldType type = getFieldTypeFromField(new IntegerFieldWrapper());
		assertEquals(FieldType.INTEGER_FIELD, type);
	}
	
	@Test
	void shouldGetCardinalFieldType() {
		FieldType type = getFieldTypeFromField(new CardinalFieldWrapper());
		assertEquals(FieldType.CARDINAL_FIELD, type);
	}
	
	@Test
	void shouldGetDecimalFieldType() {
		FieldType type = getFieldTypeFromField(new FloatFieldWrapper());
		assertEquals(FieldType.DECIMAL_FIELD, type);
	}
	
	@Test
	void shouldGetEnumFieldType() {
		EnumFieldWrapper enumField = new EnumFieldWrapper(0, LearningTableDataProvider.createEnumDomain());
		FieldType type = getFieldTypeFromField(enumField);
		assertEquals(FieldType.ENUM_FIELD, type);
	}
	
}
