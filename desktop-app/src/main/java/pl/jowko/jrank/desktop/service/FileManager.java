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
 */
public class FileManager {
	
	private static FileManager instance;
	
	private ObjectMapper mapper;
	private ConfigPaths configPaths;
	
	private FileManager() {
		mapper = new ObjectMapper();
		configPaths = ConfigPaths.getInstance();
	}
	
	public static FileManager getInstance() {
		if(isNull(instance)) {
			instance = new FileManager();
		}
		return instance;
	}
	
	public UserSettings readUserSettings() {
		try {
			return mapper.readValue(new File(configPaths.getUserSettingsPath()), UserSettings.class);
		} catch (IOException e) {
			JRankLogger.error(e.getMessage());
			throw new ConfigurationException(e.getMessage());
		}
	}
	
	public void saveUserSettings(UserSettings settings) throws IOException {
		JRankLogger.info("Saving user settings: " + configPaths.getUserSettingsPath());
		mapper.writerWithDefaultPrettyPrinter()
				.writeValue(new File(configPaths.getUserSettingsPath()), settings);
	}
	
	public JRankInfo readJRankInfo() {
		try {
			return mapper.readValue(new File(configPaths.getJRankInfoPath()), JRankInfo.class);
		} catch (IOException e) {
			JRankLogger.error(e.getMessage());
			throw new ConfigurationException(e.getMessage());
		}
	}
	
	public Map<String, String> readLanguages() {
		try {
			return mapper.readValue(new File(configPaths.getLanguagesPath()), Map.class);
		} catch (IOException e) {
			JRankLogger.error(e.getMessage());
			throw new ConfigurationException(e.getMessage());
		}
	}
	
	public Map<String, Map<String, String>> readLabels() {
		try {
			return mapper.readValue(new File(configPaths.getLabelsPath()), Map.class);
		} catch (IOException e) {
			JRankLogger.error(e.getMessage());
			throw new ConfigurationException(e.getMessage());
		}
	}

}
