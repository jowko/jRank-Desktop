package pl.jowko.jrank.desktop.feature.learningtable.dialogs;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pl.jowko.jrank.desktop.feature.learningtable.LearningTableDataProvider;
import pl.jowko.jrank.desktop.feature.learningtable.TableEnumField;
import pl.poznan.put.cs.idss.jrs.types.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.jowko.jrank.desktop.feature.learningtable.dialogs.AttributeDialogHelper.getFieldFromForm;
import static pl.jowko.jrank.desktop.feature.learningtable.dialogs.AttributeDialogHelper.getFieldTypeFromField;

/**
 * Created by Piotr on 2018-05-19.
 */
class AttributeDialogHelperTest {
	
	
	@Test
	void shouldGetStringField() {
		Field field = getFieldFromForm(FieldType.STRING_FIELD, null);
		assertTrue(field instanceof StringField);
	}
	
	@Test
	void shouldGetIntegerField() {
		Field field = getFieldFromForm(FieldType.INTEGER_FIELD, null);
		assertTrue(field instanceof IntegerField);
	}
	
	@Test
	void shouldGetCardinalField() {
		Field field = getFieldFromForm(FieldType.CARDINAL_FIELD, null);
		assertTrue(field instanceof CardinalField);
	}
	
	@Test
	void shouldGetDecimalField() {
		Field field = getFieldFromForm(FieldType.DECIMAL_FIELD, null);
		assertTrue(field instanceof FloatField);
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"test", "1,2,3", "1,2,3,"})
	void shouldGetEnumField(String enumText) {
		Field field = getFieldFromForm(FieldType.ENUM_FIELD, enumText);
		assertTrue(field instanceof TableEnumField);
	}
	
	@Test
	void shouldGetStringFieldType() {
		FieldType type = getFieldTypeFromField(new StringField());
		assertEquals(FieldType.STRING_FIELD, type);
	}
	
	@Test
	void shouldGetIntegerFieldType() {
		FieldType type = getFieldTypeFromField(new IntegerField());
		assertEquals(FieldType.INTEGER_FIELD, type);
	}
	
	@Test
	void shouldGetCardinalFieldType() {
		FieldType type = getFieldTypeFromField(new CardinalField());
		assertEquals(FieldType.CARDINAL_FIELD, type);
	}
	
	@Test
	void shouldGetDecimalFieldType() {
		FieldType type = getFieldTypeFromField(new FloatField());
		assertEquals(FieldType.DECIMAL_FIELD, type);
	}
	
	@Test
	void shouldGetEnumFieldType() {
		TableEnumField enumField = new TableEnumField(0, LearningTableDataProvider.createEnumDomain());
		FieldType type = getFieldTypeFromField(enumField);
		assertEquals(FieldType.ENUM_FIELD, type);
	}
	
}
