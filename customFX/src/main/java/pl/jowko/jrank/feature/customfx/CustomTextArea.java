package pl.jowko.jrank.feature.customfx;

import javafx.scene.control.TextArea;

import java.util.regex.Pattern;

import static java.util.Objects.isNull;

/**
 * Created by Piotr on 2018-05-13.
 * This class provides base for custom text area fields.
 * It allows to type only specific characters by using pattern.
 * It is very similar to CustomTextField class.
 * @see CustomTextField
 */
public class CustomTextArea extends TextArea {
	
	/**
	 * All characters are accepted.
	 */
	public static final String ALL_TEXT_PATTERN = ".*";
	
	private Pattern pattern;
	
	/**
	 * Default constructor with accepts all characters to field.
	 */
	public CustomTextArea() {
		this(ALL_TEXT_PATTERN);
	}
	
	/**
	 * Basic constructor for this class.
	 * @param pattern for correct field values
	 */
	public CustomTextArea(String pattern) {
		this.pattern = Pattern.compile(pattern);
	}
	
	public void setPattern(String pattern) {
		this.pattern = Pattern.compile(pattern);
	}
	
	/**
	 * Replaces text with new one typed by user.
	 * Method is called every time when user types characters
	 * @param start index provided by JavaFX
	 * @param end index provided by JavaFX
	 * @param text provided by JavaFX, with was typed by user
	 */
	@Override
	public void replaceText(int start, int end, String text) {
		if (validate(start, text)) {
			super.replaceText(start, end, text);
		}
	}
	
	/**
	 * Replaces selected text with new one.
	 * Integer.MAX_VALUE "flag" is used to differentiate this case from situation when first char is replaced(with needs special handling)
	 * @param text from JavaFX
	 */
	@Override
	public void replaceSelection(String text) {
		if (validate(Integer.MAX_VALUE, text)) {
			super.replaceSelection(text);
		}
	}
	
	/**
	 * Validate, if current text concatenated with typed text fulfills pattern.
	 * First element in field needs special handling.
	 * TODO this methods could work for more advanced cases, like replacing chars in the middle of text
	 * TODO but with simple patterns used in ruleRank, this problem doesn't exists
	 * @param start from JavaFX, with indicate position of edited text
	 * @param text from JavaFX, with was typed by user
	 * @return true if text fulfills pattern requirement, false otherwise
	 */
	private boolean validate(int start, String text) {
		String currentText = getCurrentText();
		if(start == 0) {
			return pattern.matcher(text + currentText).matches();
		} else {
			return pattern.matcher(currentText + text).matches();
		}
	}
	
	private String getCurrentText() {
		return isNull(getText()) ? "" : getText();
	}
	
}
