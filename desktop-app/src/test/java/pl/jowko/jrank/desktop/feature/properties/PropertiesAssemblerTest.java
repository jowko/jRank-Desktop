package pl.jowko.jrank.desktop.feature.properties;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.jowko.jrank.desktop.MasterTest;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.jowko.jrank.desktop.feature.properties.Names.LEARNING_DATA_FILE;
import static pl.jowko.jrank.desktop.feature.properties.Names.REFERENCE_RANKING;

/**
 * Created by Piotr on 2018-05-02.
 */
class PropertiesAssemblerTest extends MasterTest {
	
	@Test
	void shouldMapSimpleProperties() {
		Properties properties = new Properties();
		properties.setProperty(LEARNING_DATA_FILE, "someFile.isf");
		properties.setProperty(REFERENCE_RANKING, "1, 2, 3");

		PropertiesAssembler assembler = new PropertiesAssembler(properties);
		JRankProperties jRankProperties = assembler.toJrankProperties();

		assertEquals("someFile.isf", jRankProperties.getLearningDataFile());
		assertEquals("1, 2, 3", jRankProperties.getReferenceRanking());
	}
	
}
