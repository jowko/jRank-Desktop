package pl.jowko.jrank.desktop.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.jowko.jrank.desktop.exception.ConfigurationException;
import pl.jowko.jrank.desktop.settings.JRankInfo;
import pl.jowko.jrank.desktop.settings.JRankSettings;
import pl.jowko.jrank.desktop.settings.UserSettings;
import pl.jowko.jrank.logger.JRankLogger;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static java.util.Objects.isNull;
import static pl.jowko.jrank.desktop.settings.JRankConst.*;

/**
 * Created by Piotr on 2018-03-16.
 */
public class FileManager {
	
	private static FileManager instance;
	
	private ObjectMapper mapper;
	
	private FileManager() {
		mapper = new ObjectMapper();
	}
	
	public static FileManager getInstance() {
		if(isNull(instance)) {
			instance = new FileManager();
		}
		return instance;
	}
	
	public UserSettings readUserSettings() {
		try {
			return mapper.readValue(new File(USER_SETTING_FILE), UserSettings.class);
		} catch (IOException e) {
			JRankLogger.error(e.getMessage());
			throw new ConfigurationException(e.getMessage());
		}
	}
	
	public JRankInfo readJRankInfo() {
		try {
			return mapper.readValue(new File(JRANK_INFO_FILE), JRankInfo.class);
		} catch (IOException e) {
			JRankLogger.error(e.getMessage());
			throw new ConfigurationException(e.getMessage());
		}
	}
	
	public JRankSettings readJRankSettings() {
		return new JRankSettings();
	}
	
	public Map<String, String> readLanguages() {
		try {
			return mapper.readValue(new File(LANGUAGES_FILE), Map.class);
		} catch (IOException e) {
			JRankLogger.error(e.getMessage());
			throw new ConfigurationException(e.getMessage());
		}
	}
	
	public Map<String, Map<String, String>> readLabels() {
		try {
			return mapper.readValue(new File(LABELS_FILE), Map.class);
		} catch (IOException e) {
			JRankLogger.error(e.getMessage());
			throw new ConfigurationException(e.getMessage());
		}
	}

}
