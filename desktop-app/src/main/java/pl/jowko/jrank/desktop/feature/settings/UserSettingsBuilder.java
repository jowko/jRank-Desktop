package pl.jowko.jrank.desktop.feature.settings;

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
		return settings;
	}
	
}