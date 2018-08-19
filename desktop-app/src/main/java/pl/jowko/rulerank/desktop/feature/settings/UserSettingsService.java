package pl.jowko.rulerank.desktop.feature.settings;

import pl.jowko.rulerank.desktop.service.ConfigFileManager;

import java.io.IOException;

import static java.util.Objects.isNull;

/**
 * Service used in places, where UserSettings needs to be used. <br>
 *  <br>
 * Created by Piotr on 2018-03-17.
 * @see UserSettings
 */
public class UserSettingsService {
	
	private static UserSettingsService instance;
	private UserSettings userSettings;
	
	private UserSettingsService() {
		userSettings = ConfigFileManager.getInstance().readUserSettings();
	}
	
	/**
	 * Gets instance of this class or create new when such instance doesn't exists. <br>
	 * userSettings.json file is read in private constructor of this class.
	 * @return instance of this service
	 */
	public static UserSettingsService getInstance() {
		if(isNull(instance)) {
			instance = new UserSettingsService();
		}
		return instance;
	}
	
	public String getLanguage() {
		return userSettings.getLanguage();
	}
	
	/**
	 * Saves UserSettings to userSettings.json file in configuration directory. <br>
	 * Current settings in memory are not replaced. <br>
	 * To see settings changed, application restart is required.
	 * @see UserSettingsController
	 * @param newUserSettings from user settings dialog form
	 * @throws IOException when something goes wrong with saving file
	 */
	public void saveUserSettings(UserSettings newUserSettings) throws IOException {
		ConfigFileManager.getInstance().saveUserSettings(newUserSettings);
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
