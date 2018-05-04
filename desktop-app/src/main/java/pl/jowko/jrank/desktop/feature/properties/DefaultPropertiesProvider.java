package pl.jowko.jrank.desktop.feature.properties;

import pl.jowko.jrank.desktop.feature.workspace.WorkspaceService;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Piotr on 2018-05-04.
 */
public class DefaultPropertiesProvider {
	
	public JRankProperties getDefaultProperties() throws IOException {
		String propertiesPath = WorkspaceService.getInstance().getWorkspacePath() + "\\default.properties";
		
		Properties properties = new Properties();
		properties.load(new FileInputStream(propertiesPath));
		
		PropertiesAssembler assembler = new PropertiesAssembler(properties);
		
		return assembler.toJrankProperties();
	}
	
}
