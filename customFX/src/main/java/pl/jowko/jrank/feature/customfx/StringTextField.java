package pl.jowko.jrank.feature.customfx;

/**
 * Created by Piotr on 2018-05-13.
 */
public class StringTextField extends CustomTextField {
	
	public static final String STRING_FIELD_PATTERN = "[A-Za-z_0-9]+";
	public static final String ALL_TEXT_PATTERN = ".*";
	
	public StringTextField() {
		super(ALL_TEXT_PATTERN);
	}
	
	public StringTextField(String pattern) {
		super(pattern);
	}
	
}
