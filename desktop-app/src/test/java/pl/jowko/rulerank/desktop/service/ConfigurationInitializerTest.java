package pl.jowko.rulerank.desktop.service;

import org.junit.jupiter.api.Test;
import pl.jowko.rulerank.desktop.MasterTest;
import pl.jowko.rulerank.desktop.feature.settings.ConfigurationInitializer;

/**
 * Created by Piotr on 2018-05-02.
 */
class ConfigurationInitializerTest extends MasterTest {
	
	@Test
	void shouldInitializeSingletons() {
		new ConfigurationInitializer().initialize();
	}
	
}
