package pl.jowko.jrank.feature.customfx;

/**
 * Created by Piotr on 2018-05-06.
 * This TextField accepts only integers on input.
 */
public class IntegerField extends CustomTextField {
	
	public static final String POSITIVE_INTEGER_PATTERN = "[0-9]*";
	public static final String NEGATIVE_INTEGER_PATTERN = "[-]?[0-9]*";
	
	public IntegerField() {
		super(NEGATIVE_INTEGER_PATTERN);
	}
	
	public IntegerField(String pattern) {
		super(pattern);
	}
	
}
