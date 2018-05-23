package pl.jowko.jrank.desktop.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.jowko.jrank.desktop.exception.ConfigurationException;
import pl.jowko.jrank.desktop.feature.settings.ConfigPaths;
import pl.jowko.jrank.desktop.feature.settings.JRankInfo;
import pl.jowko.jrank.desktop.feature.settings.UserSettings;
import pl.jowko.jrank.logger.JRankLogger;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static java.util.Objects.isNull;

/**
 * Created by Piotr on 2018-03-16.
 * This class manages configuration files from data directory.
 *
 * Supported configuration files:
 * jRankInfo.json - contains information about application version displayed in about dialog from help settings
 * @see pl.jowko.jrank.desktop.controller.AboutController
 * @see JRankInfo
 *
 * labels.json - contains map of maps, where languages with labels codes and translations are stored
 * @see pl.jowko.jrank.desktop.feature.internationalization.LanguageService
 * @see pl.jowko.jrank.desktop.feature.internationalization.Labels
 *
 * languages.json - contains map of language codes and language labels.
 * Example: "ENG" : "English"
 * It is used mostly in user settings form
 * @see pl.jowko.jrank.desktop.feature.internationalization.LanguageService
 * @see pl.jowko.jrank.desktop.feature.settings.UserSettingsController
 *
 * userSettings.json - contains user settings, with are editable in user settings dialog form from settings menu
 * @see pl.jowko.jrank.desktop.feature.settings.UserSettingsController
 * @see UserSettings
 */
public class ConfigFileManager {
	
	private static ConfigFileManager instance;
	
	private ObjectMapper mapper;
	private ConfigPaths configPaths;
	
	private ConfigFileManager() {
		mapper = new ObjectMapper();
		configPaths = ConfigPaths.getInstance();
	}
	
	public static ConfigFileManager getInstance() {
		if(isNull(instance)) {
			instance = new ConfigFileManager();
		}
		return instance;
	}
	
	/**
	 * Reads userSettings.json from data directory.
	 * @see pl.jowko.jrank.desktop.feature.settings.UserSettingsController
	 * @see UserSettings
	 * @return UserSettings mapped from config file.
	 * @throws ConfigurationException when there is problem with reading this file
	 */
	public UserSettings readUserSettings() {
		try {
			return mapper.readValue(new File(configPaths.getUserSettingsPath()), UserSettings.class);
		} catch (IOException e) {
			JRankLogger.error(e.getMessage());
			throw new ConfigurationException(e.getMessage());
		}
	}
	
	/**
	 * Saves user settings to userSettings.json file in data directory.
	 * @see pl.jowko.jrank.desktop.feature.settings.UserSettingsController
	 * @see UserSettings
	 * @param settings from user settings form to save
	 * @throws IOException when something goes wrong with file saving
	 */
	public void saveUserSettings(UserSettings settings) throws IOException {
		JRankLogger.info("Saving user settings: " + configPaths.getUserSettingsPath());
		mapper.writerWithDefaultPrettyPrinter()
				.writeValue(new File(configPaths.getUserSettingsPath()), settings);
	}
	
	/**
	 * Reads information about application version from jRankInfo.json file
	 * @see pl.jowko.jrank.desktop.controller.AboutController
	 * @see JRankInfo
	 * @return JRankInfo mapped from config file
	 * @throws ConfigurationException when there is problem with reading this file
	 */
	public JRankInfo readJRankInfo() {
		try {
			return mapper.readValue(new File(configPaths.getJRankInfoPath()), JRankInfo.class);
		} catch (IOException e) {
			JRankLogger.error(e.getMessage());
			throw new ConfigurationException(e.getMessage());
		}
	}
	
	/**
	 * Reads language map with all available languages from languages.json file.
	 * Languages are in such format:
	 * "ENG" : "English"
	 * @see pl.jowko.jrank.desktop.feature.internationalization.LanguageService
	 * @return map of languages, where key is language code and value is language label
	 * @throws ConfigurationException when there is problem with reading this file
	 */
	public Map<String, String> readLanguages() {
		try {
			return mapper.readValue(new File(configPaths.getLanguagesPath()), Map.class);
		} catch (IOException e) {
			JRankLogger.error(e.getMessage());
			throw new ConfigurationException(e.getMessage());
		}
	}
	
	/**
	 * Reads labels from labels.json file.
	 * First map contains language code and labels map for that language.
	 * Nested map contains labels codes as keys and translations as values.
	 * @see pl.jowko.jrank.desktop.feature.internationalization.Labels
	 * @see pl.jowko.jrank.desktop.feature.internationalization.LanguageService
	 * @return map of maps, containing labels for all available languages
	 * @throws ConfigurationException when there is problem with reading this file
	 */
	public Map<String, Map<String, String>> readLabels() {
		try {
			return mapper.readValue(new File(configPaths.getLabelsPath()), Map.class);
		} catch (IOException e) {
			JRankLogger.error(e.getMessage());
			throw new ConfigurationException(e.getMessage());
		}
	}

}
