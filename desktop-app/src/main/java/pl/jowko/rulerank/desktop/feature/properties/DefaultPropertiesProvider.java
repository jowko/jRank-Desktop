package pl.jowko.rulerank.desktop.feature.properties;

import pl.jowko.rulerank.desktop.exception.ConfigurationException;
import pl.jowko.rulerank.desktop.feature.workspace.WorkspaceService;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import static pl.jowko.rulerank.desktop.utils.PathUtils.getSubDirectoryPath;

/**
 * This class loads default.properties file from workspace. <br>
 * It is assumed, that default properties are in main workspace directory(workspace path). <br>
 *  <br>
 * Created by Piotr on 2018-05-04.
 * @see RuleRankProperties
 */
public class DefaultPropertiesProvider {
	
	/**
	 * Reads default.properties file from main workspace directory(workspacePath).
	 * @return RuleRankProperties object with all properties
	 * @throws IOException when something goes wrong with file reading
	 */
	public RuleRankProperties getDefaultProperties() throws IOException {
		String propertiesPath = getSubDirectoryPath(WorkspaceService.getInstance().getWorkspacePath()) + "default.properties";
		
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(propertiesPath));
		} catch (FileNotFoundException e) {
			throw new ConfigurationException("default.properties file was not found: " + e.getMessage());
		}
		
		PropertiesAssembler assembler = new PropertiesAssembler(properties);
		
		return assembler.toRuleRankProperties();
	}
	
}
