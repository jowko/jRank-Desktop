package pl.jowko.jrank.desktop.service;

import pl.jowko.jrank.desktop.exception.ConfigurationException;
import pl.jowko.jrank.logger.JRankLogger;

/**
 * Created by Piotr on 2018-03-17.
 * Initializes all singleton objects before running application.
 * Each singleton reads necessary files if needed.
 */
public class ConfigurationInitializer {
	
	private boolean isError = false;
	
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
	
	private void initializeInfoService() {
		try {
			JRankInfoService.getInstance();
		} catch (ConfigurationException e) {
			isError = true;
			JRankLogger.error("Could not load jRankInfo.json file.");
		}
	}
	
	private void initializeLanguages() {
		try {
			LanguageService.getInstance();
		} catch (ConfigurationException e) {
			isError = true;
			JRankLogger.error("Could not load internationalization configuration files.");
		}
	}
	
	private void initializeUserSettings() {
		try {
			SettingsService.getInstance();
		} catch (ConfigurationException e) {
			isError = true;
			JRankLogger.error("Could not load userSettings.json file.");
		}
	}
	
}
