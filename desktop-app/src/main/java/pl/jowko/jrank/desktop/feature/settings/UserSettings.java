package pl.jowko.jrank.desktop.feature.settings;

import java.io.Serializable;

/**
 * Created by Piotr on 2018-03-16.
 * Stores user settings loaded from userSettings.json file.
 * User settings can be edited in UserSettings dialog from settings menu.
 * Default user settings are generated in file-generator project.
 * Instances of this class are created by UserSettingsBuilder.
 *
 * For now application have such customizable settings:
 *
 * - language:
 * Is used to determine currently used language in UI application.
 * By default it is ENG language.
 *
 * - workspacePath:
 * Is used to determine where experiment root catalog is located.
 * By default it is \\workspace path
 *
 * - tooltipsEnabled:
 * Is used to determine if user want to see advanced tips and additional information related mostly with properties form.
 * Default is true
 *
 * - advancedPropertiesEnabled:
 * If set to true, user sees all settings in properties form.
 * If is set to false, most settings are hidden and need to be expanded first to use them.
 * Default is false
 *
 * @see UserSettingsController
 * @see UserSettingsBuilder
 */
public class UserSettings implements Serializable {
	
	private static final long serialVersionUID = -8575709623215954350L;
	
	private String language;
	private String workspacePath;
	private boolean tooltipsEnabled;
	private boolean advancedPropertiesEnabled;
	
	public void setLanguage(String language) {
		this.language = language;
	}
	
	public String getLanguage() {
		return this.language;
	}
	
	public String getWorkspacePath() {
		return workspacePath;
	}
	
	public void setWorkspacePath(String workspacePath) {
		this.workspacePath = workspacePath;
	}
	
	public boolean isTooltipsEnabled() {
		return tooltipsEnabled;
	}
	
	public void setTooltipsEnabled(boolean tooltipsEnabled) {
		this.tooltipsEnabled = tooltipsEnabled;
	}
	
	public boolean isAdvancedPropertiesEnabled() {
		return advancedPropertiesEnabled;
	}
	
	public void setAdvancedPropertiesEnabled(boolean advancedPropertiesEnabled) {
		this.advancedPropertiesEnabled = advancedPropertiesEnabled;
	}
	
}
