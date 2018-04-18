package pl.jowko.jrank.desktop.service;

import pl.jowko.jrank.desktop.settings.UserSettings;

import java.io.IOException;

import static java.util.Objects.isNull;

/**
 * Created by Piotr on 2018-03-17.
 */
public class SettingsService {
	
	private static SettingsService instance;
	private UserSettings userSettings;
	
	private SettingsService() {
		userSettings = FileManager.getInstance().readUserSettings();
	}
	
	public static SettingsService getInstance() {
		if(isNull(instance)) {
			instance = new SettingsService();
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
	
}
