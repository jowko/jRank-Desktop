package pl.jowko.rulerank.desktop;

import org.junit.jupiter.api.BeforeAll;
import pl.jowko.rulerank.desktop.feature.settings.ConfigPaths;

/**
 * Created by Piotr on 2018-05-02.
 * MasterTest should be used in tests, where configuration files are used.
 * This class sets paths to configuration files in resources catalog.
 */
public abstract class MasterTest {
	
	/**
	 * Sets path to config files
	 */
	@BeforeAll
	static void setUp() {
		String parentPath = Thread.currentThread().getContextClassLoader().getResource("data/").getPath().replace("%20", " ");
		ConfigPaths.getInstance().setNewDirectory(parentPath);
	}
	
}
