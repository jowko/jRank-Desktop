package pl.jowko.rulerank.desktop.feature.tabs;

import pl.jowko.rulerank.desktop.exception.RuleRankException;

/**
 * Created by Piotr on 2018-05-21.
 * This exception is thrown when creating of tab failed with some error.
 */
public class TabInitializationException extends RuleRankException {
	
	private static final long serialVersionUID = 1183496770227648950L;
	
	public TabInitializationException(String msg) {
		super(msg);
	}
	
}
