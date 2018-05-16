package pl.jowko.jrank.desktop.feature.learningtable;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import pl.jowko.jrank.desktop.feature.learningtable.dialogs.FieldType;
import pl.poznan.put.cs.idss.jrs.types.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by Piotr on 2018-05-16.
 */
class RuleRankFieldHelperTest {
	
	@Test
	void shouldCreateNewStringField() {
		StringField field = new StringField("test");
		Field newField = RuleRankFieldHelper.createNewFieldOfProvidedType(field);
		
		assertTrue(newField instanceof StringField);
		assertNotEquals(field.get(), ((StringField)newField).get());
	}
	
	@Test
	void shouldCreateNewCardinalField() {
		CardinalField field = new CardinalField(3);
		Field newField = RuleRankFieldHelper.createNewFieldOfProvidedType(field);
		
		assertTrue(newField instanceof CardinalField);
		assertNotEquals(field.get(), ((CardinalField)newField).get());
	}
	
	@Test
	void shouldCreateNewIntegerField() {
		IntegerField field = new IntegerField(2);
		Field newField = RuleRankFieldHelper.createNewFieldOfProvidedType(field);
		
		assertTrue(newField instanceof IntegerField);
		assertNotEquals(field.get(), ((IntegerField)newField).get());
	}
	
	@Test
	void shouldCreateNewFloatField() {
		FloatField field = new FloatField(2);
		Field newField = RuleRankFieldHelper.createNewFieldOfProvidedType(field);
		
		assertTrue(newField instanceof FloatField);
		assertNotEquals(field.get(), ((FloatField)newField).get());
	}
	
	@Test
	void shouldCreateNewEnumField() {
		EnumDomain domain = new EnumDomain(new String[]{"1", "2"});
		TableEnumField field = new TableEnumField(1, domain);
		Field newField = RuleRankFieldHelper.createNewFieldOfProvidedType(field);
		
		assertTrue(newField instanceof TableEnumField);
		assertEquals(field.getDomain(), ((TableEnumField)newField).getDomain());
		assertNotEquals(field.getIndex(), ((TableEnumField)newField).getIndex());
	}
	
	@Test
	void shouldGetColumnTypeForStringField() {
		String fieldName = RuleRankFieldHelper.getColumnFieldType(new StringField());
		assertEquals(FieldType.STRING_FIELD.toString(), fieldName);
	}
	
	@Test
	void shouldGetColumnTypeForCardinalField() {
		String fieldName = RuleRankFieldHelper.getColumnFieldType(new CardinalField());
		assertEquals(FieldType.CARDINAL_FIELD.toString(), fieldName);
	}
	
	@Test
	void shouldGetColumnTypeForIntegerField() {
		String fieldName = RuleRankFieldHelper.getColumnFieldType(new IntegerField());
		assertEquals(FieldType.INTEGER_FIELD.toString(), fieldName);
	}
	
	@Test
	void shouldGetColumnTypeForDecimalField() {
		String fieldName = RuleRankFieldHelper.getColumnFieldType(new FloatField());
		assertEquals(FieldType.DECIMAL_FIELD.toString(), fieldName);
	}
	
	@Test
	void shouldGetColumnTypeForEnumField() {
		EnumDomain domain = new EnumDomain(new String[]{"1", "2"});
		String fieldName = RuleRankFieldHelper.getColumnFieldType(new TableEnumField(0, domain));
		assertEquals(FieldType.ENUM_FIELD.toString(), fieldName);
	}
	
}