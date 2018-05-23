package pl.jowko.jrank.desktop.feature.settings;

import pl.jowko.jrank.desktop.exception.ConfigurationException;
import pl.jowko.jrank.desktop.feature.internationalization.LanguageService;
import pl.jowko.jrank.desktop.service.JRankInfoService;
import pl.jowko.jrank.logger.JRankLogger;

/**
 * Created by Piotr on 2018-03-17.
 * Initializes singleton objects with are related with configuration files.
 * Each singleton reads necessary files on start if needed.
 * When config files will be unavailable or corrupted, ConfigurationException will be thrown.
 * Configuration is checked only on application start.
 * When there are configuration errors, application doesn't start.
 * @see ConfigurationException
 * @see pl.jowko.jrank.desktop.Main
 */
public class ConfigurationInitializer {
	
	private boolean isError = false;
	
	/**
	 * Initializes singletons with are reading configuration from data directory.
	 * ConfigurationException will be thrown when configuration files will be corrupted on unavailable.
	 * @see ConfigurationException
	 */
	public void initialize() {
		JRankLogger.init("Reading configuration files");
		
		initializeInfoService();
		initializeLanguages();
		initializeUserSettings();
		
		if(isError) {
			throw new ConfigurationException("Application not started because of errors");
		}
		
		JRankLogger.init("Configuration files read successfully.");
	}
	
	/**
	 * Initializes service and read data needed to about dialog in help menu.
	 * Read file: jRankInfo.json
	 */
	private void initializeInfoService() {
		try {
			JRankInfoService.getInstance();
		} catch (ConfigurationException e) {
			isError = true;
			JRankLogger.error("Could not load jRankInfo.json file: " + e.getMessage());
		}
	}
	
	/**
	 * Initializes internationalization service and loads list of available languages and labels.
	 * Read files: languages.json and labels.json
	 */
	private void initializeLanguages() {
		try {
			LanguageService.getInstance();
		} catch (ConfigurationException e) {
			isError = true;
			JRankLogger.error("Could not load internationalization configuration files: " + e.getMessage());
		}
	}
	
	/**
	 * Initializes user settings service and reads user settings from file.
	 * Read file: userSettings.json
	 */
	private void initializeUserSettings() {
		try {
			UserSettingsService.getInstance();
		} catch (ConfigurationException e) {
			isError = true;
			JRankLogger.error("Could not load userSettings.json file: " + e.getMessage());
		}
	}
	
}
