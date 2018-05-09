package pl.jowko.jrank.feature.customfx;

/**
 * Created by Piotr on 2018-05-06.
 * This TextField accepts decimal numbers on input.
 */
public class DecimalField extends NumberField {
	
	public static final String POSITIVE_DECIMAL_PATTERN = "[0-9]*(\\.[0-9]*)?";
	public static final String NEGATIVE_DECIMAL_PATTERN = "[-][0-9]*(\\.[0-9]*)?";
	
	public DecimalField() {
		super(POSITIVE_DECIMAL_PATTERN);
	}
	
	public DecimalField(String pattern) {
		super(pattern);
	}
	
}
