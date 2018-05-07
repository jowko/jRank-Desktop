package pl.jowko.jrank.desktop.settings;

import java.io.Serializable;

/**
 * Created by Piotr on 2018-03-16.
 * Stores user settings
 */
public class UserSettings implements Serializable {

	private String language = "ENG";
	private String workspacePath = "\\workspace";
	private boolean tooltipsEnabled = true;
	
	public UserSettings() {
	}
	
	public UserSettings(String language, String workspacePath, boolean tooltipsEnabled) {
		this.language = language;
		this.workspacePath = workspacePath;
		this.tooltipsEnabled = tooltipsEnabled;
	}
	
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
}
