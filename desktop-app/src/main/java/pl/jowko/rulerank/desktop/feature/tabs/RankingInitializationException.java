package pl.jowko.rulerank.desktop.feature.tabs;

import pl.jowko.rulerank.desktop.exception.RuleRankRuntimeException;

/**
 * This exception is thrown when there is error with initializing ranking tab. <br>
 * <br>
 * Created by Piotr on 2018-05-22.
 */
public class RankingInitializationException extends RuleRankRuntimeException {
	
	private static final long serialVersionUID = 2792522860234539041L;
	
	public RankingInitializationException(String msg) {
		super(msg);
	}
	
}
