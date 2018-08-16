package pl.jowko.rulerank.desktop.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.jowko.rulerank.desktop.MasterTest;
import pl.jowko.rulerank.desktop.feature.settings.RuleRankInfo;
import pl.jowko.rulerank.desktop.feature.settings.UserSettings;
import pl.jowko.rulerank.desktop.feature.settings.UserSettingsBuilder;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Piotr on 2018-05-02.
 */
class ConfigFileManagerTest extends MasterTest {
	
	private ConfigFileManager configFileManager;
	
	@BeforeEach
	void setUpEach() {
		configFileManager = ConfigFileManager.getInstance();
	}
	
	@AfterAll
	static void restoreUserSettings() throws IOException {
		UserSettings userSettings = new UserSettingsBuilder()
				.setLanguage("ENG")
				.setWorkspacePath("workspace")
				.setCsvSeparator(";")
				.createUserSettings();
		ConfigFileManager.getInstance().saveUserSettings(userSettings);
		ConfigFileManager.getInstance().readUserSettings();
	}
	
	@Test
	void shouldReadUserSettings() {
		UserSettings userSettings = configFileManager.readUserSettings();
		assertNotNull(userSettings);
	}
	
	@Test
	void shouldReadRuleRankInfo() {
		RuleRankInfo info = configFileManager.readRuleRankInfo();
		assertNotNull(info);
	}
	
	@Test
	void shouldReadLabels() {
		Map<String, Map<String, String> > labels = configFileManager.readLabels();
		
		assertNotNull(labels);
		assertTrue(labels.size() > 0);
		assertTrue(labels.get("ENG").size() > 0);
		assertTrue(labels.get("POL").size() > 0);
	}
	
	@Test
	void shouldReadLanguages() {
		Map<String, String> languages = configFileManager.readLanguages();
		
		assertNotNull(languages);
		assertTrue(languages.size() > 0);
	}
	
	@Test
	void shouldSaveUserSettings() throws IOException {
		UserSettings userSettings = new UserSettingsBuilder()
				.setLanguage("LANG")
				.setWorkspacePath("workspacePath")
				.createUserSettings();
		configFileManager.saveUserSettings(userSettings);
		UserSettings newSettings = configFileManager.readUserSettings();
		
		assertEquals(userSettings.getWorkspacePath(), newSettings.getWorkspacePath());
		assertEquals(userSettings.getLanguage(), newSettings.getLanguage());
	}
	
}
