package pl.jowko.jrank.desktop.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.jowko.jrank.desktop.MasterTest;
import pl.jowko.jrank.desktop.settings.JRankInfo;
import pl.jowko.jrank.desktop.settings.UserSettings;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Piotr on 2018-05-02.
 */
class FileManagerTest extends MasterTest {
	
	private FileManager fileManager;
	
	@BeforeEach
	void setUpEach() {
		fileManager = FileManager.getInstance();
	}
	
	@AfterAll
	static void restoreUserSettings() throws IOException {
		UserSettings userSettings = new UserSettings("ENG", "\\workspace", true);
		FileManager.getInstance().saveUserSettings(userSettings);
		FileManager.getInstance().readUserSettings();
	}
	
	@Test
	void shouldReadUserSettings() {
		UserSettings userSettings = fileManager.readUserSettings();
		assertNotNull(userSettings);
	}
	
	@Test
	void shouldReadJRankInfo() {
		JRankInfo info = fileManager.readJRankInfo();
		assertNotNull(info);
	}
	
	@Test
	void shouldReadLabels() {
		Map<String, Map<String, String> > labels = fileManager.readLabels();
		
		assertNotNull(labels);
		assertTrue(labels.size() > 0);
		assertTrue(labels.get("ENG").size() > 0);
		assertTrue(labels.get("POL").size() > 0);
	}
	
	@Test
	void shouldReadLanguages() {
		Map<String, String> languages = fileManager.readLanguages();
		
		assertNotNull(languages);
		assertTrue(languages.size() > 0);
	}
	
	@Test
	void shouldSaveUserSettings() throws IOException {
		UserSettings userSettings = new UserSettings("LANG", "workspacePath", true);
		fileManager.saveUserSettings(userSettings);
		UserSettings newSettings = fileManager.readUserSettings();
		
		assertEquals(userSettings.getWorkspacePath(), newSettings.getWorkspacePath());
		assertEquals(userSettings.getLanguage(), newSettings.getLanguage());
	}
	
}
