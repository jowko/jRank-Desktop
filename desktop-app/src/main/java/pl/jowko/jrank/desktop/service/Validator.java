package pl.jowko.jrank.desktop.service;

import pl.jowko.jrank.desktop.feature.internationalization.Labels;
import pl.jowko.jrank.desktop.feature.internationalization.LanguageService;

/**
 * Created by Piotr on 2018-05-27
 * This class serves as base for all validators in ruleRank application.
 * It provides some utility methods and StringBuilder for errors.
 */
public abstract class Validator {
	
	protected LanguageService labels;
	protected StringBuilder errorMsg;
	
	protected Validator() {
		labels = LanguageService.getInstance();
		errorMsg = new StringBuilder();
	}
	
	/**
	 * Check if validator found any validation errors.
	 * @return true if provided properties are valid, false otherwise
	 */
	public boolean isValid() {
		return errorMsg.toString().isEmpty();
	}
	
	/**
	 * Gets validation messages when validation contains errors.
	 * @return all error messages in one String
	 */
	public String getErrorMessages() {
		return errorMsg.toString();
	}
	
	/**
	 * Appends error message with line end character.
	 * @see Labels
	 * @param labelCode from Labels class
	 */
	protected void appendError(String labelCode) {
		errorMsg.append(labels.get(labelCode))
				.append('\n');
	}
	
}
