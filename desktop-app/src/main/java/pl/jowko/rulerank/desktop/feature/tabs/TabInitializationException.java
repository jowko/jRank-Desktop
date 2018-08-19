package pl.jowko.rulerank.desktop.feature.tabs;

import pl.jowko.rulerank.desktop.exception.RuleRankException;

/**
 * This exception is thrown when creating of tab failed with some error. <br>
 * <br>
 * Created by Piotr on 2018-05-21.
 */
public class TabInitializationException extends RuleRankException {
	
	private static final long serialVersionUID = 1183496770227648950L;
	
	public TabInitializationException(String msg) {
		super(msg);
	}
	
}
