package pl.jowko.rulerank.desktop.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.jowko.rulerank.desktop.utils.BooleanUtils.not;

/**
 * Created by Piotr on 2018-05-02.
 */
class BooleanUtilsTest {
	
	@Test
	void shouldChangeTrueToFalse() {
		assertEquals(true, not(false));
	}
	
	@Test
	void shouldChangeFalseToTrue() {
		assertEquals(false, not(true));
	}
	
}
