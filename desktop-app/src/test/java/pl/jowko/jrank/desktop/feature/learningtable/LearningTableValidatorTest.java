package pl.jowko.jrank.desktop.feature.learningtable;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.jowko.jrank.desktop.feature.learningtable.LearningTableDataProvider.STRING_COLUMN_NAME;

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
		LearningTable table = LearningTableDataProvider.createLearningTable();
		LearningTableValidator validator = new LearningTableValidator(table);
		
		assertFalse(validator.isValid());
		assertTrue(validator.getErrorMsg().contains(STRING_COLUMN_NAME));
	}
	
}
