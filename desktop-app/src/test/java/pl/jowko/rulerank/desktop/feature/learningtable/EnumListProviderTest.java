package pl.jowko.rulerank.desktop.feature.learningtable;

import org.junit.jupiter.api.Test;
import pl.jowko.rulerank.desktop.feature.learningtable.wrappers.EnumFieldWrapper;
import pl.poznan.put.cs.idss.jrs.types.EnumDomain;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by Piotr on 2018-05-16.
 */
class EnumListProviderTest {
	
	@Test
	void shouldGetValues() {
		String[] values = new String[]{"1", "2", "3"};
		EnumDomain domain = new EnumDomain(values);
		EnumFieldWrapper field = new EnumFieldWrapper("2", domain);
		EnumListProvider provider = new EnumListProvider(field);
		
		List<String> providedValues = provider.getValues();
		assertTrue(providedValues.containsAll(Arrays.asList(values)));
	}
	
}
