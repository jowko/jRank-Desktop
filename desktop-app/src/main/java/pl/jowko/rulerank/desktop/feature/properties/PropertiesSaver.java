package pl.jowko.rulerank.desktop.feature.properties;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * This class is used to save properties in .properties file. <br>
 * It converts RuleRankProperties back to Properties objects and replace old properties file with new one. <br>
 *  <br>
 * Created by Piotr on 2018-05-04.
 * @see Properties
 * @see RuleRankProperties
 */
public class PropertiesSaver {
	
	private Properties properties;
	
	/**
	 * Creates instance of this class and converts RuleRankProperties to properties.
	 * @param ruleRankProperties with will be saved.
	 */
	public PropertiesSaver(RuleRankProperties ruleRankProperties) {
		properties = new RawPropertiesAssembler(ruleRankProperties).toProperties();
	}
	
	/**
	 * Save properties to file on provided file path.
	 * @param filePath where file will be saved
	 * @throws IOException when something goes wrong with saving file
	 */
	public void save(String filePath) throws IOException {
		properties.store(new FileOutputStream(filePath), null);
	}
	
	Properties getProperties() {
		return properties;
	}
	
}
