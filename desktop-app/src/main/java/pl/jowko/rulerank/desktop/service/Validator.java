package pl.jowko.rulerank.desktop.service;

import pl.jowko.rulerank.desktop.feature.internationalization.Labels;
import pl.jowko.rulerank.desktop.feature.internationalization.LanguageService;

/**
 * This class serves as base for all validators in ruleRank application.<br>
 * It provides some utility methods and StringBuilder for errors.<br>
 * <br>
 * Created by Piotr on 2018-05-27
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
