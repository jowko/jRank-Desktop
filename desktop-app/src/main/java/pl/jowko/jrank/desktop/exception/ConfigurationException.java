package pl.jowko.jrank.desktop.exception;

import pl.jowko.jrank.desktop.feature.settings.ConfigurationInitializer;

/**
 * Created by Piotr on 2018-04-14.
 * This exception should be thrown on application startup.
 * It indicates, that application configuration(from config files) is not correct.
 * @see ConfigurationInitializer
 */
public class ConfigurationException extends JRankRuntimeException {
	
	private static final long serialVersionUID = -3345535156921076324L;
	
	public ConfigurationException(String msg) {
		super(msg);
	}
	
}
