package pl.jowko.rulerank.desktop.feature.settings;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pl.jowko.rulerank.desktop.MasterTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by Piotr on 2018-05-03.
 */
class UserSettingsValidatorTest extends MasterTest {
	
	private UserSettingsValidator validator;
	private UserSettingsService settingsService;
	
	@BeforeEach
	void setUpEach() {
		validator = new UserSettingsValidator();
		settingsService = UserSettingsService.getInstance();
	}
	
	@AfterEach
	void restoreCorrectSettings() {
		settingsService.setUserSettings(createUserSettings("ENG", "\\"));
	}
	
	@Test
	void userSettingsShouldBeValid() {
		String validationErrors = validator.validateUserSettingsForm("English", "\\");
		assertTrue(validationErrors.isEmpty());
	}
	
	@ParameterizedTest
	@CsvSource(value = {
			"English,\\wrongPath",
			"HAHA,\\",
			",\\",
			"English,"})
	void userSettingsShouldNotBeValid(String language, String workspacePath) {
		String validationErrors = validator.validateUserSettingsForm(language, workspacePath);
		assertFalse(validationErrors.isEmpty());
	}
	
	@Test
	void userSettingsShouldNotBeValidEmptyLanguage() {
		String validationErrors = validator.validateUserSettingsForm("", "\\");
		assertFalse(validationErrors.isEmpty());
	}
	
	@Test
	void configurationShouldBeValid() {
		String validationErrors = validator.validateConfiguration();
		assertTrue(validationErrors.isEmpty());
	}
	
	@ParameterizedTest
	@CsvSource(value = {
			"ENG,\\wrongPath",
			"HAHA,\\",
			",\\",
			"ENG,"})
	void configurationShouldBeNotBeValid(String language, String workspacePath) {
		settingsService.setUserSettings(createUserSettings(language, workspacePath));
		String validationErrors = validator.validateConfiguration();
		
		assertFalse(validationErrors.isEmpty());
	}
	
	@Test
	void configurationShouldBeNotBeValidEmptyLanguage() {
		settingsService.setUserSettings(createUserSettings("", "\\"));
		String validationErrors = validator.validateConfiguration();
		
		assertFalse(validationErrors.isEmpty());
	}
	
	private UserSettings createUserSettings(String language, String workspacePath) {
		return new UserSettingsBuilder()
				.setLanguage(language)
				.setWorkspacePath(workspacePath)
				.createUserSettings();
	}
}
