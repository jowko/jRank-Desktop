package pl.jowko.rulerank.desktop.feature.settings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Piotr on 2018-05-03.
 */
class ConfigPathsTest {
	
	private ConfigPaths configPaths;
	
	@BeforeEach
	void setUpEach() {
		configPaths = ConfigPaths.getInstance();
		configPaths.setNewDirectory("data/");
	}
	
	@Test
	void pathsShouldBeValid() {
		assertEquals("data/userSettings.json", configPaths.getUserSettingsPath());
		assertEquals("data/labels.json", configPaths.getLabelsPath());
		assertEquals("data/languages.json", configPaths.getLanguagesPath());
		assertEquals("data/jRankInfo.json", configPaths.getJRankInfoPath());
	}
	
	@Test
	void shouldChangePaths() {
		configPaths.setNewDirectory("someDirectory/forTests/");
		assertEquals("someDirectory/forTests/userSettings.json", configPaths.getUserSettingsPath());
		assertEquals("someDirectory/forTests/labels.json", configPaths.getLabelsPath());
		assertEquals("someDirectory/forTests/languages.json", configPaths.getLanguagesPath());
		assertEquals("someDirectory/forTests/jRankInfo.json", configPaths.getJRankInfoPath());
	}
	
}
