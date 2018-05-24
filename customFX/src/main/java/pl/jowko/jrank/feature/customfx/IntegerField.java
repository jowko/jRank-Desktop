package pl.jowko.jrank.feature.customfx;

/**
 * Created by Piotr on 2018-05-06.
 * This TextField accepts only integers on input.
 * By default, this fields have char limit of 10 000 characters.
 * @see CustomTextField
 */
public class IntegerField extends CustomTextField {
	
	/**
	 * Accepts positive integers only.
	 */
	public static final String POSITIVE_INTEGER_PATTERN = "[0-9]*";
	/**
	 * Accepts positive and negative integers.
	 */
	public static final String NEGATIVE_INTEGER_PATTERN = "[-]?[0-9]*";
	
	/**
	 * Default constructor. It allows for both negative and positive integers.
	 */
	public IntegerField() {
		super(NEGATIVE_INTEGER_PATTERN);
	}
	
	/**
	 *
	 * @param pattern for correct field values
	 */
	public IntegerField(String pattern) {
		super(pattern);
	}
	
}
