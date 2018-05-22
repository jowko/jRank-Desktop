package pl.jowko.jrank.desktop.feature.tabs;

/**
 * Created by Piotr on 2018-05-21.
 * This exception is thrown when creating of tab failed with some error.
 */
public class TabInitializationException extends Exception {
	
	private static final long serialVersionUID = 1183496770227648950L;
	
	public TabInitializationException(String msg) {
		super(msg);
	}
	
}