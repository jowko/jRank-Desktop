package pl.jowko.rulerank.desktop.exception;

import pl.jowko.rulerank.desktop.feature.settings.ConfigurationInitializer;

/**
 * This exception should be thrown on application startup.<br>
 * It indicates, that application configuration(from config files) is not correct.<br>
 * <br>
 * Created by Piotr on 2018-04-14.
 * @see ConfigurationInitializer
 */
public class ConfigurationException extends RuleRankRuntimeException {
	
	private static final long serialVersionUID = -3345535156921076324L;
	
	public ConfigurationException(String msg) {
		super(msg);
	}
	
}
