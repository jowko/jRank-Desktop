package pl.jowko.jrank.desktop.service;

import pl.jowko.jrank.desktop.settings.UserSettings;

import java.io.IOException;

import static java.util.Objects.isNull;

/**
 * Created by Piotr on 2018-03-17.
 */
public class UserSettingsService {
	
	private static UserSettingsService instance;
	private UserSettings userSettings;
	
	private UserSettingsService() {
		userSettings = FileManager.getInstance().readUserSettings();
	}
	
	public static UserSettingsService getInstance() {
		if(isNull(instance)) {
			instance = new UserSettingsService();
		}
		return instance;
	}
	
	public String getLanguage() {
		return userSettings.getLanguage();
	}
	
	public void saveUserSettings(UserSettings newUserSettings) throws IOException {
		FileManager.getInstance().saveUserSettings(newUserSettings);
	}
	
	public UserSettings getUserSettings() {
		return userSettings;
	}
	
	/** Used in tests */
	public void setLanguage(String language) {
		userSettings.setLanguage(language);
	}
	
	/** Used in tests */
	public void setUserSettings(UserSettings userSettings) {
		this.userSettings = userSettings;
	}
	
}
