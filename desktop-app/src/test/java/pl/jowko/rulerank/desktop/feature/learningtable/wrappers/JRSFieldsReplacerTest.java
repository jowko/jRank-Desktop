package pl.jowko.rulerank.desktop.feature.learningtable.wrappers;

import org.junit.jupiter.api.Test;
import pl.jowko.rulerank.desktop.feature.learningtable.LearningTable;
import pl.jowko.rulerank.desktop.feature.learningtable.LearningTableDataProvider;
import pl.poznan.put.cs.idss.jrs.types.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by Piotr on 2018-05-16.
 */
class JRSFieldsReplacerTest {
	
	@Test
	void shouldReplaceJRSEnumsInAttributes() {
		LearningTable table = LearningTableDataProvider.createLearningTable();
		JRSFieldsReplacer.replaceJRSEnumsWithTableEnumFields(table);
		Attribute[] attributes = table.getAttributes().toArray(new Attribute[]{});
		
		assertTrue(attributes[0].getInitialValue() instanceof IntegerFieldWrapper);
		assertTrue(attributes[1].getInitialValue() instanceof StringFieldWrapper);
		assertTrue(attributes[2].getInitialValue() instanceof EnumFieldWrapper);
		assertTrue(attributes[3].getInitialValue() instanceof FloatFieldWrapper);
	}
	
	@Test
	void shouldReplaceJRSEnumsInExamples() {
		LearningTable table = LearningTableDataProvider.createLearningTable();
		JRSFieldsReplacer.replaceJRSEnumsWithTableEnumFields(table);
		
		for(Example example : table.getExamples()) {
			Field[] fields = example.getFields();
			assertTrue(fields[0] instanceof IntegerFieldWrapper);
			assertTrue(fields[1] instanceof StringFieldWrapper);
			assertTrue(fields[2] instanceof EnumFieldWrapper);
			assertTrue(fields[3] instanceof FloatFieldWrapper);
		}
	}
	
	@Test
	void shouldReplaceTableEnumsInAttributes() {
		LearningTable table = LearningTableDataProvider.createLearningTable();
		JRSFieldsReplacer.replaceJRSEnumsWithTableEnumFields(table);
		JRSFieldsReplacer.replaceWrappersWithJRSEnums(table);
		Attribute[] attributes = table.getAttributes().toArray(new Attribute[]{});
		
		assertTrue(attributes[0].getInitialValue() instanceof IntegerField);
		assertTrue(attributes[1].getInitialValue() instanceof StringField);
		assertTrue(attributes[2].getInitialValue() instanceof EnumField);
		assertTrue(attributes[3].getInitialValue() instanceof FloatField);
	}
	
	@Test
	void shouldReplaceTableEnumsInExamples() {
		LearningTable table = LearningTableDataProvider.createLearningTable();
		JRSFieldsReplacer.replaceJRSEnumsWithTableEnumFields(table);
		JRSFieldsReplacer.replaceWrappersWithJRSEnums(table);
		
		for(Example example : table.getExamples()) {
			Field[] fields = example.getFields();
			assertTrue(fields[0] instanceof IntegerField);
			assertFalse(fields[0] instanceof IntegerFieldWrapper);
			assertTrue(fields[1] instanceof StringField);
			assertFalse(fields[1] instanceof StringFieldWrapper);
			assertTrue(fields[2] instanceof EnumField);
			assertFalse(fields[2] instanceof EnumFieldWrapper);
			assertTrue(fields[3] instanceof FloatField);
			assertFalse(fields[3] instanceof FloatFieldWrapper);
		}
	}
	
}
