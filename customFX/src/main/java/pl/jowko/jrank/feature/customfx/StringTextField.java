package pl.jowko.jrank.feature.customfx;

/**
 * Created by Piotr on 2018-05-13.
 * This TextField accepts all characters with matches with provided pattern.
 * By default, this fields have char limit of 10 000 characters.
 * @see CustomTextField
 */
public class StringTextField extends CustomTextField {
	
	/**
	 * Accepts text and numbers without special characters or whitespaces.
	 */
	public static final String ALPHANUMERIC_FIELD_PATTERN = "[A-Za-z_0-9]+";
	/**
	 * Accepts all text
	 */
	public static final String ALL_TEXT_PATTERN = ".*";
	
	/**
	 * Default constructor. It allows for all characters.
	 */
	public StringTextField() {
		super(ALL_TEXT_PATTERN);
	}
	
	/**
	 *
	 * @param pattern for correct field values
	 */
	public StringTextField(String pattern) {
		super(pattern);
	}
	
}
