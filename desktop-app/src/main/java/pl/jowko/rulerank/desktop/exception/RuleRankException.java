package pl.jowko.rulerank.desktop.exception;

/**
 * This checked exception serves as marker exception.<br>
 * All custom checked exceptions should extend this exception.<br>
 * Such approach helps to differentiate errors handled by this application from jRS and java errors in catch blocks.<br>
 * <br>
 * Created by Piotr on 2018-05-22.
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
