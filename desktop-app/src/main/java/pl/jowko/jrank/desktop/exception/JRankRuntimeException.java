package pl.jowko.jrank.desktop.exception;

/**
 * Created by Piotr on 2018-05-22.
 * This uncheck exception serves as marker exception.
 * All custom uncheck exceptions should extend this exception.
 * Such approach helps to differentiate errors handled by this application from jRS and java errors in catch blocks.
 */
public class JRankRuntimeException extends RuntimeException {
	
	private static final long serialVersionUID = 7157618282511613676L;
	
	public JRankRuntimeException() {
		super();
	}
	
	public JRankRuntimeException(String msg) {
		super(msg);
	}
	
	public JRankRuntimeException(String msg, Throwable throwable) {
		super(msg, throwable);
	}
	
	public JRankRuntimeException(Throwable throwable) {
		super(throwable);
	}
	
}
