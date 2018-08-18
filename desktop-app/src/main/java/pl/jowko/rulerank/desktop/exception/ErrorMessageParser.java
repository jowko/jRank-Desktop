package pl.jowko.rulerank.desktop.exception;

import static java.util.Objects.nonNull;

/**
 * This class converts exception to String with exception name, message and all names and messages from nested exception.<br>
 * It is used to display exceptions in more user friendly way.<br>
 * <br>
 * Created by Piotr on 2018-05-30
 */
public class ErrorMessageParser {
	
	private ErrorMessageParser() {}
	
	/**
	 * Extracts name and message from provided exception. <br>
	 * Also extracts names and messages from all nested exceptions.
	 * @param e with will be parsed
	 * @return parsed exception with most important information about error
	 */
	public static String parseException(Throwable e) {
		StringBuilder builder = new StringBuilder();
		
		builder.append("Cause: ")
				.append(e.getClass().getSimpleName())
				.append("  -  ")
				.append(e.getMessage());
		
		Throwable cause = e;
		do {
			cause = cause.getCause();
			if(nonNull(cause)) {
				builder.append("\nCaused by: ")
						.append(e.getClass().getSimpleName())
						.append("  -  ")
						.append(cause.getMessage());
			}
		} while (nonNull(cause));
		
		return builder.toString();
	}
	
}
