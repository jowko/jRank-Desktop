package pl.jowko.jrank.desktop;

import org.junit.jupiter.api.BeforeAll;
import pl.jowko.jrank.desktop.settings.ConfigPaths;

/**
 * Created by Piotr on 2018-05-02.
 */
public abstract class MasterTest {
	
	/**
	 * Sets path to config files
	 */
	@BeforeAll
	static void setUp() {
		String parentPath = Thread.currentThread().getContextClassLoader().getResource("data/").getPath();
		ConfigPaths.getInstance().setNewDirectory(parentPath);
	}
	
}
