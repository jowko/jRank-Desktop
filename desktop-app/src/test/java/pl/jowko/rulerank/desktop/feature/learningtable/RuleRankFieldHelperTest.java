package pl.jowko.rulerank.desktop.feature.learningtable;

import org.junit.jupiter.api.Test;
import pl.jowko.rulerank.desktop.feature.learningtable.dialogs.FieldType;
import pl.jowko.rulerank.desktop.feature.learningtable.wrappers.*;
import pl.poznan.put.cs.idss.jrs.types.Field;

import static org.junit.jupiter.api.Assertions.*;
import static pl.jowko.rulerank.desktop.feature.learningtable.LearningTableDataProvider.createEnumDomain;

/**
 * Created by Piotr on 2018-05-16.
 */
class RuleRankFieldHelperTest {
	
	@Test
	void shouldCreateNewStringField() {
		StringFieldWrapper field = new StringFieldWrapper("test");
		Field newField = RuleRankFieldHelper.createNewFieldOfProvidedType(field);
		
		assertTrue(newField instanceof StringFieldWrapper);
		assertNotEquals(field.get(), ((StringFieldWrapper)newField).get());
	}
	
	@Test
	void shouldCreateNewCardinalField() {
		CardinalFieldWrapper field = new CardinalFieldWrapper(3);
		Field newField = RuleRankFieldHelper.createNewFieldOfProvidedType(field);
		
		assertTrue(newField instanceof CardinalFieldWrapper);
		assertNotEquals(field.get(), ((CardinalFieldWrapper)newField).get());
	}
	
	@Test
	void shouldCreateNewIntegerField() {
		IntegerFieldWrapper field = new IntegerFieldWrapper(2);
		Field newField = RuleRankFieldHelper.createNewFieldOfProvidedType(field);
		
		assertTrue(newField instanceof IntegerFieldWrapper);
		assertNotEquals(field.get(), ((IntegerFieldWrapper)newField).get());
	}
	
	@Test
	void shouldCreateNewFloatField() {
		FloatFieldWrapper field = new FloatFieldWrapper(2);
		Field newField = RuleRankFieldHelper.createNewFieldOfProvidedType(field);
		
		assertTrue(newField instanceof FloatFieldWrapper);
		assertNotEquals(field.get(), ((FloatFieldWrapper)newField).get());
	}
	
	@Test
	void shouldCreateNewEnumField() {
		EnumFieldWrapper field = new EnumFieldWrapper(1, createEnumDomain());
		Field newField = RuleRankFieldHelper.createNewFieldOfProvidedType(field);
		
		assertTrue(newField instanceof EnumFieldWrapper);
		assertEquals(field.getDomain(), ((EnumFieldWrapper)newField).getDomain());
		assertNotEquals(field.getIndex(), ((EnumFieldWrapper)newField).getIndex());
	}
	
	@Test
	void shouldGetColumnTypeForStringField() {
		String fieldName = RuleRankFieldHelper.getColumnFieldType(new StringFieldWrapper());
		assertEquals(FieldType.STRING_FIELD.toString(), fieldName);
	}
	
	@Test
	void shouldGetColumnTypeForCardinalField() {
		String fieldName = RuleRankFieldHelper.getColumnFieldType(new CardinalFieldWrapper());
		assertEquals(FieldType.CARDINAL_FIELD.toString(), fieldName);
	}
	
	@Test
	void shouldGetColumnTypeForIntegerField() {
		String fieldName = RuleRankFieldHelper.getColumnFieldType(new IntegerFieldWrapper());
		assertEquals(FieldType.INTEGER_FIELD.toString(), fieldName);
	}
	
	@Test
	void shouldGetColumnTypeForDecimalField() {
		String fieldName = RuleRankFieldHelper.getColumnFieldType(new FloatFieldWrapper());
		assertEquals(FieldType.DECIMAL_FIELD.toString(), fieldName);
	}
	
	@Test
	void shouldGetColumnTypeForEnumField() {
		String fieldName = RuleRankFieldHelper.getColumnFieldType(new EnumFieldWrapper(0, createEnumDomain()));
		assertEquals(FieldType.ENUM_FIELD.toString(), fieldName);
	}
	
}
