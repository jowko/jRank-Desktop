package pl.jowko.rulerank.desktop.exception;

/**
 * This uncheck exception serves as marker exception.<br>
 * All custom uncheck exceptions should extend this exception.<br>
 * Such approach helps to differentiate errors handled by this application from jRS and java errors in catch blocks.<br>
 * <br>
 * Created by Piotr on 2018-05-22.
 */
public class RuleRankRuntimeException extends RuntimeException {
	
	private static final long serialVersionUID = 7157618282511613676L;
	
	public RuleRankRuntimeException() {
		super();
	}
	
	public RuleRankRuntimeException(String msg) {
		super(msg);
	}
	
	public RuleRankRuntimeException(String msg, Throwable throwable) {
		super(msg, throwable);
	}
	
	public RuleRankRuntimeException(Throwable throwable) {
		super(throwable);
	}
	
}
