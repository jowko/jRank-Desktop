package pl.jowko.jrank.desktop.settings;

import java.io.Serializable;

/**
 * Created by Piotr on 2018-03-17.
 * Settings for running jRank.
 */
public class JRankSettings implements Serializable {
	
	private String test = "aa";
	
	public JRankSettings() {
		setDefaults();
	}
	
	private void setDefaults() {
	
	}
	
	public String getTest() {
		return this.test;
	}
	
	public void setTest(String test) {
		this.test = test;
	}
	
}
