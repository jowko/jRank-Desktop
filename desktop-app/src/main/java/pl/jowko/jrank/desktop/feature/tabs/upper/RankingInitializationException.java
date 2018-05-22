package pl.jowko.jrank.desktop.feature.tabs.upper;

/**
 * Created by Piotr on 2018-05-22.
 * This exception is thrown when there is error with initializing ranking tab.
 */
class RankingInitializationException extends RuntimeException {
	
	private static final long serialVersionUID = 2792522860234539041L;
	
	RankingInitializationException(String msg) {
		super(msg);
	}
	
}
