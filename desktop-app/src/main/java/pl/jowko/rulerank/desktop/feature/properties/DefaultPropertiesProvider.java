package pl.jowko.rulerank.desktop.feature.properties;

import pl.jowko.rulerank.desktop.feature.workspace.WorkspaceService;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Piotr on 2018-05-04.
 * This class loads default.properties file from workspace.
 * It is assumed, that default properties are in main workspace directory(workspace path).
 * @see JRankProperties
 */
public class DefaultPropertiesProvider {
	
	/**
	 * Reads default.properties file from main workspace directory(workspacePath).
	 * @return JRankProperties object with all properties
	 * @throws IOException when something goes wrong with file reading
	 */
	public JRankProperties getDefaultProperties() throws IOException {
		String propertiesPath = WorkspaceService.getInstance().getWorkspacePath() + "\\default.properties";
		
		Properties properties = new Properties();
		properties.load(new FileInputStream(propertiesPath));
		
		PropertiesAssembler assembler = new PropertiesAssembler(properties);
		
		return assembler.toJrankProperties();
	}
	
}
