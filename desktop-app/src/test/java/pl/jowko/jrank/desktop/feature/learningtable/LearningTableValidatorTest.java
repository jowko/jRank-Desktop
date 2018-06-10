package pl.jowko.jrank.desktop.feature.learningtable;

import org.junit.jupiter.api.Test;
import pl.poznan.put.cs.idss.jrs.types.Attribute;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.jowko.jrank.desktop.feature.learningtable.LearningTableDataProvider.*;

/**
 * Created by Piotr on 2018-05-16.
 */
class LearningTableValidatorTest {
	
	@Test
	void decisionAttributeShouldBeValid() {
		List<Attribute> attributes = new ArrayList<>();
		attributes.add(createDecisionAttribute("decision1", true));
		attributes.add(createAttribute("test"));
		LearningTable table = createLearningTable(attributes);
		
		LearningTableValidator validator = new LearningTableValidator(table);
		
		assertTrue(validator.isValid());
	}
	
	@Test
	void decisionAttributesShouldBeValidWhenOnlyOneActive() {
		List<Attribute> attributes = new ArrayList<>();
		attributes.add(createDecisionAttribute("decision1", true));
		attributes.add(createDecisionAttribute("decision2", false));
		attributes.add(createAttribute("test"));
		LearningTable table = createLearningTable(attributes);
		
		LearningTableValidator validator = new LearningTableValidator(table);
		
		assertTrue(validator.isValid());
	}
	
	@Test
	void decisionAttributesShouldNotBeValidWhenManyAreActive() {
		List<Attribute> attributes = new ArrayList<>();
		attributes.add(createDecisionAttribute("decision1", true));
		attributes.add(createDecisionAttribute("decision2", true));
		attributes.add(createAttribute("test"));
		LearningTable table = createLearningTable(attributes);
		
		LearningTableValidator validator = new LearningTableValidator(table);
		
		assertFalse(validator.isValid());
		assertTrue(validator.getErrorMessages().contains("decision1"));
		assertTrue(validator.getErrorMessages().contains("decision2"));
	}
	
	@Test
	void shouldDetectNonUniqueAttributeNames() {
		List<Attribute> attributes = new ArrayList<>();
		attributes.add(createAttribute("name"));
		attributes.add(createAttribute("name"));
		attributes.add(createAttribute("name2"));
		attributes.add(createAttribute("name2"));
		LearningTable table = createLearningTable(attributes);
		
		LearningTableValidator validator = new LearningTableValidator(table);
		
		assertFalse(validator.isValid());
		assertTrue(validator.getErrorMessages().contains("name"));
		assertTrue(validator.getErrorMessages().contains("name2"));
	}
	
	@Test
	void shouldDetectLackOfConditionAttribute() {
		List<Attribute> attributes = new ArrayList<>();
		
		Attribute attribute = createAttribute("test");
		attribute.setKind(Attribute.DESCRIPTION);
		attributes.add(attribute);
		
		Attribute attribute2 = createAttribute("test2");
		attribute2.setPreferenceType(Attribute.NONE);
		attributes.add(attribute2);
		
		LearningTable table = createLearningTable(attributes);
		
		LearningTableValidator validator = new LearningTableValidator(table);
		assertFalse(validator.isValid());
	}
	
}
