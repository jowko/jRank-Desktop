package pl.jowko.jrank.desktop.feature.tabs.upper;

/**
 * Created by Piotr on 2018-05-21.
 * This exception is thrown when UpperTabsController tries to open directory as file.
 */
class WrongFileTypeException extends Exception {
	
	private static final long serialVersionUID = 1524917840711186367L;
	
	WrongFileTypeException(String msg) {
		super(msg);
	}
	
}
