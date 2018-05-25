package pl.jowko.jrank.desktop.feature.learningtable;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.jowko.jrank.desktop.feature.learningtable.wrappers.EnumFieldWrapper;
import pl.poznan.put.cs.idss.jrs.core.InvalidValueException;
import pl.poznan.put.cs.idss.jrs.types.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static pl.jowko.jrank.desktop.feature.learningtable.LearningTableDataProvider.createEnumDomain;

/**
 * Created by Piotr on 2018-05-16.
 */
class EnumFieldConverterTest {
	
	private EnumFieldConverter converter;
	
	@BeforeEach
	void setUpEach() {
		converter = new EnumFieldConverter(createEnumDomain());
	}
	
	@Test
	void shouldGetValidString() {
		String convertedValue = converter.toString(new EnumFieldWrapper("1", createEnumDomain()));
		assertEquals("1", convertedValue);
	}
	
	@Test
	void shouldGetEmptyString() {
		String convertedValue = converter.toString(null);
		assertEquals("", convertedValue);
	}
	
	@Test
	void shouldGetFieldFromString() {
		Field field = converter.fromString("2");
		assertEquals("2", field.toString());
	}
	
	@Test
	void shouldNotGetFieldFromString() {
		assertThrows(InvalidValueException.class, () -> converter.fromString("254"));
	}
	
}
