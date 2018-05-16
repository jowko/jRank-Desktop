package pl.jowko.jrank.desktop.feature.learningtable;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.poznan.put.cs.idss.jrs.core.InvalidValueException;
import pl.poznan.put.cs.idss.jrs.types.EnumDomain;
import pl.poznan.put.cs.idss.jrs.types.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by Piotr on 2018-05-16.
 */
class EnumFieldConverterTest {
	
	private EnumDomain domain;
	private EnumFieldConverter converter;
	
	@BeforeEach
	void setUpEach() {
		domain = new EnumDomain(new String[]{"1", "2", "3"});
		converter = new EnumFieldConverter(domain);
	}
	
	@Test
	void shouldGetValidString() {
		String convertedValue = converter.toString(new TableEnumField("1", domain));
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
