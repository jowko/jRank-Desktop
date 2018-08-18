package pl.jowko.rulerank.desktop.feature.help;

/**
 * This class contains information for help dialog. <br>
 * It is read only. <br>
 *  <br>
 * Created by Piotr on 2018-08-14
 */
public class HelpInfo {
	
	private String projectUrl;
	private String contactInfo;
	private String additionalInfo;
	
	/**
	 * @return url address to project home page
	 */
	public String getProjectUrl() {
		return projectUrl;
	}
	
	/**
	 * @return contact info for support
	 */
	public String getContactInfo() {
		return contactInfo;
	}
	
	/**
	 * @return any additional information related with help and support
	 */
	public String getAdditionalInfo() {
		return additionalInfo;
	}
	
}
