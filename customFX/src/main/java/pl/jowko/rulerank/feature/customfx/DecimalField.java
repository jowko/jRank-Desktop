package pl.jowko.rulerank.feature.customfx;

/**
 * This TextField accepts decimal numbers on input.<br>
 * By default, this fields have char limit of 10 000 characters.<br>
 *<br>
 * Created by Piotr on 2018-05-06.
 *
 * @see CustomTextField
 */
public class DecimalField extends CustomTextField {
	
	/**
	 * Positive decimal numbers only
	 */
	public static final String POSITIVE_DECIMAL_PATTERN = "[0-9]*(\\.[0-9]*)?";
	/**
	 * Positive and negative decimal numbers
	 */
	public static final String NEGATIVE_DECIMAL_PATTERN = "[-]?[0-9]*(\\.[0-9]*)?";
	/**
	 * Positive and negative decimal numbers supporting scientific notation
	 */
	public static final String NEGATIVE_SCIENTIFIC_DECIMAL_PATTERN = "[-]?[0-9]*(\\.[0-9]*([0-9][eE][0-9]*)?)?";
	
	/**
	 * Default constructor supporting negative and positive numbers with scientific notation
	 */
	public DecimalField() {
		super(NEGATIVE_SCIENTIFIC_DECIMAL_PATTERN);
	}
	
	/**
	 * Basic constructor with creates field supporting values for provided pattern.
	 * @param pattern for correct field values
	 */
	public DecimalField(String pattern) {
		super(pattern);
	}
	
}
