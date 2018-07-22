package pl.jowko.rulerank.desktop.feature.tabs.upper;

import pl.jowko.rulerank.desktop.exception.RuleRankException;

/**
 * Created by Piotr on 2018-05-21.
 * This exception is thrown when UpperTabsController tries to open directory as file.
 */
class WrongFileTypeException extends RuleRankException {
	
	private static final long serialVersionUID = 1524917840711186367L;
	
	WrongFileTypeException(String msg) {
		super(msg);
	}
	
}
