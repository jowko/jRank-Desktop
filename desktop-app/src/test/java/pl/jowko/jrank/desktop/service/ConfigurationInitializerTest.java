package pl.jowko.jrank.desktop.service;

import org.junit.jupiter.api.Test;
import pl.jowko.jrank.desktop.MasterTest;

/**
 * Created by Piotr on 2018-05-02.
 */
class ConfigurationInitializerTest extends MasterTest {
	
	@Test
	void shouldInitializeSingletons() {
		new ConfigurationInitializer().initialize();
	}
	
}
