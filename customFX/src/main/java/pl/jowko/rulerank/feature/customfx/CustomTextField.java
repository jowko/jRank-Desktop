package pl.jowko.rulerank.feature.customfx;

import javafx.scene.control.TextField;

import java.util.regex.Pattern;

import static java.util.Objects.isNull;

/**
 * This class provides base for custom text fields.<br>
 * It allows to set maximum char limit for TextField.<br>
 * It also allows to type only specific characters by using pattern.<br>
 * It is very similar to CustomTextArea class<br>
 *<br>
 * Created by Piotr on 2018-05-09.
 *
 * @see CustomTextArea
 */
public class CustomTextField extends TextField {
	
	/**
	 * All characters are accepted.
	 */
	public static final String ALL_TEXT_PATTERN = ".*";
	
	private Pattern pattern;
	private int charLimit;
	
	/**
	 * Default constructor for this class.
	 * It allows all characters to be typed in with char limit of 10 000 characters.
	 */
	CustomTextField() {
		this(ALL_TEXT_PATTERN);
	}
	
	/**
	 * Basic constructor for this class.
	 * It compiles pattern and sets default char limit to 10 000 characters.
	 * @param pattern for correct field values
	 */
	CustomTextField(String pattern) {
		this.pattern = Pattern.compile(pattern);
		charLimit = 10_000;
	}
	
	/**
	 * @param pattern for correct field values
	 * @param charLimit for maximum allowed characters count
	 */
	CustomTextField(String pattern, int charLimit) {
		this.pattern = Pattern.compile(pattern);
		this.charLimit = charLimit;
	}
	
	public void setPattern(String pattern) {
		this.pattern = Pattern.compile(pattern);
	}
	
	public void setCharLimit(int charLimit) {
		this.charLimit = charLimit;
	}
	
	/**
	 * Replaces text with new one typed by user.
	 * Method is called every time when user types characters.
	 * It validates text for maximum allowed characters and for configured pattern
	 * @param start index provided by JavaFX
	 * @param end index provided by JavaFX
	 * @param text provided by JavaFX, with was typed by user
	 */
	@Override
	public void replaceText(int start, int end, String text) {
		if(getCurrentText().length() > charLimit && !"".equals(text))
			return;
		
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
