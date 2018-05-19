package pl.jowko.jrank.desktop.feature.learningtable;

import org.junit.jupiter.api.Test;
import pl.poznan.put.cs.idss.jrs.types.Attribute;
import pl.poznan.put.cs.idss.jrs.types.IntegerField;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.jowko.jrank.desktop.feature.learningtable.LearningTableDataProvider.STRING_COLUMN_NAME;
import static pl.jowko.jrank.desktop.feature.learningtable.LearningTableDataProvider.createLearningTable;

/**
 * Created by Piotr on 2018-05-16.
 */
class LearningTableValidatorTest {
	
	@Test
	void stringFieldsShouldBeValid() {
		LearningTable table = LearningTableDataProvider.createFilledLearningTable();
		LearningTableValidator validator = new LearningTableValidator(table);
		
		assertTrue(validator.isValid());
		assertFalse(validator.getErrorMsg().contains(STRING_COLUMN_NAME));
	}
	
	@Test
	void tableShouldNotBeValidEmptyStringFields() {
		LearningTable table = createLearningTable();
		LearningTableValidator validator = new LearningTableValidator(table);
		
		assertFalse(validator.isValid());
		assertTrue(validator.getErrorMsg().contains(STRING_COLUMN_NAME));
	}
	
	@Test
	void decisionAttributeShouldBeValid() {
		List<Attribute> attributes = new ArrayList<>();
		attributes.add(createDecisionAttribute("decision1", true));
		LearningTable table = createLearningTable(attributes);
		
		LearningTableValidator validator = new LearningTableValidator(table);
		
		assertTrue(validator.isDecisionAttributesValid());
	}
	
	@Test
	void decisionAttributesShouldBeValidWhenOnlyOneActive() {
		List<Attribute> attributes = new ArrayList<>();
		attributes.add(createDecisionAttribute("decision1", true));
		attributes.add(createDecisionAttribute("decision2", false));
		LearningTable table = createLearningTable(attributes);
		
		LearningTableValidator validator = new LearningTableValidator(table);
		
		assertTrue(validator.isDecisionAttributesValid());
	}
	
	@Test
	void decisionAttributesShouldNotBeValidWhenManyAreActive() {
		List<Attribute> attributes = new ArrayList<>();
		attributes.add(createDecisionAttribute("decision1", true));
		attributes.add(createDecisionAttribute("decision2", true));
		LearningTable table = createLearningTable(attributes);
		
		LearningTableValidator validator = new LearningTableValidator(table);
		
		assertFalse(validator.isDecisionAttributesValid());
		assertTrue(validator.getDecisionMsg().contains("decision1"));
		assertTrue(validator.getDecisionMsg().contains("decision2"));
	}
	
	private Attribute createDecisionAttribute(String name, boolean isActive) {
		Attribute attribute = new Attribute(name, new IntegerField());
		attribute.setKind(Attribute.DECISION);
		attribute.setActive(isActive);
		return attribute;
	}
	
}
