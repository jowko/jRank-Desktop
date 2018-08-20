package pl.jowko.rulerank.desktop.feature.settings;

/**
 * This class contains path to configuration files. <br>
 * Configuration files should be in data directory in main catalog of application. <br>
 *  <br>
 * Created by Piotr on 2018-05-02.
 */
public class ConfigPaths {
	
	private static ConfigPaths instance;
	
	private String dataDirectory;
	private String userSettingsPath;
	private String labelsPath;
	private String languagesPath;
	private String ruleRankInfoPath;
	private String helpInfoPath;
	
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
	 * Sets new directory for config files. <br>
	 * Paths to all config files are updated with new settings.
	 * @param dataDirectory for new config catalog
	 */
	public void setNewDirectory(String dataDirectory) {
		this.dataDirectory = dataDirectory;
		
		userSettingsPath = dataDirectory + "userSettings.json";
		labelsPath = dataDirectory + "labels.json";
		languagesPath = dataDirectory + "languages.json";
		ruleRankInfoPath = dataDirectory + "ruleRankInfo.json";
		helpInfoPath = dataDirectory + "helpInfo.json";
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
	
	public String getRuleRankInfoPath() {
		return ruleRankInfoPath;
	}
	
	public String getHelpInfoPath() {
		return helpInfoPath;
	}
	
}
