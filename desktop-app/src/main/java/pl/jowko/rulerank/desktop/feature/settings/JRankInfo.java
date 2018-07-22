package pl.jowko.rulerank.desktop.feature.settings;

import java.io.Serializable;

/**
 * Created by Piotr on 2018-04-10.
 * This class contains data for about dialog in help menu.
 * Application version data are generated in file-generator project.
 *
 * @see pl.jowko.rulerank.desktop.controller.AboutController
 */
public class JRankInfo implements Serializable {
	
	private static final long serialVersionUID = 4766498563514732412L;
	
	private String version;
	private String releaseDate;
	
	public JRankInfo() {}
	
	/**
	 * Create object containing data used in about dialog in help menu.
	 * @param version of application
	 * @param releaseDate of current version
	 */
	public JRankInfo(String version, String releaseDate) {
		this.version = version;
		this.releaseDate = releaseDate;
	}
	
	public String getVersion() {
		return version;
	}
	
	public String getReleaseDate() {
		return releaseDate;
	}
	
}
