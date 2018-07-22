package pl.jowko.rulerank.desktop.feature.settings;

/**
 * Created by Piotr on 2018-05-11.
 * Builder for UserSettings class.
 * @see UserSettings
 */
public class UserSettingsBuilder {
	
	private String language;
	private String workspacePath;
	private boolean tooltipsEnabled;
	private boolean advancedPropertiesEnabled;
	private boolean manualInfoEditionEnabled;
	private boolean startMaximized;
	
	public UserSettingsBuilder setLanguage(String language) {
		this.language = language;
		return this;
	}
	
	public UserSettingsBuilder setWorkspacePath(String workspacePath) {
		this.workspacePath = workspacePath;
		return this;
	}
	
	public UserSettingsBuilder setTooltipsEnabled(boolean tooltipsEnabled) {
		this.tooltipsEnabled = tooltipsEnabled;
		return this;
	}
	
	public UserSettingsBuilder setAdvancedPropertiesEnabled(boolean advancedPropertiesEnabled) {
		this.advancedPropertiesEnabled = advancedPropertiesEnabled;
		return this;
	}
	
	public UserSettingsBuilder setManualInfoEditionEnabled(boolean manualInfoEditionEnabled) {
		this.manualInfoEditionEnabled = manualInfoEditionEnabled;
		return this;
	}
	
	public UserSettingsBuilder setStartMaximized(boolean startMaximized) {
		this.startMaximized = startMaximized;
		return this;
	}
	
	/**
	 * Creates UserSettings from data set in builder.
	 * All options are optional.
	 * This method should be called as last in creation of UserSettings using this builder.
	 * @see UserSettings
	 * @return UserSettings instance with data provided to builder.
	 */
	public UserSettings createUserSettings() {
		UserSettings settings = new UserSettings();
		settings.setLanguage(language);
		settings.setWorkspacePath(workspacePath);
		settings.setTooltipsEnabled(tooltipsEnabled);
		settings.setAdvancedPropertiesEnabled(advancedPropertiesEnabled);
		settings.setManualInfoEditionEnabled(manualInfoEditionEnabled);
		settings.setStartMaximized(startMaximized);
		return settings;
	}
	
}