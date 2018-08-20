package pl.jowko.rulerank.desktop.feature.properties.information;

import pl.jowko.rulerank.desktop.exception.RuleRankRuntimeException;

/**
 * This exception should be thrown when parsing ranking or pairs from properties text failed. <br>
 *  <br>
 * Created by Piotr on 2018-05-29
 */
public class TextParseFailException extends RuleRankRuntimeException {
	
	private static final long serialVersionUID = -7022764434411593148L;
	
	TextParseFailException(String msg, Throwable e) {
		super(msg, e);
	}
	
}
