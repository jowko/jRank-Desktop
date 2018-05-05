package pl.jowko.jrank.desktop.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.jowko.jrank.desktop.utils.StringUtils.isNotNullOrEmpty;
import static pl.jowko.jrank.desktop.utils.StringUtils.isNullOrEmpty;

/**
 * Created by Piotr on 2018-05-05.
 */
class StringUtilsTest {
	
	@Test
	void isNotNullOrEmptyShouldReturnTrue() {
		assertTrue(isNotNullOrEmpty(" Value "));
	}
	
	@Test
	void isNullOrEmptyShouldReturnTrue() {
		assertTrue(isNullOrEmpty(""));
		assertTrue(isNullOrEmpty(" "));
		assertTrue(isNullOrEmpty(null));
	}
	
	@Test
	void isNotNullOrEmptyShouldReturnFalse() {
		assertFalse(isNotNullOrEmpty(""));
		assertFalse(isNotNullOrEmpty(" "));
		assertFalse(isNotNullOrEmpty(null));
	}
	
	@Test
	void isNullOrEmptyShouldReturnFalse() {
		assertFalse(isNullOrEmpty(" Value "));
	}
	
}
