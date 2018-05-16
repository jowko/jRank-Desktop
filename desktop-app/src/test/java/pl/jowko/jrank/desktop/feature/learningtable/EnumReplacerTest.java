package pl.jowko.jrank.desktop.feature.learningtable;

import org.junit.jupiter.api.Test;
import pl.poznan.put.cs.idss.jrs.types.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by Piotr on 2018-05-16.
 */
class EnumReplacerTest {
	
	@Test
	void shouldReplaceJRSEnumsInAttributes() {
		LearningTable table = LearningTableDataProvider.createLearningTable();
		EnumReplacer.replaceJRSEnumsWithTableEnumFields(table);
		Attribute[] attributes = table.getAttributes().toArray(new Attribute[]{});
		
		assertTrue(attributes[0].getInitialValue() instanceof IntegerField);
		assertTrue(attributes[1].getInitialValue() instanceof StringField);
		assertTrue(attributes[2].getInitialValue() instanceof TableEnumField);
		assertTrue(attributes[3].getInitialValue() instanceof FloatField);
	}
	
	@Test
	void shouldReplaceJRSEnumsInExamples() {
		LearningTable table = LearningTableDataProvider.createLearningTable();
		EnumReplacer.replaceJRSEnumsWithTableEnumFields(table);
		
		for(Example example : table.getExamples()) {
			Field[] fields = example.getFields();
			assertTrue(fields[0] instanceof IntegerField);
			assertTrue(fields[1] instanceof StringField);
			assertTrue(fields[2] instanceof TableEnumField);
			assertTrue(fields[3] instanceof FloatField);
		}
	}
	
	@Test
	void shouldReplaceTableEnumsInAttributes() {
		LearningTable table = LearningTableDataProvider.createLearningTable();
		EnumReplacer.replaceJRSEnumsWithTableEnumFields(table);
		EnumReplacer.replaceTableEnumsWithJRSEnums(table);
		Attribute[] attributes = table.getAttributes().toArray(new Attribute[]{});
		
		assertTrue(attributes[0].getInitialValue() instanceof IntegerField);
		assertTrue(attributes[1].getInitialValue() instanceof StringField);
		assertTrue(attributes[2].getInitialValue() instanceof EnumField);
		assertTrue(attributes[3].getInitialValue() instanceof FloatField);
	}
	
	@Test
	void shouldReplaceTableEnumsInExamples() {
		LearningTable table = LearningTableDataProvider.createLearningTable();
		EnumReplacer.replaceJRSEnumsWithTableEnumFields(table);
		EnumReplacer.replaceTableEnumsWithJRSEnums(table);
		
		for(Example example : table.getExamples()) {
			Field[] fields = example.getFields();
			assertTrue(fields[0] instanceof IntegerField);
			assertTrue(fields[1] instanceof StringField);
			assertTrue(fields[2] instanceof EnumField);
			assertTrue(fields[3] instanceof FloatField);
		}
	}
	
}
