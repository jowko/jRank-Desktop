package pl.jowko.rulerank.desktop.exception;

/**
 * Created by Piotr on 2018-05-22.
 * This checked exception serves as marker exception.
 * All custom checked exceptions should extend this exception.
 * Such approach helps to differentiate errors handled by this application from jRS and java errors in catch blocks.
 */
public class RuleRankException extends Exception {
	
	private static final long serialVersionUID = 3137511066842981526L;
	
	public RuleRankException() {
		super();
	}
	
	public RuleRankException(String msg) {
		super(msg);
	}
	
	public RuleRankException(String msg, Throwable throwable) {
		super(msg, throwable);
	}
	
	public RuleRankException(Throwable throwable) {
		super(throwable);
	}
	
}
