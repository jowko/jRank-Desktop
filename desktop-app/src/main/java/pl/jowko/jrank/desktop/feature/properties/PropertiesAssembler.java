package pl.jowko.jrank.desktop.feature.properties;

import java.util.Properties;

/**
 * Created by Piotr on 2018-04-29.
 */
public class PropertiesAssembler {
	
	public JRankProperties toJrankProperties(Properties properties) {
		JRankProperties jRankProperties = new JRankProperties();
		jRankProperties.setTest("test");
		return jRankProperties;
	}
	
}
