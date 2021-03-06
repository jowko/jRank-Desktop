package pl.jowko.rulerank.desktop.feature.settings;

import java.io.Serializable;

/**
 * Stores user settings loaded from userSettings.json file. <br>
 * User settings can be edited in UserSettings dialog from settings menu. <br>
 * Default user settings are generated in file-generator project. <br>
 * Instances of this class are created by UserSettingsBuilder. <br>
 * <br>
 * For now application have such customizable settings: <br>
 * <br>
 * - language: <br>
 * Is used to determine currently used language in UI application. <br>
 * By default it is ENG language. <br>
 * <br>
 * - workspacePath: <br>
 * Is used to determine where experiment root catalog is located. <br>
 * By default it is \\workspace path <br>
 * <br>
 * - tooltipsEnabled: <br>
 * Is used to determine if user want to see advanced tips and additional information related mostly with properties form. <br>
 * Default is true <br>
 * <br>
 * - advancedPropertiesEnabled: <br>
 * If set to true, user sees all settings in properties form. <br>
 * If is set to false, most settings are hidden and need to be expanded first to use them. <br>
 * Default is false <br>
 * <br>
 * Created by Piotr on 2018-03-16.
 * @see UserSettingsController
 * @see UserSettingsBuilder
 */
public class UserSettings implements Serializable {
	
	private static final long serialVersionUID = -8575709623215954350L;
	
	private String language;
	private String workspacePath;
	private String csvSeparator;
	private boolean tooltipsEnabled;
	private boolean advancedPropertiesEnabled;
	private boolean manualInfoEditionEnabled;
	private boolean startMaximized;
	
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
	
	public String getCsvSeparator() {
		return csvSeparator;
	}
	
	public void setCsvSeparator(String csvSeparator) {
		this.csvSeparator = csvSeparator;
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
	
	public boolean isManualInfoEditionEnabled() {
		return manualInfoEditionEnabled;
	}
	
	public void setManualInfoEditionEnabled(boolean manualInfoEditionEnabled) {
		this.manualInfoEditionEnabled = manualInfoEditionEnabled;
	}
	
	public boolean isStartMaximized() {
		return startMaximized;
	}
	
	public void setStartMaximized(boolean startMaximized) {
		this.startMaximized = startMaximized;
	}
	
}
