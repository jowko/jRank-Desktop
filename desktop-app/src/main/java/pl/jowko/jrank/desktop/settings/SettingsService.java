package pl.jowko.jrank.desktop.settings;

import static java.util.Objects.isNull;

/**
 * Created by Piotr on 2018-03-17.
 */
public class SettingsService {
	
	private static SettingsService instance;
	private static UserSettings userSettings;
	
	private SettingsService() {
		userSettings = new UserSettings(); //TODO get from file
	}
	
	public static SettingsService getInstance() {
		if(isNull(instance)) {
			instance = new SettingsService();
		}
		return instance;
	}
	
	public static String getLanguage() {
		return userSettings.getLanguage();
	}
	
	public static void setUserSettings(UserSettings newUserSettings) {
		userSettings = newUserSettings;
	}
	
	public UserSettings getUserSettings() {
		return userSettings;
	}
	
}
