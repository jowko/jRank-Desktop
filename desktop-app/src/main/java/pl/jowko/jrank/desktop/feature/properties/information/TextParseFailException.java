package pl.jowko.jrank.desktop.feature.properties.information;

import pl.jowko.jrank.desktop.exception.JRankRuntimeException;

/**
 * Created by Piotr on 2018-05-29
 * This exception should be thrown when parsing ranking or pairs from properties text failed.
 */
public class TextParseFailException extends JRankRuntimeException {
	
	private static final long serialVersionUID = -7022764434411593148L;
	
	TextParseFailException(String msg, Throwable e) {
		super(msg, e);
	}
	
}
