package pl.jowko.jrank.desktop.feature.learningtable.dialogs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pl.jowko.jrank.desktop.feature.learningtable.LearningTableDataProvider;
import pl.jowko.jrank.desktop.feature.learningtable.TableEnumField;
import pl.poznan.put.cs.idss.jrs.types.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by Piotr on 2018-05-19.
 */
class AttributeDialogHelperTest {
	
	private AttributeDialogHelper helper;
	
	@BeforeEach
	void setUpEach() {
		helper = new AttributeDialogHelper();
	}
	
	@Test
	void shouldGetStringField() {
		Field field = helper.getFieldFromForm(FieldType.STRING_FIELD, null);
		assertTrue(field instanceof StringField);
	}
	
	@Test
	void shouldGetIntegerField() {
		Field field = helper.getFieldFromForm(FieldType.INTEGER_FIELD, null);
		assertTrue(field instanceof IntegerField);
	}
	
	@Test
	void shouldGetCardinalField() {
		Field field = helper.getFieldFromForm(FieldType.CARDINAL_FIELD, null);
		assertTrue(field instanceof CardinalField);
	}
	
	@Test
	void shouldGetDecimalField() {
		Field field = helper.getFieldFromForm(FieldType.DECIMAL_FIELD, null);
		assertTrue(field instanceof FloatField);
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"test", "1,2,3", "1,2,3,"})
	void shouldGetEnumField(String enumText) {
		Field field = helper.getFieldFromForm(FieldType.ENUM_FIELD, enumText);
		assertTrue(field instanceof TableEnumField);
	}
	
	@Test
	void shouldGetStringFieldType() {
		FieldType type = helper.getFieldTypeFromField(new StringField());
		assertEquals(FieldType.STRING_FIELD, type);
	}
	
	@Test
	void shouldGetIntegerFieldType() {
		FieldType type = helper.getFieldTypeFromField(new IntegerField());
		assertEquals(FieldType.INTEGER_FIELD, type);
	}
	
	@Test
	void shouldGetCardinalFieldType() {
		FieldType type = helper.getFieldTypeFromField(new CardinalField());
		assertEquals(FieldType.CARDINAL_FIELD, type);
	}
	
	@Test
	void shouldGetDecimalFieldType() {
		FieldType type = helper.getFieldTypeFromField(new FloatField());
		assertEquals(FieldType.DECIMAL_FIELD, type);
	}
	
	@Test
	void shouldGetEnumFieldType() {
		TableEnumField enumField = new TableEnumField(0, LearningTableDataProvider.createEnumDomain());
		FieldType type = helper.getFieldTypeFromField(enumField);
		assertEquals(FieldType.ENUM_FIELD, type);
	}
	
}
