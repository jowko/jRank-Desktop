package pl.jowko.rulerank.desktop.feature.learningtable;

import org.junit.jupiter.api.Test;
import pl.poznan.put.cs.idss.jrs.types.Attribute;
import pl.poznan.put.cs.idss.jrs.types.Example;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.jowko.rulerank.desktop.feature.learningtable.LearningTableDataProvider.*;

/**
 * Created by Piotr on 2018-05-27
 */
class UnknownFieldValidatorTest {
	
	@Test
	void tableShouldBeValid() {
		UnknownFieldValidator validator = new UnknownFieldValidator(createFilledLearningTable());
		assertTrue(validator.isValid());
	}
	
	@Test
	void tableShouldBeValidWhenDecisionIsUnknown() {
		LearningTable table = new LearningTable(null, null, null);
		
		List<Attribute> attributes = new ArrayList<>();
		attributes.add(createAttribute("test"));
		attributes.add(createDecisionAttribute("decision", true));
		table.setAttributes(attributes);
		
		List<Example> examples = createExamples(attributes);
		examples.get(0).getFields()[1].setUnknown();
		table.setExamples(examples);
		
		UnknownFieldValidator validator = new UnknownFieldValidator(createFilledLearningTable());
		assertTrue(validator.isValid());
	}
	
	@Test
	void tableShouldNotBeValidWhenConditionFieldsAreUnknown() {
		LearningTable table = createFilledLearningTable();
		table.getExamples().get(1).getFields()[2].setUnknown();
		UnknownFieldValidator validator = new UnknownFieldValidator(table);
		assertFalse(validator.isValid());
	}
	
}
