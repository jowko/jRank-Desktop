package pl.jowko.jrank.desktop.settings;

import java.io.Serializable;

/**
 * Created by Piotr on 2018-03-16.
 * Stores user settings
 */
public class UserSettings implements Serializable {

	private String language = "ENG";
	
	public UserSettings() {
	}
	
	public void setLanguage(String language) {
		this.language = language;
	}
	
	public String getLanguage() {
		return this.language;
	}
	
}
