package pl.jowko.jrank.desktop.settings;

/**
 * Created by Piotr on 2018-04-10.
 */
public class JRankInfo {
	
	private String version;
	private String releaseDate;
	
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
