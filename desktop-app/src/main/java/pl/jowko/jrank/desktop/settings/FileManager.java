package pl.jowko.jrank.desktop.settings;

import java.util.Map;

import static java.util.Objects.isNull;

/**
 * Created by Piotr on 2018-03-16.
 */
public class FileManager {
	
	private static FileManager instance;
	
	private FileManager() {}
	
	public static FileManager getInstance() {
		if(isNull(instance)) {
			instance = new FileManager();
		}
		return instance;
	}
	
	public UserSettings readUserSettings() {
		return new UserSettings();
	}
	
	public JRankSettings readJRankSettings() {
		return new JRankSettings();
	}
	
	public Map<String, String> readLanguages() {
		return null;
	}
	
	public Map<String, Map<String, String>> readLabels() {
		return null;
	}

}
