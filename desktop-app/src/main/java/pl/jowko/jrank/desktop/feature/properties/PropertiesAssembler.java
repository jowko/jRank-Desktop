package pl.jowko.jrank.desktop.feature.properties;

import pl.poznan.put.cs.idss.jrs.ranking.RankerParameters;

import java.util.Properties;

/**
 * Created by Piotr on 2018-04-29.
 */
public class PropertiesAssembler {
	
	public JRankProperties toJrankProperties(Properties properties) {
		JRankProperties jRankProperties = new JRankProperties();
		return jRankProperties;
	}
	
	public RankerParameters toRankerParameters(JRankProperties jRankProperties) {
		return null;
	}
	
}
