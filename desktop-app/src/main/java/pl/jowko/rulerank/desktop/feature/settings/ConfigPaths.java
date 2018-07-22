package pl.jowko.rulerank.desktop.feature.settings;

/**
 * Created by Piotr on 2018-05-02.
 * This class contains path to configuration files.
 * Configuration files should be in data directory in main catalog of application.
 */
public class ConfigPaths {
	
	private static ConfigPaths instance;
	
	private String dataDirectory;
	private String userSettingsPath;
	private String labelsPath;
	private String languagesPath;
	private String jRankInfoPath;
	
	private ConfigPaths() {
		dataDirectory = "data/";
		setNewDirectory(dataDirectory);
	}
	
	public static ConfigPaths getInstance() {
		if(instance == null) {
			instance = new ConfigPaths();
		}
		return instance;
	}
	
	/**
	 * Sets new directory for config files.
	 * Paths to all config files are updated with new settings.
	 * @param dataDirectory for new config catalog
	 */
	public void setNewDirectory(String dataDirectory) {
		this.dataDirectory = dataDirectory;
		
		userSettingsPath = dataDirectory + "userSettings.json";
		labelsPath = dataDirectory + "labels.json";
		languagesPath = dataDirectory + "languages.json";
		jRankInfoPath = dataDirectory + "jRankInfo.json";
	}
	
	public String getDataDirectory() {
		return dataDirectory;
	}
	
	public String getUserSettingsPath() {
		return userSettingsPath;
	}
	
	public String getLabelsPath() {
		return labelsPath;
	}
	
	public String getLanguagesPath() {
		return languagesPath;
	}
	
	public String getJRankInfoPath() {
		return jRankInfoPath;
	}
	
}
